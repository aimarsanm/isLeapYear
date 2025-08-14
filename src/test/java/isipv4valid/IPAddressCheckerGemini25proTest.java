package isipv4valid;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Test suite for the IPAddressChecker class.
 * This class employs both white-box and black-box testing methodologies
 * to ensure 100% coverage and correctness of the IP validation logic.
 */
@DisplayName("IPAddressChecker Test Suite")
class IPAddressCheckerGemini25proTest {

    private IPAddressChecker ipAddressChecker;

    @BeforeEach
    void setUp() {
        ipAddressChecker = new IPAddressChecker();
    }

    /**
     * Tests for the isValidIPV4Part(String s) method.
     * This nested class focuses on validating individual parts of an IPv4 address.
     */
    @Nested
    @DisplayName("Tests for isValidIPV4Part(String s) method")
    class IsValidIPV4PartTest {

        @DisplayName("Should return false for parts longer than 3 characters")
        @Test
        void isValidIPV4Part_shouldReturnFalse_whenPartIsLongerThanThreeChars() {
            try {
                assertFalse(ipAddressChecker.isValidIPV4Part("1234"), "Part '1234' should be invalid");
            } catch (Exception e) {
                fail("Test failed with an unexpected exception: " + e.getMessage());
            }
        }

        @DisplayName("Should return false for parts containing non-digit characters")
        @ParameterizedTest(name = "Part: \"{0}\"")
        @ValueSource(strings = { "1a", "b2", "1.2", "-1" })
        void isValidIPV4Part_shouldReturnFalse_whenPartContainsNonDigits(String invalidPart) {
            try {
                assertFalse(ipAddressChecker.isValidIPV4Part(invalidPart), "Part '" + invalidPart + "' should be invalid");
            } catch (Exception e) {
                fail("Test failed with an unexpected exception: " + e.getMessage());
            }
        }

        @DisplayName("Should return false for parts with leading zeros")
        @ParameterizedTest(name = "Part: \"{0}\"")
        @ValueSource(strings = { "01", "00", "099" })
        void isValidIPV4Part_shouldReturnFalse_whenPartHasLeadingZeros(String invalidPart) {
            try {
                assertFalse(ipAddressChecker.isValidIPV4Part(invalidPart), "Part '" + invalidPart + "' should be invalid");
            } catch (Exception e) {
                fail("Test failed with an unexpected exception: " + e.getMessage());
            }
        }

        @DisplayName("Should return false for parts representing numbers greater than 255")
        @ParameterizedTest(name = "Part: \"{0}\"")
        @ValueSource(strings = { "256", "300", "999" })
        void isValidIPV4Part_shouldReturnFalse_whenPartIsGreaterThan255(String invalidPart) {
            try {
                assertFalse(ipAddressChecker.isValidIPV4Part(invalidPart), "Part '" + invalidPart + "' should be invalid");
            } catch (Exception e) {
                fail("Test failed with an unexpected exception: " + e.getMessage());
            }
        }

        @DisplayName("Should return true for valid parts (0-255)")
        @ParameterizedTest(name = "Part: \"{0}\"")
        @ValueSource(strings = { "0", "1", "10", "100", "255", "254" })
        void isValidIPV4Part_shouldReturnTrue_forValidParts(String validPart) {
            try {
                assertTrue(ipAddressChecker.isValidIPV4Part(validPart), "Part '" + validPart + "' should be valid");
            } catch (Exception e) {
                fail("Test failed with an unexpected exception: " + e.getMessage());
            }
        }

        @DisplayName("Should return false for empty or blank parts")
        @ParameterizedTest(name = "Part: \"{0}\"")
        @ValueSource(strings = { "", " " })
        void isValidIPV4Part_shouldReturnFalse_forEmptyOrBlankParts(String part) {
            try {
                assertFalse(ipAddressChecker.isValidIPV4Part(part), "Part '" + part + "' should be invalid");
            } catch (Exception e) {
                fail("Test failed with an unexpected exception: " + e.getMessage());
            }
        }
    }

