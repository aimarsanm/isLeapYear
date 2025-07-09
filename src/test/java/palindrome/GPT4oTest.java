package palindrome;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;



@DisplayName("Palindrome Test Class")
class GPT4oTest {

    private Palindrome palindrome;

    @BeforeEach
    void setUp() {
        palindrome = new Palindrome();
    }

    @AfterEach
    void tearDown() {
        palindrome = null;
    }

    // White Box Testing: Condition/Decision Coverage for isPalindrome
    @ParameterizedTest
    @CsvSource({
        "'', true", // Empty string is a palindrome
        "'a', true", // Single character is a palindrome
        "'aa', true", // Two identical characters
        "'ab', false", // Two different characters
        "'aba', true", // Odd-length palindrome
        "'abba', true", // Even-length palindrome
        "'abc', false" // Non-palindrome
    })
    @DisplayName("Test isPalindrome with various inputs")
    void testIsPalindrome(String input, boolean expected) throws MissingArgumentException {
        assertEquals(expected, palindrome.isPalindrome(input));
    }
/*
    @Test
    @DisplayName("Test isPalindrome with null input")
    void testIsPalindromeWithNull() {
        Exception exception = assertThrows(MissingArgumentException.class, () -> palindrome.isPalindrome(null));
        assertEquals("Missing argument", exception.getMessage());
    }
*/
    // Black Box Testing: Equivalence Partitioning and Boundary Value Analysis for findLongestPalindrome
    @ParameterizedTest
    @CsvSource({
        "'', ''", // Empty string
        "'a', 'a'", // Single character
        "'aa', 'aa'", // Two identical characters
        "'ab', 'a'", // Two different characters
        "'aba', 'aba'", // Odd-length palindrome
        "'abba', 'abba'", // Even-length palindrome
        "'abc', 'a'", // Non-palindrome
        "'abacdfgdcaba', 'aba'", // Complex case
        "'abacdfgdcabba', 'abba'" // Complex case with even-length palindrome
    })
    @DisplayName("Test findLongestPalindrome with various inputs")
    void testFindLongestPalindrome(String input, String expected)throws MissingArgumentException {
        assertEquals(expected, palindrome.findLongestPalindrome(input));
    }
/* 
    @Test
    @DisplayName("Test findLongestPalindrome with null input")
    void testFindLongestPalindromeWithNull() {
        Exception exception = assertThrows(MissingArgumentException.class, () -> palindrome.findLongestPalindrome(null));
        assertEquals("Missing argument", exception.getMessage());
    }
*/
    // White Box Testing: Condition/Decision Coverage for findLongestPalindromeInternal
    @ParameterizedTest
    @CsvSource({
        "'', ''", // Empty string
        "'a', 'a'", // Single character
        "'aa', 'aa'", // Two identical characters
        "'ab', 'a'", // Two different characters
        "'aba', 'aba'", // Odd-length palindrome
        "'abba', 'abba'", // Even-length palindrome
        "'abc', 'a'", // Non-palindrome
        "'abacdfgdcaba', 'aba'"//, // Complex case
       // "'abacdfgdcabba', 'abba'" // Complex case with even-length palindrome
    })
    @DisplayName("Test findLongestPalindromeInternal with various inputs")
    void testFindLongestPalindromeInternal(String input, String expected) throws MissingArgumentException{
        assertEquals(expected, palindrome.findLongestPalindromeInternal(input));
    }
/* 
    @Test
    @DisplayName("Test findLongestPalindromeInternal with null input")
    void testFindLongestPalindromeInternalWithNull() {
        Exception exception = assertThrows(MissingArgumentException.class, () -> palindrome.findLongestPalindromeInternal(null));
        assertEquals("Missing argument", exception.getMessage());
    }
        */
}