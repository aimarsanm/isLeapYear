package palindrome;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Comprehensive test class for Palindrome using White Box (Condition/Decision Coverage)
 * and Black Box (Equivalence Partitioning and Boundary Value Analysis) testing methods.
 */
class Sonnet4Test {

    private Palindrome palindrome;

    @BeforeEach
    void setUp() {
        palindrome = new Palindrome();
    }

    // White Box Testing - findLongestPalindrome method
    
    @Test
    @DisplayName("findLongestPalindrome should throw MissingArgumentException when word is null")
    void findLongestPalindrome_NullInput_ThrowsException() {
        assertThrows(MissingArgumentException.class, () -> palindrome.findLongestPalindrome(null));
    }

    @Test
    @DisplayName("findLongestPalindrome should return empty string for empty input")
    void findLongestPalindrome_EmptyString_ReturnsEmptyString() {
        try {
            String result = palindrome.findLongestPalindrome("");
            assertEquals("", result);
        } catch (MissingArgumentException e) {
            fail("Should not throw exception for empty string");
        }
    }

    @Test
    @DisplayName("findLongestPalindrome should return single character for single character input")
    void findLongestPalindrome_SingleCharacter_ReturnsSameCharacter() {
        try {
            String result = palindrome.findLongestPalindrome("a");
            assertEquals("a", result);
        } catch (MissingArgumentException e) {
            fail("Should not throw exception for single character");
        }
    }

    @ParameterizedTest
    @DisplayName("findLongestPalindrome should find longest palindrome in various strings")
    @CsvSource({
        "aba, aba",
        "abcba, abcba", 
        "racecar, racecar",
        "abccba, abccba",
        "abcdef, a",
        "abcdefedcba, abcdefedcba",
       // "banana, ana",
        "malayalam, malayalam",
        "hello, ll",
       // "world, d"
    })
    void findLongestPalindrome_VariousInputs_ReturnsCorrectPalindrome(String input, String expected) {
        try {
            String result = palindrome.findLongestPalindrome(input);
            assertEquals(expected, result);
        } catch (MissingArgumentException e) {
            fail("Should not throw exception for valid input: " + input);
        }
    }
/* 
    @Test
    @DisplayName("findLongestPalindrome should handle string with multiple palindromes of same length")
    void findLongestPalindrome_MultiplePalindromesSameLength_ReturnsFirst() {
        try {
            String result = palindrome.findLongestPalindrome("abacabad");
            assertEquals("aba", result);
        } catch (MissingArgumentException e) {
            fail("Should not throw exception");
        }
    }
*/
    // White Box Testing - findLongestPalindromeInternal method

    @Test
    @DisplayName("findLongestPalindromeInternal should throw MissingArgumentException when word is null")
    void findLongestPalindromeInternal_NullInput_ThrowsException() {
        assertThrows(MissingArgumentException.class, () -> palindrome.findLongestPalindromeInternal(null));
    }

    @Test
    @DisplayName("findLongestPalindromeInternal should return empty string for empty input")
    void findLongestPalindromeInternal_EmptyString_ReturnsEmptyString() {
        try {
            String result = palindrome.findLongestPalindromeInternal("");
            assertEquals("", result);
        } catch (MissingArgumentException e) {
            fail("Should not throw exception for empty string");
        }
    }

    @ParameterizedTest
    @DisplayName("findLongestPalindromeInternal should find palindrome starting from beginning")
    @CsvSource({
        "a, a",
        "ab, a",
        "aba, aba",
        "abcd, a",
        "abcba, abcba",
        "racecar, racecar"
    })
    void findLongestPalindromeInternal_VariousInputs_ReturnsCorrectPalindrome(String input, String expected) {
        try {
            String result = palindrome.findLongestPalindromeInternal(input);
            assertEquals(expected, result);
        } catch (MissingArgumentException e) {
            fail("Should not throw exception for valid input: " + input);
        }
    }

    // White Box Testing - isPalindrome method (Condition/Decision Coverage)

    @Test
    @DisplayName("isPalindrome should throw MissingArgumentException when word is null")
    void isPalindrome_NullInput_ThrowsException() {
        assertThrows(MissingArgumentException.class, () -> palindrome.isPalindrome(null));
    }

    @Test
    @DisplayName("isPalindrome should return true for empty string")
    void isPalindrome_EmptyString_ReturnsTrue() {
        try {
            boolean result = palindrome.isPalindrome("");
            assertTrue(result);
        } catch (MissingArgumentException e) {
            fail("Should not throw exception for empty string");
        }
    }

    @Test
    @DisplayName("isPalindrome should return true for single character")
    void isPalindrome_SingleCharacter_ReturnsTrue() {
        try {
            boolean result = palindrome.isPalindrome("a");
            assertTrue(result);
        } catch (MissingArgumentException e) {
            fail("Should not throw exception for single character");
        }
    }

    @ParameterizedTest
    @DisplayName("isPalindrome should return true for valid palindromes")
    @ValueSource(strings = {"aa", "aba", "abba", "abcba", "racecar", "madam", "level", "noon"})
    void isPalindrome_ValidPalindromes_ReturnsTrue(String palindromeStr) {
        try {
            boolean result = palindrome.isPalindrome(palindromeStr);
            assertTrue(result);
        } catch (MissingArgumentException e) {
            fail("Should not throw exception for valid palindrome: " + palindromeStr);
        }
    }

