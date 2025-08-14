package isstrobogrammic;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Test suite for the StrobogrammicNumber class.
 * This class contains tests for the isStrobogrammic method, applying both
 * White Box and Black Box testing methodologies to ensure full coverage.
 */
@DisplayName("StrobogrammicNumber Test Suite")
class StrobogrammicNumberGemini25proTest {

    /**
     * This inner class groups tests based on the White Box methodology,
     * specifically focusing on Condition/Decision coverage for the conditional
     * statements within the isStrobogrammic method.
     */
    @Nested
    @DisplayName("White Box Tests (Condition/Decision Coverage)")
    class WhiteBoxTests {

        /**
         * Tests the first condition: if (number == null || number.length() == 0).
         * This test covers the case where the condition is true because the input string is null.
         * According to the implementation, this should return true.
         */
        @Test
        @DisplayName("Test with null input should return true")
        void testIsStrobogrammicWithNullInput() {
            try {
                assertTrue(StrobogrammicNumber.isStrobogrammic(null), "A null string should be considered strobogrammic.");
            } catch (Exception e) {
                fail("Test threw an unexpected exception: " + e.getMessage());
            }
        }

        /**
         * Tests the first condition: if (number == null || number.length() == 0).
         * This test covers the case where the condition is true because the input string is empty.
         * According to the implementation, this should return true.
         */
        @Test
        @DisplayName("Test with empty string should return true")
        void testIsStrobogrammicWithEmptyString() {
            try {
                assertTrue(StrobogrammicNumber.isStrobogrammic(""), "An empty string should be considered strobogrammic.");
            } catch (Exception e) {
                fail("Test threw an unexpected exception: " + e.getMessage());
            }
        }

        /**
         * Tests the main loop's conditional: if (!map.containsKey(number.charAt(right)) || number.charAt(left) != map.get(number.charAt(right))).
         * This parameterized test covers cases where the condition evaluates to false, meaning the number is strobogrammic.
         * It checks various valid strobogrammic numbers of different lengths.
         * C1 = !map.containsKey(...) -> false
         * C2 = charAt(left) != map.get(...) -> false
         */
        @ParameterizedTest(name = "Input: ''{0}'' should be strobogrammic")
        @ValueSource(strings = { "1", "8", "69", "101", "88", "96", "619", "808", "1111", "6009", "8118", "9886", "6889" })
        @DisplayName("Test with valid strobogrammic numbers")
        void testWithValidStrobogrammicNumbers(String number) {
            try {
                assertTrue(StrobogrammicNumber.isStrobogrammic(number), "Expected " + number + " to be strobogrammic.");
            } catch (Exception e) {
                fail("Test for " + number + " threw an unexpected exception: " + e.getMessage());
            }
        }

        /**
         * Tests the main loop's conditional: if (!map.containsKey(number.charAt(right)) || ...).
         * This test covers the case where the first part of the condition (C1) is true.
         * This occurs when a character in the number is not a valid strobogrammic digit (0,1,6,8,9).
         * C1 = !map.containsKey(...) -> true
         */
        @ParameterizedTest(name = "Input: ''{0}'' contains invalid characters")
        @ValueSource(strings = { "2", "3", "4", "5", "7", "121", "694" })
        @DisplayName("Test with numbers containing non-strobogrammic digits")
        void testWithNonStrobogrammicDigits(String number) {
            try {
                assertFalse(StrobogrammicNumber.isStrobogrammic(number), "Expected " + number + " to not be strobogrammic.");
            } catch (Exception e) {
                fail("Test for " + number + " threw an unexpected exception: " + e.getMessage());
            }
        }

