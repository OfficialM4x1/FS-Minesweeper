@echo off
setlocal
rem this file was created with the help of ChatGPT
rem If you are a windows user please use this file to start the game 
rem Navigate to the directory where your JAR file and libraries are located
cd /d %~dp0

rem Run the JAR file with the necessary classpath and enable preview features
java --enable-preview -cp lib/sqlite-jdbc-3.34.0.jar;FS-Minesweeper.jar src.MainFrame

endlocal