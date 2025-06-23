package pruebas;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;


 class IsPrimeTest {

  
/* 
 @Test
  void testNullArgsThrowsMissingArgumentException() {

  assertThrows(MissingArgumentException.class, () -> IsPrime.isPrime(null));
 }

  

@Test

 void testMoreThanOneArgThrowsOnly1ArgumentException() throws Exception {

assertThrows(Only1ArgumentException.class, () -> IsPrime.isPrime(new String[] { "2", "3" }));
}

  

@Test
 void testNegativeNumberThrowsNoPositiveNumberException() throws Exception {

assertThrows(NoPositiveNumberException.class, () -> IsPrime.isPrime(new String[] { "-5" }));
}

  

@Test

 void testNonNumericThrowsNoPositiveNumberException() throws Exception {

assertThrows(NoPositiveNumberException.class, () -> IsPrime.isPrime(new String[] { "abc" }));
}

  

@Test

 void testZeroThrowsNoPositiveNumberException() throws Exception {

assertThrows(NoPositiveNumberException.class, () -> IsPrime.isPrime(new String[] { "0" }));
}
*/
  

@Test

 void testOneIsConsideredPrime() throws Exception {

assertTrue(IsPrime.isPrime(new String[] { "1" }));

}

  

@Test

 void testTwoIsPrime() throws Exception {

assertTrue(IsPrime.isPrime(new String[] { "2" }));

}

  


@Test

 void testPrimeNumber() throws Exception {

assertTrue(IsPrime.isPrime(new String[] { "17" }));

}

  

@Test

 void testCompositeNumber() throws Exception {

assertFalse(IsPrime.isPrime(new String[] { "18" }));

}

  

@Test

 void testFloatStringTruncatedToPrime() throws Exception {

 // "7.9" becomes 7, which is prime

assertTrue(IsPrime.isPrime(new String[] { "7.9" }));

 }

  

@Test

 void testFloatStringTruncatedToComposite() throws Exception {

// "9.1" becomes 9, which is composite

assertFalse(IsPrime.isPrime(new String[] { "9.1" }));

}

}