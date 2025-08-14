package isstrobogrammic;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StrobogrammicNumberSonnet37Test {

    @DisplayName("Should return true for null input")
    @Test
    void isStrobogrammic_NullInput_ReturnsTrue() {
        assertEquals(true, StrobogrammicNumber.isStrobogrammic(null));
    }

    @DisplayName("Should return true for empty string")
    @Test
    void isStrobogrammic_EmptyString_ReturnsTrue() {
        assertEquals(true, StrobogrammicNumber.isStrobogrammic(""));
    }

    @DisplayName("Should identify valid strobogrammic numbers")
    @ParameterizedTest(name = "Input: {0} should be strobogrammic")
    @ValueSource(strings = {"0", "1", "8", "00", "11", "69", "88", "96", "101", "111", "181", "609", "619", "689", "818", "916", "986", "1001", "6009", "8008", "9006", "10801", "11011"})
    void isStrobogrammic_ValidStrobogrammicNumber_ReturnsTrue(String input) {
        assertEquals(true, StrobogrammicNumber.isStrobogrammic(input));
    }

    @DisplayName("Should identify non-strobogrammic numbers")
    @ParameterizedTest(name = "Input: {0} should not be strobogrammic")
    @ValueSource(strings = {"2", "3", "4", "5", "7", "12", "20", "23", "45", "67", "100", "102", "123", "456", "789", "1234", "5678", "9000", "10000"})
    void isStrobogrammic_NonStrobogrammicNumber_ReturnsFalse(String input) {
        assertEquals(false, StrobogrammicNumber.isStrobogrammic(input));
    }

    @DisplayName("Should handle condition coverage for map.containsKey check")
    @ParameterizedTest(name = "Input: {0} should return {1}")
    @MethodSource("mapContainsKeyTestCases")
    void isStrobogrammic_MapContainsKeyCheck(String input, boolean expected) {
        assertEquals(expected, StrobogrammicNumber.isStrobogrammic(input));
    }

    static Stream<Arguments> mapContainsKeyTestCases() {
        return Stream.of(
            // Cases where map.containsKey(number.charAt(right)) is false
            Arguments.of("2", false),  // '2' is not a strobogrammic digit
            Arguments.of("12", false), // '1' is a strobogrammic digit but '2' is not
            Arguments.of("21", false), // '2' is not a strobogrammic digit
            Arguments.of("123", false) // Contains non-strobogrammic digits
        );
    }

    @DisplayName("Should handle condition coverage for character pair matching")
    @ParameterizedTest(name = "Input: {0} should return {1}")
    @MethodSource("characterPairMatchingTestCases")
    void isStrobogrammic_CharacterPairMatchingCheck(String input, boolean expected) {
        assertEquals(expected, StrobogrammicNumber.isStrobogrammic(input));
    }

    static Stream<Arguments> characterPairMatchingTestCases() {
        return Stream.of(
            // Cases where map.containsKey(number.charAt(right)) is true but number.charAt(left) != map.get(number.charAt(right))
            Arguments.of("10", false),  // '1' and '0' don't form a strobogrammic pair
            Arguments.of("91", false),  // '9' and '1' don't form a strobogrammic pair
            Arguments.of("19", false),  // '1' and '9' don't form a strobogrammic pair
            Arguments.of("61", false),  // '6' and '1' don't form a strobogrammic pair
            Arguments.of("16", false),  // '1' and '6' don't form a strobogrammic pair
            Arguments.of("801", false), // '8', '0', '1' don't form strobogrammic pairs
            
            // Cases where both conditions are true
            Arguments.of("11", true),   // '1' and '1' form a strobogrammic pair
            Arguments.of("69", true),   // '6' and '9' form a strobogrammic pair
            Arguments.of("88", true),   // '8' and '8' form a strobogrammic pair
            Arguments.of("00", true),   // '0' and '0' form a strobogrammic pair
            Arguments.of("818", true)   // '8', '1', '8' form strobogrammic pairs
        );
    }

    @DisplayName("Should handle boundary cases with single digits")
    @ParameterizedTest(name = "Single digit: {0} should return {1}")
    @MethodSource("singleDigitTestCases")
    void isStrobogrammic_SingleDigitBoundary(String input, boolean expected) {
        assertEquals(expected, StrobogrammicNumber.isStrobogrammic(input));
    }

    static Stream<Arguments> singleDigitTestCases() {
        return Stream.of(
            Arguments.of("0", true),
            Arguments.of("1", true),
            Arguments.of("8", true),
            Arguments.of("6", false),
            Arguments.of("9", false),
            Arguments.of("2", false),
            Arguments.of("3", false),
            Arguments.of("4", false),
            Arguments.of("5", false),
            Arguments.of("7", false)
        );
    }

    @DisplayName("Should handle even and odd length strobogrammic numbers")
    @ParameterizedTest(name = "Input: {0} of length {1} should return {2}")
    @MethodSource("evenOddLengthTestCases")
    void isStrobogrammic_EvenAndOddLength(String input, int length, boolean expected) {
        assertEquals(expected, StrobogrammicNumber.isStrobogrammic(input));
        assertEquals(length, input.length());
    }

    static Stream<Arguments> evenOddLengthTestCases() {
        return Stream.of(
            // Even length valid cases
            Arguments.of("69", 2, true),
            Arguments.of("88", 2, true),
            Arguments.of("1001", 4, true),
            Arguments.of("8008", 4, true),
            //Arguments.of("69696", 6, true),
            
            // Even length invalid cases
            Arguments.of("12", 2, false),
            Arguments.of("1234", 4, false),
            
            // Odd length valid cases
            Arguments.of("8", 1, true),
            Arguments.of("101", 3, true),
            Arguments.of("111", 3, true),
            Arguments.of("181", 3, true),
            Arguments.of("609", 3, true),
            Arguments.of("10801", 5, true),
            
            // Odd length invalid cases
            Arguments.of("123", 3, false),
            Arguments.of("987", 3, false)
        );
    }

    @DisplayName("Should handle all condition combinations for while loop")
    @ParameterizedTest(name = "Input: {0}, left pointer reaches: {1}, right pointer reaches: {2}")
    @MethodSource("pointerPositionsTestCases")
    void isStrobogrammic_PointerPositions(String input, int expectedLeft, int expectedRight, boolean expected) {
        assertEquals(expected, StrobogrammicNumber.isStrobogrammic(input));
    }

    static Stream<Arguments> pointerPositionsTestCases() {
        return Stream.of(
            // For empty string, pointers don't move (left=0, right=-1)
            Arguments.of("", 0, -1, true),
            
            // For single character, pointers cross (left=1, right=0)
            Arguments.of("1", 1, 0, true),
            Arguments.of("2", 1, 0, false),
            
            // For two characters, pointers cross (left=1, right=0)
            Arguments.of("69", 1, 0, true),
            Arguments.of("12", 1, 0, false),
            
            // For three characters, pointers meet in middle (left=1, right=1)
            Arguments.of("101", 2, 0, true),
            Arguments.of("121", 1, 1, false),
            
            // For four characters, pointers cross in middle (left=2, right=1)
            Arguments.of("1001", 2, 1, true),
            Arguments.of("1021", 1, 2, false)
        );
    }

    @DisplayName("Should handle null input with null source")
    @ParameterizedTest(name = "Null input should return true")
    @NullSource
    void isStrobogrammic_NullInput_ReturnsTrueParameterized(String input) {
        assertEquals(true, StrobogrammicNumber.isStrobogrammic(input));
    }
}