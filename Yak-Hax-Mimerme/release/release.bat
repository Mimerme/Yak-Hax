@ECHO off
if not exists %UTILS_PATH%\yhack\values.txt echo. 2>values.txt
java -jar %UTILS_PATH%\yhack\yhack.jar %1 %2 %3 %4 %5 %6 %7 %8