Java 21 information 24au

The lab machines have Java 21 installed, and we will use that to run
and test your projects.  If your local machine is using Java 11 or
newer you probably should be fine, but we recommend that you update to
Java 21 unless you have a reason not to do that.  If, for some reason,
you have a more recent version of Java after Java 21 installed, you
should be very careful to be sure that your code works using only
language features and libraries available in Java 21.  Your IDE (for
instance, IntelliJ) should be able to help with this by using
appropriate language compatibility level settings.

If you have a Windows machine, before installing Java 21, we *strongly*
recommend that you use the Windows control panels to remove all older
versions of Java, which will minimize the potential for problems or
confusion that can happen when multiple versions of Java are installed. 
If you have a Mac, you normally do not need to remove older versions of
Java, but, if you wish, open a terminal window and use the command
"cd /Library/Java/JavaVirtualMachines/" to find the directory where Java
is located.  Then use "sudo rm -rf ..." to remove the older Java
installations that you no longer want.

Once that is done, use the openjdk link on the CSE 401/M501 resources
web page and download the appropriate Adoptium installer for Java 21. 
Run the installer and, particularly on windows, select all the options
to update environment variables and perform a clean install to get a
version that that should have minimal problems.
