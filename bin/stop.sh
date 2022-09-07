#!/bin/bash

WORKING_DIR=$(cd $(dirname "$0")/.. && pwd)

if [ -f "${WORKING_DIR}/bin/gotty.pid" ]; then
  kill $(cat "${WORKING_DIR}"/bin/gotty.pid)
fi