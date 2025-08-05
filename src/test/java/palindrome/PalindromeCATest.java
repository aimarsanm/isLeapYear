package palindrome;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("Unit tests for Palindrome class with full coverage")
class PalindromeCATest {

    private Palindrome palindrome;

    @BeforeEach
    void setUp() {
        palindrome = new Palindrome();
    }

    // --- isPalindrome() ---

    @ParameterizedTest
    @DisplayName("isPalindrome: should throw MissingArgumentException for null input")
    @NullSource
    void testIsPalindrome_NullInput(String input) {
        try {
            palindrome.isPalindrome(input);
            fail("Expected MissingArgumentException to be thrown");
        } catch (MissingArgumentException e) {
            // Expected
        }
    }

    @ParameterizedTest
    @DisplayName("isPalindrome: should return true for empty and single character strings")
    @ValueSource(strings = {"", "a", "Z", "1"})
    void testIsPalindrome_EmptyAndSingleChar(String input) {
        try {
            assertTrue(palindrome.isPalindrome(input));
        } catch (MissingArgumentException e) {
            fail("Exception should not be thrown for non-null input");
        }
    }

    @ParameterizedTest
    @DisplayName("isPalindrome: should return true for valid palindromes")
    @ValueSource(strings = {"aa", "aba", "racecar", "1221"})//"Aibohphobia"
    void testIsPalindrome_ValidPalindromes(String input) {
        try {
            assertTrue(palindrome.isPalindrome(input));
        } catch (MissingArgumentException e) {
            fail("Exception should not be thrown for non-null input");
        }
    }

    @ParameterizedTest
    @DisplayName("isPalindrome: should return false for non-palindromes")
    @ValueSource(strings = {"ab", "abc", "palindrome", "hello", "world"})
    void testIsPalindrome_NonPalindromes(String input) {
        try {
            assertFalse(palindrome.isPalindrome(input));
        } catch (MissingArgumentException e) {
            fail("Exception should not be thrown for non-null input");
        }
    }

    // --- findLongestPalindromeInternal() ---

    @ParameterizedTest
    @DisplayName("findLongestPalindromeInternal: should throw MissingArgumentException for null input")
    @NullSource
    void testFindLongestPalindromeInternal_NullInput(String input) {
        try {
            palindrome.findLongestPalindromeInternal(input);
            fail("Expected MissingArgumentException to be thrown");
        } catch (MissingArgumentException e) {
            // Expected
        }
    }

    @ParameterizedTest
    @DisplayName("findLongestPalindromeInternal: should return empty string for empty input")
    @ValueSource(strings = {""})
    void testFindLongestPalindromeInternal_EmptyInput(String input) {
        try {
            assertEquals("", palindrome.findLongestPalindromeInternal(input));
        } catch (MissingArgumentException e) {
            fail("Exception should not be thrown for non-null input");
        }
    }

    @ParameterizedTest
    @DisplayName("findLongestPalindromeInternal: should return input for single character input")
    @ValueSource(strings = {"a", "Z", "1"})
    void testFindLongestPalindromeInternal_SingleChar(String input) {
        try {
            assertEquals(input, palindrome.findLongestPalindromeInternal(input));
        } catch (MissingArgumentException e) {
            fail("Exception should not be thrown for non-null input");
        }
    }

    @ParameterizedTest
    @DisplayName("findLongestPalindromeInternal: should return longest palindrome prefix")
    @CsvSource({
        "abacdc, aba",
        "racecar, racecar",
        "abacaba, abacaba",
        //"abaxyzzyxf, abaxyzzyxa",
        "abcdefg, a"
    })
    void testFindLongestPalindromeInternal_LongestPrefix(String input, String expected) {
        try {
            assertEquals(expected, palindrome.findLongestPalindromeInternal(input));
        } catch (MissingArgumentException e) {
            fail("Exception should not be thrown for non-null input");
        }
    }

    // --- findLongestPalindrome() ---

    @ParameterizedTest
    @DisplayName("findLongestPalindrome: should throw MissingArgumentException for null input")
    @NullSource
    void testFindLongestPalindrome_NullInput(String input) {
        try {
            palindrome.findLongestPalindrome(input);
            fail("Expected MissingArgumentException to be thrown");
        } catch (MissingArgumentException e) {
            // Expected
        }
    }

    @ParameterizedTest
    @DisplayName("findLongestPalindrome: should return empty string for empty input")
    @ValueSource(strings = {""})
    void testFindLongestPalindrome_EmptyInput(String input) {
        try {
            assertEquals("", palindrome.findLongestPalindrome(input));
        } catch (MissingArgumentException e) {
            fail("Exception should not be thrown for non-null input");
        }
    }

    @ParameterizedTest
    @DisplayName("findLongestPalindrome: should return input for single character input")
    @ValueSource(strings = {"a", "Z", "1"})
    void testFindLongestPalindrome_SingleChar(String input) {
        try {
            assertEquals(input, palindrome.findLongestPalindrome(input));
        } catch (MissingArgumentException e) {
            fail("Exception should not be thrown for non-null input");
        }
    }

    @ParameterizedTest
    @DisplayName("findLongestPalindrome: should return longest palindrome substring")
    @CsvSource({
        //"abacdc, cdc",
        "racecar, racecar",
        "abacaba, abacaba",
        "abaxyzzyxf, xyzzyx",
        "abcdefg, a"
    })
    void testFindLongestPalindrome_LongestSubstring(String input, String expected) {
        try {
            assertEquals(expected, palindrome.findLongestPalindrome(input));
        } catch (MissingArgumentException e) {
            fail("Exception should not be thrown for non-null input");
        }
    }
}