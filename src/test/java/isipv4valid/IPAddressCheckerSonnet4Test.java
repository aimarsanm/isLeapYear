package isipv4valid;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("IPAddressChecker Test Suite")
class IPAddressCheckerSonnet4Test {

    private IPAddressChecker ipChecker;

    @BeforeEach
    void setUp() {
        ipChecker = new IPAddressChecker();
    }

    // ============================================================================
    // WHITE BOX TESTING - isValidIPV4Part method
    // ============================================================================

    @ParameterizedTest
    @DisplayName("Valid IPv4 parts - single digit numbers")
    @ValueSource(strings = {"0", "1", "5", "9"})
    void testIsValidIPV4Part_ValidSingleDigit(String part) {
        assertTrue(ipChecker.isValidIPV4Part(part));
    }

    @ParameterizedTest
    @DisplayName("Valid IPv4 parts - two digit numbers")
    @ValueSource(strings = {"10", "25", "50", "99"})
    void testIsValidIPV4Part_ValidTwoDigits(String part) {
        assertTrue(ipChecker.isValidIPV4Part(part));
    }

    @ParameterizedTest
    @DisplayName("Valid IPv4 parts - three digit numbers within range")
    @ValueSource(strings = {"100", "127", "200", "255"})
    void testIsValidIPV4Part_ValidThreeDigitsInRange(String part) {
        assertTrue(ipChecker.isValidIPV4Part(part));
    }

    @Test
    @DisplayName("Invalid IPv4 part - length greater than 3")
    void testIsValidIPV4Part_LengthGreaterThanThree() {
        assertFalse(ipChecker.isValidIPV4Part("1234"));
    }

    @ParameterizedTest
    @DisplayName("Invalid IPv4 parts - non-digit characters")
    @ValueSource(strings = {"a", "1a", "12a", "1.2", "12.", ".12", "1-2", "1 2"})
    void testIsValidIPV4Part_NonDigitCharacters(String part) {
        assertFalse(ipChecker.isValidIPV4Part(part));
    }

    @ParameterizedTest
    @DisplayName("Invalid IPv4 parts - leading zeros")
    @ValueSource(strings = {"00", "01", "05", "010", "001", "012"})
    void testIsValidIPV4Part_LeadingZeros(String part) {
        assertFalse(ipChecker.isValidIPV4Part(part));
    }

    @ParameterizedTest
    @DisplayName("Invalid IPv4 parts - numbers greater than 255")
    @ValueSource(strings = {"256", "300", "999", "1000"})
    void testIsValidIPV4Part_NumbersGreaterThan255(String part) {
        assertFalse(ipChecker.isValidIPV4Part(part));
    }

    @Test
    @DisplayName("Valid IPv4 part - boundary value 255")
    void testIsValidIPV4Part_Boundary255() {
        assertTrue(ipChecker.isValidIPV4Part("255"));
    }

    @Test
    @DisplayName("Invalid IPv4 part - boundary value 256")
    void testIsValidIPV4Part_Boundary256() {
        assertFalse(ipChecker.isValidIPV4Part("256"));
    }

    @Test
    @DisplayName("Valid IPv4 part - empty string causes NumberFormatException")
    void testIsValidIPV4Part_EmptyString() {
        assertFalse(ipChecker.isValidIPV4Part(""));
    }

    // ============================================================================
    // WHITE BOX TESTING - isValidIPV4 method
    // ============================================================================

    @Test
    @DisplayName("Invalid IPv4 - null input")
    void testIsValidIPV4_NullInput() {
        assertEquals(0, ipChecker.isValidIPV4(null));
    }

    @ParameterizedTest
    @DisplayName("Valid IPv4 addresses")
    @ValueSource(strings = {
        "0.0.0.0",
        "1.1.1.1",
        "127.0.0.1",
        "192.168.1.1",
        "255.255.255.255",
        "10.0.0.1",
        "172.16.0.1"
    })
    void testIsValidIPV4_ValidAddresses(String ip) {
        assertEquals(1, ipChecker.isValidIPV4(ip));
    }

    @ParameterizedTest
    @DisplayName("Invalid IPv4 - incorrect number of dots")
    @CsvSource({
        "'1.1.1', 'Too few dots'",
        "'1.1.1.1.1', 'Too many dots'",
        "'1..1.1', 'Consecutive dots'",
        "'1.1..1', 'Consecutive dots middle'",
        "'1.1.1.', 'Trailing dot'",
        "'.1.1.1', 'Leading dot'",
        "'1.1.1.1.', 'Trailing dot with complete'",
        "'...', 'Only dots'",
        "'1...1', 'Multiple consecutive dots'"
    })
    void testIsValidIPV4_IncorrectDotCount(String ip, String description) {
        assertEquals(0, ipChecker.isValidIPV4(ip));
    }

    @ParameterizedTest
    @DisplayName("Invalid IPv4 - invalid parts")
    @ValueSource(strings = {
        "256.1.1.1",
        "1.256.1.1", 
        "1.1.256.1",
        "1.1.1.256",
        "300.1.1.1",
        "1.300.1.1",
        "1.1.300.1",
        "1.1.1.300"
    })
    void testIsValidIPV4_InvalidParts_OverRange(String ip) {
        assertEquals(0, ipChecker.isValidIPV4(ip));
    }

