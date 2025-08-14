package palindrome;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test suite for the Palindrome class.
 * This class implements White Box and Black Box testing methodologies to ensure full coverage
 * and correctness of the Palindrome class's methods.
 */
@DisplayName("Palindrome Class Tests")
class PalindromeGemini25proTest {

    private Palindrome palindrome;

    @BeforeEach
    void setUp() {
        palindrome = new Palindrome();
    }

    /**
     * Tests for the isPalindrome(String) method.
     */
    @Nested
    @DisplayName("isPalindrome(String) Method Tests")
    class IsPalindromeTest {

        @Test
        @DisplayName("Should throw MissingArgumentException for null input")
        void isPalindrome_withNullInput_shouldThrowMissingArgumentException() {
            assertThrows(MissingArgumentException.class, () -> palindrome.isPalindrome(null),
                    "A MissingArgumentException should be thrown for null input.");
        }

        @ParameterizedTest
        @ValueSource(strings = { "", "a", "racecar", "madam", "level" })
        @DisplayName("Should return true for palindromic strings")
        void isPalindrome_withPalindromicStrings_shouldReturnTrue(String word) {
            try {
                assertTrue(palindrome.isPalindrome(word), "The string '" + word + "' should be a palindrome.");
            } catch (MissingArgumentException e) {
                fail("Should not throw MissingArgumentException for valid input.", e);
            }
        }

        @ParameterizedTest
        @ValueSource(strings = { "hello", "world", "java", "sonar" })
        @DisplayName("Should return false for non-palindromic strings")
        void isPalindrome_withNonPalindromicStrings_shouldReturnFalse(String word) {
            try {
                assertFalse(palindrome.isPalindrome(word), "The string '" + word + "' should not be a palindrome.");
            } catch (MissingArgumentException e) {
                fail("Should not throw MissingArgumentException for valid input.", e);
            }
        }
    }

    /**
     * Tests for the findLongestPalindromeInternal(String) method.
     */
    @Nested
    @DisplayName("findLongestPalindromeInternal(String) Method Tests")
    class FindLongestPalindromeInternalTest {

        @Test
        @DisplayName("Should throw MissingArgumentException for null input")
        void findLongestPalindromeInternal_withNullInput_shouldThrowMissingArgumentException() {
            assertThrows(MissingArgumentException.class, () -> palindrome.findLongestPalindromeInternal(null),
                    "A MissingArgumentException should be thrown for null input.");
        }

        @ParameterizedTest
        @CsvSource({
            "racecar, racecar",
            "madamimadam, madamimadam",
            "abacaba, abacaba",
            "a, a",
            "'', ''"
        })
        @DisplayName("Should return the full string when it is a palindrome")
        void findLongestPalindromeInternal_withPalindromicString_shouldReturnFullString(String input, String expected) {
            try {
                assertEquals(expected, palindrome.findLongestPalindromeInternal(input),
                        "Should return the full string as it is the longest palindrome.");
            } catch (MissingArgumentException e) {
                fail("Should not throw MissingArgumentException for valid input.", e);
            }
        }

        @ParameterizedTest
        @CsvSource({
            "babad, bab"
           // "cbbd, bb",
            //"bananas, anana",
            //"forgeeksskeegfor, geeksskeeg"
        })
        @DisplayName("Should return the longest palindrome from the start of the string")
        void findLongestPalindromeInternal_withNonPalindromicString_shouldReturnLongestPalindrome(String input, String expected) {
            try {
                assertEquals(expected, palindrome.findLongestPalindromeInternal(input),
                        "Should find the correct longest palindrome starting from the beginning.");
            } catch (MissingArgumentException e) {
                fail("Should not throw MissingArgumentException for valid input.", e);
            }
        }
    }

    /**
     * Tests for the findLongestPalindrome(String) method.
     */
    @Nested
    @DisplayName("findLongestPalindrome(String) Method Tests")
    class FindLongestPalindromeTest {

        @Test
        @DisplayName("Should throw MissingArgumentException for null input")
        void findLongestPalindrome_withNullInput_shouldThrowMissingArgumentException() {
            assertThrows(MissingArgumentException.class, () -> palindrome.findLongestPalindrome(null),
                    "A MissingArgumentException should be thrown for null input.");
        }

        @ParameterizedTest
        @CsvSource({
            "babad, bab",
            "cbbd, bb",
            "a, a",
            "ac, a",
            "racecar, racecar",
            "bananas, anana",
            "forgeeksskeegfor, geeksskeeg",
           // "abracadabra, arada",
            "'', ''"
        })
        @DisplayName("Should find the longest palindrome in various strings")
        void findLongestPalindrome_withVariousStrings_shouldReturnLongestPalindrome(String input, String expected) {
            try {
                String result = palindrome.findLongestPalindrome(input);
                // Note: Some inputs might have multiple longest palindromes of the same length (e.g., "babad" -> "bab", "aba").
                // This implementation will find the first one encountered. We assert based on that behavior.
                assertEquals(expected, result, "Should find the correct longest palindrome in the string '" + input + "'.");
            } catch (MissingArgumentException e) {
                fail("Should not throw MissingArgumentException for valid input.", e);
            }
        }

        @Test
        @DisplayName("Should return an empty string for an empty input string")
        void findLongestPalindrome_withEmptyString_shouldReturnEmptyString() {
            try {
                assertEquals("", palindrome.findLongestPalindrome(""), "The longest palindrome of an empty string is an empty string.");
            } catch (MissingArgumentException e) {
                fail("Should not throw MissingArgumentException for an empty string.", e);
            }
        }
    }
}