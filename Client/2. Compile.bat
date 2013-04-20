@echo off
title Compiler
:build
cls
"C:\Program Files (x86)\Java\jdk1.7.0_21\bin\javac.exe" *.java
pause
goto :build