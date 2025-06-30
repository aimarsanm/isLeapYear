package palindrome;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;





/**
 * Test suite for the Palindrome class.
 * This class uses White Box (Condition/Decision Coverage) and Black Box (Equivalence Partitioning, Boundary Value Analysis)
 * techniques to ensure correctness and 100% code coverage.
 */
@DisplayName("Palindrome Class Tests")
class Gemini25propreTest {

    private Palindrome palindrome;

    @BeforeEach
    void setUp() {
        palindrome = new Palindrome();
    }

    @Nested
    @DisplayName("isPalindrome(String) Method Tests")
    class IsPalindromeTests {

        @Test
        @DisplayName("Should throw MissingArgumentException when word is null")
        void isPalindrome_whenWordIsNull_thenThrowsMissingArgumentException() {
            assertThrows(MissingArgumentException.class, () -> palindrome.isPalindrome(null),
                    "A MissingArgumentException should be thrown for null input.");
        }

        @Test
        @DisplayName("Should return true for an empty string")
        void isPalindrome_whenWordIsEmpty_thenReturnsTrue() {
            try {
                assertTrue(palindrome.isPalindrome(""), "An empty string is considered a palindrome.");
            } catch (MissingArgumentException e) {
                fail("Test failed with an unexpected exception: " + e.getMessage());
            }
        }

        @Test
        @DisplayName("Should return true for a single character string")
        void isPalindrome_whenWordIsSingleCharacter_thenReturnsTrue() {
            try {
                assertTrue(palindrome.isPalindrome("a"), "A single character string is a palindrome.");
            } catch (MissingArgumentException e) {
                fail("Test failed with an unexpected exception: " + e.getMessage());
            }
        }

        @ParameterizedTest
        @ValueSource(strings = {"racecar", "anna", "madam", "level"})
        @DisplayName("Should return true for valid palindromes")
        void isPalindrome_whenWordIsPalindrome_thenReturnsTrue(String word) {
            try {
                assertTrue(palindrome.isPalindrome(word), "The word '" + word + "' should be identified as a palindrome.");
            } catch (MissingArgumentException e) {
                fail("Test failed with an unexpected exception: " + e.getMessage());
            }
        }

        @ParameterizedTest
        @ValueSource(strings = {"hello", "world", "palindrome"})
        @DisplayName("Should return false for non-palindrome words")
        void isPalindrome_whenWordIsNotPalindrome_thenReturnsFalse(String word) {
            try {
                assertFalse(palindrome.isPalindrome(word), "The word '" + word + "' should not be identified as a palindrome.");
            } catch (MissingArgumentException e) {
                fail("Test failed with an unexpected exception: " + e.getMessage());
            }
        }
    }

    @Nested
    @DisplayName("findLongestPalindromeInternal(String) Method Tests")
    class FindLongestPalindromeInternalTests {

        @Test
        @DisplayName("Should throw MissingArgumentException when word is null")
        void findLongestPalindromeInternal_whenWordIsNull_thenThrowsMissingArgumentException() {
            assertThrows(MissingArgumentException.class, () -> palindrome.findLongestPalindromeInternal(null),
                    "A MissingArgumentException should be thrown for null input.");
        }

        @Test
        @DisplayName("Should return an empty string for an empty input")
        void findLongestPalindromeInternal_whenWordIsEmpty_thenReturnsEmptyString() {
            try {
                assertEquals("", palindrome.findLongestPalindromeInternal(""), "Expected an empty string for an empty input.");
            } catch (MissingArgumentException e) {
                fail("Test failed with an unexpected exception: " + e.getMessage());
            }
        }

        @Test
        @DisplayName("Should return the full string if it is a palindrome")
        void findLongestPalindromeInternal_whenWordIsAPalindrome_thenReturnsWord() {
            String word = "racecar";
            try {
                assertEquals(word, palindrome.findLongestPalindromeInternal(word), "Expected the full string to be returned.");
            } catch (MissingArgumentException e) {
                fail("Test failed with an unexpected exception: " + e.getMessage());
            }
        }

