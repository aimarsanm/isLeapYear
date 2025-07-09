package isipv4valid;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;





class Sonnet37ThinkTest {

    private final IPAddressChecker checker = new IPAddressChecker();

    // Tests for isValidIPV4Part method

    @DisplayName("Valid IPv4 parts should be validated correctly")
    @ParameterizedTest(name = "Part: {0} should be valid")
    @ValueSource(strings = {"0", "1", "9", "10", "99", "100", "200", "255"})
    void validIPv4PartsShouldBeValidated(String part) {
        assertTrue(checker.isValidIPV4Part(part));
    }

    @DisplayName("Invalid IPv4 parts with length > 3 should be rejected")
    @ParameterizedTest(name = "Part: {0} should be invalid due to length > 3")
    @ValueSource(strings = {"1234", "9999"})
    void invalidIPv4PartsWithLengthGreaterThan3ShouldBeRejected(String part) {
        assertFalse(checker.isValidIPV4Part(part));
    }

    @DisplayName("Invalid IPv4 parts with non-digits should be rejected")
    @ParameterizedTest(name = "Part: {0} should be invalid due to non-digits")
    @ValueSource(strings = {"a", "1a", "a1", "1a2", "-1", "2.5"})
    void invalidIPv4PartsWithNonDigitsShouldBeRejected(String part) {
        assertFalse(checker.isValidIPV4Part(part));
    }

    @DisplayName("Invalid IPv4 parts with leading zeros should be rejected")
    @ParameterizedTest(name = "Part: {0} should be invalid due to leading zeros")
    @ValueSource(strings = {"01", "001", "00", "05", "059"})
    void invalidIPv4PartsWithLeadingZerosShouldBeRejected(String part) {
        assertFalse(checker.isValidIPV4Part(part));
    }

    @DisplayName("Invalid IPv4 parts with value > 255 should be rejected")
    @ParameterizedTest(name = "Part: {0} should be invalid due to value > 255")
    @ValueSource(strings = {"256", "300", "999"})
    void invalidIPv4PartsWithValueGreaterThan255ShouldBeRejected(String part) {
        assertFalse(checker.isValidIPV4Part(part));
    }

    // Tests for isValidIPV4 method

    @DisplayName("Valid IPv4 addresses should be validated correctly")
    @ParameterizedTest(name = "IP: {0} should be valid")
    @ValueSource(strings = {
            "0.0.0.0", 
            "1.1.1.1", 
            "192.168.1.1", 
            "255.255.255.255",
            "10.0.0.1",
            "172.16.0.1",
            "127.0.0.1"
    })
    void validIPv4AddressesShouldBeValidated(String ip) {
        assertEquals(1, checker.isValidIPV4(ip));
    }

    @DisplayName("Null IPv4 address should be rejected")
    @ParameterizedTest(name = "IP: null should be invalid")
    @NullSource
    void nullIPv4AddressShouldBeRejected(String ip) {
        assertEquals(0, checker.isValidIPV4(ip));
    }

    @DisplayName("Empty string should be rejected")
    @Test
    void emptyStringShouldBeRejected() {
        assertEquals(0, checker.isValidIPV4(""));
    }

    @DisplayName("Invalid IPv4 addresses with incorrect number of dots should be rejected")
    @ParameterizedTest(name = "IP: {0} should be invalid due to incorrect number of dots")
    @ValueSource(strings = {
            "192.168.1", 
            "192.168", 
            "192", 
            "192.168.1.1.1", 
            "192.168.1.1.1.1"
    })
    void invalidIPv4AddressesWithIncorrectNumberOfDotsShouldBeRejected(String ip) {
        assertEquals(0, checker.isValidIPV4(ip));
    }

    @DisplayName("Invalid IPv4 addresses with invalid parts should be rejected")
    @ParameterizedTest(name = "IP: {0} should be invalid due to invalid parts")
    @ValueSource(strings = {
            "256.0.0.0", 
            "0.256.0.0", 
            "0.0.256.0", 
            "0.0.0.256",
            "a.0.0.0",
            "0.a.0.0",
            "0.0.a.0",
            "0.0.0.a",
            "01.0.0.0",
            "0.01.0.0",
            "0.0.01.0",
            "0.0.0.01",
            "1234.0.0.0",
            "0.1234.0.0",
            "0.0.1234.0",
            "0.0.0.1234"
    })
    void invalidIPv4AddressesWithInvalidPartsShouldBeRejected(String ip) {
        assertEquals(0, checker.isValidIPV4(ip));
    }

    @DisplayName("Invalid IPv4 addresses with consecutive dots should be rejected")
    @ParameterizedTest(name = "IP: {0} should be invalid due to consecutive dots")
    @ValueSource(strings = {
            "192..168.1.1", 
            "192.168..1.1", 
            "192.168.1..1",
            ".192.168.1.1",
            "192.168.1.1.",
            "192.168.1..",
            "..192.168.1",
            "..."
    })
    void invalidIPv4AddressesWithConsecutiveDotsShouldBeRejected(String ip) {
        assertEquals(0, checker.isValidIPV4(ip));
    }

    @DisplayName("Invalid IPv4 with mixed issues should be rejected")
    @ParameterizedTest(name = "IP: {0} should be invalid due to mixed issues")
    @ValueSource(strings = {
            "192.168.1.256", 
            "192.168.01.1",
            "192.a.1.1",
            "...1"
    })
    void invalidIPv4AddressesWithMixedIssuesShouldBeRejected(String ip) {
        assertEquals(0, checker.isValidIPV4(ip));
    }

    @DisplayName("Boundary value tests for IPv4 parts")
    @ParameterizedTest(name = "Part: {0} should have expected validity")
    @ValueSource(strings = {"0", "255", "256"})
    void boundaryValueTestsForIPv4Parts(String part) {
        if (part.equals("0") || part.equals("255")) {
            assertTrue(checker.isValidIPV4Part(part));
        } else {
            assertFalse(checker.isValidIPV4Part(part));
        }
    }
    
    @DisplayName("Edge case with maximum valid value in all octets")
    @Test
    void edgeCaseWithMaximumValidValuesInAllOctets() {
        assertEquals(1, checker.isValidIPV4("255.255.255.255"));
    }
    
    @DisplayName("Edge case with minimum valid value in all octets")
    @Test
    void edgeCaseWithMinimumValidValuesInAllOctets() {
        assertEquals(1, checker.isValidIPV4("0.0.0.0"));
    }
}