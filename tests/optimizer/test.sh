#!/usr/bin/env bash
#
# Run tests, assuming the following:
# 1. all executables to be tested are compiled and in `bin/`
#   - implies you already ran `dcf-to-asm.sh` and/or `asm-to-bin.sh`
# 2. executables output data to files under `output/`
# 3. if the actual output is written to `output/foo.txt`, then the expected
#    output is `expected/foo.txt`
#
# USAGE
#   ./test.sh

source "$(git rev-parse --show-toplevel)/tests/source.sh"

# $1 -> executable to check
function check {
  declare -r EXEC="$1"

  if ( cd "$ROOT/tests/optimizer/" && "$EXEC" &> /dev/null ); then
    declare -r HEAD="$ROOT/tests/optimizer"
    declare -r TAIL="$(basename "$EXEC").pgm"

    declare -r OUTPUT="$HEAD/output/$TAIL"
    declare -r EXPECTED="$HEAD/expected/$TAIL"

    if diff "$EXPECTED" "$OUTPUT" &> /dev/null; then
      green "PASSED '$(pretty "$EXEC")'"
    else
      red "OUTPUT MISMATCH '$(pretty "$EXPECTED")' != '$(pretty "$OUTPUT")'"
    fi

  else
    red "RUNTIME ERROR '$(pretty "$EXEC")'"
  fi
}

export -f check

find "$ROOT/tests/optimizer/bin" -type f |
  "$ROOT/tests/parallel" 'check {}'
