//// filepath: c:\Users\a.sanmartin\Desktop\IsLeapYearVisual\isLeapYear\src\test\java\isipv4valid\IPAddressCheckerTest.java
package isipv4valid;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.stream.Stream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public class GPTo1preTest {

    private static IPAddressChecker checker;

    @BeforeAll
    static void setUpBeforeClass() {
        checker = new IPAddressChecker();
    }

    @AfterAll
    static void tearDownAfterClass() {
        // Cleanup if needed
    }

    @BeforeEach
    void setUp() {
        // Setup before each test
    }

    @AfterEach
    void tearDown() {
        // Cleanup after each test
    }

    // -------------------------------------------------------------
    // WHITE BOX / BLACK BOX TESTS FOR isValidIPV4Part(String s)
    // -------------------------------------------------------------

    @DisplayName("Testing isValidIPV4Part() with various inputs for condition/decision and black-box coverage.")
    @Order(1)
    @ParameterizedTest(name = "Input: \"{0}\", Expected: {1}")
    @MethodSource("provideTestCasesForIsValidIPV4Part")
    void testIsValidIPV4Part(String input, boolean expected) {
        try {
            boolean actual = checker.isValidIPV4Part(input);
            assertEquals(expected, actual, () -> "Failed for input: " + input);
        } catch (Exception e) {
            fail("An unexpected exception was thrown for input: " + input);
        }
    }

    static Stream<org.junit.jupiter.params.provider.Arguments> provideTestCasesForIsValidIPV4Part() {
        return Stream.of(
            // length > 3
            org.junit.jupiter.params.provider.Arguments.of("9999", false),
            // contains non-digit
            org.junit.jupiter.params.provider.Arguments.of("abc", false),
            org.junit.jupiter.params.provider.Arguments.of("12a", false),
            // leading zero check
            org.junit.jupiter.params.provider.Arguments.of("0", true),
            org.junit.jupiter.params.provider.Arguments.of("01", false),
            org.junit.jupiter.params.provider.Arguments.of("000", false),
            // parse to int > 255
            org.junit.jupiter.params.provider.Arguments.of("256", false),
            org.junit.jupiter.params.provider.Arguments.of("255", true),
            // normal case within range
            org.junit.jupiter.params.provider.Arguments.of("127", true),
            // large number that causes NumberFormatException
            org.junit.jupiter.params.provider.Arguments.of("9999999999", false)
        );
    }

    // -------------------------------------------------------------
    // WHITE BOX / BLACK BOX TESTS FOR isValidIPV4(String ipStr)
    // -------------------------------------------------------------

    @DisplayName("Testing isValidIPV4() with various inputs for condition/decision and black-box coverage.")
    @Order(2)
    @ParameterizedTest(name = "Input: \"{0}\", Expected: {1}")
    @MethodSource("provideTestCasesForIsValidIPV4")
    void testIsValidIPV4(String input, int expected) {
        try {
            int actual = checker.isValidIPV4(input);
            assertEquals(expected, actual, () -> "Failed for input: " + input);
        } catch (Exception e) {
            fail("An unexpected exception was thrown for input: " + input);
        }
    }

    static Stream<org.junit.jupiter.params.provider.Arguments> provideTestCasesForIsValidIPV4() {
        return Stream.of(
            // null input
            org.junit.jupiter.params.provider.Arguments.of(null, 0),
            // less than 3 dots
            org.junit.jupiter.params.provider.Arguments.of("1.2.3", 0),
            // more than 3 dots
            org.junit.jupiter.params.provider.Arguments.of("1.2.3.4.5", 0),
            // empty tokens (consecutive dots, or trailing dot)
            org.junit.jupiter.params.provider.Arguments.of("1..2.3", 0),
            org.junit.jupiter.params.provider.Arguments.of("1.2.3.", 0),
            // invalid token inside
            org.junit.jupiter.params.provider.Arguments.of("1.2.300.4", 0),
            org.junit.jupiter.params.provider.Arguments.of("9999999999.2.3.4", 0),
            org.junit.jupiter.params.provider.Arguments.of("IPTest", 0),
            // valid IP
            org.junit.jupiter.params.provider.Arguments.of("1.2.3.4", 1),
            org.junit.jupiter.params.provider.Arguments.of("255.255.255.255", 1),
            org.junit.jupiter.params.provider.Arguments.of("0.0.0.0", 1),
            // leading zeros in part
            org.junit.jupiter.params.provider.Arguments.of("01.2.3.4", 0),
            // boundary out of range
            org.junit.jupiter.params.provider.Arguments.of("256.255.255.255", 0)
        );
    }
}