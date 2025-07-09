package palindrome;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Additional tests for Palindrome class.
 */
public class GPTo3miniGTTest {

    private final Palindrome palindrome = new Palindrome();

    @Test
    public void testIsPalindromeTrue() throws MissingArgumentException {
        assertTrue(palindrome.isPalindrome("racecar"));
        assertTrue(palindrome.isPalindrome("abba"));
        assertTrue(palindrome.isPalindrome("a"));
    }

    @Test
    public void testIsPalindromeFalse() throws MissingArgumentException {
        assertFalse(palindrome.isPalindrome("hello"));
        assertFalse(palindrome.isPalindrome("world"));
    }
/* 
    @Test
    public void testIsPalindromeNull() throws MissingArgumentException {
        palindrome.isPalindrome(null);
    }
*/
    @Test
    public void testFindLongestPalindromeSimple() throws MissingArgumentException {
        // When the entire string is a palindrome.
        String input = "aba";
        String expected = "aba";
        assertEquals(expected, palindrome.findLongestPalindrome(input));
    }

    @Test
    public void testFindLongestPalindromeMultipleOptions() throws MissingArgumentException {
        // For input "babad":
        // The algorithm finds the longest palindrome starting at some index,
        // which in this case is "bab" (from index 0) because:
        // "babad" -> not a full palindrome, "baba" -> not, then "bab" -> yes.
        String input = "babad";
        String expected = "bab";
        assertEquals(expected, palindrome.findLongestPalindrome(input));
    }
/*
    @Test
    public void testFindLongestPalindromeNull() throws MissingArgumentException {
        palindrome.findLongestPalindrome(null);
    }
*/
    @Test
    public void testFindLongestPalindromeInternal() throws MissingArgumentException {
        // For input "racecars", the longest palindrome at the beginning is "racecar".
        String input = "racecars";
        String expected = "racecar";
        assertEquals(expected, palindrome.findLongestPalindromeInternal(input));
    }
}