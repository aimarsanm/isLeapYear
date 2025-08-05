package palindrome;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class PalindromeGTTest {

    private final Palindrome palindrome = new Palindrome();

    @Test
    void testFindLongestPalindrome_NullInput_ThrowsException() {
        assertThrows(MissingArgumentException.class, () -> palindrome.findLongestPalindrome(null));
    }

    @Test
    void testFindLongestPalindrome_EmptyString_ReturnsEmpty() throws MissingArgumentException {
        assertEquals("", palindrome.findLongestPalindrome(""));
    }

    @Test
    void testFindLongestPalindrome_SingleChar_ReturnsChar() throws MissingArgumentException {
        assertEquals("a", palindrome.findLongestPalindrome("a"));
    }

    @Test
    void testFindLongestPalindrome_PalindromeWord_ReturnsWord() throws MissingArgumentException {
        assertEquals("racecar", palindrome.findLongestPalindrome("racecar"));
    }

    @Test
    void testFindLongestPalindrome_MultiplePalindromes_ReturnsLongest() throws MissingArgumentException {
        assertEquals("anana", palindrome.findLongestPalindrome("bananas"));
    }

    @Test
    void testFindLongestPalindrome_NoPalindrome_ReturnsFirstChar() throws MissingArgumentException {
        assertEquals("a", palindrome.findLongestPalindrome("abc"));
    }

    @Test
    void testFindLongestPalindromeInternal_NullInput_ThrowsException() {
        assertThrows(MissingArgumentException.class, () -> palindrome.findLongestPalindromeInternal(null));
    }

    @Test
    void testFindLongestPalindromeInternal_PalindromeAtStart() throws MissingArgumentException {
        assertEquals("aba", palindrome.findLongestPalindromeInternal("abac"));
    }

    @Test
    void testIsPalindrome_NullInput_ThrowsException() {
        assertThrows(MissingArgumentException.class, () -> palindrome.isPalindrome(null));
    }

    @Test
    void testIsPalindrome_TrueCases() throws MissingArgumentException {
        assertTrue(palindrome.isPalindrome("madam"));
        assertTrue(palindrome.isPalindrome("a"));
        assertTrue(palindrome.isPalindrome(""));
    }

    @Test
    void testIsPalindrome_FalseCases() throws MissingArgumentException {
        assertFalse(palindrome.isPalindrome("hello"));
        assertFalse(palindrome.isPalindrome("ab"));
    }
}