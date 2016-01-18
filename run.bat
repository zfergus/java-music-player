echo off

if not exist ./bin/	mkdir bin

javac -XDignore.symbol.file -d bin ./src/*.java 
java -cp ./bin PlayMusic