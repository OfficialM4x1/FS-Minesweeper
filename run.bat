@echo off
setlocal

rem Set the path to your JDK installation if necessary
rem set JAVA_HOME=C:\path\to\jdk

rem Navigate to the directory where your JAR file and libraries are located
cd /d %~dp0

rem Run the JAR file with the necessary classpath and enable preview features
java --enable-preview -cp lib/sqlite-jdbc-3.34.0.jar;FS-Minesweeper.jar src.MainFrame

endlocal
