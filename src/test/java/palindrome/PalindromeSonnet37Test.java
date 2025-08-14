package palindrome;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

/**
 * Test class for the Palindrome class.
 * This test class implements both White Box (Condition/Decision coverage) 
 * and Black Box (Equivalence Partitioning and Boundary Value Analysis) testing methods.
 */
public class PalindromeSonnet37Test {

    private Palindrome palindrome;

    @BeforeEach
    public void setUp() {
        palindrome = new Palindrome();
    }

    // -------------------------------------------------------------------------
    // isPalindrome Method Tests - White Box Testing (Condition/Decision coverage)
    // -------------------------------------------------------------------------

    @DisplayName("Should throw MissingArgumentException when null is passed to isPalindrome")
    @Test
    public void testIsPalindromeWithNull() {
        assertThrows(MissingArgumentException.class, () -> palindrome.isPalindrome(null));
    }

    @DisplayName("Should return true for empty strings in isPalindrome")
    @ParameterizedTest
    @EmptySource
    public void testIsPalindromeWithEmptyString(String input) {
        try {
            assertTrue(palindrome.isPalindrome(input));
        } catch (MissingArgumentException e) {
            fail("Should not throw MissingArgumentException for empty string");
        }
    }

    @DisplayName("Should return true for single character strings in isPalindrome")
    @ParameterizedTest
    @ValueSource(strings = {"a", "z", "5", "!"})
    public void testIsPalindromeWithSingleCharacter(String input) {
        try {
            assertTrue(palindrome.isPalindrome(input));
        } catch (MissingArgumentException e) {
            fail("Should not throw MissingArgumentException");
        }
    }

    @DisplayName("Should correctly identify palindromes")
    @ParameterizedTest
    @ValueSource(strings = {"aba", "abba", "racecar", "madam", "123321"})//, "a man a plan a canal panama
    public void testIsPalindromeWithValidPalindromes(String input) {
        try {
            assertTrue(palindrome.isPalindrome(input));
        } catch (MissingArgumentException e) {
            fail("Should not throw MissingArgumentException");
        }
    }

    @DisplayName("Should correctly identify non-palindromes")
    @ParameterizedTest
    @ValueSource(strings = {"abc", "hello", "world", "123456"})
    public void testIsPalindromeWithNonPalindromes(String input) {
        try {
            assertFalse(palindrome.isPalindrome(input));
        } catch (MissingArgumentException e) {
            fail("Should not throw MissingArgumentException");
        }
    }

    // White Box Testing: Testing the while loop condition and execution paths in isPalindrome
    @DisplayName("Should verify all paths in isPalindrome method")
    @ParameterizedTest
    @MethodSource("providePalindromeTestCases")
    public void testIsPalindromeDecisionCoverage(String input, boolean expected) {
        try {
            assertEquals(expected, palindrome.isPalindrome(input));
        } catch (MissingArgumentException e) {
            fail("Should not throw MissingArgumentException");
        }
    }

    private static Stream<Arguments> providePalindromeTestCases() {
        return Stream.of(
            // Test case where while loop executes 0 times (empty string or single char)
            Arguments.of("", true),
            Arguments.of("a", true),
            
            // Test case where loop executes once and characters match
            Arguments.of("aa", true),
            
            // Test case where loop executes once and characters don't match
            Arguments.of("ab", false),
            
            // Test case where loop executes multiple times and all characters match
            Arguments.of("abcba", true),
            
            // Test case where loop executes multiple times but characters don't match
            Arguments.of("abcde", false),
            
            // Test case where the mismatch is at the beginning
           // Arguments.of("xbcbx", false),
            
            // Test case where the mismatch is at the end
            Arguments.of("abcbz", false),
            
            // Test case with even length palindrome
            Arguments.of("abccba", true),
            
            // Test case with odd length palindrome
            Arguments.of("abcdcba", true)
        );
    }

    // -------------------------------------------------------------------------
    // findLongestPalindromeInternal Method Tests - White Box Testing
    // -------------------------------------------------------------------------

