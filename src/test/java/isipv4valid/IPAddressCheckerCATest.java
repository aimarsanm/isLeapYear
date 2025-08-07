package isipv4valid;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("Unit tests for IPAddressChecker with 100% coverage")
class IPAddressCheckerCATest {

    private IPAddressChecker checker;

    @BeforeEach
    void setUp() {
        checker = new IPAddressChecker();
    }

    // --- isValidIPV4Part ---

    @ParameterizedTest(name = "Valid part: \"{0}\" should return {1}")
    @MethodSource("validIPV4PartProvider")
    @DisplayName("isValidIPV4Part: Equivalence, boundary and decision coverage")
    void testIsValidIPV4Part(String input, boolean expected) {
        assertEquals(expected, checker.isValidIPV4Part(input));
    }

    static Stream<Arguments> validIPV4PartProvider() {
        return Stream.of(
            // Length > 3
            Arguments.of("1234", false),
            Arguments.of("0000", false),
            // Non-digit characters
            Arguments.of("1a2", false),
            Arguments.of("a12", false),
            Arguments.of("12a", false),
            Arguments.of("1.2", false),
            Arguments.of("", false),
            // Leading zero, length > 1
            Arguments.of("00", false),
            Arguments.of("001", false),
            Arguments.of("05", false),
            Arguments.of("010", false),
            Arguments.of("000", false),
            // Valid numbers, no leading zero, length <= 3, 0-255
            Arguments.of("0", true),
            Arguments.of("1", true),
            Arguments.of("10", true),
            Arguments.of("99", true),
            Arguments.of("100", true),
            Arguments.of("255", true),
            // Boundary: just above 255
            Arguments.of("256", false),
            Arguments.of("999", false),
            // Leading zero, but single digit
            Arguments.of("0", true),
            // Negative number
            Arguments.of("-1", false),
            // NumberFormatException cases
            Arguments.of(" ", false),
            Arguments.of("abc", false)
        );
    }

    // --- isValidIPV4 ---

    @ParameterizedTest(name = "Valid IPv4: \"{0}\" should return {1}")
    @MethodSource("validIPV4Provider")
    @DisplayName("isValidIPV4: Equivalence, boundary and decision coverage")
    void testIsValidIPV4(String input, int expected) {
        assertEquals(expected, checker.isValidIPV4(input));
    }

    static Stream<Arguments> validIPV4Provider() {
        return Stream.of(
            // Null input
            Arguments.of(null, 0),
            // Not enough dots
            Arguments.of("1.2.3", 0),
            Arguments.of("1.2.3.4.5", 0),
            Arguments.of("1.2.3.4.", 0),
            Arguments.of(".1.2.3.4", 0),
            // Valid IPv4
            Arguments.of("0.0.0.0", 1),
            Arguments.of("255.255.255.255", 1),
            Arguments.of("1.2.3.4", 1),
            Arguments.of("10.20.30.40", 1),
            Arguments.of("192.168.1.1", 1),
            // Invalid part: out of range
            Arguments.of("256.1.1.1", 0),
            Arguments.of("1.256.1.1", 0),
            Arguments.of("1.1.256.1", 0),
            Arguments.of("1.1.1.256", 0),
            // Invalid part: leading zero
            Arguments.of("01.2.3.4", 0),
            Arguments.of("1.02.3.4", 0),
            Arguments.of("1.2.003.4", 0),
            Arguments.of("1.2.3.04", 0),
            // Invalid part: non-digit
            Arguments.of("a.2.3.4", 0),
            Arguments.of("1.b.3.4", 0),
            Arguments.of("1.2.c.4", 0),
            Arguments.of("1.2.3.d", 0),
            Arguments.of("1.2.3.4a", 0),
            Arguments.of("1.2.3.4.", 0),
            // Invalid: empty part
            Arguments.of("1..3.4", 0),
            Arguments.of("1.2..4", 0),
            Arguments.of("..3.4", 0),
            Arguments.of("1.2.3.", 0),
            // Invalid: extra dots
            Arguments.of("1...1", 0),
            Arguments.of("1.2.3..4", 0),
            // Boundary: valid and invalid
            Arguments.of("0.0.0.255", 1),
            Arguments.of("0.0.0.256", 0),
            // Spaces
            Arguments.of(" 1.2.3.4", 0),
            Arguments.of("1.2.3.4 ", 0),
            Arguments.of("1. 2.3.4", 0),
            Arguments.of("1.2. 3.4", 0),
            Arguments.of("1.2.3. 4", 0),
            // Negative numbers
            Arguments.of("-1.2.3.4", 0),
            Arguments.of("1.-2.3.4", 0),
            Arguments.of("1.2.-3.4", 0),
            Arguments.of("1.2.3.-4", 0)
        );
    }

    @Test
    @DisplayName("isValidIPV4: Valid IP with all parts at lower boundary")
    void testIsValidIPV4LowerBoundary() {
        assertEquals(1, checker.isValidIPV4("0.0.0.0"));
    }

    @Test
    @DisplayName("isValidIPV4: Valid IP with all parts at upper boundary")
    void testIsValidIPV4UpperBoundary() {
        assertEquals(1, checker.isValidIPV4("255.255.255.255"));
    }

    @Test
    @DisplayName("isValidIPV4: Invalid IP with part just above upper boundary")
    void testIsValidIPV4AboveUpperBoundary() {
        assertEquals(0, checker.isValidIPV4("256.255.255.255"));
    }

    @Test
    @DisplayName("isValidIPV4: Invalid IP with part just below lower boundary")
    void testIsValidIPV4BelowLowerBoundary() {
        assertEquals(0, checker.isValidIPV4("-1.255.255.255"));
    }
}