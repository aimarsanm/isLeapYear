package isipv4valid;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class IPAddressCheckerGPTo4minipreviewTest {

    private static IPAddressChecker checker;

    @BeforeAll
    static void initChecker() {
        checker = new IPAddressChecker();
    }

    @AfterAll
    static void teardownChecker() {
        checker = null;
    }

    @ParameterizedTest
    @MethodSource("provideIPv4PartCases")
    @DisplayName("isValidIPV4Part(String) returns expected boolean for all edge and partition cases")
    void testIsValidIPV4Part(String input, boolean expected) {
        try {
            boolean actual = checker.isValidIPV4Part(input);
            assertEquals(expected, actual, () -> "Input part: '" + input + "'");
        } catch (Exception e) {
            fail("Unexpected exception for part '" + input + "'", e);
        }
    }

    static Stream<Arguments> provideIPv4PartCases() {
        return Stream.of(
            // length > 3
            Arguments.of("1234", false),
            // non‐digit characters
            Arguments.of("12a", false),
            // leading zero with length>1
            Arguments.of("00", false),
            Arguments.of("01", false),
            // single zero is allowed
            Arguments.of("0", true),
            // valid upper bound
            Arguments.of("255", true),
            // just above upper bound
            Arguments.of("256", false),
            // empty string leads to NumberFormatException
            Arguments.of("", false)
        );
    }

    @ParameterizedTest
    @MethodSource("provideIPv4StringCases")
    @DisplayName("isValidIPV4(String) returns expected 0/1 for all edge and partition cases")
    void testIsValidIPV4(String input, int expected) {
        try {
            int actual = checker.isValidIPV4(input);
            assertEquals(expected, actual, () -> "Input IP: '" + input + "'");
        } catch (Exception e) {
            fail("Unexpected exception for IP '" + input + "'", e);
        }
    }

    static Stream<Arguments> provideIPv4StringCases() {
        return Stream.of(
            // null input
            Arguments.of((String) null, 0),
            // empty or wrong dot count
            Arguments.of("", 0),
            Arguments.of("1.1.1", 0),
            Arguments.of("1.1.1.1.1", 0),
            // exactly 3 dots but empty tokens or too few tokens
            Arguments.of("1...1", 0),
            Arguments.of(".1.1.1", 0),
            Arguments.of("1.1.1.", 0),
            // invalid parts (leading zero, non‐digit, out of range, whitespace)
            Arguments.of("01.1.1.1", 0),
            Arguments.of("1.a.1.1", 0),
            Arguments.of("256.1.1.1", 0),
            Arguments.of(" 1.1.1.1", 0),
            // valid addresses (boundaries and normal)
            Arguments.of("0.0.0.0", 1),
            Arguments.of("255.255.255.255", 1),
            Arguments.of("192.168.0.1", 1)
        );
    }
}