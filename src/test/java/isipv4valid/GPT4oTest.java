package isipv4valid;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

// filepath: src/test/java/isipv4valid/IPAddressCheckerTest.java




@DisplayName("Unit Tests for IPAddressChecker")
class GPT4oTest {

    private IPAddressChecker ipAddressChecker;

    @BeforeEach
    void setUp() {
        ipAddressChecker = new IPAddressChecker();
    }

    @ParameterizedTest
    @DisplayName("Test isValidIPV4Part with valid inputs")
    @CsvSource({
        "0, true",
        "255, true",
        "1, true",
        "123, true"
    })
    void testIsValidIPV4PartValidInputs(String input, boolean expected) {
        assertEquals(expected, ipAddressChecker.isValidIPV4Part(input));
    }

    @ParameterizedTest
    @DisplayName("Test isValidIPV4Part with invalid inputs")
    @CsvSource({
        "256, false",
        "-1, false",
        "abc, false",
        "00, false",
        "001, false",
        "05, false",
        "1234, false"
        //", false"
    })
    void testIsValidIPV4PartInvalidInputs(String input, boolean expected) {
        assertEquals(expected, ipAddressChecker.isValidIPV4Part(input));
    }

    @ParameterizedTest
    @DisplayName("Test isValidIPV4 with valid IPs")
    @CsvSource({
        "192.168.0.1, 1",
        "127.0.0.1, 1",
        "255.255.255.255, 1",
        "0.0.0.0, 1"
    })
    void testIsValidIPV4ValidInputs(String input, int expected) {
        assertEquals(expected, ipAddressChecker.isValidIPV4(input));
    }

    @ParameterizedTest
    @DisplayName("Test isValidIPV4 with invalid IPs")
    @CsvSource({
        "256.256.256.256, 0",
        "192.168.0, 0",
        "192.168.0.1.1, 0",
        "192.168..1, 0",
        "abc.def.ghi.jkl, 0",
        ", 0",
        "192.168.0.01, 0"
    })
    void testIsValidIPV4InvalidInputs(String input, int expected) {
        assertEquals(expected, ipAddressChecker.isValidIPV4(input));
    }

    @Test
    @DisplayName("Test isValidIPV4 with null input")
    void testIsValidIPV4NullInput() {
        assertEquals(0, ipAddressChecker.isValidIPV4(null));
    }
/* 
    @Test
    @DisplayName("Test isValidIPV4Part with null input")
    void testIsValidIPV4PartNullInput() {
        try {
            assertFalse(ipAddressChecker.isValidIPV4Part(null));
        } catch (Exception e) {
            fail("Exception should not be thrown for null input");
        }
    }
        */
}