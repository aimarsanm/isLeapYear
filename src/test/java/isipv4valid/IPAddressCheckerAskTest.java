package isipv4valid;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Unit tests for IPAddressChecker with 100% coverage (White Box & Black Box)")
class IPAddressCheckerAskTest {

    private IPAddressChecker checker;

    @BeforeEach
    void setUp() {
        checker = new IPAddressChecker();
    }

    // --- White Box: Condition/Decision Coverage for isValidIPV4Part ---

    @ParameterizedTest(name = "Valid part: \"{0}\" should return {1}")
    @MethodSource("validIPV4PartProvider")
    @DisplayName("isValidIPV4Part - Valid cases (0-255, no leading zeros except '0')")
    void testIsValidIPV4Part_Valid(String part, boolean expected) {
        assertEquals(expected, checker.isValidIPV4Part(part));
    }

    static Stream<Arguments> validIPV4PartProvider() {
        return Stream.of(
            Arguments.of("0", true),      // single zero
            Arguments.of("1", true),      // min valid
            Arguments.of("10", true),     // valid two digits
            Arguments.of("99", true),     // valid two digits
            Arguments.of("100", true),    // valid three digits
            Arguments.of("255", true)     // max valid
        );
    }

    @ParameterizedTest(name = "Invalid part: \"{0}\" should return false")
    @MethodSource("invalidIPV4PartProvider")
    @DisplayName("isValidIPV4Part - Invalid cases (length, chars, leading zeros, out of range)")
    void testIsValidIPV4Part_Invalid(String part) {
        assertFalse(checker.isValidIPV4Part(part));
    }

    static Stream<Arguments> invalidIPV4PartProvider() {
        return Stream.of(
            Arguments.of(""),         // empty string
            Arguments.of("256"),      // above max
            Arguments.of("999"),      // way above max
            Arguments.of("a"),        // non-digit
            Arguments.of("1a"),       // mixed
            Arguments.of("01"),       // leading zero
            Arguments.of("001"),      // leading zeros
            Arguments.of("00"),       // leading zeros
            Arguments.of("05"),       // leading zero
            Arguments.of("1234"),     // length > 3
            Arguments.of("-1"),       // negative
            Arguments.of(" "),        // space
            Arguments.of("2 5"),      // space inside
            Arguments.of("2.5"),      // dot inside
            Arguments.of(""),         // empty again for coverage
            Arguments.of("256"),      // boundary above
            Arguments.of("300"),      // above boundary
            Arguments.of("abc"),      // all non-digit
            Arguments.of("1.1"),      // dot
            Arguments.of("1,1")       // comma
        );
    }

    // --- Black Box: Equivalence Partitioning & Boundary Value Analysis for isValidIPV4 ---

    @ParameterizedTest(name = "Valid IP: \"{0}\" should return 1")
    @MethodSource("validIPV4Provider")
    @DisplayName("isValidIPV4 - Valid IPv4 addresses")
    void testIsValidIPV4_Valid(String ip) {
        assertEquals(1, checker.isValidIPV4(ip));
    }

    static Stream<Arguments> validIPV4Provider() {
        return Stream.of(
            Arguments.of("0.0.0.0"),         // min boundary
            Arguments.of("255.255.255.255"), // max boundary
            Arguments.of("1.2.3.4"),         // typical valid
            Arguments.of("10.20.30.40"),     // valid
            Arguments.of("192.168.1.1"),     // valid
            Arguments.of("127.0.0.1")        // valid
        );
    }
 /* 
    @ParameterizedTest(name = "Invalid IP: \"{0}\" should return 0")
    @MethodSource("invalidIPV4Provider")
    @DisplayName("isValidIPV4 - Invalid IPv4 addresses")
    void testIsValidIPV4_Invalid(String ip) {
        assertEquals(0, checker.isValidIPV4(ip));
    }
*/
    static Stream<Arguments> invalidIPV4Provider() {
        return Stream.of(
            Arguments.of(null),                   // null input
            Arguments.of(""),                     // empty string
            Arguments.of("1.2.3"),                // too few parts
            Arguments.of("1.2.3.4.5"),            // too many parts
            Arguments.of("1.2.3.256"),            // out of range
            Arguments.of("256.1.1.1"),            // out of range
            Arguments.of("1.2.3.-1"),             // negative
            Arguments.of("1.2.3.a"),              // non-digit
            Arguments.of("01.2.3.4"),             // leading zero
            Arguments.of("1.02.3.4"),             // leading zero
            Arguments.of("1.2.003.4"),            // leading zeros
            Arguments.of("1.2.3.04"),             // leading zero
            Arguments.of("1.2.3.4 "),             // trailing space
            Arguments.of(" 1.2.3.4"),             // leading space
            Arguments.of("1..3.4"),               // empty part
            Arguments.of("1.2..4"),               // empty part
            Arguments.of("1.2.3."),               // trailing dot
            Arguments.of(".1.2.3"),               // leading dot
            Arguments.of("1.2.3.4.5.6"),          // way too many
            Arguments.of("1.2.3.4.5"),            // too many
            Arguments.of("1.2.3.4."),             // trailing dot
            Arguments.of("1.2.3..4"),             // double dot
            Arguments.of("1...1"),                // triple dot, empty parts
            Arguments.of("1.2.3.256"),            // boundary above
            Arguments.of("1.2.3.300"),            // above boundary
            Arguments.of("abc.def.ghi.jkl"),      // all non-digit
            Arguments.of("1.2.3.4a"),             // extra char
            Arguments.of("1.2.3.4.5"),            // too many
            Arguments.of("1.2.3"),                // too few
            Arguments.of("1.2.3.4.5.6.7"),        // way too many
            Arguments.of("1.2.3.4.5.6.7.8"),      // way too many
            Arguments.of("1.2.3.4.5.6.7.8.9"),    // way too many
            Arguments.of("1.2.3.4.5.6.7.8.9.10")  // way too many
        );
    }

