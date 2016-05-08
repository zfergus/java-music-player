#!/bin/bash          

if [ ! -d bin ]
then
	mkdir bin
fi

javac -XDignore.symbol.file -d bin ./src/*.java

