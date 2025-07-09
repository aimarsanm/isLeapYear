package palindrome;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GPTo1preGTTest {

    private Palindrome palindrome;

    @BeforeEach
    public void setUp() {
        palindrome = new Palindrome();
    }
/* 
    @Test
    public void testFindLongestPalindromeWithNull() throws MissingArgumentException {
        palindrome.findLongestPalindrome(null);
    }
*/
    @Test
    public void testFindLongestPalindromeWithEmptyString() throws MissingArgumentException {
        assertEquals("", palindrome.findLongestPalindrome(""));
    }

    @Test
    public void testFindLongestPalindromeWithSingleChar() throws MissingArgumentException {
        assertEquals("a", palindrome.findLongestPalindrome("a"));
    }

    @Test
    public void testFindLongestPalindromeNormal() throws MissingArgumentException {
        assertEquals("racecar", palindrome.findLongestPalindrome("abcracecardef"));
    }

    @Test
    public void testFindLongestPalindromeInternal() throws MissingArgumentException {
        assertEquals("noon", palindrome.findLongestPalindromeInternal("noonabc"));
    }

    @Test
    public void testIsPalindromeFalse() throws MissingArgumentException {
        assertFalse(palindrome.isPalindrome("abc"));
    }
/* 
    @Test
    public void testIsPalindromeNull() throws MissingArgumentException {
        palindrome.isPalindrome(null);
    }*/
}