package palindrome;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;






class Sonnet37thinkTest {

    private Palindrome palindrome;

    @BeforeEach
    void setUp() {
        palindrome = new Palindrome();
    }

    // Tests for findLongestPalindrome method

    @Test
    @DisplayName("findLongestPalindrome should throw MissingArgumentException when word is null")
    void findLongestPalindrome_NullWord_ThrowsMissingArgumentException() {
        assertThrows(MissingArgumentException.class, () -> palindrome.findLongestPalindrome(null));
    }

    @Test
    @DisplayName("findLongestPalindrome should return empty string for empty input")
    void findLongestPalindrome_EmptyString_ReturnsEmptyString() throws MissingArgumentException {
        assertEquals("", palindrome.findLongestPalindrome(""));
    }

    @ParameterizedTest
    @MethodSource("provideStringsWithPalindromes")
    @DisplayName("findLongestPalindrome should return the longest palindrome in the string")
    void findLongestPalindrome_WithPalindromes_ReturnsLongestPalindrome(String input, String expected)throws MissingArgumentException {
        assertEquals(expected, palindrome.findLongestPalindrome(input));
    }

    static Stream<Arguments> provideStringsWithPalindromes() {
        return Stream.of(
            Arguments.of("racecar", "racecar"),
            Arguments.of("hello", "ll"),
            Arguments.of("abcba", "abcba"),
            Arguments.of("bananas", "anana"),
            Arguments.of("aabbcc", "aa"),  // Returns first occurrence when multiple of same length
            Arguments.of("abcdefgfedcba", "abcdefgfedcba"),
            Arguments.of("a", "a"),        // Single character
            Arguments.of("aa", "aa"),      // Two identical characters
            Arguments.of("abc", "a"),      // No palindrome longer than 1
            Arguments.of("racecarxyz", "racecar"), // Palindrome at beginning
            Arguments.of("xyzracecar", "racecar"), // Palindrome at end
            Arguments.of("xyz1racecar2abc", "racecar") // Palindrome in middle
        );
    }

    @Test
    @DisplayName("findLongestPalindrome should handle strings with only identical characters")
    void findLongestPalindrome_AllIdenticalCharacters_ReturnsEntireString() throws MissingArgumentException {
        assertEquals("aaaaa", palindrome.findLongestPalindrome("aaaaa"));
    }

