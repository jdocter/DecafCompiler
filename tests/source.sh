#!/usr/bin/env bash

function green {
  printf "\e[32m$@\e[m\n" >&2
}

function red {
  printf "\e[31m$@\e[m\n" >&2
}

function build {
  ( cd "$ROOT" && "$BUILDER" || exit 1 )
}

function remove-ext {
  sed -E 's/\.[^\.]+$//' <<< "$1"
}

function pretty {
  echo "$(basename "$(dirname "$1")")/$(basename "$1")"
}

declare -r ROOT=$(git rev-parse --show-toplevel)
declare -r BUILDER="$ROOT/build.sh"
declare -r RUNNER="$ROOT/run.sh"

export -f green red build remove-ext pretty
export ROOT BUILDER RUNNER
