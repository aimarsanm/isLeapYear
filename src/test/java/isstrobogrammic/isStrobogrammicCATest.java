package isstrobogrammic;

import java.util.stream.Stream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("Unit tests for StrobogrammicNumber.isStrobogrammic")
class isStrobogrammicCATest {

    @BeforeAll
    static void beforeAll() {
        // No global setup required
    }

    @AfterAll
    static void afterAll() {
        // No global teardown required
    }

    @BeforeEach
    void beforeEach() {
        // No per-test setup required
    }

    @AfterEach
    void afterEach() {
        // No per-test teardown required
    }

    // --- White Box: Condition/Decision Coverage ---

    @ParameterizedTest(name = "Null or empty input \"{0}\" should return true")
    @NullAndEmptySource
    @DisplayName("Should return true for null or empty string (boundary/partition)")
    void testNullOrEmptyInputReturnsTrue(String input) {
        assertTrue(StrobogrammicNumber.isStrobogrammic(input));
    }

    @ParameterizedTest(name = "Single valid strobogrammic digit \"{0}\" should return true")
    @ValueSource(strings = {"0", "1", "8"})
    @DisplayName("Should return true for single valid strobogrammic digits")
    void testSingleValidStrobogrammicDigitReturnsTrue(String input) {
        assertTrue(StrobogrammicNumber.isStrobogrammic(input));
    }

    @ParameterizedTest(name = "Single invalid strobogrammic digit \"{0}\" should return false")
    @ValueSource(strings = {"2", "3", "4", "5", "7"})
    @DisplayName("Should return false for single invalid strobogrammic digits")
    void testSingleInvalidStrobogrammicDigitReturnsFalse(String input) {
        assertFalse(StrobogrammicNumber.isStrobogrammic(input));
    }

    @ParameterizedTest(name = "Valid strobogrammic number \"{0}\" should return true")
    @MethodSource("validStrobogrammicNumbers")
    @DisplayName("Should return true for valid strobogrammic numbers")
    void testValidStrobogrammicNumbers(String input) {
        assertTrue(StrobogrammicNumber.isStrobogrammic(input));
    }

    static Stream<Arguments> validStrobogrammicNumbers() {
        return Stream.of(
            Arguments.of("69"),
            Arguments.of("96"),
            Arguments.of("818"),
            Arguments.of("1001"),
            Arguments.of("609"),
            Arguments.of("906"),
            Arguments.of("888"),
            Arguments.of("1"),
            Arguments.of("0"),
            Arguments.of("8"),
            Arguments.of("11"),
            Arguments.of("101"),
            Arguments.of("808"),
           // Arguments.of("609906"),
            Arguments.of("1691")
           // Arguments.of("68986")
        );
    }

    @ParameterizedTest(name = "Invalid strobogrammic number \"{0}\" should return false")
    @MethodSource("invalidStrobogrammicNumbers")
    @DisplayName("Should return false for invalid strobogrammic numbers")
    void testInvalidStrobogrammicNumbers(String input) {
        assertFalse(StrobogrammicNumber.isStrobogrammic(input));
    }

    static Stream<Arguments> invalidStrobogrammicNumbers() {
        return Stream.of(
            Arguments.of("12"),      // contains '2'
            Arguments.of("123"),     // contains '2' and '3'
           // Arguments.of("689"),     // '8' and '9' are valid, but '6' at left does not match '9' at right
            Arguments.of("8082"),    // ends with '2'
            Arguments.of("8183"),    // ends with '3'
            Arguments.of("8184"),    // ends with '4'
            Arguments.of("8185"),    // ends with '5'
            Arguments.of("8187"),    // ends with '7'
            Arguments.of("1234567890"), // contains invalid digits
            Arguments.of("6"),       // single '6' is not strobogrammic
            Arguments.of("9"),       // single '9' is not strobogrammic
            Arguments.of("68"),      // '6' and '8' do not form a valid pair
           // Arguments.of("96"),      // '9' and '6' are valid, but reversed order
           // Arguments.of("689"),     // '6' and '9' are valid, but '8' in the middle does not match
            Arguments.of("8186"),    // ends with '6'
            Arguments.of("8189")     // ends with '9'
        );
    }

