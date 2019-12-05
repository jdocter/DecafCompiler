#!/usr/bin/env bash
#
# Run tests and benchmarks them using hyperfine, assuming the following:
# 1. you are running this script from `tests/derby/`
# 2. all executables to be tested are compiled and in `bin/`
#   - implies you already ran `dcf-to-asm.sh` and/or `asm-to-bin.sh`
# 3. executables output data to files under `output/`
# 4. if the actual output is written to `output/foo.txt`, then the expected
#    output is `expected/foo.txt`
#
# USAGE
#   ./test.sh

source "$(git rev-parse --show-toplevel)/tests/source.sh"

# $1 -> executable to check
function check {
  declare -r EXEC="$1"

  if "$EXEC" &> /dev/null; then
    declare -r HEAD="$ROOT/tests/derby"

    declare -r OUTPUT="$HEAD/output/output.ppm"
    declare -r EXPECTED="$HEAD/expected/output.ppm"

    if diff "$EXPECTED" "$OUTPUT" &> /dev/null; then
      green "\nPASSED; benching '$(pretty "$EXEC")'"
      
      "$ROOT/tests/hyperfine" "$EXEC"
    else
      red "OUTPUT MISMATCH for '$EXEC' : '$(pretty "$EXPECTED")' != '$(pretty "$OUTPUT")'"
    fi

  else
    red "RUNTIME ERROR '$(pretty "$EXEC")'"
  fi
}

export -f check

find "$ROOT/tests/derby/bin" -type f |
  while read f; do
    check "$f"
  done
