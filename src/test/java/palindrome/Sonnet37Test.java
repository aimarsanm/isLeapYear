package palindrome;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;






@DisplayName("Palindrome Tests")
class Sonnet37Test {

    private final Palindrome palindrome = new Palindrome();

    // Tests for isPalindrome method

    @ParameterizedTest(name = "isPalindrome(\"{0}\") should return true")
    @DisplayName("Test isPalindrome with valid palindromes")
    @ValueSource(strings = {"a", "aa", "aba", "abba", "radar", "level", "madam", ""})
    void isPalindrome_WithValidPalindromes_ReturnsTrue(String input) {
        try {
            assertTrue(palindrome.isPalindrome(input));
        } catch (MissingArgumentException e) {
            fail("MissingArgumentException was thrown unexpectedly");
        }
    }

    @ParameterizedTest(name = "isPalindrome(\"{0}\") should return false")
    @DisplayName("Test isPalindrome with non-palindromes")
    @ValueSource(strings = {"ab", "abc", "hello", "palindrome", "test"})
    void isPalindrome_WithNonPalindromes_ReturnsFalse(String input) {
        try {
            assertFalse(palindrome.isPalindrome(input));
        } catch (MissingArgumentException e) {
            fail("MissingArgumentException was thrown unexpectedly");
        }
    }

    @Test
    @DisplayName("isPalindrome with null input should throw MissingArgumentException")
    void isPalindrome_WithNullInput_ThrowsException() {
        assertThrows(MissingArgumentException.class, () -> palindrome.isPalindrome(null));
    }

    // Tests for findLongestPalindromeInternal method

    @ParameterizedTest(name = "findLongestPalindromeInternal(\"{0}\") should return \"{1}\"")
    @DisplayName("Test findLongestPalindromeInternal with different inputs")
    @CsvSource({
        "a, a",
        "ab, a",
        "ba, b",
        "aba, aba",
        "abba, abba",
        //"abbc, bb",
        "abbca, a",
        //"hello, ll",
        "racecar, racecar",
        //", ''", // Empty string case
    })
    void findLongestPalindromeInternal_WithVariousInputs_ReturnsCorrectPalindrome(String input, String expected) {
        try {
            String result = palindrome.findLongestPalindromeInternal(input);
            assertEquals(expected, result);
        } catch (MissingArgumentException e) {
            fail("MissingArgumentException was thrown unexpectedly");
        }
    }

    @Test
    @DisplayName("findLongestPalindromeInternal with null input should throw MissingArgumentException")
    void findLongestPalindromeInternal_WithNullInput_ThrowsException() {
        assertThrows(MissingArgumentException.class, () -> palindrome.findLongestPalindromeInternal(null));
    }

    // Tests for findLongestPalindrome method

    @ParameterizedTest(name = "findLongestPalindrome(\"{0}\") should return \"{1}\"")
    @DisplayName("Test findLongestPalindrome with different inputs")
    @MethodSource("provideFindLongestPalindromeTestCases")
    void findLongestPalindrome_WithVariousInputs_ReturnsCorrectPalindrome(String input, String expected) {
        try {
            String result = palindrome.findLongestPalindrome(input);
            assertEquals(expected, result);
        } catch (MissingArgumentException e) {
            fail("MissingArgumentException was thrown unexpectedly");
        }
    }

    private static Stream<Arguments> provideFindLongestPalindromeTestCases() {
        return Stream.of(
            // Single character tests
            Arguments.of("a", "a"),
            
            // Simple palindrome tests
            Arguments.of("aba", "aba"),
            Arguments.of("abba", "abba"),
            
            // Multiple palindromes - should return longest
            Arguments.of("abbaccd", "abba"),
            Arguments.of("forgeeksskeegfor", "geeksskeeg"),
            Arguments.of("abacdeedcxyz", "cdeedc"),
            
            // Palindrome at beginning
            Arguments.of("racecarxyz", "racecar"),
            
            // Palindrome at end
            Arguments.of("xyzracecar", "racecar"),
            
            // Palindrome in middle
            Arguments.of("xyzracecarmno", "racecar"),
            
            // No palindromes longer than 1 character
            Arguments.of("xyz", "x"),
            
            // Empty string
            Arguments.of("", "")
        );
    }

    @Test
    @DisplayName("findLongestPalindrome with null input should throw MissingArgumentException")
    void findLongestPalindrome_WithNullInput_ThrowsException() {
        assertThrows(MissingArgumentException.class, () -> palindrome.findLongestPalindrome(null));
    }

    // Edge cases and boundary tests

    @ParameterizedTest(name = "Empty string test for method: {0}")
    @DisplayName("Test all methods with empty string")
    @ValueSource(strings = {"isPalindrome", "findLongestPalindromeInternal", "findLongestPalindrome"})
    void allMethods_WithEmptyString_HandleCorrectly(String methodName) {
        try {
            String emptyString = "";
            switch (methodName) {
                case "isPalindrome":
                    assertTrue(palindrome.isPalindrome(emptyString));
                    break;
                case "findLongestPalindromeInternal":
                    assertEquals("", palindrome.findLongestPalindromeInternal(emptyString));
                    break;
                case "findLongestPalindrome":
                    assertEquals("", palindrome.findLongestPalindrome(emptyString));
                    break;
                default:
                    fail("Unknown method name: " + methodName);
            }
        } catch (MissingArgumentException e) {
            fail("MissingArgumentException was thrown unexpectedly");
        }
    }

    @ParameterizedTest(name = "Long palindrome test with length {0}")
    @DisplayName("Test performance with long palindromes")
    @ValueSource(ints = {10, 50, 100})
    void findLongestPalindrome_WithLongPalindromes_WorksCorrectly(int length) {
        try {
            // Create a palindrome of specified length
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < length / 2; i++) {
                sb.append('a');
            }
            if (length % 2 != 0) {
                sb.append('b');
            }
            for (int i = 0; i < length / 2; i++) {
                sb.append('a');
            }
            
            String longPalindrome = sb.toString();
            assertEquals(longPalindrome, palindrome.findLongestPalindrome(longPalindrome));
        } catch (MissingArgumentException e) {
            fail("MissingArgumentException was thrown unexpectedly");
        }
    }

    @Test
    @DisplayName("findLongestPalindrome with special characters")
    void findLongestPalindrome_WithSpecialCharacters_WorksCorrectly() {
        try {
            assertEquals("!@##@!", palindrome.findLongestPalindrome("abc!@##@!def"));
            assertEquals("$%%$", palindrome.findLongestPalindrome("abc$%%$def"));
        } catch (MissingArgumentException e) {
            fail("MissingArgumentException was thrown unexpectedly");
        }
    }
}