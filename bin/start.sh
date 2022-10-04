#!/bin/bash

WORKING_DIR=$(cd $(dirname "$0")/.. && pwd)

cd "${WORKING_DIR}"
JAVA_COMMAND="./gradlew --console plain run"

if [ ! -x $(command -v gotty >> /dev/null) ]; then
  eval "${JAVA_COMMAND}"
else
  nohup gotty -w -p 10000 --title-format Damazon ${JAVA_COMMAND} &>> "${WORKING_DIR}"/bin/gotty.out &
  pid="$!"
  echo $pid > "${WORKING_DIR}"/bin/gotty.pid

  for i in {1..10}; do
    if [[ $(ps -p "${pid}" -o comm=) =~ "gotty" ]]; then
       break
    fi
    sleep 0.5
  done

  if [[ ! $(ps -p "${pid}" -o comm=) =~ "gotty" ]]; then
    echo "Failed to launch: the damazon with gotty"
    tail -10 "${WORKING_DIR}"/bin/gotty.out
  fi
fi
