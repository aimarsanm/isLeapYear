package isstrobogrammic;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;






class isStrobogrammicAskTest {

    @BeforeAll
    static void setupAll() {
        // No setup needed, but method included for completeness
    }

    @AfterAll
    static void tearDownAll() {
        // No teardown needed, but method included for completeness
    }

    // --- White Box: Condition/Decision Coverage ---

    @Test
    @DisplayName("Should return true for null input (null check branch)")
    void testNullInputReturnsTrue() {
        assertTrue(StrobogrammicNumber.isStrobogrammic(null));
    }

    @Test
    @DisplayName("Should return true for empty string input (empty string branch)")
    void testEmptyStringReturnsTrue() {
        assertTrue(StrobogrammicNumber.isStrobogrammic(""));
    }

    @ParameterizedTest(name = "Valid strobogrammic number: {0}")
    @MethodSource("validStrobogrammicNumbers")
    @DisplayName("Should return true for valid strobogrammic numbers")
    void testValidStrobogrammicNumbers(String input) {
        assertTrue(StrobogrammicNumber.isStrobogrammic(input));
    }

    static Stream<String> validStrobogrammicNumbers() {
        return Stream.of(
                "0",    // single digit
                "1",
                "8",
                "69",   // two digits
                "96",
                "11",
                "88",
                "818",  // odd length
                "609",
                "906",
                "1001",
                "8008",
                "1691",
               // "98689",
                "6889",
               // "6896",
                "9696",
                "888888",
                "818818"
        );
    }

    @ParameterizedTest(name = "Invalid strobogrammic number: {0}")
    @MethodSource("invalidStrobogrammicNumbers")
    @DisplayName("Should return false for invalid strobogrammic numbers")
    void testInvalidStrobogrammicNumbers(String input) {
        assertFalse(StrobogrammicNumber.isStrobogrammic(input));
    }

    static Stream<String> invalidStrobogrammicNumbers() {
        return Stream.of(
                "2",        // invalid single digit
                "3",
                "4",
                "5",
                "7",
                "12",       // invalid pair
                "19",
                "68",
                "123",
              //  "689",
                "890",
               // "101",      // invalid middle
               // "808",      // valid except for middle
                "8181",     // valid except for last
                "1692",     // invalid last
                "98688",    // invalid last
                "888889",   // invalid last
                "8188182",  // invalid last
                "6a9",      // invalid char
                "9b6",      // invalid char
                "1 1",      // space
                "1-1",      // symbol
                "1_1",      // symbol
                "1.1",      // symbol
                "1/1",      // symbol
                "1,1"       // symbol
        );
    }

    // --- Black Box: Equivalence Partitioning & Boundary Value Analysis ---

    @ParameterizedTest(name = "Boundary value: {0}")
    @MethodSource("boundaryValues")
    @DisplayName("Boundary value analysis for strobogrammic numbers")
    void testBoundaryValues(String input, boolean expected) {
        assertEquals(expected, StrobogrammicNumber.isStrobogrammic(input));
    }

    static Stream<org.junit.jupiter.params.provider.Arguments> boundaryValues() {
        return Stream.of(
                org.junit.jupiter.params.provider.Arguments.of("0", true),
                org.junit.jupiter.params.provider.Arguments.of("1", true),
                org.junit.jupiter.params.provider.Arguments.of("8", true),
                org.junit.jupiter.params.provider.Arguments.of("6", false),
                org.junit.jupiter.params.provider.Arguments.of("9", false),
                org.junit.jupiter.params.provider.Arguments.of("2", false),
                org.junit.jupiter.params.provider.Arguments.of("69", true),
                org.junit.jupiter.params.provider.Arguments.of("96", true),
                org.junit.jupiter.params.provider.Arguments.of("11", true),
                org.junit.jupiter.params.provider.Arguments.of("88", true),
                org.junit.jupiter.params.provider.Arguments.of("12", false),
                org.junit.jupiter.params.provider.Arguments.of("19", false),
               // org.junit.jupiter.params.provider.Arguments.of("101", false),
               // org.junit.jupiter.params.provider.Arguments.of("808", false),
                org.junit.jupiter.params.provider.Arguments.of("818", true),
                org.junit.jupiter.params.provider.Arguments.of("8181", false),
                org.junit.jupiter.params.provider.Arguments.of("818818", true),
                org.junit.jupiter.params.provider.Arguments.of("8188182", false)
        );
    }

    @ParameterizedTest(name = "Invalid character input: {0}")
    @MethodSource("invalidCharacterInputs")
    @DisplayName("Should return false for inputs with invalid characters")
    void testInvalidCharacterInputs(String input) {
        assertFalse(StrobogrammicNumber.isStrobogrammic(input));
    }

    static Stream<String> invalidCharacterInputs() {
        return Stream.of(
                "a", "b", "c", "d", "e", "f", "g", "h", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
                "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
                "!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "-", "+", "=", "_", "[", "]", "{", "}", "|", "\\", ":", ";", "\"", "'", "<", ">", ",", ".", "/", "?", "~", "`",
                "6a9", "9b6", "1 1", "1-1", "1_1", "1.1", "1/1", "1,1"
        );
    }

    @Test
    @DisplayName("Should not throw any exception for any valid or invalid input")
    void testNoExceptionThrownForAnyInput() {
        Stream<String> allInputs = Stream.concat(
                validStrobogrammicNumbers(),
                Stream.concat(invalidStrobogrammicNumbers(), invalidCharacterInputs())
        );
        allInputs.forEach(input -> {
            try {
                StrobogrammicNumber.isStrobogrammic(input);
            } catch (Exception e) {
                fail("Exception thrown for input: " + input);
            }
        });
    }
}