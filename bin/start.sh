cd ..
command -v gotty
if [ $? -eq "0" ]
then
  java -Dfile.encoding=UTF-8 -classpath bookstore/out com.onlinejava.project.bookstore.Main
else
  nohup gotty -w -p 10000 java -Dfile.encoding=UTF-8 -classpath bookstore/out com.onlinejava.project.bookstore.Main &>> h2.out &
  pid="$!"
  echo $pid > gotty.pid
fi