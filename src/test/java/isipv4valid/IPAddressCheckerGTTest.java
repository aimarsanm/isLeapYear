package isipv4valid;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;




class IPAddressCheckerGTTest {

    private final IPAddressChecker checker = new IPAddressChecker();

    @Test
    void testValidIPAddresses() {
        assertEquals(1, checker.isValidIPV4("192.168.1.1"));
        assertEquals(1, checker.isValidIPV4("0.0.0.0"));
        assertEquals(1, checker.isValidIPV4("255.255.255.255"));
        assertEquals(1, checker.isValidIPV4("1.2.3.4"));
    }

    @Test
    void testInvalidIPAddresses_WrongDotCount() {
        assertEquals(0, checker.isValidIPV4("192.168.1"));
        assertEquals(0, checker.isValidIPV4("192.168.1.1.1"));
        assertEquals(0, checker.isValidIPV4("19216811"));
    }

    @Test
    void testInvalidIPAddresses_OutOfRange() {
        assertEquals(0, checker.isValidIPV4("256.100.100.100"));
        assertEquals(0, checker.isValidIPV4("192.168.1.300"));
        assertEquals(0, checker.isValidIPV4("999.999.999.999"));
    }

    @Test
    void testInvalidIPAddresses_LeadingZeros() {
        assertEquals(0, checker.isValidIPV4("01.2.3.4"));
        assertEquals(0, checker.isValidIPV4("192.168.01.1"));
        assertEquals(0, checker.isValidIPV4("001.002.003.004"));
    }

    @Test
    void testInvalidIPAddresses_NonNumeric() {
        assertEquals(0, checker.isValidIPV4("abc.def.ghi.jkl"));
        assertEquals(0, checker.isValidIPV4("192.168.one.1"));
        assertEquals(0, checker.isValidIPV4("192.168.1.a"));
    }

    @Test
    void testInvalidIPAddresses_EmptyOrNull() {
        assertEquals(0, checker.isValidIPV4(""));
        assertEquals(0, checker.isValidIPV4(null));
    }

    @Test
    void testInvalidIPAddresses_ExtraDots() {
        assertEquals(0, checker.isValidIPV4("1...1"));
        assertEquals(0, checker.isValidIPV4(".192.168.1.1"));
        assertEquals(0, checker.isValidIPV4("192.168.1.1."));
    }
}