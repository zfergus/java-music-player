@echo off

if not exist ./bin/	mkdir bin

javac -d bin ./src/*.java 
java -cp ./bin PlayMusic
