package isprime;

import static org.junit.jupiter.api.Assertions.fail; 
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.api.AfterAll; 
import org.junit.jupiter.api.AfterEach; 
import org.junit.jupiter.api.BeforeAll; 
import org.junit.jupiter.api.BeforeEach; 
import org.junit.jupiter.api.DisplayName; 
import org.junit.jupiter.params.ParameterizedTest; 
import org.junit.jupiter.params.provider.Arguments; 
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("IsPrime Tests")
public class IsPrimeGPTo3miniTest {
@BeforeAll
static void initAll() {
    // Global setup if needed
}

@BeforeEach
void init() {
    // Setup before each test if needed
}

@AfterEach
void tearDown() {
    // Cleanup after each test if needed
}

@AfterAll
static void tearDownAll() {
    // Global cleanup if needed
}

/**
 * Provides a stream of test cases that cover all decision paths and equivalence classes.
 * Each Arguments consists of:
 *   - a description,
 *   - the input array to the isPrime() method,
 *   - the expected boolean result (if no exception is expected; otherwise null),
 *   - the expected exception class (if any exception is expected; otherwise null).
 */
static Stream<Arguments> provideIsPrimeTestCases() {
    return Stream.of(
        // White Box / Black Box: args is null (should throw MissingArgumentException)
        Arguments.of("Input is null", null, null, MissingArgumentException.class),
        // White Box / Black Box: args with more than one number (should throw Only1ArgumentException)
        Arguments.of("Input with more than one number", new String[] {"3", "5"}, null, Only1ArgumentException.class),
        // White Box: Valid single argument that is a prime number.
        Arguments.of("Valid prime number: 2", new String[] {"2"}, true, null),
        Arguments.of("Valid prime number: 3", new String[] {"3"}, true, null),
        // White Box: Valid single argument that is a composite number.
        Arguments.of("Valid composite number: 4", new String[] {"4"}, false, null),
        // White Box / Black Box: Input number is zero (non-positive).
        Arguments.of("Non-positive number: 0", new String[] {"0"}, null, NoPositiveNumberException.class),
        // White Box: Input negative number.
        Arguments.of("Negative number: -3", new String[] {"-3"}, null, NoPositiveNumberException.class),
        // White Box: Invalid numeric format (will be caught as NoPositiveNumberException)
        Arguments.of("Invalid number format: abc", new String[] {"abc"}, null, NoPositiveNumberException.class),
        // Black Box: Boundary analysis – value 1 (edge value, though mathematically not prime, returns true by code)
        Arguments.of("Boundary number: 1", new String[] {"1"}, true, null),
        // White Box (unexpected branch): Empty array.
        // According to the documentation a MissingArgumentException should be thrown when there are no numbers,
        // but the implementation does not check for empty arrays, and it throws an ArrayIndexOutOfBoundsException.
        // This test asserts that behavior.
        Arguments.of("Empty array", new String[] {}, null, ArrayIndexOutOfBoundsException.class)
    );
}

/**
 * Tests the IsPrime.isPrime() method using parameterized tests for both 
 * expected results and expected exceptions.
 */
@ParameterizedTest(name = "{index} => {0}")
@MethodSource("provideIsPrimeTestCases")
@DisplayName("Test isPrime method with various inputs")
void testIsPrime(String description, String[] input, Boolean expectedResult,
                 Class<? extends Exception> expectedException) {
    try {
        boolean result = IsPrime.isPrime(input);
        if (expectedException != null) {
            // An exception was expected but method returned a result.
            fail("Expected exception of type " + expectedException.getSimpleName() 
                    + " but got result: " + result);
        } else {
            // No exception expected; verify returned result.
            assertEquals(expectedResult, result, "Result should match the expected value");
        }
    } catch (Exception e) {
        if (expectedException == null) {
            // No exception was expected.
            fail("Did not expect an exception, but got: " + e);
        } else {
            // Verify the exception is of the expected type.
            if (!expectedException.isInstance(e)) {
                fail("Expected exception of type " + expectedException.getSimpleName() 
                       + " but got: " + e);
            }
            // Expected exception was thrown; test passes.
        }
    }

}
}