package isipv4valid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;





/**
 * Test suite for the IPAddressChecker class.
 * This class employs White Box and Black Box testing techniques to ensure full coverage.
 */
@DisplayName("IPAddressChecker Test Suite")
class Gemini25propreTest {

    private IPAddressChecker checker;

    @BeforeEach
    void setUp() {
        checker = new IPAddressChecker();
    }

    @Nested
    @DisplayName("Tests for isValidIPV4Part(String)")
    class IsValidIPV4PartTests {

        @ParameterizedTest(name = "Should return true for valid part: \"{0}\"")
        @ValueSource(strings = {"0", "1", "10", "125", "254", "255"})
        @DisplayName("Black Box: Test valid IP parts (Equivalence Partitioning & Boundary Values)")
        void shouldReturnTrueForValidParts(String validPart) {
            assertTrue(checker.isValidIPV4Part(validPart), "Part '" + validPart + "' should be valid.");
        }

        @ParameterizedTest(name = "Should return false for invalid part: \"{0}\"")
        @ValueSource(strings = {
            "256",      // Out of range
            "abc",      // Non-numeric
            "1.2",      // Contains delimiter
            "-1",       // Negative number
            " 1",       // Contains whitespace
            ""          // Empty string
        })
        @DisplayName("Black Box: Test invalid IP parts (Equivalence Partitioning)")
        void shouldReturnFalseForInvalidParts(String invalidPart) {
            assertFalse(checker.isValidIPV4Part(invalidPart), "Part '" + invalidPart + "' should be invalid.");
        }

        @Test
        @DisplayName("White Box: Should return false for part longer than 3 characters")
        void shouldReturnFalseForPartTooLong() {
            assertFalse(checker.isValidIPV4Part("1234"), "Part '1234' is longer than 3 chars and should be invalid.");
        }

        @ParameterizedTest(name = "Should return false for part with leading zeros: \"{0}\"")
        @ValueSource(strings = {"01", "00", "099"})
        @DisplayName("White Box: Test parts with leading zeros")
        void shouldReturnFalseForLeadingZeros(String partWithLeadingZero) {
            assertFalse(checker.isValidIPV4Part(partWithLeadingZero), "Part '" + partWithLeadingZero + "' has leading zeros and should be invalid.");
        }

        @Test
        @DisplayName("White Box: Should return false for part with non-digit characters")
        void shouldReturnFalseForNonDigitCharacters() {
            assertFalse(checker.isValidIPV4Part("1a2"), "Part '1a2' contains non-digits and should be invalid.");
        }

        @Test
        @DisplayName("White Box: Should handle NumberFormatException for very large numbers")
        void shouldReturnFalseForNumberTooLargeForInteger() {
            // This string is numeric and has length <= 3, but will throw NumberFormatException
            assertFalse(checker.isValidIPV4Part("9999999999"), "A number too large for int should be invalid.");
        }
    }

    @Nested
    @DisplayName("Tests for isValidIPV4(String)")
    class IsValidIPV4Tests {

        @ParameterizedTest(name = "Should return 1 (valid) for IP: \"{0}\"")
        @ValueSource(strings = {
            "1.1.1.1",
            "192.168.1.1",
            "0.0.0.0",          // Boundary
            "255.255.255.255"   // Boundary
        })
        @DisplayName("Black Box: Test valid IP addresses (Equivalence Partitioning & Boundary Values)")
        void shouldReturnOneForValidIPs(String validIp) {
            assertEquals(1, checker.isValidIPV4(validIp), "IP '" + validIp + "' should be valid.");
        }

        @ParameterizedTest(name = "Should return 0 (invalid) for IP: \"{0}\"")
        @CsvSource({
            "'1.1.1.256'",      // Part out of range
            "'1.1.1.01'",       // Part with leading zero
            "'1.1.1.abc'",      // Part with non-numeric characters
            "'1.1.1'",          // Too few parts
            "'1.1.1.1.1'",      // Too many parts
            "'1..1.1'",         // Empty part
            "'...'",            // All empty parts
            "'1.2.3.4. '",      // Trailing space
            "' 1.2.3.4'",       // Leading space
            "''"                // Empty string
        })
        @DisplayName("Black Box: Test invalid IP addresses (Equivalence Partitioning)")
        void shouldReturnZeroForInvalidIPs(String invalidIp) {
            assertEquals(0, checker.isValidIPV4(invalidIp), "IP '" + invalidIp + "' should be invalid.");
        }

        @Test
        @DisplayName("White Box: Should return 0 for null input string")
        void shouldReturnZeroForNullInput() {
            assertEquals(0, checker.isValidIPV4(null), "A null IP string should be invalid.");
        }

        @ParameterizedTest(name = "Should return 0 (invalid) for IP with incorrect dot count: \"{0}\"")
        @ValueSource(strings = {"1.2.3", "1.2.3.4.5", "1.2..3.4"})
        @DisplayName("White Box: Test IPs with incorrect number of dots")
        void shouldReturnZeroForIncorrectDotCount(String ip) {
            assertEquals(0, checker.isValidIPV4(ip), "IP '" + ip + "' has an incorrect dot count and should be invalid.");
        }

        @Test
        @DisplayName("White Box: Should return 0 for IP with empty parts from tokenizer")
        void shouldReturnZeroForIpWithEmptyParts() {
            // This case tests the final `dots != 3` check after tokenization
            // "1...1" has 3 dots initially, but only 2 tokens, so dots will be 1.
            assertEquals(0, checker.isValidIPV4("1...1"), "IP '1...1' has empty parts and should be invalid.");
        }
    }
}