package isstrobogrammic;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;





class Sonnet37thinkTest {

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("Should return true for null or empty inputs")
    void testNullOrEmptyInput(String input) {
        assertTrue(StrobogrammicNumber.isStrobogrammic(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"0", "1", "8", "00", "11", "69", "88", "96", "1001", "6009", "8008", "9006"})//, "69096"
    @DisplayName("Should return true for valid strobogrammic numbers")
    void testValidStrobogrammic(String input) {
        assertTrue(StrobogrammicNumber.isStrobogrammic(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"2", "3", "4", "5", "7", "12", "23", "45", "67", "78", "910"})
    @DisplayName("Should return false for numbers with non-strobogrammic digits")
    void testNonStrobogrammic(String input) {
        assertFalse(StrobogrammicNumber.isStrobogrammic(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"19", "10", "16", "68", "90", "106", "901"})
    @DisplayName("Should return false for numbers with valid strobogrammic digits but incorrect pairing")
    void testInvalidPairing(String input) {
        assertFalse(StrobogrammicNumber.isStrobogrammic(input));
    }

    @ParameterizedTest
    @MethodSource("singleCharCenterProvider")
    @DisplayName("Should test odd-length strobogrammic numbers with different center characters")
    void testSingleCharCenter(String input, boolean expected) {
        assertEquals(expected, StrobogrammicNumber.isStrobogrammic(input));
    }
    
    private static Stream<Arguments> singleCharCenterProvider() {
        return Stream.of(
            Arguments.of("101", true),   // Valid center character (1)
            Arguments.of("808", true),   // Valid center character (8)
            Arguments.of("111", true),   // Valid center character (1)
            Arguments.of("121", false),  // Invalid center character (2)
            Arguments.of("151", false),  // Invalid center character (5)
            //Arguments.of("696", true),   // Valid with 9 and 6 at ends, 9 in middle
            Arguments.of("181", true)    // Valid with 1 at ends, 8 in middle
        );
    }

    @ParameterizedTest
    @MethodSource("firstConditionFailProvider")
    @DisplayName("Should test cases where map doesn't contain the right character")
    void testFirstConditionFail(String input) {
        assertFalse(StrobogrammicNumber.isStrobogrammic(input));
    }
    
    private static Stream<String> firstConditionFailProvider() {
        return Stream.of(
            "12",    // Right character 2 is not in map
            "34",    // Right character 4 is not in map
            "56",    // Right character 6 is in map but left char 5 isn't valid
            "123",   // Right character 3 is not in map
            "7890"   // Right character 0 is in map but left char 7 isn't valid
        );
    }

    @ParameterizedTest
    @MethodSource("secondConditionFailProvider")
    @DisplayName("Should test cases where map contains right character but left doesn't match")
    void testSecondConditionFail(String input) {
        assertFalse(StrobogrammicNumber.isStrobogrammic(input));
    }
    
    private static Stream<String> secondConditionFailProvider() {
        return Stream.of(
            "18",    // Right char 8 is in map, but left char 1 should match with 1 not 8
            "10",    // Right char 0 is in map, but left char 1 should match with 1 not 0
            "61",    // Right char 1 is in map, but left char 6 should match with 9 not 1
            "89"     // Right char 9 is in map, but left char 8 should match with 8 not 9
        );
    }

    @ParameterizedTest
    @MethodSource("complexCasesProvider")
    @DisplayName("Should test complex cases with mixed strobogrammic patterns")
    void testComplexCases(String input, boolean expected) {
        assertEquals(expected, StrobogrammicNumber.isStrobogrammic(input));
    }
    
    private static Stream<Arguments> complexCasesProvider() {
        return Stream.of(
            //Arguments.of("10901", true),   // Valid strobogrammic with odd length
            Arguments.of("609060", false), // Valid digits but incorrect pairing
            Arguments.of("180081", true),  // Valid strobogrammic with even length
            Arguments.of("123321", false)//, // Palindrome but not strobogrammic
           // Arguments.of("080", true),     // Valid strobogrammic with leading zero
            //Arguments.of("69896", true),   // Valid complex strobogrammic number
            //Arguments.of("1860981", false) // Mix of valid and invalid strobogrammic digits
        );
    }
/*
    @Test
    @DisplayName("Should test extremely long strobogrammic number")
    void testLongStrobogrammic() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 500; i++) {
            sb.append("69");
        }
        for (int i = 0; i < 500; i++) {
            sb.append("96");
        }
        
        assertTrue(StrobogrammicNumber.isStrobogrammic(sb.toString()));
    }*/

    @Test
    @DisplayName("Should test extremely long non-strobogrammic number")
    void testLongNonStrobogrammic() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 500; i++) {
            sb.append("69");
        }
        sb.append("5"); // Add a non-strobogrammic character
        for (int i = 0; i < 499; i++) {
            sb.append("96");
        }
        
        assertFalse(StrobogrammicNumber.isStrobogrammic(sb.toString()));
    }

    @Test
    @DisplayName("Should test boundary case with single digit valid strobogrammic number")
    void testSingleDigitValid() {
        assertTrue(StrobogrammicNumber.isStrobogrammic("0"));
        assertTrue(StrobogrammicNumber.isStrobogrammic("1"));
        assertTrue(StrobogrammicNumber.isStrobogrammic("8"));
    }

    @Test
    @DisplayName("Should test boundary case with single digit invalid strobogrammic number")
    void testSingleDigitInvalid() {
        assertFalse(StrobogrammicNumber.isStrobogrammic("2"));
        assertFalse(StrobogrammicNumber.isStrobogrammic("3"));
        assertFalse(StrobogrammicNumber.isStrobogrammic("4"));
        assertFalse(StrobogrammicNumber.isStrobogrammic("5"));
        assertFalse(StrobogrammicNumber.isStrobogrammic("6")); // 6 alone is not strobogrammic
        assertFalse(StrobogrammicNumber.isStrobogrammic("7"));
        assertFalse(StrobogrammicNumber.isStrobogrammic("9")); // 9 alone is not strobogrammic
    }
}