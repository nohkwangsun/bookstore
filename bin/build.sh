#!/bin/bash

WORKING_DIR=$(cd $(dirname "$0")/.. && pwd)

cd "${WORKING_DIR}"

git stash
git pull

./gradlew clean build