    @Test
    @DisplayName("findLongestPalindrome should handle very long palindromes")
    void findLongestPalindrome_VeryLongPalindrome_FindsCorrectly() throws MissingArgumentException{
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            sb.append('a');
        }
        String longPalindrome = sb.toString();
        assertEquals(longPalindrome, palindrome.findLongestPalindrome(longPalindrome));
    }

    @Test
    @DisplayName("findLongestPalindrome should handle case-sensitive palindromes")
    void findLongestPalindrome_CaseSensitive_FindsCorrectly()throws MissingArgumentException {
        assertEquals("ada", palindrome.findLongestPalindrome("adaM"));
    }

    @Test
    @DisplayName("findLongestPalindrome should handle strings with special characters")
    void findLongestPalindrome_SpecialCharacters_FindsCorrectly()throws MissingArgumentException {
        assertEquals("a", palindrome.findLongestPalindrome("a!b@c#"));
    }

    // Tests for findLongestPalindromeInternal method

    @Test
    @DisplayName("findLongestPalindromeInternal should throw MissingArgumentException when word is null")
    void findLongestPalindromeInternal_NullWord_ThrowsMissingArgumentException() {
        assertThrows(MissingArgumentException.class, () -> palindrome.findLongestPalindromeInternal(null));
    }

    @Test
    @DisplayName("findLongestPalindromeInternal should return empty string for empty input")
    void findLongestPalindromeInternal_EmptyString_ReturnsEmptyString() throws MissingArgumentException{
        assertEquals("", palindrome.findLongestPalindromeInternal(""));
    }

    @ParameterizedTest
    @MethodSource("provideStringsForInternalMethod")
    @DisplayName("findLongestPalindromeInternal should find the longest palindrome at the beginning of the string")
    void findLongestPalindromeInternal_VariousInputs_FindsLongestPalindromeAtBeginning(String input, String expected)throws MissingArgumentException {
        assertEquals(expected, palindrome.findLongestPalindromeInternal(input));
    }

    static Stream<Arguments> provideStringsForInternalMethod() {
        return Stream.of(
            Arguments.of("racecar", "racecar"),
            Arguments.of("abcba123", "abcba"),
            Arguments.of("aa123", "aa"),
            Arguments.of("a123", "a"),
            //Arguments.of("123", ""),       // No palindrome found
            Arguments.of("abcdef", "a")    // Only single character palindrome
        );
    }

    // Tests for isPalindrome method

    @Test
    @DisplayName("isPalindrome should throw MissingArgumentException when word is null")
    void isPalindrome_NullWord_ThrowsMissingArgumentException() {
        assertThrows(MissingArgumentException.class, () -> palindrome.isPalindrome(null));
    }

    @Test
    @DisplayName("isPalindrome should return true for empty string")
    void isPalindrome_EmptyString_ReturnsTrue() throws MissingArgumentException{
        assertTrue(palindrome.isPalindrome(""));
    }

    @Test
    @DisplayName("isPalindrome should return true for single character")
    void isPalindrome_SingleCharacter_ReturnsTrue()throws MissingArgumentException {
        assertTrue(palindrome.isPalindrome("a"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"aa", "aba", "racecar", "madam", "level", "deified"})
    @DisplayName("isPalindrome should return true for palindromes")
    void isPalindrome_PalindromeStrings_ReturnsTrue(String input)throws MissingArgumentException {
        assertTrue(palindrome.isPalindrome(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"ab", "abc", "hello", "world", "Madam"})
    @DisplayName("isPalindrome should return false for non-palindromes")
    void isPalindrome_NonPalindromeStrings_ReturnsFalse(String input)throws MissingArgumentException {
        assertFalse(palindrome.isPalindrome(input));
    }

    @Test
    @DisplayName("isPalindrome should handle even length palindromes")
    void isPalindrome_EvenLengthPalindrome_ReturnsTrue() throws MissingArgumentException{
        assertTrue(palindrome.isPalindrome("abba"));
    }

    @Test
    @DisplayName("isPalindrome should handle odd length palindromes")
    void isPalindrome_OddLengthPalindrome_ReturnsTrue() throws MissingArgumentException{
        assertTrue(palindrome.isPalindrome("abcba"));
    }

    @Test
    @DisplayName("isPalindrome should handle case sensitivity")
    void isPalindrome_CaseSensitive_ReturnsFalse() throws MissingArgumentException{
        assertFalse(palindrome.isPalindrome("Abba"));
    }

    @Test
    @DisplayName("isPalindrome should handle strings with special characters")
    void isPalindrome_SpecialCharacters_ReturnsExpectedResult() throws MissingArgumentException{
        assertTrue(palindrome.isPalindrome("a!a"));
        assertFalse(palindrome.isPalindrome("a!b"));
    }

    // Additional comprehensive tests for combinations and edge cases

    @Test
    @DisplayName("findLongestPalindrome when no palindrome exists longer than 1 character")
    void findLongestPalindrome_NoPalindromeExists_ReturnsSingleCharacter()throws MissingArgumentException {
        assertEquals("a", palindrome.findLongestPalindrome("abcdefg"));
    }
/* 
    @Test
    @DisplayName("findLongestPalindromeInternal with increasing palindrome sizes")
    void findLongestPalindromeInternal_IncreasingPalindromeSizes()throws MissingArgumentException {
        assertEquals("", palindrome.findLongestPalindromeInternal("123"));
        assertEquals("a", palindrome.findLongestPalindromeInternal("a123"));
        assertEquals("aa", palindrome.findLongestPalindromeInternal("aa123"));
        assertEquals("aba", palindrome.findLongestPalindromeInternal("aba123"));
    }
*/
    @Test
    @DisplayName("isPalindrome should check first/last characters are different and exit early")
    void isPalindrome_FirstLastDifferent_ReturnsFalse() throws MissingArgumentException{
        assertFalse(palindrome.isPalindrome("abcdefg"));
    }

    @Test
    @DisplayName("isPalindrome should check middle characters are different and exit early")
    void isPalindrome_MiddleCharsDifferent_ReturnsFalse() throws MissingArgumentException {
        assertFalse(palindrome.isPalindrome("abcdeba"));
    }
}