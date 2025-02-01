# MiniJava to x86_64 Compiler

This is a compiler that compiles MiniJava (a subset of Java) to x86_64.
The compiler uses CUP and JFlex for parsing. 
It was developed together with Jaechan Lee for UW CSE401 24au. 
Credit to Hal Perkins and all the TAs that created the curriculum and starter code that made this project possible.

You will need to have ant installed locally to build the compiler and
run the demo programs.  If you do not have ant installed, we strongly
suggest you install it using the package at https://ant.apache.org.
This applies particularly to MacOS users, where you should avoid using
the Homebrew package manager.  Although Homebrew does include ant,
it also tends to install its own copy of openjdk with a different
version than the one already installed on the system.  That can create
problems if it is a newer version and creates class files that cannot
be run with the regular version of Java.

The build.xml ant file supports building, running, and testing the demo
compiler scanning and parsing examples.  Look at it for details and use
it as a start for your own project's build sequence.  You can find more
detailed documentation about testing your code in test/README.txt.

Sources: AST classes and SampleMiniJavaPrograms from the Appel /
Palsberg MiniJava project.  Some code and ideas borrowed from an earlier
UW version by Craig Chambers with modifications by Jonathan Beall and
Hal Perkins.  Updates to include more recent releases of JFlex and CUP
by Hal Perkins, Jan. 2017.  Updates to use recent JFlex ComplexSymbol
class by Nate Yazdani, April 2018.  Updates to improve testing support
by Aaron Johnston, Sep. 2019.  Update to JFlex 1.8.2 by Hal Perkins,
Oct. 2020.
