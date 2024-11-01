#!/bin/bash

# Navigate to project directory
cd "$(dirname "$0")"

# Check if the lib directory and required JAR files are present
if [ ! -d "lib" ]; then
    echo "Lib directory not found. Please include the lib directory with required JAR files."
    exit 1
fi

# Create necessary directories
mkdir -p out/production
mkdir -p out/test

# Find all Java files and compile them
find src/main/java -name "*.java" > sources.txt
find src/test/java -name "*.java" > test_sources.txt

# Compile production code
javac -d out/production @sources.txt

# Compile test code
javac -d out/test -cp "out/production:lib/*" @test_sources.txt

# Run the tests using JUnit Platform Console Launcher
java -cp "out/test:out/production:lib/*" org.junit.platform.console.ConsoleLauncher --scan-classpath

# Clean up temporary files and directories
rm -f sources.txt test_sources.txt
rm -rf out
