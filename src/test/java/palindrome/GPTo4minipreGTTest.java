package palindrome;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GPTo4minipreGTTest {
    private Palindrome palindrome;

    @BeforeEach
    public void setUp() {
        palindrome = new Palindrome();
    }

    @Test
    public void testIsPalindrome_singleChar() throws MissingArgumentException {
        assertTrue(palindrome.isPalindrome("a"));
    }

    @Test
    public void testIsPalindrome_evenLength() throws MissingArgumentException {
        assertTrue(palindrome.isPalindrome("abba"));
    }

    @Test
    public void testIsPalindrome_false() throws MissingArgumentException {
        assertFalse(palindrome.isPalindrome("abca"));
    }
/* 
    @Test
    public void testIsPalindrome_null() throws MissingArgumentException {
        palindrome.isPalindrome(null);
    }
*/
    @Test
    public void testFindLongestPalindrome_simple() throws MissingArgumentException {
        assertEquals("anana", palindrome.findLongestPalindrome("bananas"));
    }

    @Test
    public void testFindLongestPalindrome_multipleMax() throws MissingArgumentException {
        String result = palindrome.findLongestPalindrome("babad");
        assertTrue(result.equals("bab") || result.equals("aba"));
    }

    @Test
    public void testFindLongestPalindrome_noPalindromeLongerThanOne() throws MissingArgumentException {
        assertEquals("a", palindrome.findLongestPalindrome("abc"));
    }

    @Test
    public void testFindLongestPalindrome_emptyString() throws MissingArgumentException {
        assertEquals("", palindrome.findLongestPalindrome(""));
    }
/*
    @Test
    public void testFindLongestPalindrome_null() throws MissingArgumentException {
        palindrome.findLongestPalindrome(null);
    }

    @Test
    public void testFindLongestPalindromeInternal_basic() throws MissingArgumentException {
        assertEquals("bb", palindrome.findLongestPalindromeInternal("cbbd"));
    }

    @Test
    public void testFindLongestPalindromeInternal_null() throws MissingArgumentException {
        palindrome.findLongestPalindromeInternal(null);
    }
    */
}