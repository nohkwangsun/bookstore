#!/bin/bash

WORKING_DIR=$(cd $(dirname "$0")/.. && pwd)

pid_file="${WORKING_DIR}/bin/gotty.pid"
pid="$(cat "$pid_file")"
if [ -f "$pid_file" ]; then
  if [[ $(ps -p "$pid" -o comm=) =~ gotty ]]; then
    echo "Stopping the damazon with gotty"
    kill "${pid}" && rm "${WORKING_DIR}/bin/gotty.pid"

    for i in {1..10}; do
      if [[ $(ps -p "${pid}" -o comm=) != "gotty" ]]; then
        break
      fi
      sleep 0.5
    done
    echo "Stopped the damazon with gotty"

  else
    echo "Failed to stop"
  fi

else
  echo "Failed to stop"
fi