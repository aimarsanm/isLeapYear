package palindrome;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;

class PalindromeGPTo4minipreviewTest {

    private static Palindrome sharedPalindrome;
    private Palindrome palindrome;

    @BeforeAll
    static void initAll() {
        // one-time setup if needed
        sharedPalindrome = new Palindrome();
    }

    @AfterAll
    static void tearDownAll() {
        // one-time teardown if needed
        sharedPalindrome = null;
    }

    @BeforeEach
    void init() {
        // fresh instance before each test
        palindrome = new Palindrome();
    }

    @AfterEach
    void tearDown() {
        // cleanup after each test
        palindrome = null;
    }

    //
    // isPalindrome() tests
    //

    @DisplayName("isPalindrome throws MissingArgumentException on null input")
    @Test
    void isPalindrome_null_throws() {
        assertThrows(MissingArgumentException.class, () -> sharedPalindrome.isPalindrome(null));
    }

    @DisplayName("Empty string is considered a palindrome")
    @Test
    void isPalindrome_empty_returnsTrue() {
        try {
            assertTrue(palindrome.isPalindrome(""),
                       "Empty string should return true");
        } catch (MissingArgumentException e) {
            fail("Unexpected exception for empty string");
        }
    }

    @DisplayName("Single character is a palindrome")
    @ParameterizedTest(name = "'{0}' → true")
    @ValueSource(strings = { "a", "Z", "0" })
    void isPalindrome_singleChar_returnsTrue(String input) {
        try {
            assertTrue(palindrome.isPalindrome(input));
        } catch (MissingArgumentException e) {
            fail("Unexpected exception for single-character input");
        }
    }

    @DisplayName("Known palindromes return true")
    @ParameterizedTest(name = "'{0}' → true")
    @ValueSource(strings = { "aa", "aba", "racecar", "1221" })
    void isPalindrome_variousPalindromes_returnsTrue(String input) {
        try {
            assertTrue(palindrome.isPalindrome(input));
        } catch (MissingArgumentException e) {
            fail("Unexpected exception for palindrome: " + input);
        }
    }

    @DisplayName("Non-palindromes return false")
    @ParameterizedTest(name = "'{0}' → false")
    @ValueSource(strings = { "ab", "palindrome", "123ab321" })
    void isPalindrome_nonPalindromes_returnsFalse(String input) {
        try {
            assertFalse(palindrome.isPalindrome(input));
        } catch (MissingArgumentException e) {
            fail("Unexpected exception for non-palindrome: " + input);
        }
    }

    //
    // findLongestPalindromeInternal() tests – white-box C/D coverage on `while(index>0 && !isPalindrome(…))`
    //

    @DisplayName("findLongestPalindromeInternal throws MissingArgumentException on null")
    @Test
    void findLongestPalindromeInternal_null_throws() {
        assertThrows(MissingArgumentException.class,
                     () -> sharedPalindrome.findLongestPalindromeInternal(null));
    }

    @DisplayName("Internal: empty input → empty result (left side false, skip loop)")
    @Test
    void findLongestPalindromeInternal_empty_returnsEmpty() {
        try {
            String result = palindrome.findLongestPalindromeInternal("");
            assertEquals("", result);
        } catch (MissingArgumentException e) {
            fail("Unexpected exception for empty input");
        }
    }

    @DisplayName("Internal: full-palindrome input (e.g. 'racecar') returns full string (loop exit on right=false)")
    @Test
    void findLongestPalindromeInternal_fullPalindrome() {
        String in = "racecar";
        try {
            String result = palindrome.findLongestPalindromeInternal(in);
            assertEquals(in, result);
        } catch (MissingArgumentException e) {
            fail("Unexpected exception for full palindrome");
        }
    }

    @DisplayName("Internal: non-palindrome input returns longest prefix palindrome (covers both sides true then exit)")
    @ParameterizedTest(name = "'{0}' → '{1}'")
    @CsvSource({
        "ab, a",
        "abcbaX, abcba"
       // "banana, ana"  // 'banana' → first longest prefix palindrome is 'b'
    })
    void findLongestPalindromeInternal_nonPalindromes_returnPrefix(String input, String expected) {
        try {
            String result = palindrome.findLongestPalindromeInternal(input);
            assertEquals(expected, result);
        } catch (MissingArgumentException e) {
            fail("Unexpected exception for input: " + input);
        }
    }

    //
    // findLongestPalindrome() tests – combining black-box partitions with boundary values
    //

    @DisplayName("findLongestPalindrome throws MissingArgumentException on null")
    @Test
    void findLongestPalindrome_null_throws() {
        assertThrows(MissingArgumentException.class,
                     () -> sharedPalindrome.findLongestPalindrome(null));
    }

    @DisplayName("findLongestPalindrome on empty returns empty")
    @Test
    void findLongestPalindrome_empty_returnsEmpty() {
        try {
            assertEquals("", palindrome.findLongestPalindrome(""),
                         "Empty input should yield empty output");
        } catch (MissingArgumentException e) {
            fail("Unexpected exception for empty input");
        }
    }

    @DisplayName("findLongestPalindrome on single-char returns the char")
    @ParameterizedTest(name = "'{0}' → '{0}'")
    @ValueSource(strings = { "x", "Y", "7" })
    void findLongestPalindrome_singleChar(String input) {
        try {
            assertEquals(input, palindrome.findLongestPalindrome(input));
        } catch (MissingArgumentException e) {
            fail("Unexpected exception for single-char input");
        }
    }

    @DisplayName("findLongestPalindrome on no-palindrome multi-char returns first‐char")
    @ParameterizedTest(name = "'{0}' → '{1}'")
    @CsvSource({
        "ab, a",
        "abc, a",
        "xyz, x"
    })
    void findLongestPalindrome_noPalindrome(String input, String expected) {
        try {
            assertEquals(expected, palindrome.findLongestPalindrome(input));
        } catch (MissingArgumentException e) {
            fail("Unexpected exception for non-palindrome word");
        }
    }

    @DisplayName("findLongestPalindrome returns the longest palindrome substring")
    @ParameterizedTest(name = "'{0}' → '{1}'")
    @CsvSource({
        "babad, bab",
        "cbbd, bb",
        "forgeeksskeegfor, geeksskeeg",
        "abacdfgdcaba, aba"
    })
    void findLongestPalindrome_multipleCases(String input, String expected) {
        try {
            assertEquals(expected, palindrome.findLongestPalindrome(input));
        } catch (MissingArgumentException e) {
            fail("Unexpected exception for input: " + input);
        }
    }
}