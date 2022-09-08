#!/bin/bash

WORKING_DIR=$(cd $(dirname "$0")/.. && pwd)

cd "${WORKING_DIR}"
JAVA_COMMAND="java -Dfile.encoding=UTF-8 -classpath ${WORKING_DIR}/bookstore/out com.onlinejava.project.bookstore.Main"

if [ ! -x $(command -v gotty >> /dev/null) ]; then
  eval "${JAVA_COMMAND}"
else
  nohup gotty -w -p 10000 --title-format Damazon ${JAVA_COMMAND} &>> "${WORKING_DIR}"/bin/gotty.out &
  pid="$!"
  echo $pid > "${WORKING_DIR}"/bin/gotty.pid
fi
