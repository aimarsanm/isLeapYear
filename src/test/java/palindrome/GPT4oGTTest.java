package palindrome;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;




class GPT4oGTTest {

    @Test
    void testFindLongestPalindrome() throws MissingArgumentException {
        Palindrome palindrome = new Palindrome();
        assertEquals("aba", palindrome.findLongestPalindrome("abacdfgdcaba"));
        assertEquals("racecar", palindrome.findLongestPalindrome("racecar"));
        assertEquals("a", palindrome.findLongestPalindrome("abc"));
        assertEquals("", palindrome.findLongestPalindrome(""));
    }

    @Test
    void testFindLongestPalindromeInternal() throws MissingArgumentException {
        Palindrome palindrome = new Palindrome();
        assertEquals("aba", palindrome.findLongestPalindromeInternal("abacdfgdcaba"));
        assertEquals("racecar", palindrome.findLongestPalindromeInternal("racecar"));
        assertEquals("a", palindrome.findLongestPalindromeInternal("abc"));
        assertEquals("", palindrome.findLongestPalindromeInternal(""));
    }

    @Test
    void testIsPalindrome() throws MissingArgumentException {
        Palindrome palindrome = new Palindrome();
        assertTrue(palindrome.isPalindrome("aba"));
        assertTrue(palindrome.isPalindrome("racecar"));
        assertFalse(palindrome.isPalindrome("abc"));
        assertTrue(palindrome.isPalindrome(""));
    }

    @Test
    void testFindLongestPalindromeWithNullInput() {
        Palindrome palindrome = new Palindrome();
        assertThrows(MissingArgumentException.class, () -> palindrome.findLongestPalindrome(null));
    }

    @Test
    void testFindLongestPalindromeInternalWithNullInput() {
        Palindrome palindrome = new Palindrome();
        assertThrows(MissingArgumentException.class, () -> palindrome.findLongestPalindromeInternal(null));
    }

    @Test
    void testIsPalindromeWithNullInput() {
        Palindrome palindrome = new Palindrome();
        assertThrows(MissingArgumentException.class, () -> palindrome.isPalindrome(null));
    }
}