    @DisplayName("Should throw MissingArgumentException when null is passed to findLongestPalindromeInternal")
    @Test
    public void testFindLongestPalindromeInternalWithNull() {
        assertThrows(MissingArgumentException.class, () -> palindrome.findLongestPalindromeInternal(null));
    }

    @DisplayName("Should return empty string when empty string is passed to findLongestPalindromeInternal")
    @ParameterizedTest
    @EmptySource
    public void testFindLongestPalindromeInternalWithEmptyString(String input) {
        try {
            assertEquals("", palindrome.findLongestPalindromeInternal(input));
        } catch (MissingArgumentException e) {
            fail("Should not throw MissingArgumentException for empty string");
        }
    }

    @DisplayName("Should return correct palindrome from start of string")
    @ParameterizedTest
    @MethodSource("provideInternalTestCases")
    public void testFindLongestPalindromeInternal(String input, String expected) {
        try {
            assertEquals(expected, palindrome.findLongestPalindromeInternal(input));
        } catch (MissingArgumentException e) {
            fail("Should not throw MissingArgumentException");
        }
    }

    private static Stream<Arguments> provideInternalTestCases() {
        return Stream.of(
            // Test for palindrome at start of string
            //Arguments.of("abacus", "a"),
            Arguments.of("radar", "radar"),
            Arguments.of("level1234", "level"),
            Arguments.of("abba123", "abba"),
            Arguments.of("abcba123", "abcba"),
            
            // Test where there's no palindrome longer than single character
            Arguments.of("xyz", "x"),
            
            // Test for edge cases
            Arguments.of("a", "a"),
            Arguments.of("ab", "a")
        );
    }

    // Tests for the while loop execution paths in findLongestPalindromeInternal
    @DisplayName("Should verify all decision paths in findLongestPalindromeInternal method")
    @ParameterizedTest
    @MethodSource("provideInternalDecisionCoverage")
    public void testFindLongestPalindromeInternalDecisionCoverage(String input, String expected) {
        try {
            assertEquals(expected, palindrome.findLongestPalindromeInternal(input));
        } catch (MissingArgumentException e) {
            fail("Should not throw MissingArgumentException");
        }
    }

    private static Stream<Arguments> provideInternalDecisionCoverage() {
        return Stream.of(
            // When the entire string is a palindrome
            Arguments.of("radar", "radar"),
            
            // When the entire string is not a palindrome but contains one
            Arguments.of("notapalindrome", "n"),
            
            // When multiple iterations of the loop are needed
            //Arguments.of("hello", "ll"),
            
            // When the index decreases all the way to 1
            Arguments.of("xyz", "x")
        );
    }

    // -------------------------------------------------------------------------
    // findLongestPalindrome Method Tests - White Box Testing
    // -------------------------------------------------------------------------

    @DisplayName("Should throw MissingArgumentException when null is passed to findLongestPalindrome")
    @Test
    public void testFindLongestPalindromeWithNull() {
        assertThrows(MissingArgumentException.class, () -> palindrome.findLongestPalindrome(null));
    }

    @DisplayName("Should return empty string when empty string is passed to findLongestPalindrome")
    @ParameterizedTest
    @EmptySource
    public void testFindLongestPalindromeWithEmptyString(String input) {
        try {
            assertEquals("", palindrome.findLongestPalindrome(input));
        } catch (MissingArgumentException e) {
            fail("Should not throw MissingArgumentException for empty string");
        }
    }

    @DisplayName("Should find the longest palindrome in a string")
    @ParameterizedTest
    @MethodSource("provideFindLongestPalindromeTestCases")
    public void testFindLongestPalindrome(String input, String expected) {
        try {
            assertEquals(expected, palindrome.findLongestPalindrome(input));
        } catch (MissingArgumentException e) {
            fail("Should not throw MissingArgumentException");
        }
    }

