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

    private void runScannerTestCase(String testCaseName) {
        try {
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

    /*
        Test for the countdown program
    */
    @Test
    public void testCountdown() {
        runScannerTestCase("Countdown");
    }
}