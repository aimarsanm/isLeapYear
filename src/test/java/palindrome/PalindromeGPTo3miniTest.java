package palindrome;

import static org.junit.jupiter.api.Assertions.fail; 
import static org.junit.jupiter.api.Assertions.assertEquals; 
import static org.junit.jupiter.api.Assertions.assertTrue; 
import static org.junit.jupiter.api.Assertions.assertFalse; 
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach; 
import org.junit.jupiter.api.DisplayName; 
import org.junit.jupiter.api.Test; 
import org.junit.jupiter.api.AfterEach; 

import org.junit.jupiter.params.ParameterizedTest; 
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName("Palindrome Class Tests") 
public class PalindromeGPTo3miniTest {


private Palindrome palindrome;

@BeforeEach
public void setUp() {
    palindrome = new Palindrome();
}

@AfterEach
public void tearDown() {
    // any cleanup if needed
}

// ==================== Tests for findLongestPalindrome ====================

@Test
@DisplayName("findLongestPalindrome: Should throw MissingArgumentException for null input")
public void testFindLongestPalindrome_NullInput() {
    try {
        assertThrows(MissingArgumentException.class, () -> {
            palindrome.findLongestPalindrome(null);
        });
    } catch(Exception e) {
        fail("Exception thrown when testing null input: " + e.getMessage());
    }
}

@ParameterizedTest(name = "Input: \"{0}\" => Expected longest palindrome: \"{1}\"")
@CsvSource({
    "'' , ''",                        // empty string boundary
    "'a' , 'a'",                      // single character
    "'aba' , 'aba'",                  // entire word is a palindrome
    "'abba' , 'abba'",                // even-length palindrome
    "'abcde' , 'a'",                  // no multi-char palindrome, fallback to first char
    "'babad' , 'bab'",                // multiple valid palindromes, order determines result
    "'civicxyz' , 'civic'"            // longest palindrome prefix found is at beginning
})
@DisplayName("findLongestPalindrome: Valid input tests")
public void testFindLongestPalindrome_ValidInputs(String input, String expected) {
    try {
        String result = palindrome.findLongestPalindrome(input);
        assertEquals(expected, result, "Longest palindrome not detected properly for input: " + input);
    } catch(Exception e) {
        fail("Unexpected exception for input \"" + input + "\": " + e.getMessage());
    }
}

// ==================== Tests for findLongestPalindromeInternal ====================

@Test
@DisplayName("findLongestPalindromeInternal: Should throw MissingArgumentException for null input")
public void testFindLongestPalindromeInternal_NullInput() {
    try {
        assertThrows(MissingArgumentException.class, () -> {
            palindrome.findLongestPalindromeInternal(null);
        });
    } catch(Exception e) {
        fail("Exception thrown when testing null input for findLongestPalindromeInternal: " + e.getMessage());
    }
}

@ParameterizedTest(name = "Input: \"{0}\" => Expected longest palindrome prefix: \"{1}\"")
@CsvSource({
    "'' , ''",                     // boundary: empty string, returns empty string
    "'a' , 'a'",                   // single character returns itself
    "'aba' , 'aba'",               // already a palindrome
    "'abba' , 'abba'",             // even-length full palindrome
    // If the full string is not a palindrome, the method returns the maximal prefix that is.
    // For input "abcde":  "abcde" is not a palindrome; checks "abcd", "abc", "ab", then "a"
    "'abcde' , 'a'",              
    // For input "babad": not a palindrome; longest prefix palindrome is "bab"
    "'babad' , 'bab'",
    // For input where no prefix >1 is a palindrome, e.g. "abcbx": 
    // "abcbx" -> not palindrome; "abcb" is not; "abc" is not; "ab" is not; "a" is.
    "'abcbx' , 'a'"
})
@DisplayName("findLongestPalindromeInternal: Valid input tests")
public void testFindLongestPalindromeInternal_ValidInputs(String input, String expected) {
    try {
        String result = palindrome.findLongestPalindromeInternal(input);
        assertEquals(expected, result, "Longest palindrome prefix not detected properly for input: " + input);
    } catch(Exception e) {
        fail("Unexpected exception for input \"" + input + "\": " + e.getMessage());
    }
}

// ==================== Tests for isPalindrome ====================

@Test
@DisplayName("isPalindrome: Should throw MissingArgumentException for null input")
public void testIsPalindrome_NullInput() {
    try {
        assertThrows(MissingArgumentException.class, () -> {
            palindrome.isPalindrome(null);
        });
    } catch(Exception e) {
        fail("Exception thrown when testing null input for isPalindrome: " + e.getMessage());
    }
}

@ParameterizedTest(name = "Input: \"{0}\" => Expected isPalindrome: {1}")
@CsvSource({
    "'' , true",             // empty string is considered a palindrome
    "'a' , true",             // single character
    "'aa' , true",            // two same characters
    "'aba' , true",           // odd-length palindrome
    "'abba' , true",          // even-length palindrome
    "'abc' , false",          // not a palindrome
    "'abca' , false"          // not a palindrome
})
@DisplayName("isPalindrome: Valid input tests")
public void testIsPalindrome_ValidInputs(String input, boolean expected) {
    try {
        boolean result = palindrome.isPalindrome(input);
        if (expected) {
            assertTrue(result, "Expected \"" + input + "\" to be a palindrome.");
        } else {
            assertFalse(result, "Expected \"" + input + "\" not to be a palindrome.");
        }
    } catch(Exception e) {
        fail("Unexpected exception for input \"" + input + "\": " + e.getMessage());
    }
}
}