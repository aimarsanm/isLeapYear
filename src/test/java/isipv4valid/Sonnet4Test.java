package isipv4valid;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;





class Sonnet4Test {

    private final IPAddressChecker checker = new IPAddressChecker();

    // White Box Tests for isValidIPV4Part method

    @Test
    @DisplayName("Should return false when IPv4 part length is greater than 3")
    void testIsValidIPV4Part_LengthGreaterThanThree() {
        assertFalse(checker.isValidIPV4Part("1234"));
        assertFalse(checker.isValidIPV4Part("12345"));
    }

    @Test
    @DisplayName("Should return false when IPv4 part contains non-digit characters")
    void testIsValidIPV4Part_NonDigitCharacters() {
        assertFalse(checker.isValidIPV4Part("1a2"));
        assertFalse(checker.isValidIPV4Part("ab"));
        assertFalse(checker.isValidIPV4Part("1.2"));
        assertFalse(checker.isValidIPV4Part("1-2"));
        assertFalse(checker.isValidIPV4Part("1 2"));
    }

    @Test
    @DisplayName("Should return false when IPv4 part has leading zero with length greater than 1")
    void testIsValidIPV4Part_LeadingZeroWithLengthGreaterThanOne() {
        assertFalse(checker.isValidIPV4Part("00"));
        assertFalse(checker.isValidIPV4Part("01"));
        assertFalse(checker.isValidIPV4Part("001"));
        assertFalse(checker.isValidIPV4Part("010"));
        assertFalse(checker.isValidIPV4Part("099"));
    }

    @Test
    @DisplayName("Should return true for single digit zero")
    void testIsValidIPV4Part_SingleDigitZero() {
        assertTrue(checker.isValidIPV4Part("0"));
    }

    @Test
    @DisplayName("Should return false when IPv4 part value is greater than 255")
    void testIsValidIPV4Part_ValueGreaterThan255() {
        assertFalse(checker.isValidIPV4Part("256"));
        assertFalse(checker.isValidIPV4Part("300"));
        assertFalse(checker.isValidIPV4Part("999"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "12", "123", "255", "200", "100", "50"})
    @DisplayName("Should return true for valid IPv4 parts")
    void testIsValidIPV4Part_ValidParts(String part) {
        assertTrue(checker.isValidIPV4Part(part));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "01", "00", "001", "256", "300", "1234", "abc", "1a", "-1", "1.2"})
    @DisplayName("Should return false for invalid IPv4 parts")
    void testIsValidIPV4Part_InvalidParts(String part) {
        assertFalse(checker.isValidIPV4Part(part));
    }

    // White Box Tests for isValidIPV4 method

    @Test
    @DisplayName("Should return 0 when IPv4 string is null")
    void testIsValidIPV4_NullInput() {
        assertEquals(0, checker.isValidIPV4(null));
    }

    @Test
    @DisplayName("Should return 0 when IPv4 string has no dots")
    void testIsValidIPV4_NoDots() {
        assertEquals(0, checker.isValidIPV4("192168001001"));
        assertEquals(0, checker.isValidIPV4("1"));
        assertEquals(0, checker.isValidIPV4(""));
    }

    @Test
    @DisplayName("Should return 0 when IPv4 string has less than 3 dots")
    void testIsValidIPV4_LessThanThreeDots() {
        assertEquals(0, checker.isValidIPV4("192.168"));
        assertEquals(0, checker.isValidIPV4("192.168.1"));
        assertEquals(0, checker.isValidIPV4("192"));
    }

    @Test
    @DisplayName("Should return 0 when IPv4 string has more than 3 dots")
    void testIsValidIPV4_MoreThanThreeDots() {
        assertEquals(0, checker.isValidIPV4("192.168.1.1.1"));
        assertEquals(0, checker.isValidIPV4("1.2.3.4.5"));
        assertEquals(0, checker.isValidIPV4("192.168.1.1."));
    }

    @Test
    @DisplayName("Should return 0 when IPv4 string has consecutive dots")
    void testIsValidIPV4_ConsecutiveDots() {
        assertEquals(0, checker.isValidIPV4("192..1.1"));
        assertEquals(0, checker.isValidIPV4("192.168..1"));
        assertEquals(0, checker.isValidIPV4("..."));
        assertEquals(0, checker.isValidIPV4("1...1"));
    }

    @Test
    @DisplayName("Should return 0 when IPv4 string has invalid parts")
    void testIsValidIPV4_InvalidParts() {
        assertEquals(0, checker.isValidIPV4("256.168.1.1"));
        assertEquals(0, checker.isValidIPV4("192.256.1.1"));
        assertEquals(0, checker.isValidIPV4("192.168.256.1"));
        assertEquals(0, checker.isValidIPV4("192.168.1.256"));
        assertEquals(0, checker.isValidIPV4("01.168.1.1"));
        assertEquals(0, checker.isValidIPV4("192.01.1.1"));
        assertEquals(0, checker.isValidIPV4("192.168.01.1"));
        assertEquals(0, checker.isValidIPV4("192.168.1.01"));
    }

