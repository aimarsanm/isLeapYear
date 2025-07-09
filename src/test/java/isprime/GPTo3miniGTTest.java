package isprime;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;




public class GPTo3miniGTTest {

    @Test
    public void testMissingArgumentException() {
        assertThrows(MissingArgumentException.class, () -> {
            IsPrime.isPrime(null);
        });
    }

    @Test
    public void testOnly1ArgumentException_withMultipleArgs() {
        String[] args = {"7", "11"};
        assertThrows(Only1ArgumentException.class, () -> {
            IsPrime.isPrime(args);
        });
    }

    @Test
    public void testNoPositiveNumberException_NegativeNumber() {
        String[] args = {"-7"};
        assertThrows(NoPositiveNumberException.class, () -> {
            IsPrime.isPrime(args);
        });
    }

    @Test
    public void testNoPositiveNumberException_NonNumber() {
        String[] args = {"abc"};
        assertThrows(NoPositiveNumberException.class, () -> {
            IsPrime.isPrime(args);
        });
    }

    @Test
    public void testIsPrime_WithPrimeNumber() throws Exception {
        String[] args = {"7"};
        assertTrue(IsPrime.isPrime(args));
    }

    @Test
    public void testIsPrime_WithNonPrimeNumber() throws Exception {
        String[] args = {"8"};
        assertFalse(IsPrime.isPrime(args));
    }
}