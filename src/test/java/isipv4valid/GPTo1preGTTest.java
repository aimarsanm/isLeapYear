package isipv4valid;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class GPTo1preGTTest {

    @Test
    public void testValidIPAddress() {
        IPAddressChecker checker = new IPAddressChecker();
        assertEquals(1, checker.isValidIPV4("192.168.0.1"));
    }

    @Test
    public void testInvalidIPAddressOutOfRange() {
        IPAddressChecker checker = new IPAddressChecker();
        assertEquals(0, checker.isValidIPV4("256.255.255.255"));
    }

    @Test
    public void testInvalidIPAddressTooShort() {
        IPAddressChecker checker = new IPAddressChecker();
        assertEquals(0, checker.isValidIPV4("192.168.0"));
    }

    @Test
    public void testInvalidIPAddressLeadingZeros() {
        IPAddressChecker checker = new IPAddressChecker();
        assertEquals(0, checker.isValidIPV4("01.02.03.04"));
    }

    @Test
    public void testInvalidIPAddressNonNumeric() {
        IPAddressChecker checker = new IPAddressChecker();
        assertEquals(0, checker.isValidIPV4("abc.def.ghi.jkl"));
    }

    @Test
    public void testNullIPAddress() {
        IPAddressChecker checker = new IPAddressChecker();
        assertEquals(0, checker.isValidIPV4(null));
    }

    @Test
    public void testEmptyIPAddress() {
        IPAddressChecker checker = new IPAddressChecker();
        assertEquals(0, checker.isValidIPV4(""));
    }

    @Test
    public void testLoopbackAddress() {
        IPAddressChecker checker = new IPAddressChecker();
        assertEquals(1, checker.isValidIPV4("127.0.0.1"));
    }
}