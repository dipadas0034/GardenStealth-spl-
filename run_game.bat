@echo off
title Garden Stealth Game
cd /d "%~dp0"
echo Compiling and Starting Garden Stealth Game...
javac -d bin -sourcepath src src/GardenStealth.java src/javaapplication/*.java src/entity/*.java src/collision/*.java
java -cp "bin;src" GardenStealth
pause
