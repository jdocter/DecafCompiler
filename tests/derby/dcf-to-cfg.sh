#!/usr/bin/env bash
#
# if no argument is provided
#   all decaf files under `dcf/` will be compiled to `.s` files under `asm/` with matching names
# else 
#   only the specified files provided as arguments will be compiled to `asm/`
#
# USAGE
#   ./dcf-to-asm.sh                # compile all in `dcf/` to `asm/`
#   ./dcf-to-asm.sh "$1" "$2" ...  # compile $1, $2, ... to `dcf/`
#
# EXAMPLE
#   - ./dcf-to-asm.sh 'dcf/noise_median.dcf' 'dcf/saman_sl.dcf'
#     will compile those two decaf files and create the following files, respectively
#     - `asm/noise_median.s`
#     - `asm/saman_sl.s`

source "$(git rev-parse --show-toplevel)/tests/source.sh"

# $1 is the decaf file to compile
# $2 is the dir in which to create asm files
# 
# $1 gets compiled to unoptimized asm and optimized asm in $2/
function compile {
  declare -r ASM_DIR=$(sed -E 's/\/+$//' <<< "$2")
  declare -r FILENAME=$(basename "$1")
  time "$RUNNER" --target=cfg           "$1" -o "$ASM_DIR/$FILENAME.unoptimized.s" >&2
  time "$RUNNER" --target=cfg --opt=all "$1" -o "$ASM_DIR/$FILENAME.optimized.s" >&2
  green "Done compiling '$(pretty "$1")' -> '$(pretty "$2")'"
}

export -f compile

if ! build; then
  red 'build failed'
  exit 1
fi

mkdir -p "$ROOT/tests/derby/"{asm,cfg,bin,output}

if [[ ${#1} -eq 0 ]]; then  # no args provided
  find "$ROOT/tests/derby/dcf" -type f -name '*.dcf'
else
  for DCF_FILE in "$@"; do 
    echo "$DCF_FILE"
  done
fi |
  while read DCF_FILE; do
    green "Compiling '$(pretty "$DCF_FILE")'..."
    echo "$DCF_FILE"
  done |
  "$ROOT/tests/parallel" "compile {} '$ROOT/tests/derby/cfg'"
