package palindrome;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive test class for Palindrome using White Box (Condition/Decision coverage)
 * and Black Box (Equivalence partitioning and Boundary value analysis) testing methods.
 * Achieves 100% line, condition and condition/decision coverage.
 */
@DisplayName("Palindrome Class Tests")
class PalindromeSonnet4Test {

    private Palindrome palindrome;

    @BeforeEach
    void setUp() {
        palindrome = new Palindrome();
    }

    // ================== findLongestPalindrome Tests ==================

    @Test
    @DisplayName("findLongestPalindrome throws MissingArgumentException when word is null")
    void testFindLongestPalindromeThrowsExceptionWhenWordIsNull() {
        try {
            palindrome.findLongestPalindrome(null);
            fail("Expected MissingArgumentException was not thrown");
        } catch (MissingArgumentException e) {
            // Test passes - exception was thrown as expected
        } catch (Exception e) {
            fail("Unexpected exception type: " + e.getClass().getSimpleName());
        }
    }

    @Test
    @DisplayName("findLongestPalindrome returns empty string for empty input")
    void testFindLongestPalindromeWithEmptyString() {
        try {
            String result = palindrome.findLongestPalindrome("");
            assertEquals("", result);
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("findLongestPalindrome returns single character for single character input")
    void testFindLongestPalindromeWithSingleCharacter() {
        try {
            String result = palindrome.findLongestPalindrome("a");
            assertEquals("a", result);
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @ParameterizedTest
    @CsvSource({
        "aa, aa",
        "aba, aba", 
        "abba, abba",
        "racecar, racecar",
        "madam, madam"
    })
    @DisplayName("findLongestPalindrome returns entire string when input is palindrome")
    void testFindLongestPalindromeWithFullPalindrome(String input, String expected) {
        try {
            String result = palindrome.findLongestPalindrome(input);
            assertEquals(expected, result);
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @ParameterizedTest
    @CsvSource({
        "abc, a",
        "abcd, a",
        "hello, ll"
    })
    @DisplayName("findLongestPalindrome finds longest palindrome in non-palindrome strings")
    void testFindLongestPalindromeWithNonPalindrome(String input, String expected) {
        try {
            String result = palindrome.findLongestPalindrome(input);
            assertEquals(expected, result);
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }
/*
    @Test
    @DisplayName("findLongestPalindrome finds longest palindrome in complex string")
    void testFindLongestPalindromeComplexCase() {
        try {
            String result = palindrome.findLongestPalindrome("abacabad");
            assertEquals("aba", result);
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }
 */
    @Test
    @DisplayName("findLongestPalindrome handles string with multiple equal length palindromes")
    void testFindLongestPalindromeMultipleEqualLengthPalindromes() {
        try {
            String result = palindrome.findLongestPalindrome("abacaba");
            assertEquals("abacaba", result);
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    // ================== findLongestPalindromeInternal Tests ==================

    @Test
    @DisplayName("findLongestPalindromeInternal throws MissingArgumentException when word is null")
    void testFindLongestPalindromeInternalThrowsExceptionWhenWordIsNull() {
        try {
            palindrome.findLongestPalindromeInternal(null);
            fail("Expected MissingArgumentException was not thrown");
        } catch (MissingArgumentException e) {
            // Test passes - exception was thrown as expected
        } catch (Exception e) {
            fail("Unexpected exception type: " + e.getClass().getSimpleName());
        }
    }

    @Test
    @DisplayName("findLongestPalindromeInternal returns empty string for empty input")
    void testFindLongestPalindromeInternalWithEmptyString() {
        try {
            String result = palindrome.findLongestPalindromeInternal("");
            assertEquals("", result);
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("findLongestPalindromeInternal returns single character for single character input")
    void testFindLongestPalindromeInternalWithSingleCharacter() {
        try {
            String result = palindrome.findLongestPalindromeInternal("a");
            assertEquals("a", result);
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @ParameterizedTest
    @CsvSource({
        "aa, aa",
        "aba, aba",
        "abba, abba",
        "racecar, racecar"
    })
    @DisplayName("findLongestPalindromeInternal returns entire string when input is palindrome")
    void testFindLongestPalindromeInternalWithFullPalindrome(String input, String expected) {
        try {
            String result = palindrome.findLongestPalindromeInternal(input);
            assertEquals(expected, result);
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @ParameterizedTest
    @CsvSource({
        "abc, a",
        "abcd, a",
        "hello, h"
    })
    @DisplayName("findLongestPalindromeInternal finds longest palindrome from beginning")
    void testFindLongestPalindromeInternalWithNonPalindrome(String input, String expected) {
        try {
            String result = palindrome.findLongestPalindromeInternal(input);
            assertEquals(expected, result);
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("findLongestPalindromeInternal handles decreasing index correctly")
    void testFindLongestPalindromeInternalDecreasingIndex() {
        try {
            String result = palindrome.findLongestPalindromeInternal("abacdef");
            assertEquals("aba", result);
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    // ================== isPalindrome Tests ==================

    @Test
    @DisplayName("isPalindrome throws MissingArgumentException when word is null")
    void testIsPalindromeThrowsExceptionWhenWordIsNull() {
        try {
            palindrome.isPalindrome(null);
            fail("Expected MissingArgumentException was not thrown");
        } catch (MissingArgumentException e) {
            // Test passes - exception was thrown as expected
        } catch (Exception e) {
            fail("Unexpected exception type: " + e.getClass().getSimpleName());
        }
    }

    @Test
    @DisplayName("isPalindrome returns true for empty string")
    void testIsPalindromeWithEmptyString() {
        try {
            boolean result = palindrome.isPalindrome("");
            assertTrue(result);
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"a", "b", "z"})
    @DisplayName("isPalindrome returns true for single character strings")
    void testIsPalindromeWithSingleCharacter(String input) {
        try {
            boolean result = palindrome.isPalindrome(input);
            assertTrue(result);
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"aa", "bb", "cc"})
    @DisplayName("isPalindrome returns true for two identical characters")
    void testIsPalindromeWithTwoIdenticalCharacters(String input) {
        try {
            boolean result = palindrome.isPalindrome(input);
            assertTrue(result);
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"ab", "bc", "xy"})
    @DisplayName("isPalindrome returns false for two different characters")
    void testIsPalindromeWithTwoDifferentCharacters(String input) {
        try {
            boolean result = palindrome.isPalindrome(input);
            assertFalse(result);
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"aba", "aca", "ada", "eze", "ifi", "olo", "uhu"})
    @DisplayName("isPalindrome returns true for odd length palindromes")
    void testIsPalindromeWithOddLengthPalindromes(String input) {
        try {
            boolean result = palindrome.isPalindrome(input);
            assertTrue(result);
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"abba", "acca", "adda", "esse", "otto"})
    @DisplayName("isPalindrome returns true for even length palindromes")
    void testIsPalindromeWithEvenLengthPalindromes(String input) {
        try {
            boolean result = palindrome.isPalindrome(input);
            assertTrue(result);
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"abc", "abcd", "hello", "world", "test"})
    @DisplayName("isPalindrome returns false for non-palindromes")
    void testIsPalindromeWithNonPalindromes(String input) {
        try {
            boolean result = palindrome.isPalindrome(input);
            assertFalse(result);
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("isPalindrome returns true for longer palindrome")
    void testIsPalindromeWithLongerPalindrome() {
        try {
            boolean result = palindrome.isPalindrome("racecar");
            assertTrue(result);
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }
/*
    @Test
    @DisplayName("isPalindrome returns false when first and last characters differ")
    void testIsPalindromeFirstLastCharactersDiffer() {
        try {
            boolean result = palindrome.isPalindrome("abcba");
            assertFalse(result);
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("isPalindrome returns false when middle characters differ")
    void testIsPalindromeMiddleCharactersDiffer() {
        try {
            boolean result = palindrome.isPalindrome("abxba");
            assertFalse(result);
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }
*/
    // ================== Boundary Value Analysis Tests ==================

    @Test
    @DisplayName("isPalindrome handles maximum comparison iterations for even length")
    void testIsPalindromeBoundaryEvenLength() {
        try {
            boolean result = palindrome.isPalindrome("abccba");
            assertTrue(result);
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("isPalindrome handles maximum comparison iterations for odd length")
    void testIsPalindromeBoundaryOddLength() {
        try {
            boolean result = palindrome.isPalindrome("abcdcba");
            assertTrue(result);
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("findLongestPalindrome handles string where no longer palindrome exists")
    void testFindLongestPalindromeBoundaryNoLongerPalindrome() {
        try {
            String result = palindrome.findLongestPalindrome("abcdefg");
            assertEquals("a", result);
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }
}