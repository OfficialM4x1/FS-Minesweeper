#!/bin/bash
# This file created with the help of ChatGPT 
# please use this file when you have a Mac
# Ensure the script is executed from the directory where the script itself is located
cd "$(dirname "$0")"

# Run the JAR file with the necessary classpath and enable preview features
java --enable-preview -jar FS-Minesweeper.jar