    @ParameterizedTest
    @DisplayName("Invalid IPv4 - leading zeros in parts")
    @ValueSource(strings = {
        "01.1.1.1",
        "1.01.1.1",
        "1.1.01.1", 
        "1.1.1.01",
        "00.1.1.1",
        "1.00.1.1",
        "1.1.00.1",
        "1.1.1.00"
    })
    void testIsValidIPV4_InvalidParts_LeadingZeros(String ip) {
        assertEquals(0, ipChecker.isValidIPV4(ip));
    }

    @ParameterizedTest
    @DisplayName("Invalid IPv4 - non-numeric characters")
    @ValueSource(strings = {
        "a.1.1.1",
        "1.a.1.1",
        "1.1.a.1",
        "1.1.1.a",
        "1a.1.1.1",
        "1.1b.1.1",
        "1.1.1c.1",
        "1.1.1.1d"
    })
    void testIsValidIPV4_InvalidParts_NonNumeric(String ip) {
        assertEquals(0, ipChecker.isValidIPV4(ip));
    }

    // ============================================================================
    // BLACK BOX TESTING - Boundary Value Analysis
    // ============================================================================

    @ParameterizedTest
    @DisplayName("Boundary values - valid range limits")
    @ValueSource(strings = {
        "0.0.0.0",
        "255.255.255.255",
        "0.255.0.255",
        "255.0.255.0"
    })
    void testIsValidIPV4_BoundaryValues_Valid(String ip) {
        assertEquals(1, ipChecker.isValidIPV4(ip));
    }

    @ParameterizedTest
    @DisplayName("Boundary values - invalid range limits")
    @ValueSource(strings = {
        "256.0.0.0",
        "0.256.0.0",
        "0.0.256.0",
        "0.0.0.256"
    })
    void testIsValidIPV4_BoundaryValues_Invalid(String ip) {
        assertEquals(0, ipChecker.isValidIPV4(ip));
    }

    // ============================================================================
    // BLACK BOX TESTING - Equivalence Partitioning
    // ============================================================================

    @Test
    @DisplayName("Equivalence class - empty string")
    void testIsValidIPV4_EmptyString() {
        assertEquals(0, ipChecker.isValidIPV4(""));
    }

    @Test
    @DisplayName("Equivalence class - single character")
    void testIsValidIPV4_SingleCharacter() {
        assertEquals(0, ipChecker.isValidIPV4("1"));
    }

    @Test
    @DisplayName("Equivalence class - no dots")
    void testIsValidIPV4_NoDots() {
        assertEquals(0, ipChecker.isValidIPV4("192168001001"));
    }

    @ParameterizedTest
    @DisplayName("Equivalence class - parts too long")
    @ValueSource(strings = {
        "1234.1.1.1",
        "1.1234.1.1",
        "1.1.1234.1",
        "1.1.1.1234"
    })
    void testIsValidIPV4_PartsToolong(String ip) {
        assertEquals(0, ipChecker.isValidIPV4(ip));
    }

    // ============================================================================
    // EDGE CASES AND SPECIAL SCENARIOS
    // ============================================================================

    @Test
    @DisplayName("Edge case - string with only dots")
    void testIsValidIPV4_OnlyDots() {
        assertEquals(0, ipChecker.isValidIPV4("..."));
    }

    @Test
    @DisplayName("Edge case - mixed valid and invalid parts")
    void testIsValidIPV4_MixedParts() {
        assertEquals(0, ipChecker.isValidIPV4("192.168.01.1"));
    }

    @Test
    @DisplayName("Edge case - spaces in IP")
    void testIsValidIPV4_Spaces() {
        assertEquals(0, ipChecker.isValidIPV4("192.168.1. 1"));
    }

    @Test
    @DisplayName("Condition coverage - exactly 3 dots with empty parts")
    void testIsValidIPV4_ThreeDotsEmptyParts() {
        assertEquals(0, ipChecker.isValidIPV4("..1."));
    }

    @Test
    @DisplayName("Decision coverage - valid parts but wrong dot count due to tokenization")
    void testIsValidIPV4_ValidPartsWrongDotCount() {
        assertEquals(0, ipChecker.isValidIPV4("1.1.1"));
    }

    // ============================================================================
    // COMPREHENSIVE CONDITION/DECISION COVERAGE
    // ============================================================================

    @ParameterizedTest
    @DisplayName("Complete condition coverage - all digit combinations")
    @CsvSource({
        "'0', true",
        "'9', true", 
        "'a', false",
        "'A', false",
        "'-', false",
        "'+', false",
        "' ', false"
    })
    void testIsValidIPV4Part_AllCharacterTypes(String input, boolean expected) {
        assertEquals(expected, ipChecker.isValidIPV4Part(input));
    }

    @Test
    @DisplayName("Decision coverage - parseInt exception handling")
    void testIsValidIPV4Part_ParseIntException() {
        // This should not throw exception but return false
        try {
            boolean result = ipChecker.isValidIPV4Part("");
            assertFalse(result);
        } catch (Exception e) {
            fail("Method should handle NumberFormatException internally");
        }
    }

    @Test
    @DisplayName("Complete path coverage - all conditions in isValidIPV4")
    void testIsValidIPV4_AllConditionPaths() {
        // Test path: null check
        assertEquals(0, ipChecker.isValidIPV4(null));
        
        // Test path: wrong dot count
        assertEquals(0, ipChecker.isValidIPV4("1.1"));
        
        // Test path: invalid part
        assertEquals(0, ipChecker.isValidIPV4("256.1.1.1"));
        
        // Test path: dots count mismatch after tokenization
        assertEquals(0, ipChecker.isValidIPV4("1.1.1."));
        
        // Test path: all conditions pass
        assertEquals(1, ipChecker.isValidIPV4("1.1.1.1"));
    }
}