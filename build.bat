@echo off
echo ====================================================
echo           COMPILING DSA PROBLEM TRACKER
echo ====================================================

if not exist bin mkdir bin

echo Compiling Java source files...
javac -d bin -sourcepath src src/com/dsatracker/model/*.java src/com/dsatracker/repository/*.java src/com/dsatracker/sort/*.java src/com/dsatracker/filter/*.java src/com/dsatracker/service/*.java src/com/dsatracker/ui/*.java src/com/dsatracker/util/*.java src/com/dsatracker/Main.java tests/com/dsatracker/TestRunner.java

if %ERRORLEVEL% EQU 0 (
    echo [SUCCESS] Compilation finished cleanly! Binary output saved to bin/
) else (
    echo [ERROR] Compilation failed. Please check Java errors above.
    exit /b %ERRORLEVEL%
)
