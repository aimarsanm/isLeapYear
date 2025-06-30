package palindrome;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Test class for {@link Palindrome}.
 * It uses Condition/Decision coverage for white-box testing and
 * applies Black Box testing with equivalence partitioning and
 * boundary value analysis to achieve 100% coverage.
 */
public class GPTo1preTest {

    private Palindrome palindrome;

    @BeforeEach
    void setUp() {
        palindrome = new Palindrome();
    }

    // -------------------------------------------------------
    // Tests for isPalindrome
    // -------------------------------------------------------
    @Test
    @DisplayName("Should throw MissingArgumentException when isPalindrome is called with null")
    void testIsPalindromeWithNull() {
        assertThrows(
            MissingArgumentException.class,
            () -> palindrome.isPalindrome(null),
            "Expected MissingArgumentException on null input"
        );
    }

    @ParameterizedTest
    @CsvSource({
        "'', true",
        "a, true",
        "aa, true",
        "aba, true",
        "abba, true",
        "abc, false",
        "ab, false"
    })
    @DisplayName("Should return expected boolean result when isPalindrome is called with various strings")
    void testIsPalindromeVarious(String input, boolean expected) {
        try {
            boolean result = palindrome.isPalindrome(input);
            assertEquals(expected, result, "Unexpected result for isPalindrome(\"" + input + "\")");
        } catch (MissingArgumentException e) {
            fail("No exception expected, but got: " + e.getMessage());
        }
    }

    // -------------------------------------------------------
    // Tests for findLongestPalindromeInternal
    // -------------------------------------------------------
    @Test
    @DisplayName("Should throw MissingArgumentException when findLongestPalindromeInternal is called with null")
    void testFindLongestPalindromeInternalWithNull() {
        assertThrows(
            MissingArgumentException.class,
            () -> palindrome.findLongestPalindromeInternal(null),
            "Expected MissingArgumentException on null input"
        );
    }

    @ParameterizedTest
    @CsvSource({
        "abc, a",
        "abba, abba",
        "'', ''",
        "abcba, abcba",
        "babad, bab"
    })
    @DisplayName("Should return the correct longest palindrome for findLongestPalindrome")
    void testFindLongestPalindromeVarious(String input, String expected) {
        try {
            String result = palindrome.findLongestPalindrome(input);
            assertEquals(
                expected,
                result,
                "Unexpected longest palindrome in \"" + input + "\""
            );
        } catch (MissingArgumentException e) {
            fail("No exception expected, but got: " + e.getMessage());
        }
    }
}