    @Test
    @DisplayName("Should return 0 when IPv4 string has empty parts")
    void testIsValidIPV4_EmptyParts() {
        assertEquals(0, checker.isValidIPV4(".168.1.1"));
        assertEquals(0, checker.isValidIPV4("192..1.1"));
        assertEquals(0, checker.isValidIPV4("192.168.1."));
        assertEquals(0, checker.isValidIPV4("192.168..1"));
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "192.168.1.1",
        "0.0.0.0",
        "255.255.255.255",
        "127.0.0.1",
        "10.0.0.1",
        "172.16.0.1",
        "1.1.1.1",
        "8.8.8.8",
        "192.168.0.1"
    })
    @DisplayName("Should return 1 for valid IPv4 addresses")
    void testIsValidIPV4_ValidAddresses(String ipAddress) {
        assertEquals(1, checker.isValidIPV4(ipAddress));
    }

    @ParameterizedTest
    @CsvSource({
        "'', 0",
        "'192.168.1', 0",
        "'192.168.1.1.1', 0",
        "'256.168.1.1', 0",
        "'192.256.1.1', 0",
        "'192.168.256.1', 0",
        "'192.168.1.256', 0",
        "'01.168.1.1', 0",
        "'192.01.1.1', 0",
        "'192.168.01.1', 0",
        "'192.168.1.01', 0",
        "'192..1.1', 0",
        "'192.168..1', 0",
        "'.168.1.1', 0",
        "'192.168.1.', 0",
        "'...', 0",
        "'1...1', 0",
        "'abc.def.ghi.jkl', 0",
        "'192.168.1.a', 0",
        "'192.168.a.1', 0",
        "'192.a.1.1', 0",
        "'a.168.1.1', 0",
        "'192.168.1.1.', 0",
        "'.192.168.1.1', 0",
        "'192.168.1.-1', 0",
        "'192.168.-1.1', 0",
        "'192.-1.1.1', 0",
        "'-1.168.1.1', 0",
        "'192 168 1 1', 0",
        "'192,168,1,1', 0"
    })
    @DisplayName("Should return correct result for various IPv4 address formats")
    void testIsValidIPV4_VariousFormats(String ipAddress, int expected) {
        assertEquals(expected, checker.isValidIPV4(ipAddress));
    }

    // Boundary Value Analysis Tests

    @Test
    @DisplayName("Should validate boundary values for IPv4 parts")
    void testIsValidIPV4Part_BoundaryValues() {
        // Lower boundary
        assertTrue(checker.isValidIPV4Part("0"));
        assertTrue(checker.isValidIPV4Part("1"));
        
        // Upper boundary
        assertTrue(checker.isValidIPV4Part("254"));
        assertTrue(checker.isValidIPV4Part("255"));
        assertFalse(checker.isValidIPV4Part("256"));
        
        // Length boundaries
        assertTrue(checker.isValidIPV4Part("1"));     // length 1
        assertTrue(checker.isValidIPV4Part("12"));    // length 2
        assertTrue(checker.isValidIPV4Part("123"));   // length 3
        assertFalse(checker.isValidIPV4Part("1234")); // length 4
    }

    @Test
    @DisplayName("Should validate IPv4 addresses with boundary values")
    void testIsValidIPV4_BoundaryValues() {
        assertEquals(1, checker.isValidIPV4("0.0.0.0"));
        assertEquals(1, checker.isValidIPV4("255.255.255.255"));
        assertEquals(0, checker.isValidIPV4("256.255.255.255"));
        assertEquals(0, checker.isValidIPV4("255.256.255.255"));
        assertEquals(0, checker.isValidIPV4("255.255.256.255"));
        assertEquals(0, checker.isValidIPV4("255.255.255.256"));
    }

    // Edge Cases

    @Test
    @DisplayName("Should handle edge cases for IPv4 validation")
    void testIsValidIPV4_EdgeCases() {
        assertEquals(0, checker.isValidIPV4(""));
        assertEquals(0, checker.isValidIPV4(" "));
        assertEquals(0, checker.isValidIPV4("..."));
        assertEquals(0, checker.isValidIPV4("192.168.1.1."));
        assertEquals(0, checker.isValidIPV4(".192.168.1.1"));
        assertEquals(0, checker.isValidIPV4("192.168.1.1 "));
        assertEquals(0, checker.isValidIPV4(" 192.168.1.1"));
    }

    @Test
    @DisplayName("Should handle special characters in IPv4 parts")
    void testIsValidIPV4Part_SpecialCharacters() {
        assertFalse(checker.isValidIPV4Part(" "));
        assertFalse(checker.isValidIPV4Part("1 "));
        assertFalse(checker.isValidIPV4Part(" 1"));
        assertFalse(checker.isValidIPV4Part("1+1"));
        assertFalse(checker.isValidIPV4Part("1*1"));
        assertFalse(checker.isValidIPV4Part("1/1"));
        assertFalse(checker.isValidIPV4Part("1\\1"));
        assertFalse(checker.isValidIPV4Part("1@1"));
        assertFalse(checker.isValidIPV4Part("1#1"));
        assertFalse(checker.isValidIPV4Part("1$1"));
        assertFalse(checker.isValidIPV4Part("1%1"));
        assertFalse(checker.isValidIPV4Part("1^1"));
        assertFalse(checker.isValidIPV4Part("1&1"));
        assertFalse(checker.isValidIPV4Part("1(1"));
        assertFalse(checker.isValidIPV4Part("1)1"));
    }

    @Test
    @DisplayName("Should handle empty string for IPv4 part")
    void testIsValidIPV4Part_EmptyString() {
        assertFalse(checker.isValidIPV4Part(""));
    }

    @Test
    @DisplayName("Should validate maximum valid three-digit numbers")
    void testIsValidIPV4Part_ThreeDigitBoundaries() {
        assertTrue(checker.isValidIPV4Part("100"));
        assertTrue(checker.isValidIPV4Part("200"));
        assertTrue(checker.isValidIPV4Part("250"));
        assertTrue(checker.isValidIPV4Part("255"));
        assertFalse(checker.isValidIPV4Part("256"));
        assertFalse(checker.isValidIPV4Part("300"));
        assertFalse(checker.isValidIPV4Part("999"));
    }
}