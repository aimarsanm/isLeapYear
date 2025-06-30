package palindrome;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

// filepath: src/test/java/palindrome/PalindromeTest.java



/**
 * Unit tests for Palindrome class using White Box (Condition/Decision) and Black Box 
 * (Equivalence Partitioning and Boundary Value Analysis) techniques.
 */
public class GPTo3miniTest {

    private Palindrome palindrome;

    @BeforeEach
    public void setUp() {
        palindrome = new Palindrome();
    }

    // Tests for isPalindrome()

    @ParameterizedTest
    @CsvSource({
        "'racecar', true",
        "'madam', true",
        "'hello', false",
        "'', true",         // boundary: empty string
        "'a', true"         // single character is palindrome
    })
    @DisplayName("Test isPalindrome with valid non-null inputs")
    public void testIsPalindrome(String input, boolean expected) {
        try {
            boolean result = palindrome.isPalindrome(input);
            assertEquals(expected, result, "isPalindrome returned unexpected result for input: " + input);
        } catch (MissingArgumentException e) {
            fail("Unexpected MissingArgumentException for non-null input: " + input);
        }
    }

    @Test
    @DisplayName("Test isPalindrome with null input throws MissingArgumentException")
    public void testIsPalindromeNull() {
        assertThrows(MissingArgumentException.class, () -> {
            palindrome.isPalindrome(null);
        }, "isPalindrome did not throw MissingArgumentException for null input");
    }

    // Tests for findLongestPalindromeInternal()

    @ParameterizedTest
    @CsvSource({
        "'babad', bab",     // check normal case reduction to prefix palindrome
        "'racecar', racecar",// already a palindrome
        "'ab', a",           // reduction at second character fails, so returns first char
        "'a', a",            // single character word
        "'', ''"             // empty string boundary
    })
    @DisplayName("Test findLongestPalindromeInternal with valid non-null inputs")
    public void testFindLongestPalindromeInternal(String input, String expected) {
        try {
            String result = palindrome.findLongestPalindromeInternal(input);
            assertEquals(expected, result, "findLongestPalindromeInternal returned unexpected result for input: " + input);
        } catch (MissingArgumentException e) {
            fail("Unexpected MissingArgumentException for non-null input: " + input);
        }
    }

    @Test
    @DisplayName("Test findLongestPalindromeInternal with null input throws MissingArgumentException")
    public void testFindLongestPalindromeInternalNull() {
        assertThrows(MissingArgumentException.class, () -> {
            palindrome.findLongestPalindromeInternal(null);
        }, "findLongestPalindromeInternal did not throw MissingArgumentException for null input");
    }

    // Tests for findLongestPalindrome()

    @ParameterizedTest
    @CsvSource({
        "'babad', bab",      // loops through all substrings; first longest encountered is returned
        "'racecar', racecar", // full palindrome returns itself
        "'ab', a",           // returns first character
        "'a', a",            // single char
        "'', ''"             // empty string boundary
    })
    @DisplayName("Test findLongestPalindrome with valid non-null inputs")
    public void testFindLongestPalindrome(String input, String expected) {
        try {
            String result = palindrome.findLongestPalindrome(input);
            assertEquals(expected, result, "findLongestPalindrome returned unexpected result for input: " + input);
        } catch (MissingArgumentException e) {
            fail("Unexpected MissingArgumentException for non-null input: " + input);
        }
    }

    @Test
    @DisplayName("Test findLongestPalindrome with null input throws MissingArgumentException")
    public void testFindLongestPalindromeNull() {
        assertThrows(MissingArgumentException.class, () -> {
            palindrome.findLongestPalindrome(null);
        }, "findLongestPalindrome did not throw MissingArgumentException for null input");
    }
}