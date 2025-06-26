package isprime;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class GPTo1previewTest {

    @BeforeAll
    static void setUpAll() {
        // Initialization for all tests if needed
    }

    @AfterAll
    static void tearDownAll() {
        // Clean-up for all tests if needed
    }

    @BeforeEach
    void setUp() {
        // Initialization before each test if needed
    }

    @AfterEach
    void tearDown() {
        // Clean-up after each test if needed
    }

    @DisplayName("Parameterized Test for Valid Single Argument: Prime/Non-Prime")
    @ParameterizedTest(name = "Input: {0}, Expected isPrime: {1}")
    @CsvSource({
        "1, true",
        "2, true",
        "3, true",
        "4, false",
        "9, false"
    })
    void testIsPrimeWithValidArgs(String input, boolean expected) {
        try {
            boolean result = IsPrime.isPrime(new String[]{input});
            assertEquals(expected, result, 
                () -> "Expected: " + expected + " but got different result for input: " + input);
        } catch (Exception e) {
            fail("No exception was expected, but got: " + e);
        }
    }

    @Test
    @DisplayName("Null Args => MissingArgumentException")
    void testNullArgs() {
        assertThrows(MissingArgumentException.class, () -> IsPrime.isPrime(null));
    }

    @Test
    @DisplayName("Multiple Args => Only1ArgumentException")
    void testMultipleArgs() {
        String[] inputs = {"1", "2"};
        assertThrows(Only1ArgumentException.class, () -> IsPrime.isPrime(inputs));
    }

        @Test
    @DisplayName("Empty Args => ArrayIndexOutOfBoundsException")
    void testEmptyArgs() {
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> IsPrime.isPrime(new String[]{}));
    }

    @Test
    @DisplayName("Negative Number => NoPositiveNumberException")
    void testNegativeNumber() {
        assertThrows(NoPositiveNumberException.class, () -> IsPrime.isPrime(new String[]{"-1"}));
    }

    @Test
    @DisplayName("Zero => NoPositiveNumberException")
    void testZeroNumber() {
        assertThrows(NoPositiveNumberException.class, () -> IsPrime.isPrime(new String[]{"0"}));
    }

    @Test
    @DisplayName("Non-Numeric => NoPositiveNumberException")
    void testNonNumeric() {
        assertThrows(NoPositiveNumberException.class, () -> IsPrime.isPrime(new String[]{"abc"}));
    }
}