    // --- Black Box: Equivalence Partitioning & Boundary Value Analysis ---

    @ParameterizedTest(name = "Boundary value: \"{0}\" should return {1}")
    @MethodSource("boundaryValues")
    @DisplayName("Boundary value analysis for strobogrammic numbers")
    void testBoundaryValues(String input, boolean expected) {
        assertEquals(expected, StrobogrammicNumber.isStrobogrammic(input));
    }

    static Stream<Arguments> boundaryValues() {
        return Stream.of(
            Arguments.of("", true),      // empty string
            Arguments.of("0", true),     // single valid digit
            Arguments.of("1", true),     // single valid digit
            Arguments.of("8", true),     // single valid digit
            Arguments.of("6", false),    // single invalid digit
            Arguments.of("9", false),    // single invalid digit
            Arguments.of("11", true),    // two valid digits
            Arguments.of("69", true),    // two valid digits
            Arguments.of("96", true),    // two valid digits
            Arguments.of("88", true),    // two valid digits
            Arguments.of("18", false),   // two digits, invalid pair
            Arguments.of("81", false),   // two digits, invalid pair
            Arguments.of("19", false),   // two digits, invalid pair
            Arguments.of("91", false),   // two digits, invalid pair
            Arguments.of("1001", true),  // four digits, valid
            Arguments.of("1002", false)  // four digits, invalid
        );
    }

    @ParameterizedTest(name = "Equivalence partition: \"{0}\" should return {1}")
    @MethodSource("equivalencePartitions")
    @DisplayName("Equivalence partitioning for strobogrammic numbers")
    void testEquivalencePartitions(String input, boolean expected) {
        assertEquals(expected, StrobogrammicNumber.isStrobogrammic(input));
    }

    static Stream<Arguments> equivalencePartitions() {
        return Stream.of(
            Arguments.of("818", true),      // all valid digits, odd length
            Arguments.of("808", true),      // all valid digits, odd length
            //Arguments.of("609906", true),   // all valid digits, even length
            Arguments.of("1234567890", false), // contains invalid digits
            Arguments.of("123", false),     // contains invalid digits
            Arguments.of("888", true),      // all valid digits
            //Arguments.of("68986", true),    // valid strobogrammic
            Arguments.of("68989", false),   // invalid strobogrammic
            Arguments.of("1691", true),     // valid strobogrammic
            Arguments.of("1692", false)     // invalid strobogrammic
        );
    }

    // --- Defensive: Input with whitespace, symbols, and non-digit characters ---

    @ParameterizedTest(name = "Non-digit input \"{0}\" should return false")
    @ValueSource(strings = {"a", "abc", "1a1", "6a9", "!", " "})
    @DisplayName("Should return false for non-digit input")
    void testNonDigitInputReturnsFalse(String input) {
        assertFalse(StrobogrammicNumber.isStrobogrammic(input));
    }

    // --- Defensive: Input with leading/trailing zeros ---

    @ParameterizedTest(name = "Input with leading/trailing zeros \"{0}\" should return {1}")
    @MethodSource("leadingTrailingZeros")
    @DisplayName("Should handle leading/trailing zeros correctly")
    void testLeadingTrailingZeros(String input, boolean expected) {
        assertEquals(expected, StrobogrammicNumber.isStrobogrammic(input));
    }

    static Stream<Arguments> leadingTrailingZeros() {
        return Stream.of(
            Arguments.of("010", true),
            Arguments.of("00100", true),
            Arguments.of("06090", true),
            Arguments.of("09060", true),
            Arguments.of("08080", true),
            Arguments.of("08081", false),
            Arguments.of("08082", false)
        );
    }
}