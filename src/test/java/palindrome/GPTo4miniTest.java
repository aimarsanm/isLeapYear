package palindrome;

import static org.junit.jupiter.api.Assertions.*;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.MethodSource;




class GPTo4miniTest {

    private Palindrome palindrome;

    @BeforeEach
    void setUp() {
        palindrome = new Palindrome();
    }

    // --- isPalindrome true cases ---
    @DisplayName("isPalindrome should return true for valid palindromes")
    @ParameterizedTest(name = "''{0}'' is palindrome")
    @ValueSource(strings = {"", "a", "aa", "aba", "racecar"})
    void testIsPalindromeTrueCases(String input) {
        try {
            assertTrue(palindrome.isPalindrome(input));
        } catch (MissingArgumentException e) {
            fail("Unexpected exception for input: " + input);
        }
    }

    // --- isPalindrome false cases ---
    @DisplayName("isPalindrome should return false for non-palindromes")
    @ParameterizedTest(name = "''{0}'' is not palindrome")
    @ValueSource(strings = {"ab", "abc", "abca"})
    void testIsPalindromeFalseCases(String input) {
        try {
            assertFalse(palindrome.isPalindrome(input));
        } catch (MissingArgumentException e) {
            fail("Unexpected exception for input: " + input);
        }
    }

    // --- isPalindrome null argument ---
    @Test
    @DisplayName("isPalindrome should throw MissingArgumentException when input is null")
    void testIsPalindromeNull() {
        assertThrows(MissingArgumentException.class, () -> palindrome.isPalindrome(null));
    }

    // --- findLongestPalindromeInternal valid cases ---
    private static Stream<org.junit.jupiter.params.provider.Arguments> internalProvider() {
        return Stream.of(
            org.junit.jupiter.params.provider.Arguments.of("", ""),
            org.junit.jupiter.params.provider.Arguments.of("a", "a"),
            org.junit.jupiter.params.provider.Arguments.of("abaX", "aba"),
            org.junit.jupiter.params.provider.Arguments.of("abacdfg", "aba"),
            org.junit.jupiter.params.provider.Arguments.of("xyz", "x")
        );
    }

    @DisplayName("findLongestPalindromeInternal should find correct prefix palindrome")
    @ParameterizedTest(name = "input=''{0}'' => ''{1}''")
    @MethodSource("internalProvider")
    void testFindLongestPalindromeInternal(String input, String expected) {
        try {
            String actual = palindrome.findLongestPalindromeInternal(input);
            assertEquals(expected, actual);
        } catch (MissingArgumentException e) {
            fail("Unexpected exception for input: " + input);
        }
    }

    @Test
    @DisplayName("findLongestPalindromeInternal should throw when input is null")
    void testFindLongestPalindromeInternalNull() {
        assertThrows(MissingArgumentException.class,
                     () -> palindrome.findLongestPalindromeInternal(null));
    }

    // --- findLongestPalindrome valid cases ---
    private static Stream<org.junit.jupiter.params.provider.Arguments> longestProvider() {
        return Stream.of(
            org.junit.jupiter.params.provider.Arguments.of("", ""),
            org.junit.jupiter.params.provider.Arguments.of("a", "a"),
            org.junit.jupiter.params.provider.Arguments.of("racecar", "racecar"),
            org.junit.jupiter.params.provider.Arguments.of("babad", "bab"),
            org.junit.jupiter.params.provider.Arguments.of("cbbd", "bb")
        );
    }

    @DisplayName("findLongestPalindrome should find the overall longest palindrome")
    @ParameterizedTest(name = "input=''{0}'' => ''{1}''")
    @MethodSource("longestProvider")
    void testFindLongestPalindrome(String input, String expected) {
        try {
            String actual = palindrome.findLongestPalindrome(input);
            assertEquals(expected, actual);
        } catch (MissingArgumentException e) {
            fail("Unexpected exception for input: " + input);
        }
    }

    @Test
    @DisplayName("findLongestPalindrome should throw when input is null")
    void testFindLongestPalindromeNull() {
        assertThrows(MissingArgumentException.class,
                     () -> palindrome.findLongestPalindrome(null));
    }
}