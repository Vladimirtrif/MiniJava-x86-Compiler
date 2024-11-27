#!/bin/bash

# Check if the file path is provided as an argument
if [ "$#" -ne 1 ]; then
    echo "Usage: $0 <file-path>"
    exit 1
fi

# Extract the input file path and filename without extension
INPUT_FILE="$1"
BASENAME=$(basename -- "$INPUT_FILE")
FILENAME="${BASENAME%.*}"

# Run the MiniJava compiler script
./minijava.sh "$INPUT_FILE" > "$FILENAME.s"

# Check if the assembly file was successfully generated
if [ ! -s "$FILENAME.s" ]; then
    echo "Error: Assembly file $FILENAME.s was not generated."
    exit 2
fi

# Compile the assembly file into an executable
gcc -g -o "$FILENAME" "$FILENAME.s" "src/runtime/boot.c"

# Check if the compilation was successful
if [ $? -ne 0 ]; then
    echo "Error: Compilation failed."
    exit 3
fi

# Run the executable
./"$FILENAME"
