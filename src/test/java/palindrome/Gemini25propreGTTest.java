package palindrome;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class Gemini25propreGTTest {

    private final Palindrome palindrome = new Palindrome();

    @Test
    public void testIsPalindromeWithPalindromeWord() throws MissingArgumentException {
        assertTrue(palindrome.isPalindrome("racecar"));
    }

    @Test
    public void testIsPalindromeWithNonPalindromeWord() throws MissingArgumentException {
        assertFalse(palindrome.isPalindrome("hello"));
    }

    @Test
    public void testIsPalindromeWithEmptyString() throws MissingArgumentException {
        assertTrue(palindrome.isPalindrome(""));
    }

    @Test
    public void testIsPalindromeWithSingleCharacter() throws MissingArgumentException {
        assertTrue(palindrome.isPalindrome("a"));
    }

    @Test
    public void testIsPalindromeWithEvenLengthPalindrome() throws MissingArgumentException {
        assertTrue(palindrome.isPalindrome("noon"));
    }
/* 
    @Test
    public void testIsPalindromeWithNull() throws MissingArgumentException {
        palindrome.isPalindrome(null);
    }*/

    @Test
    public void testFindLongestPalindromeSimple() throws MissingArgumentException {
        assertEquals("racecar", palindrome.findLongestPalindrome("racecar"));
    }

    @Test
    public void testFindLongestPalindromeInMiddle() throws MissingArgumentException {
        assertEquals("anana", palindrome.findLongestPalindrome("bananas"));
    }
/*
    @Test
    public void testFindLongestPalindromeAtBeginning() throws MissingArgumentException {
        assertEquals("madam", palindrome.findLongestPalindrome("madamimadam"));
    }*/
    
    @Test
    public void testFindLongestPalindromeComplex() throws MissingArgumentException {
        assertEquals("xyzzyx", palindrome.findLongestPalindrome("abaxyzzyxf"));
    }
/*
    @Test
    public void testFindLongestPalindromeWithMultiplePalindromes() throws MissingArgumentException {
        assertEquals("level", palindrome.findLongestPalindrome("racecarlevel"));
    }
*/
    @Test
    public void testFindLongestPalindromeEmptyString() throws MissingArgumentException {
        assertEquals("", palindrome.findLongestPalindrome(""));
    }

    @Test
    public void testFindLongestPalindromeSingleChar() throws MissingArgumentException {
        assertEquals("a", palindrome.findLongestPalindrome("a"));
    }
/*
    @Test
    public void testFindLongestPalindromeWithNull() throws MissingArgumentException {
        palindrome.findLongestPalindrome(null);
    }*/

    @Test
    public void testFindLongestPalindromeInternalSimple() throws MissingArgumentException {
        assertEquals("racecar", palindrome.findLongestPalindromeInternal("racecar"));
    }

    @Test
    public void testFindLongestPalindromeInternalAtStart() throws MissingArgumentException {
        assertEquals("level", palindrome.findLongestPalindromeInternal("levelworld"));
    }

    @Test
    public void testFindLongestPalindromeInternalNoPalindrome() throws MissingArgumentException {
        assertEquals("h", palindrome.findLongestPalindromeInternal("helloworld"));
    }
/* 
    @Test
    public void testFindLongestPalindromeInternalWithNull() throws MissingArgumentException {
        palindrome.findLongestPalindromeInternal(null);
    }*/
}