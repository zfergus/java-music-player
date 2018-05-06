#!/bin/bash

if [ ! -d bin ]
then
	mkdir bin
fi

javac -d bin ./src/*.java