        @Test
        @DisplayName("Should return the longest palindrome from the start of the string")
        void findLongestPalindromeInternal_whenPalindromeIsAtStart_thenReturnsPalindrome() {
            try {
                assertEquals("level", palindrome.findLongestPalindromeInternal("levelheaded"), "Expected 'level' to be returned.");
            } catch (MissingArgumentException e) {
                fail("Test failed with an unexpected exception: " + e.getMessage());
            }
        }

        @Test
        @DisplayName("Should return the first character if no longer palindrome exists at the start")
        void findLongestPalindromeInternal_whenNoPalindromeAtStart_thenReturnsFirstChar() {
            try {
                assertEquals("h", palindrome.findLongestPalindromeInternal("hello"), "Expected the first character 'h' to be returned.");
            } catch (MissingArgumentException e) {
                fail("Test failed with an unexpected exception: " + e.getMessage());
            }
        }
    }

    @Nested
    @DisplayName("findLongestPalindrome(String) Method Tests")
    class FindLongestPalindromeTests {

        @Test
        @DisplayName("Should throw MissingArgumentException when word is null")
        void findLongestPalindrome_whenWordIsNull_thenThrowsMissingArgumentException() {
            assertThrows(MissingArgumentException.class, () -> palindrome.findLongestPalindrome(null),
                    "A MissingArgumentException should be thrown for null input.");
        }

        @Test
        @DisplayName("Should return an empty string for an empty input")
        void findLongestPalindrome_whenWordIsEmpty_thenReturnsEmptyString() {
            try {
                assertEquals("", palindrome.findLongestPalindrome(""), "Expected an empty string for an empty input.");
            } catch (MissingArgumentException e) {
                fail("Test failed with an unexpected exception: " + e.getMessage());
            }
        }

        @Test
        @DisplayName("Should return the character for a single character input")
        void findLongestPalindrome_whenWordIsSingleChar_thenReturnsTheChar() {
            try {
                assertEquals("a", palindrome.findLongestPalindrome("a"), "Expected 'a' for input 'a'.");
            } catch (MissingArgumentException e) {
                fail("Test failed with an unexpected exception: " + e.getMessage());
            }
        }

        @Test
        @DisplayName("Should return the first character for a word with no palindrome longer than 1")
        void findLongestPalindrome_whenNoPalindromeLongerThanOne_thenReturnsFirstChar() {
            try {
                assertEquals("w", palindrome.findLongestPalindrome("world"), "Expected 'w' for input 'world'.");
            } catch (MissingArgumentException e) {
                fail("Test failed with an unexpected exception: " + e.getMessage());
            }
        }

        @Test
        @DisplayName("Should find the longest palindrome at the beginning of the word")
        void findLongestPalindrome_whenPalindromeIsAtTheBeginning_thenReturnsPalindrome() {
            try {
                assertEquals("racecar", palindrome.findLongestPalindrome("racecarxyz"), "Expected 'racecar' to be found at the beginning.");
            } catch (MissingArgumentException e) {
                fail("Test failed with an unexpected exception: " + e.getMessage());
            }
        }

        @Test
        @DisplayName("Should find the longest palindrome in the middle of the word")
        void findLongestPalindrome_whenPalindromeIsInTheMiddle_thenReturnsPalindrome() {
            try {
                assertEquals("racecar", palindrome.findLongestPalindrome("abcracecardef"), "Expected 'racecar' to be found in the middle.");
            } catch (MissingArgumentException e) {
                fail("Test failed with an unexpected exception: " + e.getMessage());
            }
        }

        @Test
        @DisplayName("Should find the longest palindrome at the end of the word")
        void findLongestPalindrome_whenPalindromeIsAtTheEnd_thenReturnsPalindrome() {
            try {
                assertEquals("racecar", palindrome.findLongestPalindrome("xyzracecar"), "Expected 'racecar' to be found at the end.");
            } catch (MissingArgumentException e) {
                fail("Test failed with an unexpected exception: " + e.getMessage());
            }
        }

        @Test
        @DisplayName("Should find the longest palindrome among multiple palindromes")
        void findLongestPalindrome_whenMultiplePalindromes_thenReturnsLongest() {
            try {
                assertEquals("malayalam", palindrome.findLongestPalindrome("wowmalayalamlevel"), "Expected 'malayalam' as the longest palindrome.");
            } catch (MissingArgumentException e) {
                fail("Test failed with an unexpected exception: " + e.getMessage());
            }
        }
    }
}