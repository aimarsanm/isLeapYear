package isstrobogrammic;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;





class Sonnet37Test {

    // White Box Testing - Condition/Decision Coverage

    @Test
    @DisplayName("Should return true when input is null")
    void isStrobogrammic_NullInput_ReturnsTrue() {
        assertTrue(StrobogrammicNumber.isStrobogrammic(null));
    }

    @Test
    @DisplayName("Should return true when input is empty string")
    void isStrobogrammic_EmptyString_ReturnsTrue() {
        assertTrue(StrobogrammicNumber.isStrobogrammic(""));
    }

    @ParameterizedTest
    @DisplayName("Should return true for valid single digit strobogrammic numbers")
    @ValueSource(strings = {"0", "1", "8"})
    void isStrobogrammic_SingleDigitValid_ReturnsTrue(String input) {
        assertTrue(StrobogrammicNumber.isStrobogrammic(input));
    }

    @ParameterizedTest
    @DisplayName("Should return false for invalid single digit numbers")
    @ValueSource(strings = {"2", "3", "4", "5", "7"})
    void isStrobogrammic_SingleDigitInvalid_ReturnsFalse(String input) {
        assertFalse(StrobogrammicNumber.isStrobogrammic(input));
    }

    @ParameterizedTest
    @DisplayName("Should return true for valid multi-digit strobogrammic numbers")
    @ValueSource(strings = {"69", "88", "96", "609", "818", "916", "1001", "6009", "8118", "9006", "11011"})//, "69096"
    void isStrobogrammic_MultiDigitValid_ReturnsTrue(String input) {
        assertTrue(StrobogrammicNumber.isStrobogrammic(input));
    }

    @ParameterizedTest
    @DisplayName("Should return false for numbers with invalid strobogrammic pairs")
    @ValueSource(strings = {"12", "123", "678","1234", "10011"})//, "619", "689" "6119", 
    void isStrobogrammic_InvalidPairs_ReturnsFalse(String input) {
        assertFalse(StrobogrammicNumber.isStrobogrammic(input));
    }

    @ParameterizedTest
    @DisplayName("Should return false for numbers with non-strobogrammic characters")
    @ValueSource(strings = {"a", "abc", "1a1", "6a9", "8a8", "1$1", "6$9"})
    void isStrobogrammic_NonStrobogrammicCharacters_ReturnsFalse(String input) {
        assertFalse(StrobogrammicNumber.isStrobogrammic(input));
    }

    // Black Box Testing - Equivalence Partitioning and Boundary Value Analysis

    @ParameterizedTest
    @DisplayName("Should handle null and empty inputs (boundary cases)")
    @NullSource
    @EmptySource
    void isStrobogrammic_NullOrEmpty_ReturnsTrue(String input) {
        assertTrue(StrobogrammicNumber.isStrobogrammic(input));
    }

    @ParameterizedTest
    @DisplayName("Should correctly identify strobogrammic numbers of various lengths")
    @MethodSource("provideStrobogrammicNumbers")
    void isStrobogrammic_ValidNumbers_ReturnsTrue(String input) {
        assertTrue(StrobogrammicNumber.isStrobogrammic(input));
    }

    private static Stream<String> provideStrobogrammicNumbers() {
        return Stream.of(
            // Single digit strobogrammic numbers
            "0", "1", "8",
            // Two digit strobogrammic numbers
            "00", "11", "69", "88", "96",
            // Three digit strobogrammic numbers
            "101", "111", "181", "609", "619", "689", "808", "818", "888", "916", "986",
            // Longer strobogrammic numbers "69096",
            "1001", "6009", "8118", "9006", "11011", 
            // Very long strobogrammic numbers (boundary testing) "69999999996",
            "1000000001",  "8888888888"
        );
    }

    @ParameterizedTest
    @DisplayName("Should correctly identify non-strobogrammic numbers of various lengths")
    @MethodSource("provideNonStrobogrammicNumbers")
    void isStrobogrammic_InvalidNumbers_ReturnsFalse(String input) {
        assertFalse(StrobogrammicNumber.isStrobogrammic(input));
    }

    private static Stream<String> provideNonStrobogrammicNumbers() {
        return Stream.of(
            // Single digit non-strobogrammic numbers
            "2", "3", "4", "5", "7",
            // Two digit non-strobogrammic numbers
            "12", "23", "45", "67", "90",
            // Three digit non-strobogrammic numbers
            "123", "234", "456", "678", "901",
            // Longer non-strobogrammic numbers
            "1234", "6789", "9876", "10011", "69097",
            // Numbers with non-numeric characters
            "a", "1a", "a1", "6a9", "8a8", "abc", "123a", "a123"
        );
    }

    @ParameterizedTest
    @DisplayName("Should correctly handle edge case strobogrammic pairs")
    @CsvSource({
        "69,true",     // 6-9 pair
        "96,true",     // 9-6 pair
       // "619,false",   // 6-1-9 (invalid middle)
        "918,false",   // 9-1-8 (invalid)
        //"6119,false",  // 6-1-1-9 (invalid)
        "8006,false"   // 8-0-0-6 (invalid)
    })
    void isStrobogrammic_EdgeCases_ReturnsExpectedResult(String input, boolean expected) {
        assertEquals(expected, StrobogrammicNumber.isStrobogrammic(input));
    }

    @ParameterizedTest
    @DisplayName("Should correctly validate all possible strobogrammic digit pairs")
    @MethodSource("provideAllPossiblePairs")
    void isStrobogrammic_AllPossiblePairs_ReturnsExpected(String input, boolean expected) {
        assertEquals(expected, StrobogrammicNumber.isStrobogrammic(input));
    }

    private static Stream<Arguments> provideAllPossiblePairs() {
        return Stream.of(
            // Valid pairs
            Arguments.of("00", true),
            Arguments.of("11", true),
            Arguments.of("88", true),
            Arguments.of("69", true),
            Arguments.of("96", true),
            
            // Invalid pairs with valid digits
            Arguments.of("01", false),
            Arguments.of("08", false),
            Arguments.of("06", false),
            Arguments.of("09", false),
            Arguments.of("10", false),
            Arguments.of("18", false),
            Arguments.of("16", false),
            Arguments.of("19", false),
            Arguments.of("80", false),
            Arguments.of("81", false),
            Arguments.of("86", false),
            Arguments.of("89", false),
            Arguments.of("60", false),
            Arguments.of("61", false),
            Arguments.of("68", false),
            Arguments.of("90", false),
            Arguments.of("91", false),
            Arguments.of("98", false),
            
            // Invalid digits in pairs
            Arguments.of("22", false),
            Arguments.of("33", false),
            Arguments.of("44", false),
            Arguments.of("55", false),
            Arguments.of("77", false),
            Arguments.of("23", false),
            Arguments.of("45", false),
            Arguments.of("67", false)
        );
    }

    @ParameterizedTest
    @DisplayName("Should correctly validate odd-length strobogrammic numbers")
    @ValueSource(strings = {"0", "1", "8", "101", "111", "181", "808", "818", "888", "10801", "11011", "18081"})
    void isStrobogrammic_OddLengthValidNumbers_ReturnsTrue(String input) {
        assertTrue(StrobogrammicNumber.isStrobogrammic(input));
    }

    @ParameterizedTest
    @DisplayName("Should correctly validate even-length strobogrammic numbers")
    @ValueSource(strings = {"00", "11", "69", "88", "96", "1001", "6009", "8118", "9006", "696969", "888888"})
    void isStrobogrammic_EvenLengthValidNumbers_ReturnsTrue(String input) {
        assertTrue(StrobogrammicNumber.isStrobogrammic(input));
    }
}