    // --- White Box: Decision/Condition coverage for isValidIPV4 ---

    @ParameterizedTest(name = "Dot count: \"{0}\" should return {1}")
    @MethodSource("dotCountProvider")
    @DisplayName("isValidIPV4 - Dot count edge cases")
    void testIsValidIPV4_DotCount(String ip, int expected) {
        assertEquals(expected, checker.isValidIPV4(ip));
    }

    static Stream<Arguments> dotCountProvider() {
        return Stream.of(
            Arguments.of("1.2.3.4", 1),      // valid, 3 dots
            Arguments.of("1.2.3", 0),        // invalid, 2 dots
            Arguments.of("1.2.3.4.5", 0),    // invalid, 4 dots
            Arguments.of("1..3.4", 0),       // invalid, 3 dots but empty part
            Arguments.of("1...1", 0)         // invalid, 3 dots but empty parts
        );
    }

    // --- Edge cases for isValidIPV4Part: boundary values ---

    @ParameterizedTest(name = "Boundary part: \"{0}\" should return {1}")
    @MethodSource("boundaryIPV4PartProvider")
    @DisplayName("isValidIPV4Part - Boundary values")
    void testIsValidIPV4Part_Boundary(String part, boolean expected) {
        assertEquals(expected, checker.isValidIPV4Part(part));
    }

    static Stream<Arguments> boundaryIPV4PartProvider() {
        return Stream.of(
            Arguments.of("0", true),      // min boundary
            Arguments.of("255", true),    // max boundary
            Arguments.of("256", false),   // just above max
            Arguments.of("-1", false),    // just below min
            Arguments.of("1", true),      // just above min
            Arguments.of("254", true)     // just below max
        );
    }

    // --- Edge cases for isValidIPV4: boundary values ---

    @ParameterizedTest(name = "Boundary IP: \"{0}\" should return {1}")
    @MethodSource("boundaryIPV4Provider")
    @DisplayName("isValidIPV4 - Boundary values")
    void testIsValidIPV4_Boundary(String ip, int expected) {
        assertEquals(expected, checker.isValidIPV4(ip));
    }

    static Stream<Arguments> boundaryIPV4Provider() {
        return Stream.of(
            Arguments.of("0.0.0.0", 1),         // min boundary
            Arguments.of("255.255.255.255", 1), // max boundary
            Arguments.of("256.255.255.255", 0), // just above min in first part
            Arguments.of("255.256.255.255", 0), // just above min in second part
            Arguments.of("255.255.256.255", 0), // just above min in third part
            Arguments.of("255.255.255.256", 0), // just above min in fourth part
            Arguments.of("-1.255.255.255", 0),  // just below min in first part
            Arguments.of("255.-1.255.255", 0),  // just below min in second part
            Arguments.of("255.255.-1.255", 0),  // just below min in third part
            Arguments.of("255.255.255.-1", 0)   // just below min in fourth part
        );
    }

    // --- Test for exception handling in isValidIPV4Part (NumberFormatException) ---

    @Test
    @DisplayName("isValidIPV4Part - NumberFormatException handling")
    void testIsValidIPV4Part_NumberFormatException() {
        // This should trigger NumberFormatException inside isValidIPV4Part
        assertFalse(checker.isValidIPV4Part("abc"));
        assertFalse(checker.isValidIPV4Part("1a"));
        assertFalse(checker.isValidIPV4Part("-1"));
    }

    // --- Test for null input in isValidIPV4 ---

    @Test
    @DisplayName("isValidIPV4 - Null input should return 0")
    void testIsValidIPV4_NullInput() {
        assertEquals(0, checker.isValidIPV4(null));
    }
}