    @ParameterizedTest
    @DisplayName("isPalindrome should return false for non-palindromes")
    @ValueSource(strings = {"ab", "abc", "abcd", "hello", "world", "test", "programming"})
    void isPalindrome_NonPalindromes_ReturnsFalse(String nonPalindrome) {
        try {
            boolean result = palindrome.isPalindrome(nonPalindrome);
            assertFalse(result);
        } catch (MissingArgumentException e) {
            fail("Should not throw exception for valid input: " + nonPalindrome);
        }
    }

    // Condition/Decision Coverage for isPalindrome while loop
    @Test
    @DisplayName("isPalindrome should handle two character palindrome")
    void isPalindrome_TwoCharacterPalindrome_ReturnsTrue() {
        try {
            boolean result = palindrome.isPalindrome("aa");
            assertTrue(result);
        } catch (MissingArgumentException e) {
            fail("Should not throw exception");
        }
    }

    @Test
    @DisplayName("isPalindrome should handle two character non-palindrome")
    void isPalindrome_TwoCharacterNonPalindrome_ReturnsFalse() {
        try {
            boolean result = palindrome.isPalindrome("ab");
            assertFalse(result);
        } catch (MissingArgumentException e) {
            fail("Should not throw exception");
        }
    }

    // Black Box Testing - Boundary Value Analysis

    @Test
    @DisplayName("Boundary: Single character string")
    void boundaryTest_SingleCharacter_AllMethods() {
        try {
            // Test all methods with single character
            assertEquals("a", palindrome.findLongestPalindrome("a"));
            assertEquals("a", palindrome.findLongestPalindromeInternal("a"));
            assertTrue(palindrome.isPalindrome("a"));
        } catch (MissingArgumentException e) {
            fail("Should not throw exception for single character");
        }
    }

    @Test
    @DisplayName("Boundary: Two character strings")
    void boundaryTest_TwoCharacters_AllMethods() {
        try {
            // Palindrome case
            assertEquals("aa", palindrome.findLongestPalindrome("aa"));
            assertEquals("aa", palindrome.findLongestPalindromeInternal("aa"));
            assertTrue(palindrome.isPalindrome("aa"));
            
            // Non-palindrome case
            assertEquals("a", palindrome.findLongestPalindrome("ab"));
            assertEquals("a", palindrome.findLongestPalindromeInternal("ab"));
            assertFalse(palindrome.isPalindrome("ab"));
        } catch (MissingArgumentException e) {
            fail("Should not throw exception for two characters");
        }
    }

    // Black Box Testing - Equivalence Partitioning

    @Test
    @DisplayName("Equivalence Partition: All same characters")
    void equivalencePartition_AllSameCharacters() {
        try {
            String input = "aaaa";
            assertEquals("aaaa", palindrome.findLongestPalindrome(input));
            assertEquals("aaaa", palindrome.findLongestPalindromeInternal(input));
            assertTrue(palindrome.isPalindrome(input));
        } catch (MissingArgumentException e) {
            fail("Should not throw exception");
        }
    }

    @Test
    @DisplayName("Equivalence Partition: No palindromes longer than 1")
    void equivalencePartition_NoLongPalindromes() {
        try {
            String input = "abcdef";
            assertEquals("a", palindrome.findLongestPalindrome(input));
            assertEquals("a", palindrome.findLongestPalindromeInternal(input));
            assertFalse(palindrome.isPalindrome(input));
        } catch (MissingArgumentException e) {
            fail("Should not throw exception");
        }
    }

    @Test
    @DisplayName("Equivalence Partition: Entire string is palindrome")
    void equivalencePartition_EntireStringIsPalindrome() {
        try {
            String input = "racecar";
            assertEquals("racecar", palindrome.findLongestPalindrome(input));
            assertEquals("racecar", palindrome.findLongestPalindromeInternal(input));
            assertTrue(palindrome.isPalindrome(input));
        } catch (MissingArgumentException e) {
            fail("Should not throw exception");
        }
    }

    @Test
    @DisplayName("Equivalence Partition: Palindrome at the end")
    void equivalencePartition_PalindromeAtEnd() {
        try {
            String input = "abcdd";
            assertEquals("dd", palindrome.findLongestPalindrome(input));
        } catch (MissingArgumentException e) {
            fail("Should not throw exception");
        }
    }
/* 
    @Test
    @DisplayName("Equivalence Partition: Palindrome in the middle")
    void equivalencePartition_PalindromeInMiddle() {
        try {
            String input = "abcbax";
            assertEquals("bcb", palindrome.findLongestPalindrome(input));
        } catch (MissingArgumentException e) {
            fail("Should not throw exception");
        }
    }
*/
    // Edge cases for complete coverage
    @Test
    @DisplayName("Edge case: Long string with palindrome at beginning")
    void edgeCase_LongStringPalindromeAtBeginning() {
        try {
            String input = "abcbadef";
            assertEquals("abcba", palindrome.findLongestPalindrome(input));
            assertEquals("abcba", palindrome.findLongestPalindromeInternal(input));
        } catch (MissingArgumentException e) {
            fail("Should not throw exception");
        }
    }

    @Test
    @DisplayName("Edge case: String where first character forms longest palindrome")
    void edgeCase_FirstCharacterLongestPalindrome() {
        try {
            String input = "abcdefg";
            assertEquals("a", palindrome.findLongestPalindrome(input));
            assertEquals("a", palindrome.findLongestPalindromeInternal(input));
        } catch (MissingArgumentException e) {
            fail("Should not throw exception");
        }
    }
}