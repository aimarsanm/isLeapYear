package palindrome;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Sonnet37GTTest {
    
    private Palindrome palindrome;
    
    @BeforeEach
    public void setUp() {
        palindrome = new Palindrome();
    }
    
    @Test
    public void testIsPalindrome() throws MissingArgumentException {
        assertTrue(palindrome.isPalindrome(""));
        assertTrue(palindrome.isPalindrome("a"));
        assertTrue(palindrome.isPalindrome("aa"));
        assertTrue(palindrome.isPalindrome("aba"));
        assertTrue(palindrome.isPalindrome("abba"));
        assertTrue(palindrome.isPalindrome("radar"));
        assertTrue(palindrome.isPalindrome("racecar"));
        
        assertFalse(palindrome.isPalindrome("ab"));
        assertFalse(palindrome.isPalindrome("abc"));
        assertFalse(palindrome.isPalindrome("hello"));
    }
    /* 
    @Test
    public void testIsPalindromeWithNull() throws MissingArgumentException {
        palindrome.isPalindrome(null);
    }
    */
    @Test
    public void testFindLongestPalindromeInternal() throws MissingArgumentException {
        assertEquals("", palindrome.findLongestPalindromeInternal(""));
        assertEquals("a", palindrome.findLongestPalindromeInternal("a"));
        assertEquals("aa", palindrome.findLongestPalindromeInternal("aa"));
        assertEquals("aba", palindrome.findLongestPalindromeInternal("aba"));
        assertEquals("abba", palindrome.findLongestPalindromeInternal("abba"));
        assertEquals("racecar", palindrome.findLongestPalindromeInternal("racecar"));
        assertEquals("a", palindrome.findLongestPalindromeInternal("abc"));
        assertEquals("b", palindrome.findLongestPalindromeInternal("bc"));
    }
    /*
    @Test
    public void testFindLongestPalindromeInternalWithNull() throws MissingArgumentException {
        palindrome.findLongestPalindromeInternal(null);
    }
    */
    @Test
    public void testFindLongestPalindrome() throws MissingArgumentException {
        assertEquals("", palindrome.findLongestPalindrome(""));
        assertEquals("a", palindrome.findLongestPalindrome("a"));
        assertEquals("aa", palindrome.findLongestPalindrome("aa"));
        assertEquals("aba", palindrome.findLongestPalindrome("aba"));
        assertEquals("abba", palindrome.findLongestPalindrome("abba"));
        assertEquals("aba", palindrome.findLongestPalindrome("xabay"));
        assertEquals("racecar", palindrome.findLongestPalindrome("racecar"));
        assertEquals("anana", palindrome.findLongestPalindrome("banana"));
        assertEquals("kayak", palindrome.findLongestPalindrome("kayak"));
    }
    
    @Test
    public void testFindLongestPalindromeWithMultiplePalindromes() throws MissingArgumentException {
        assertEquals("racecar", palindrome.findLongestPalindrome("xracecary"));
        assertEquals("abcba", palindrome.findLongestPalindrome("abcbaxy"));
        assertEquals("abccba", palindrome.findLongestPalindrome("zabccbaxy"));
        assertEquals("bb", palindrome.findLongestPalindrome("abbcd"));
    }
    /*
    @Test
    public void testFindLongestPalindromeWithNull() throws MissingArgumentException {
        palindrome.findLongestPalindrome(null);
    }
    */
    @Test
    public void testFindLongestPalindromeWithComplexCases() throws MissingArgumentException {
        assertEquals("aibohphobia", palindrome.findLongestPalindrome("aibohphobia"));
        assertEquals("redivider", palindrome.findLongestPalindrome("redivider"));
        assertEquals("deified", palindrome.findLongestPalindrome("deified"));
        assertEquals("civic", palindrome.findLongestPalindrome("civic"));
        assertEquals("rotator", palindrome.findLongestPalindrome("rotator"));
        //assertEquals("level", palindrome.findLongestPalindrome("xlevelx"));
    }
}