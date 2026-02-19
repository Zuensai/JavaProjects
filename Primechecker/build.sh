#!/bin/bash
echo "Compiling .."
javac -d target src/main/java/com/primechecker/PrimeChecker.java
echo "Running .."
java -cp target com.primechecker.PrimeChecker $1 

# echo "-- TESTS --"
# java -cp target com.primechecker.PrimeChecker -1
# java -cp target com.primechecker.PrimeChecker 0
# java -cp target com.primechecker.PrimeChecker 1

