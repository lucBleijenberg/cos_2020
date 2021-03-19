# Compilers and Operating systems

This is a base project for the assignment of Compilers and Operating Systems 2019-2020.

The project has support for reading your files, assembling Jasmin bytecode into classes and even running them automatically in JUnit-tests.
The actual code generation is of course missing still (since that is the whole point of the assignment) and you should replace the grammar (and associated lexer and parser) with one of your own.

You can adapt this code in any way you see fit.

## Edit (17-03-2021)

How to run:

- Run the class `nl.saxion.cos.Compiler` with a `.lr` file as argument.  
(for example `testFiles/Main.lr`)
  
- Run the generated `.class` file.    
(for example `java -cp testFiles Main`)
