package palindrome;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;




public class Sonnet4GTTest {
    
    private Palindrome palindrome;
    
    @BeforeEach
    public void setUp() {
        palindrome = new Palindrome();
    }
    
    @Test
    public void testIsPalindrome_withValidPalindrome() throws MissingArgumentException {
        assertTrue(palindrome.isPalindrome("racecar"));
        assertTrue(palindrome.isPalindrome("level"));
        assertTrue(palindrome.isPalindrome("a"));
        assertTrue(palindrome.isPalindrome(""));
    }
    
    @Test
    public void testIsPalindrome_withNonPalindrome() throws MissingArgumentException {
        assertFalse(palindrome.isPalindrome("hello"));
        assertFalse(palindrome.isPalindrome("world"));
        assertFalse(palindrome.isPalindrome("abc"));
    }
    
    @Test
    public void testIsPalindrome_withNull() {
        assertThrows(MissingArgumentException.class, () -> {
            palindrome.isPalindrome(null);
        });
    }
    
    @Test
    public void testFindLongestPalindrome_withValidInput() throws MissingArgumentException {
        assertEquals("racecar", palindrome.findLongestPalindrome("racecar"));
        //assertEquals("aba", palindrome.findLongestPalindrome("abacabad"));
        assertEquals("aa", palindrome.findLongestPalindrome("aab"));
        assertEquals("a", palindrome.findLongestPalindrome("abc"));
    }
    
    @Test
    public void testFindLongestPalindrome_withEmptyString() throws MissingArgumentException {
        assertEquals("", palindrome.findLongestPalindrome(""));
    }
    
    @Test
    public void testFindLongestPalindrome_withNull() {
        assertThrows(MissingArgumentException.class, () -> {
            palindrome.findLongestPalindrome(null);
        });
    }
    
    @Test
    public void testFindLongestPalindromeInternal_withValidInput() throws MissingArgumentException {
        assertEquals("racecar", palindrome.findLongestPalindromeInternal("racecar"));
        assertEquals("aba", palindrome.findLongestPalindromeInternal("aba"));
        assertEquals("a", palindrome.findLongestPalindromeInternal("abc"));
        assertEquals("", palindrome.findLongestPalindromeInternal(""));
    }
    
    @Test
    public void testFindLongestPalindromeInternal_withNull() {
        assertThrows(MissingArgumentException.class, () -> {
            palindrome.findLongestPalindromeInternal(null);
        });
    }
}