@echo off
title Client Compiler
echo starting...
"C:\Program Files\Java\jdk1.7.0_25/bin/javac" -cp lib/*; -d bin -sourcepath src src/*.java
@pause