package isleapyear;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Test class for {@link isleapyear.LeapYear} providing:
 * - White Box (Condition/Decision) coverage
 * - Black Box (Equivalence Partitioning and Boundary Value Analysis) coverage
 * Ensuring 100% coverage of lines, conditions, and decisions.
 */
public class GPTo1previewTest {

    private LeapYear leapYear;

    @BeforeAll
    static void setUpAllTests() {
        // Global initialization if needed
    }

    @AfterAll
    static void tearDownAllTests() {
        // Global teardown if needed
    }

    @BeforeEach
    void setUpEachTest() {
        leapYear = new LeapYear();
    }

    @AfterEach
    void tearDownEachTest() {
        // Teardown if needed
    }

    /**
     * Verifies that passing a null input throws NullPointerException.
     */
    @DisplayName("Null input => should throw NullPointerException")
    @Test
    void testNullInput() {
        try {
            leapYear.isLeapYear(null);
            fail("Expected NullPointerException for null input");
        } catch (NullPointerException e) {
            // Test passes
        } catch (EmptyException | NumberFormatException e) {
            fail("Expected NullPointerException but got: " + e.getClass().getSimpleName());
        }
    }

    /**
     * Verifies that passing an empty string throws EmptyException.
     */
    @DisplayName("Empty input => should throw EmptyException")
    @Test
    void testEmptyInput() {
        try {
            leapYear.isLeapYear("");
            fail("Expected EmptyException for empty input");
        } catch (EmptyException e) {
            // Test passes
        } catch (NullPointerException | NumberFormatException e) {
            fail("Expected EmptyException but got: " + e.getClass().getSimpleName());
        }
    }

    /**
     * Verifies that passing a non-numeric string throws NumberFormatException.
     */
    @DisplayName("Non-numeric input => should throw NumberFormatException")
    @Test
    void testNonNumericInput() {
        try {
            leapYear.isLeapYear("abc");
            fail("Expected NumberFormatException for non-numeric input");
        } catch (NumberFormatException e) {
            // Test passes
        } catch (NullPointerException | EmptyException e) {
            fail("Expected NumberFormatException but got: " + e.getClass().getSimpleName());
        }
    }

    /**
     * Tests numeric inputs covering boundary values and condition/decision coverage.
     */
    @DisplayName("Numeric inputs => testing boundary conditions and leap year logic")
    @ParameterizedTest(name = "Year: {0}, Expected: {1}")
    @MethodSource("provideNumericTestCases")
    void testNumericInput(String year, boolean expected) {
        try {
            boolean result = leapYear.isLeapYear(year);
            assertEquals(expected, result, "Mismatch for year: " + year);
        } catch (NullPointerException | EmptyException | NumberFormatException e) {
            fail("No exception expected for valid numeric input (" + year + "), but got: " 
                 + e.getClass().getSimpleName());
        }
    }

    /**
     * Supplies numeric test data including boundaries and equivalence partitioning.
     */
    private static Stream<Arguments> provideNumericTestCases() {
        return Stream.of(
            Arguments.of("0", false),
            Arguments.of("-1", false),
            Arguments.of("1", false),
            Arguments.of("4", true),
            Arguments.of("2100", true),
            Arguments.of("2101", false)
        );
    }
}