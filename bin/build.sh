cd ..
find . -name "*.java" > sources.txt
javac -d bookstore/out @sources.txt