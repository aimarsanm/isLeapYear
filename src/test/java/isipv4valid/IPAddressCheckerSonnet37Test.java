package isipv4valid;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class IPAddressCheckerSonnet37Test {

    private IPAddressChecker ipAddressChecker;

    @BeforeEach
    void setUp() {
        ipAddressChecker = new IPAddressChecker();
    }

    // ========== WHITE BOX TESTING: isValidIPV4Part() ==========

    @DisplayName("Should validate string length condition (<=3 characters)")
    @ParameterizedTest(name = "{index} => part=''{0}'', expected={1}")
    @CsvSource({
            "1, true",      // 1 character
            "12, true",     // 2 characters
            "123, true",    // 3 characters
            "1234, false",  // 4 characters (exceeds max length)
            "12345, false"  // 5 characters (exceeds max length)
    })
    void testValidIPV4PartLength(String part, boolean expected) {
        assertEquals(expected, ipAddressChecker.isValidIPV4Part(part));
    }

    @DisplayName("Should validate string contains only digits")
    @ParameterizedTest(name = "{index} => part=''{0}'', expected={1}")
    @CsvSource({
            "123, true",     // Only digits
            "a23, false",    // Contains letter 'a'
            "1b3, false",    // Contains letter 'b'
            "12c, false",    // Contains letter 'c'
            "1.3, false",    // Contains period
            "1-3, false",    // Contains hyphen
            "1 3, false"     // Contains space
    })
    void testValidIPV4PartOnlyDigits(String part, boolean expected) {
        assertEquals(expected, ipAddressChecker.isValidIPV4Part(part));
    }

    @DisplayName("Should validate string with leading zeros")
    @ParameterizedTest(name = "{index} => part=''{0}'', expected={1}")
    @CsvSource({
            "0, true",       // Single zero is valid
            "00, false",     // Leading zero with multiple digits is invalid
            "01, false",     // Leading zero with multiple digits is invalid
            "001, false",    // Leading zero with multiple digits is invalid
            "010, false"     // Leading zero with multiple digits is invalid
    })
    void testValidIPV4PartLeadingZeros(String part, boolean expected) {
        assertEquals(expected, ipAddressChecker.isValidIPV4Part(part));
    }

    @DisplayName("Should validate numeric range (0-255)")
    @ParameterizedTest(name = "{index} => part=''{0}'', expected={1}")
    @CsvSource({
            "0, true",       // Minimum value
            "1, true",       // Low value
            "127, true",     // Middle value
            "254, true",     // High value
            "255, true",     // Maximum value
            "256, false",    // Exceeds maximum value
            "300, false",    // Significantly exceeds maximum value
            "999, false"     // Significantly exceeds maximum value
    })
    void testValidIPV4PartNumericRange(String part, boolean expected) {
        assertEquals(expected, ipAddressChecker.isValidIPV4Part(part));
    }

    @DisplayName("Should handle NumberFormatException")
    @Test
    void testValidIPV4PartNumberFormatException() {
        // Empty string - causes NumberFormatException, should return false
        assertFalse(ipAddressChecker.isValidIPV4Part(""));
    }

    // ========== WHITE BOX TESTING: isValidIPV4() ==========

    @DisplayName("Should handle null input")
    @Test
    void testIsValidIPV4Null() {
        assertEquals(0, ipAddressChecker.isValidIPV4(null));
    }

    @DisplayName("Should validate exact number of dots")
    @ParameterizedTest(name = "{index} => ip=''{0}'', expected={1}")
    @CsvSource({
            "192.168.1, 0",         // 2 dots (too few)
            "192.168.1.1, 1",       // 3 dots (correct)
            "192.168.1.1.1, 0"      // 4 dots (too many)
    })
    void testIsValidIPV4DotCount(String ip, int expected) {
        assertEquals(expected, ipAddressChecker.isValidIPV4(ip));
    }

    @DisplayName("Should handle empty parts")
    @ParameterizedTest(name = "{index} => ip=''{0}''")
    @ValueSource(strings = {
            "192..168.1",
            "192.168..1",
            "192.168.1.",
            ".192.168.1",
            "192.168.1..",
            "..192.168.1",
            "192.168...",
            "..."
    })
    void testIsValidIPV4EmptyParts(String ip) {
        assertEquals(0, ipAddressChecker.isValidIPV4(ip));
    }

    @DisplayName("Should handle consecutive dots")
    @ParameterizedTest(name = "{index} => ip=''{0}''")
    @ValueSource(strings = {
            "192.168.1.1..",
            "..192.168.1.1",
            "192..168.1.1",
            "192.168..1.1"
    })
    void testIsValidIPV4ConsecutiveDots(String ip) {
        assertEquals(0, ipAddressChecker.isValidIPV4(ip));
    }

    @DisplayName("Should validate for valid IP addresses")
    @ParameterizedTest(name = "{index} => ip=''{0}''")
    @ValueSource(strings = {
            "192.168.1.1",
            "127.0.0.1",
            "0.0.0.0",
            "255.255.255.255",
            "10.10.10.10",
            "172.16.0.1",
            "8.8.8.8"
    })
    void testIsValidIPV4ValidAddresses(String ip) {
        assertEquals(1, ipAddressChecker.isValidIPV4(ip));
    }

    // ========== BLACK BOX TESTING ==========
    
    // Equivalence Partitioning and Boundary Value Analysis for isValidIPV4Part

    @DisplayName("EP & BVA: Part values partitioning")
    @ParameterizedTest(name = "{index} => part=''{0}'', expected={1}")
    @MethodSource("providePartValuesForEP")
    void testPartValuesEquivalencePartitioning(String part, boolean expected) {
        assertEquals(expected, ipAddressChecker.isValidIPV4Part(part));
    }
    
    static Stream<Arguments> providePartValuesForEP() {
        return Stream.of(
            // Valid equivalence class: 0-255, no leading zeros (except single 0)
            Arguments.of("0", true),     // Boundary: minimum value
            Arguments.of("1", true),     // Valid low value
            Arguments.of("127", true),   // Valid middle value
            Arguments.of("254", true),   // Valid high value
            Arguments.of("255", true),   // Boundary: maximum value
            
            // Invalid equivalence class: values > 255
            Arguments.of("256", false),  // Boundary: just above max
            Arguments.of("300", false),  // Invalid high value
            
            // Invalid equivalence class: values with leading zeros
            Arguments.of("00", false),   // Invalid leading zero
            Arguments.of("01", false),   // Invalid leading zero
            
            // Invalid equivalence class: non-numeric characters
            Arguments.of("a", false),    // Letter
            Arguments.of("1a", false),   // Alphanumeric
            Arguments.of("1@", false)    // Special character
        );
    }

    // Equivalence Partitioning and Boundary Value Analysis for isValidIPV4

    @DisplayName("EP & BVA: IP address format")
    @ParameterizedTest(name = "{index} => ip=''{0}'', expected={1}")
    @MethodSource("provideIPAddressesForEP")
    void testIPAddressEquivalencePartitioning(String ip, int expected) {
        assertEquals(expected, ipAddressChecker.isValidIPV4(ip));
    }
    
    static Stream<Arguments> provideIPAddressesForEP() {
        return Stream.of(
            // Valid equivalence class: properly formatted IP addresses
            Arguments.of("0.0.0.0", 1),               // Boundary: minimum value IP
            Arguments.of("255.255.255.255", 1),       // Boundary: maximum value IP
            Arguments.of("192.168.1.1", 1),           // Typical valid IP
            
            // Invalid equivalence class: incorrect number of octets
            Arguments.of("192.168.1", 0),             // Too few octets
            Arguments.of("192.168.1.1.5", 0),         // Too many octets
            
            // Invalid equivalence class: invalid format with dots
            Arguments.of("192.168.1.", 0),            // Trailing dot
            Arguments.of(".192.168.1", 0),            // Leading dot
            Arguments.of("192..168.1", 0),            // Consecutive dots
            
            // Invalid equivalence class: invalid octet values
            Arguments.of("256.168.1.1", 0),           // First octet > 255
            Arguments.of("192.256.1.1", 0),           // Second octet > 255
            Arguments.of("192.168.256.1", 0),         // Third octet > 255
            Arguments.of("192.168.1.256", 0),         // Fourth octet > 255
            
            // Invalid equivalence class: octets with leading zeros
            Arguments.of("01.168.1.1", 0),            // Leading zero in first octet
            Arguments.of("192.01.1.1", 0),            // Leading zero in second octet
            Arguments.of("192.168.01.1", 0),          // Leading zero in third octet
            Arguments.of("192.168.1.01", 0),          // Leading zero in fourth octet
            
            // Invalid equivalence class: non-numeric characters
            Arguments.of("a.168.1.1", 0),             // Letter in first octet
            Arguments.of("192.a.1.1", 0),             // Letter in second octet
            Arguments.of("192.168.a.1", 0),           // Letter in third octet
            Arguments.of("192.168.1.a", 0)            // Letter in fourth octet
        );
    }

    @DisplayName("IP address with empty string input")
    @Test
    void testEmptyStringIPAddress() {
        assertEquals(0, ipAddressChecker.isValidIPV4(""));
    }

    @DisplayName("IP address with extreme values")
    @ParameterizedTest(name = "{index} => ip=''{0}'', expected={1}")
    @CsvSource({
            "0.0.0.0, 1",
            "255.255.255.255, 1",
            "0.255.0.255, 1",
            "255.0.255.0, 1"
    })
    void testExtremeIPAddressValues(String ip, int expected) {
        assertEquals(expected, ipAddressChecker.isValidIPV4(ip));
    }
}