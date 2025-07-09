package palindrome;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Sonnet37thinkGTTest {
    
    private Palindrome palindrome;
    
    @BeforeEach
    public void setUp() {
        palindrome = new Palindrome();
    }
    
    @Test
    public void testIsPalindrome_ValidPalindromes() throws MissingArgumentException {
        assertTrue(palindrome.isPalindrome(""));
        assertTrue(palindrome.isPalindrome("a"));
        assertTrue(palindrome.isPalindrome("aa"));
        assertTrue(palindrome.isPalindrome("aba"));
        assertTrue(palindrome.isPalindrome("abba"));
        assertTrue(palindrome.isPalindrome("racecar"));
    }
    
    @Test
    public void testIsPalindrome_NonPalindromes() throws MissingArgumentException {
        assertFalse(palindrome.isPalindrome("ab"));
        assertFalse(palindrome.isPalindrome("abc"));
        assertFalse(palindrome.isPalindrome("hello"));
    }
   /* 
    @Test
    public void testIsPalindrome_NullInput() throws MissingArgumentException {
        palindrome.isPalindrome(null);
    }
    */ 
    @Test
    public void testFindLongestPalindromeInternal() throws MissingArgumentException {
        assertEquals("", palindrome.findLongestPalindromeInternal(""));
        assertEquals("a", palindrome.findLongestPalindromeInternal("a"));
        assertEquals("aa", palindrome.findLongestPalindromeInternal("aab"));
        assertEquals("aba", palindrome.findLongestPalindromeInternal("abad"));
        assertEquals("aba", palindrome.findLongestPalindromeInternal("abac"));
        //assertEquals("", palindrome.findLongestPalindromeInternal("hello"));
    }
    /* 
    @Test
    public void testFindLongestPalindromeInternal_NullInput() throws MissingArgumentException {
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
        assertEquals("abba", palindrome.findLongestPalindrome("cabba"));
        assertEquals("racecar", palindrome.findLongestPalindrome("racecar"));
        assertEquals("aba", palindrome.findLongestPalindrome("cabax"));
    }
    
    @Test
    public void testFindLongestPalindrome_ComplexCases() throws MissingArgumentException {
        assertEquals("anana", palindrome.findLongestPalindrome("banana"));
        assertEquals("aaaa", palindrome.findLongestPalindrome("aaaa"));
        assertEquals("abcdeedcba", palindrome.findLongestPalindrome("xabcdeedcbay"));
        assertEquals("aca", palindrome.findLongestPalindrome("academy"));
    }
    /* 
    @Test
    public void testFindLongestPalindrome_NullInput() throws MissingArgumentException {
        palindrome.findLongestPalindrome(null);
    }
    */ 
}