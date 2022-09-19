#!/bin/bash

WORKING_DIR=$(cd $(dirname "$0")/.. && pwd)

cd "${WORKING_DIR}"

git stash
git pull

rm -rf bookstore/out
find bookstore/src -name "*.java" > sources.txt
javac -d bookstore/out @sources.txt
rm sources.txt