    /**
     * Tests for the isValidIPV4(String ipStr) method.
     * This nested class focuses on validating the full IPv4 address string.
     */
    @Nested
    @DisplayName("Tests for isValidIPV4(String ipStr) method")
    class IsValidIPV4Test {

        @DisplayName("Should return 0 for null input string")
        @Test
        void isValidIPV4_shouldReturnZero_whenIpIsNull() {
            try {
                assertEquals(0, ipAddressChecker.isValidIPV4(null), "A null IP string should be invalid");
            } catch (Exception e) {
                fail("Test failed with an unexpected exception: " + e.getMessage());
            }
        }

        @DisplayName("Should return 0 for IPs with incorrect number of dots")
        @ParameterizedTest(name = "IP: \"{0}\"")
        @ValueSource(strings = { "1.2.3", "1.2.3.4.5", "1..2.3.4" })
        void isValidIPV4_shouldReturnZero_whenDotCountIsNotThree(String invalidIp) {
            try {
                assertEquals(0, ipAddressChecker.isValidIPV4(invalidIp), "IP '" + invalidIp + "' should be invalid");
            } catch (Exception e) {
                fail("Test failed with an unexpected exception: " + e.getMessage());
            }
        }

        @DisplayName("Should return 0 for IPs with invalid parts")
        @ParameterizedTest(name = "IP: \"{0}\"")
        @ValueSource(strings = { "1.2.3.256", "1.2.3.abc", "1.2.3.01" })
        void isValidIPV4_shouldReturnZero_whenIpHasInvalidPart(String invalidIp) {
            try {
                assertEquals(0, ipAddressChecker.isValidIPV4(invalidIp), "IP '" + invalidIp + "' should be invalid");
            } catch (Exception e) {
                fail("Test failed with an unexpected exception: " + e.getMessage());
            }
        }

        @DisplayName("Should return 0 for IPs with empty parts")
        @ParameterizedTest(name = "IP: \"{0}\"")
        @ValueSource(strings = { "1.2..4", ".1.2.3", "1.2.3." })
        void isValidIPV4_shouldReturnZero_whenIpHasEmptyPart(String invalidIp) {
            try {
                assertEquals(0, ipAddressChecker.isValidIPV4(invalidIp), "IP '" + invalidIp + "' should be invalid");
            } catch (Exception e) {
                fail("Test failed with an unexpected exception: " + e.getMessage());
            }
        }

        @DisplayName("Should return 1 for valid IP addresses")
        @ParameterizedTest(name = "IP: \"{0}\"")
        @ValueSource(strings = { "0.0.0.0", "255.255.255.255", "192.168.1.1", "1.2.3.4" })
        void isValidIPV4_shouldReturnOne_forValidIp(String validIp) {
            try {
                assertEquals(1, ipAddressChecker.isValidIPV4(validIp), "IP '" + validIp + "' should be valid");
            } catch (Exception e) {
                fail("Test failed with an unexpected exception: " + e.getMessage());
            }
        }

        @DisplayName("White Box: Condition/Decision Coverage for isValidIPV4")
        @ParameterizedTest(name = "IP: \"{0}\", Expected: {1}")
        @CsvSource({
            // C1: ipStr == null -> T (returns 0)
            // This case is handled by the dedicated null test.

            // C2: count != 3 -> T (returns 0)
            "'1.2.3', 0",
            "'1.2.3.4.5', 0",

            // C2: count != 3 -> F (continues)
            // C3: isValidIPV4Part(part) -> F (returns 0)
            "'1.2.3.abc', 0",

            // C2: F, C3: T, C4: st.hasMoreTokens() -> T (dots++)
            "'1.2.3.4', 1",

            // C2: F, C3: T, C4: F (loop ends)
            // C5: dots != 3 -> T (returns 0)
            "'1...4', 0"
        })
        void isValidIPV4_whiteboxCoverage(String ip, int expected) {
            try {
                assertEquals(expected, ipAddressChecker.isValidIPV4(ip));
            } catch (Exception e) {
                fail("Test failed for IP '" + ip + "' with an unexpected exception: " + e.getMessage());
            }
        }
    }
}