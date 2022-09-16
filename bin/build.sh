#!/bin/bash

WORKING_DIR=$(cd $(dirname "$0")/.. && pwd)

cd "${WORKING_DIR}"

git stash
git pull

find bookstore/src -name "*.java" > sources.txt
javac -d bookstore/out @sources.txt
rm sources.txt