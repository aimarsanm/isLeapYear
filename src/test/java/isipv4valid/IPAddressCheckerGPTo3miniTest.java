package isipv4valid;

import static org.junit.jupiter.api.Assertions.assertEquals; 
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.AfterEach; 
import org.junit.jupiter.api.BeforeEach; 
import org.junit.jupiter.api.DisplayName; 
import org.junit.jupiter.api.Test; 
import org.junit.jupiter.params.ParameterizedTest; 
import org.junit.jupiter.params.provider.CsvSource;

public class IPAddressCheckerGPTo3miniTest {


private IPAddressChecker ipChecker;

@BeforeEach
public void setUp() {
    ipChecker = new IPAddressChecker();
}

@AfterEach
public void tearDown() {
    ipChecker = null;
}

// =============================== Tests for isValidIPV4Part ===============================

@DisplayName("White Box & Black Box: Valid IPv4 part values")
@ParameterizedTest(name = "Input \"{0}\" should return {1}")
@CsvSource({
    "'0', true",
    "'1', true",
    "'10', true",
    "'123', true",
    "'255', true"
})
public void testIsValidIPV4Part_Valid(String input, boolean expected) {
    try {
        boolean result = ipChecker.isValidIPV4Part(input);
        assertEquals(expected, result);
    } catch (Exception e) {
        fail("Unexpected exception for input \"" + input + "\": " + e.getMessage());
    }
}

@DisplayName("White Box & Black Box: Invalid IPv4 part values")
@ParameterizedTest(name = "Input \"{0}\" should return {1}")
@CsvSource({
    "'', false",       // empty string: parse will fail
    "'01', false",     // leading zero when length > 1
    "'00', false",     // leading zero when length > 1
    "'001', false",    // leading zero
    "'256', false",    // numeric value exceeds 255
    "'1234', false",   // length > 3
    "'a12', false",    // non-digit character
    "'12a', false"     // non-digit character
})
public void testIsValidIPV4Part_Invalid(String input, boolean expected) {
    try {
        boolean result = ipChecker.isValidIPV4Part(input);
        assertEquals(expected, result);
    } catch (Exception e) {
        fail("Unexpected exception for input \"" + input + "\": " + e.getMessage());
    }
}

// =============================== Tests for isValidIPV4 ===============================

@DisplayName("Black Box: Valid full IPv4 addresses")
@ParameterizedTest(name = "IP \"{0}\" should return {1}")
@CsvSource({
    "'0.0.0.0', 1",
    "'255.255.255.255', 1",
    "'192.168.0.1', 1"
})
public void testIsValidIPV4_Valid(String ip, int expected) {
    try {
        int result = ipChecker.isValidIPV4(ip);
        assertEquals(expected, result);
    } catch (Exception e) {
        fail("Unexpected exception for IP \"" + ip + "\": " + e.getMessage());
    }
}

@DisplayName("Black Box: Invalid IPv4 addresses - null value")
@Test
public void testIsValidIPV4_Null() {
    try {
        int result = ipChecker.isValidIPV4(null);
        assertEquals(0, result);
    } catch (Exception e) {
        fail("Unexpected exception for null IP: " + e.getMessage());
    }
}

@DisplayName("Black Box: Invalid IPv4 addresses - Incorrect dot count or empty string")
@ParameterizedTest(name = "IP \"{0}\" should return {1}")
@CsvSource({
    "'', 0",              // empty string
    "'192.168.0', 0",      // less than 3 dots
    "'192.168.0.1.1', 0"   // more than 3 dots
})
public void testIsValidIPV4_InvalidDotCount(String ip, int expected) {
    try {
        int result = ipChecker.isValidIPV4(ip);
        assertEquals(expected, result);
    } catch (Exception e) {
        fail("Unexpected exception for IP \"" + ip + "\": " + e.getMessage());
    }
}

@DisplayName("White Box & Black Box: Invalid IPv4 addresses - Faulty parts")
@ParameterizedTest(name = "IP \"{0}\" should return {1}")
@CsvSource({
    "'256.168.0.1', 0",      // First octet invalid: 256 > 255
    "'192.168.01.1', 0",     // Second octet invalid: leading zero in \"01\"
    "'192.168.a.1', 0",      // Non-digit character in third octet
    "'1...1', 0"            // Missing parts due to consecutive dots (tokens lost)
})
public void testIsValidIPV4_InvalidParts(String ip, int expected) {
    try {
        int result = ipChecker.isValidIPV4(ip);
        assertEquals(expected, result);
    } catch (Exception e) {
        fail("Unexpected exception for IP \"" + ip + "\": " + e.getMessage());
    }

}
}