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

# compile $1 to $2, where $1 is .dcf and $2 is .s
function compile {
  time "$RUNNER" --target=assembly --opt=all "$1" -o "$2" >&2
  green "Done compiling '$(pretty "$1")' -> '$(pretty "$2")'"
}

export -f compile

if ! build; then
  red 'build failed'
  exit 1
fi

mkdir -p "$ROOT/tests/optimizer/"{asm,bin,output}

if [[ ${#1} -eq 0 ]]; then  # no args provided
  find "$ROOT/tests/optimizer/dcf" -type f -name '*.dcf'
else
  for DCF_FILE in "$@"; do 
    echo "$DCF_FILE"
  done
fi |
  while read DCF_FILE; do
    green "Compiling '$(pretty "$DCF_FILE")'..."
    echo "$DCF_FILE"
  done |
  "$ROOT/tests/parallel" "compile {} '$ROOT/tests/optimizer/asm/{/.}.s'"
