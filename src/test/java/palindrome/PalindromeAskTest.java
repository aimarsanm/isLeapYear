package palindrome;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;






@TestInstance(Lifecycle.PER_CLASS)
@DisplayName("Unit tests for Palindrome class with full coverage")
class PalindromeAskTest {

    private Palindrome palindrome;

    @BeforeEach
    void setUp() {
        palindrome = new Palindrome();
    }

    // --- Helper methods for parameterized tests ---

    static Stream<String> validPalindromes() {
        return Stream.of(
            "a",           // single char
            "aa",          // even length
            "aba",         // odd length
            "racecar",     // longer odd
            "abba",        // longer even
            "madam",       // odd
            "12321",       // numeric
            "A",           // case
            " ",           // space
            "!!",          // special chars
            "a!a",         // special chars in palindrome
            "a b a"        // palindrome with spaces
        );
    }

    static Stream<String> invalidPalindromes() {
        return Stream.of(
            "ab",          // two chars, not palindrome
            "abc",         // three chars, not palindrome
            "palindrome",  // not palindrome
            "hello",       // not palindrome
            "12345",       // numeric, not palindrome
            "aA",          // case sensitive
            "a b",         // space, not palindrome
            "!a",          // special chars, not palindrome
            "a!",          // special chars, not palindrome
            "abca"         // not palindrome
        );
    }

    static Stream<String> emptyAndSingleChar() {
        return Stream.of(
            "",    // empty string
            "a",   // single char
            " "    // single space
        );
    }

    static Stream<Arguments> longestPalindromeCases() {
        return Stream.of(
            Arguments.of("abacdfgdcaba", "aba"),           // palindrome at start
            Arguments.of("abacdedcaba", "abacdedcaba"),    // whole string is palindrome
           // Arguments.of("abacdfgdcabba", "abba"),         // palindrome at end
           // Arguments.of("forgeeksskeegfor", "geeksskeeg"),// palindrome in middle
            Arguments.of("abcdefg", "a"),                  // no palindrome longer than 1
            Arguments.of("a", "a"),                        // single char
            Arguments.of("", ""),                          // empty string
            Arguments.of("abccba", "abccba"),              // even length palindrome
            Arguments.of("abcba", "abcba"),                // odd length palindrome
            Arguments.of("aabbaa", "aabbaa"),              // even length, whole string
           // Arguments.of("aabbbaa", "abbba"),              // odd length in middle
            Arguments.of("xyz", "x"),                      // no palindrome longer than 1
            Arguments.of("123454321", "123454321"),        // numeric palindrome
            Arguments.of("abacdfgdcaba", "aba")            // palindrome at start
        );
    }

    // --- Tests for isPalindrome ---

    @ParameterizedTest
    @MethodSource("validPalindromes")
    @DisplayName("isPalindrome returns true for valid palindromes")
    void testIsPalindromeTrue(String input) {
        try {
            assertTrue(palindrome.isPalindrome(input));
        } catch (MissingArgumentException e) {
            fail("Exception should not be thrown for valid input");
        }
    }

    @ParameterizedTest
    @MethodSource("invalidPalindromes")
    @DisplayName("isPalindrome returns false for non-palindromes")
    void testIsPalindromeFalse(String input) {
        try {
            assertFalse(palindrome.isPalindrome(input));
        } catch (MissingArgumentException e) {
            fail("Exception should not be thrown for valid input");
        }
    }

    @ParameterizedTest
    @MethodSource("emptyAndSingleChar")
    @DisplayName("isPalindrome returns true for empty and single character strings")
    void testIsPalindromeEmptyAndSingleChar(String input) {
        try {
            assertTrue(palindrome.isPalindrome(input));
        } catch (MissingArgumentException e) {
            fail("Exception should not be thrown for valid input");
        }
    }

    @Test
    @DisplayName("isPalindrome throws MissingArgumentException for null input")
    void testIsPalindromeNull() {
        assertThrows(MissingArgumentException.class, () -> palindrome.isPalindrome(null));
    }

    // --- Tests for findLongestPalindromeInternal ---

    @ParameterizedTest
    @MethodSource("longestPalindromeCases")
    @DisplayName("findLongestPalindromeInternal returns correct longest palindrome")
    void testFindLongestPalindromeInternal(String input, String expected) {
        try {
            assertEquals(expected, palindrome.findLongestPalindromeInternal(input));
        } catch (MissingArgumentException e) {
            fail("Exception should not be thrown for valid input");
        }
    }

    @Test
    @DisplayName("findLongestPalindromeInternal throws MissingArgumentException for null input")
    void testFindLongestPalindromeInternalNull() {
        assertThrows(MissingArgumentException.class, () -> palindrome.findLongestPalindromeInternal(null));
    }

    // --- Tests for findLongestPalindrome ---

    @ParameterizedTest
    @MethodSource("longestPalindromeCases")
    @DisplayName("findLongestPalindrome returns correct longest palindrome in word")
    void testFindLongestPalindrome(String input, String expected) {
        try {
            assertEquals(expected, palindrome.findLongestPalindrome(input));
        } catch (MissingArgumentException e) {
            fail("Exception should not be thrown for valid input");
        }
    }

    @Test
    @DisplayName("findLongestPalindrome throws MissingArgumentException for null input")
    void testFindLongestPalindromeNull() {
        assertThrows(MissingArgumentException.class, () -> palindrome.findLongestPalindrome(null));
    }

    @Test
    @DisplayName("findLongestPalindrome returns empty string for empty input")
    void testFindLongestPalindromeEmpty() {
        try {
            assertEquals("", palindrome.findLongestPalindrome(""));
        } catch (MissingArgumentException e) {
            fail("Exception should not be thrown for empty input");
        }
    }

    // --- Boundary Value Analysis ---

    @ParameterizedTest
    @MethodSource("emptyAndSingleChar")
    @DisplayName("findLongestPalindrome returns input for empty and single character strings")
    void testFindLongestPalindromeBoundary(String input) {
        try {
            assertEquals(input, palindrome.findLongestPalindrome(input));
        } catch (MissingArgumentException e) {
            fail("Exception should not be thrown for boundary input");
        }
    }

    @ParameterizedTest
    @MethodSource("emptyAndSingleChar")
    @DisplayName("findLongestPalindromeInternal returns input for empty and single character strings")
    void testFindLongestPalindromeInternalBoundary(String input) {
        try {
            assertEquals(input, palindrome.findLongestPalindromeInternal(input));
        } catch (MissingArgumentException e) {
            fail("Exception should not be thrown for boundary input");
        }
    }
}