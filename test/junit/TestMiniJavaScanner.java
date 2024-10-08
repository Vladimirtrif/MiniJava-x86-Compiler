import java.io.*;
import java.nio.file.Path;

import static org.junit.Assert.*;
import org.junit.Test;


/*
    See TestDemoLanguageScanner for documentation / starter code and the testing utility docs on CSE401 website
*/
public class TestMiniJavaScanner {

    public static final String TEST_FILES_LOCATION = "test/resources/MiniJavaScanTests/";
    public static final String TEST_FILES_INPUT_EXTENSION = ".java";
    public static final String TEST_FILES_EXPECTED_EXTENSION = ".expected";

    /*
        You may be able to reuse this private helper method for your own
        testing of the MiniJava scanner.
    */
    private void runScannerTestCase(String testCaseName) {
        try {
            //Testing utility code, doesn't work right now
            new MiniJavaTestBuilder()
                    .assertSystemOutMatchesContentsOf(
                            Path.of(TEST_FILES_LOCATION,
                                    testCaseName + TEST_FILES_EXPECTED_EXTENSION))
                    .testCompiler("-S", TEST_FILES_LOCATION + testCaseName + TEST_FILES_INPUT_EXTENSION);
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    /*
        Short test for a simple math program that prints out to system
    */
    @Test
    public void testSomeMath() {
        runScannerTestCase("SomeMath");
    }
}