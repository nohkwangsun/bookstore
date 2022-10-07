#!/bin/bash

WORKING_DIR=$(cd $(dirname "$0")/.. && pwd)
cd "${WORKING_DIR}"

docker build --build-arg STEP_DUMMY=$(date +%s) -t bookstore:0.1.0 .