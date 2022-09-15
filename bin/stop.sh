#!/bin/bash

WORKING_DIR=$(cd $(dirname "$0")/.. && pwd)

pid_file="${WORKING_DIR}/bin/gotty.pid"
pid="$(cat "$pid_file")"
if [ -f "$pid_file" ]; then
  if [[ $(ps -p "$pid" -o comm=) =~ gotty ]]; then
    echo "Stopping gotty, damazon"
    kill "pid" && rm "${WORKING_DIR}/bin/gotty.pid"

    for i in {1..10}; do
      if [[ $(ps -p "${pid}" -o comm=) =~ "gotty" ]]; then
        sleep 0.5
        continue
      else
        echo "Stopped gotty, damazon"
        break
      fi
    done

  else
    echo "Fail to stop"
  fi
  echo "Fail to stop"
fi