    private static Stream<Arguments> provideFindLongestPalindromeTestCases() {
        return Stream.of(
            // Test for palindrome anywhere in string
            Arguments.of("babad", "bab"),  // Could also be "aba"
            Arguments.of("cbbd", "bb"),
            Arguments.of("racecar", "racecar"),
            Arguments.of("hello", "ll"),
            //Arguments.of("abracadabra", "ada"),
            Arguments.of("bananas", "anana"),
            
            // Test for edge cases
            Arguments.of("a", "a"),
            Arguments.of("ab", "a"),  // Could also be "b"
            
            // Test where there are multiple palindromes of the same length
            Arguments.of("aabaa", "aabaa")
        );
    }

    // Tests for loop condition coverage in findLongestPalindrome
    @DisplayName("Should verify all loop conditions in findLongestPalindrome method")
    @ParameterizedTest
    @MethodSource("provideLoopCoverageTestCases")
    public void testFindLongestPalindromeLoopCoverage(String input, String expected) {
        try {
            assertEquals(expected, palindrome.findLongestPalindrome(input));
        } catch (MissingArgumentException e) {
            fail("Should not throw MissingArgumentException");
        }
    }

    private static Stream<Arguments> provideLoopCoverageTestCases() {
        return Stream.of(
            // Test for loop that executes 0 times (empty string)
            Arguments.of("", ""),
            
            // Test for loop that executes exactly once (single character)
            Arguments.of("a", "a"),
            
            // Test for loop that executes more than once
            Arguments.of("abc", "a"),  // Could also be "b" or "c"
            
            // Test for if condition inside loop (when current is longer than longest)
            Arguments.of("aba", "aba"),
            
            // Test for if condition inside loop (when current is not longer than longest)
            Arguments.of("abcabc", "a")  // Could also be "b" or "c"
        );
    }

    // -------------------------------------------------------------------------
    // Black Box Testing - Equivalence Partitioning and Boundary Value Analysis
    // -------------------------------------------------------------------------

    @DisplayName("Black Box Testing: Equivalence partitioning for isPalindrome")
    @ParameterizedTest
    @MethodSource("provideEquivalencePartitioningForIsPalindrome")
    public void testIsPalindromeEquivalencePartitioning(String input, boolean expected) {
        try {
            assertEquals(expected, palindrome.isPalindrome(input));
        } catch (MissingArgumentException e) {
            fail("Should not throw MissingArgumentException");
        }
    }

    private static Stream<Arguments> provideEquivalencePartitioningForIsPalindrome() {
        return Stream.of(
            // Empty string (boundary case)
            Arguments.of("", true),
            
            // Single character (boundary case)
            Arguments.of("x", true),
            
            // Two characters - same (boundary case)
            Arguments.of("aa", true),
            
            // Two characters - different (boundary case)
            Arguments.of("ab", false),
            
            // Even length palindrome
            Arguments.of("abccba", true),
            
            // Odd length palindrome
            Arguments.of("racecar", true),
            
            // Non-palindrome
            Arguments.of("hello", false),
            
            // Case sensitivity
            Arguments.of("Aba", false)
        );
    }

    @DisplayName("Black Box Testing: Boundary Value Analysis for findLongestPalindrome")
    @ParameterizedTest
    @MethodSource("provideBoundaryValueAnalysis")
    public void testFindLongestPalindromeBoundaryValueAnalysis(String input, String expected) {
        try {
            assertEquals(expected, palindrome.findLongestPalindrome(input));
        } catch (MissingArgumentException e) {
            fail("Should not throw MissingArgumentException");
        }
    }

    private static Stream<Arguments> provideBoundaryValueAnalysis() {
        return Stream.of(
            // Empty string
            Arguments.of("", ""),
            
            // Single character
            Arguments.of("a", "a"),
            
            // Two identical characters
            Arguments.of("aa", "aa"),
            
            // Two different characters
            Arguments.of("ab", "a"),
            
            // String with multiple same characters
            Arguments.of("aaa", "aaa"),
            
            // String with palindrome at beginning
            Arguments.of("abacdef", "aba"),
            
            // String with palindrome at end
            Arguments.of("defaba", "aba"),
            
            // String with palindrome in middle
            Arguments.of("defabaghi", "aba"),
            
            // String with multiple palindromes
            //Arguments.of("abacadaeafa", "afa"),
            
            // Entire string is a palindrome
            Arguments.of("rotator", "rotator")
        );
    }
}