        /**
         * Tests the main loop's conditional: if (... || number.charAt(left) != map.get(number.charAt(right))).
         * This test covers the case where the first part of the condition (C1) is false, but the second part (C2) is true.
         * This occurs when all characters are valid, but they do not form correct symmetric pairs.
         * C1 = !map.containsKey(...) -> false
         * C2 = charAt(left) != map.get(...) -> true
         */
        @ParameterizedTest(name = "Input: ''{0}'' has incorrect strobogrammic pairing")
        @CsvSource({
            "6, 6",
            "9, 9",
            "16, 61",
            "68, 89",
            "186, 981"
        })
        @DisplayName("Test with numbers with incorrect strobogrammic pairing")
        void testWithIncorrectStrobogrammicPairing(String number) {
            try {
                assertFalse(StrobogrammicNumber.isStrobogrammic(number), "Expected " + number + " to not be strobogrammic due to incorrect pairing.");
            } catch (Exception e) {
                fail("Test for " + number + " threw an unexpected exception: " + e.getMessage());
            }
        }
    }

    /**
     * This inner class groups tests based on the Black Box methodology,
     * using equivalence partitioning and boundary value analysis. Redundant
     * test cases already covered by White Box tests are omitted.
     */
    @Nested
    @DisplayName("Black Box Tests (Equivalence Partitioning & Boundary Values)")
    class BlackBoxTests {

        // Note: Most equivalence classes (valid strobogrammic, invalid non-strobogrammic digits,
        // invalid pairing) and boundary values (null, empty, single-digit) are already
        // covered by the comprehensive White Box tests. The following tests ensure any remaining
        // logical gaps are filled.

        /**
         * Equivalence Class: Valid strobogrammic numbers with odd length.
         * The middle character must be a self-symmetric digit (0, 1, 8).
         * This test verifies that numbers with a non-symmetric middle digit (6 or 9) are correctly identified as non-strobogrammic.
         */
        @ParameterizedTest(name = "Input: ''{0}'' has invalid middle digit")
        @ValueSource(strings = { "669", "966", "161", "898" })
        @DisplayName("Test odd-length numbers with invalid middle digit (6 or 9)")
        void testOddLengthNumberWithInvalidMiddleDigit(String number) {
            try {
                assertFalse(StrobogrammicNumber.isStrobogrammic(number), "Expected " + number + " to be non-strobogrammic due to invalid middle digit.");
            } catch (Exception e) {
                fail("Test for " + number + " threw an unexpected exception: " + e.getMessage());
            }
        }

        /**
         * Equivalence Class: Palindromic numbers that are not strobogrammic.
         * This test ensures that the logic correctly distinguishes between palindromes and strobogrammic numbers.
         */
        @ParameterizedTest(name = "Input: ''{0}'' is a palindrome but not strobogrammic")
        @ValueSource(strings = { "22", "343", "515", "7777" })
        @DisplayName("Test with palindromes that are not strobogrammic")
        void testPalindromesThatAreNotStrobogrammic(String number) {
            try {
                assertFalse(StrobogrammicNumber.isStrobogrammic(number), "Expected palindrome " + number + " to be non-strobogrammic.");
            } catch (Exception e) {
                fail("Test for " + number + " threw an unexpected exception: " + e.getMessage());
            }
        }

        /**
         * Boundary Value Analysis: Test with a very long strobogrammic number.
         * This checks for performance or issues with large inputs.
         */
        @Test
        @DisplayName("Test with a very long valid strobogrammic number")
        void testWithVeryLongStrobogrammicNumber() {
            String longNumber = "6".repeat(500) + "9".repeat(500);
            try {
                assertTrue(StrobogrammicNumber.isStrobogrammic(longNumber), "A very long valid strobogrammic number should return true.");
            } catch (Exception e) {
                fail("Test for long number threw an unexpected exception: " + e.getMessage());
            }
        }

        /**
         * Boundary Value Analysis: Test with a very long non-strobogrammic number.
         * This checks for early exit performance with large inputs.
         */
        @Test
        @DisplayName("Test with a very long invalid strobogrammic number")
        void testWithVeryLongInvalidStrobogrammicNumber() {
            String longNumber = "6".repeat(1000) + "1";
            try {
                assertFalse(StrobogrammicNumber.isStrobogrammic(longNumber), "A very long invalid strobogrammic number should return false.");
            } catch (Exception e) {
                fail("Test for long number threw an unexpected exception: " + e.getMessage());
            }
        }
    }
}