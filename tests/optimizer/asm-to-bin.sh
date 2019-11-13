#!/usr/bin/env bash
#
# Identical to `dcf-to-asm.sh` except
# - compiled executables are placed in `bin/`
# - grabs assembly files from `asm/` to `bin/`

source "$(git rev-parse --show-toplevel)/tests/source.sh"

# compile $1 -> $2, where $1 is .s and $2 is exec
function compile {
  # `gcc -no-pie -L lib/ -l6035 -lpthread "$1" -o "$2"` DOES NOT work; haven't look into why
  time gcc -no-pie "$1" -o "$2" -L lib/ -l6035 -lpthread
  green "Done compiling '$(pretty "$1")' -> '$(pretty "$2")'"
}

export -f compile

mkdir -p "$ROOT/tests/optimizer/"{asm,bin,output}

if [[ ${#1} -eq 0 ]]; then  # no args provided
  find "$ROOT/tests/optimizer/asm" -type f -name '*.s'
else
  for ASM_FILE in "$@"; do
    echo "$ASM_FILE"
  done
fi |
  while read ASM_FILE; do
    green "Compiling '$(pretty "$ASM_FILE")'..."
    echo "$ASM_FILE"
  done |
  "$ROOT/tests/parallel" "compile {} '$ROOT/tests/optimizer/bin/{/.}'"
