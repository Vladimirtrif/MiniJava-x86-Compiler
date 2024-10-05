#!/bin/bash
if [ "$OSTYPE" == "msys" ]; then
  java -cp "build/classes;lib/java-cup-11b.jar" MiniJava "$@"
else
  java -cp build/classes:lib/java-cup-11b.jar MiniJava "$@"
fi
