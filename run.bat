@echo off
if not exist bin (
    call build.bat
)

echo Starting DSA Problem Tracker...
java -cp bin com.dsatracker.Main
