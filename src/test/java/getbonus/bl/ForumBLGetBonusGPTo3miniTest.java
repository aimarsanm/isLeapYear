package getbonus.bl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import getbonus.db.ForumDAOInterface;
import getbonus.domain.Purchase;
import getbonus.domain.PurchasedArticle;
import getbonus.domain.User;
import getbonus.exceptions.NullParameterException;
import getbonus.exceptions.QuantityLessThan1Exception;
import getbonus.exceptions.UserNotFoundException;

public class ForumBLGetBonusGPTo3miniTest {

    ForumDAOInterface daoMock;
    ForumBL forumBL;
    AutoCloseable closeable;

    @BeforeEach
    public void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        daoMock = Mockito.mock(ForumDAOInterface.class);
        forumBL = new ForumBL(daoMock);
    }

    @AfterEach
    public void tearDown() throws Exception {
        closeable.close();
    }

    // Test for white-box coverage: null id check
    @Test
    @DisplayName("TC1: getBonus should throw exception when id is null")
    public void testGetBonus_NullId() {
        try {
            forumBL.getBonus(null);
            fail("Expected Exception for null id");
        } catch (Exception e) {
            assertEquals("id null or not valid", e.getMessage());
        }
    }

    // Test for white-box coverage: invalid DNI (fails ValidadorDNI)
    @Test
    @DisplayName("TC2: getBonus should throw exception when id is not valid according to ValidadorDNI")
    public void testGetBonus_InvalidDNI() {
        // "12345678A" is invalid because the correct letter should be Z.
        try {
            forumBL.getBonus("12345678A");
            fail("Expected Exception for invalid DNI");
        } catch (Exception e) {
            assertEquals("id null or not valid", e.getMessage());
        }
    }

    // Test for white-box: user not found in DAO
    @Test
    @DisplayName("TC3: getBonus should throw exception when user is not found in Database")
    public void testGetBonus_UserNotFound() {
        String validDNI = "12345678Z"; // Valid DNI (calculated letter is correct)
        try {
            // Stub dao.getUserDAO to return null
            Mockito.when(daoMock.getUserDAO(ArgumentMatchers.eq(validDNI))).thenReturn(null);
            forumBL.getBonus(validDNI);
            fail("Expected Exception for non-existing user");
        } catch (Exception e) {
            assertEquals("NAN not in Database", e.getMessage());
        }
    }

    // Test for white-box: user with null telephone
    @Test
    @DisplayName("TC4: getBonus should throw exception when user's telephone is not registered")
    public void testGetBonus_UserWithoutTelephone() {
        String validDNI = "12345678Z"; 
        User user = new User(validDNI, validDNI, validDNI);
        user.setTelephone(null);
        // Assuming User has a setter for telephone
        try {
            Mockito.when(daoMock.getUserDAO(ArgumentMatchers.eq(validDNI))).thenReturn(user);
            forumBL.getBonus(validDNI);
            fail("Expected Exception for user without telephone");
        } catch (Exception e) {
            assertEquals(validDNI + " not registered telephone", e.getMessage());
        }
    }

    // Parameterized tests for bonus calculation based on purchase amounts.
    // This test simulates a single purchase with one purchased article.
    @ParameterizedTest
    @CsvSource({
        // price, quantity, isOutlet, expectedBonus
        "10.0, 1, false, 0.0",    // sum = 10.0 <= 30 => bonus 0
        "10.0, 3, false, 0.0",    // sum = 30.0 (not > 30) => bonus 0
        "10.0, 4, false, 6.94",   // sum = 40.0 -> bonus = 40*0.1735 = 6.94 (approx)
        "150.0, 2, false, 50.0",  // sum = 300.0 > 288 => bonus = 50
        "100.0, 2, true, 0.0"     // article is outlet so ignored => bonus = 0
    })
    @DisplayName("TC5: getBonus calculation for single purchase with one article")
    public void testGetBonusCalculation_SinglePurchase(double price, int quantity, boolean isOutlet, double expectedBonus) {
        String validDNI = "12345678Z";
        // Create a valid user with non-null telephone.
        User user = new User(validDNI, validDNI, validDNI);
        user.setTelephone("555-1234");
        try {
            // Stub dao.getUserDAO to return valid user.
            Mockito.when(daoMock.getUserDAO(ArgumentMatchers.eq(validDNI))).thenReturn(user);

            // Prepare a dummy purchase and purchased article.
            PurchasedArticle purchasedArticle = Mockito.mock(PurchasedArticle.class);
            Mockito.when(purchasedArticle.isOutlet()).thenReturn(isOutlet);
            Mockito.when(purchasedArticle.getPrice()).thenReturn((float) price);
            Mockito.when(purchasedArticle.getQuantity()).thenReturn(quantity);
            
            // Create iterator for purchased articles (single article in this test)
            Iterator<PurchasedArticle> articlesIterator = Collections.singletonList(purchasedArticle).iterator();
            
            Purchase purchase = Mockito.mock(Purchase.class);
            Mockito.when(purchase.getPurchaseIterator()).thenReturn(articlesIterator);
            
            // Create iterator for purchases (single purchase)
            Iterator<Purchase> purchasesIterator = Collections.singletonList(purchase).iterator();
            
            // Stub the dao.getPurchasesDAO to return the purchases iterator.
            // The dates passed can be matched with any Date using ArgumentMatchers.
            Mockito.when(daoMock.getPurchasesDAO(ArgumentMatchers.eq(user), 
                    ArgumentMatchers.any(Date.class), ArgumentMatchers.any(Date.class))
            ).thenReturn(purchasesIterator);
            
            // Call getBonus method.
            float bonus = forumBL.getBonus(validDNI);
            
            // Assert bonus with a delta for floating point arithmetic.
            assertEquals(expectedBonus, bonus, 0.01, "Bonus calculation did not match expected value");
            
        } catch (Exception e) {
            fail("No exception expected, but got: " + e.getMessage());
        }
    }

    // Parameterized tests for bonus calculation with multiple purchases and multiple articles.
    // This reduces redundancy by combining several purchase items.
    /*@ParameterizedTest
    @CsvSource({
        // In this test, we simulate two articles in one purchase.
        // price1, quantity1, isOutlet1, price2, quantity2, isOutlet2, expectedBonus
        // Calculation: sum = (price1*quantity1 if non-outlet) + (price2*quantity2 if non-outlet)
        "5.0, 2, false, 10.0, 1, false,  (5.0*2+10.0*1) <= 30 ? 0.0 : 5.0",  // sum = 20.0 => bonus 0
        "20.0, 1, false, 15.0, 1, false,  (20.0+15.0)  > 30 && <=288 ? 35.0*0.1735 : 0.0", // sum = 35.0 => bonus = 6.07 approx
        "50.0, 2, false, 30.0, 1, false,  (50.0*2+30.0) > 288 ? 50.0 : 0.0" // sum = 130? Actually recalc: 50*2+30=130 -> bonus = 130*0.1735 = 22.555, so update
        // We'll update the values manually in code below rather than rely on expression parsing in CsvSource.
    })
    @DisplayName("TC6: getBonus calculation for multiple articles in one purchase")
    public void testGetBonusCalculation_MultipleArticles(
            // We'll ignore the expression parts in CSV and use fixed numeric values.
            double price1, int quantity1, boolean isOutlet1,
            double price2, int quantity2, boolean isOutlet2,
            double dummy) {
        // For this test, we will hardcode the expected bonus based on given parameters.
        // Compute sum from non-outlet articles.
        double sum = 0.0;
        if (!isOutlet1) {
            sum += price1 * quantity1;
        }
        if (!isOutlet2) {
            sum += price2 * quantity2;
        }
        double expectedBonus = 0.0;
        if (sum > 30) {
            if (sum > 288) {
                expectedBonus = 50.0;
            } else {
                expectedBonus = sum * 0.1735;
            }
        }
                
        String validDNI = "12345678Z";
        User user = new User(validDNI, validDNI, validDNI);
        user.setTelephone("555-4321");
        try {
            Mockito.when(daoMock.getUserDAO(ArgumentMatchers.eq(validDNI))).thenReturn(user);
            
            // Create two purchased articles.
            PurchasedArticle pa1 = Mockito.mock(PurchasedArticle.class);
            Mockito.when(pa1.isOutlet()).thenReturn(isOutlet1);
            Mockito.when(pa1.getPrice()).thenReturn((float) price1);
            Mockito.when(pa1.getQuantity()).thenReturn(quantity1);
            
            PurchasedArticle pa2 = Mockito.mock(PurchasedArticle.class);
            Mockito.when(pa2.isOutlet()).thenReturn(isOutlet2);
            Mockito.when(pa2.getPrice()).thenReturn((float) price2);
            Mockito.when(pa2.getQuantity()).thenReturn(quantity2);
            
            // Create iterator for articles (two articles).
            Iterator<PurchasedArticle> articlesIterator = Arrays.asList(pa1, pa2).iterator();
            
            Purchase purchase = Mockito.mock(Purchase.class);
            Mockito.when(purchase.getPurchaseIterator()).thenReturn(articlesIterator);
            
            // Create iterator for purchases (single purchase)
            Iterator<Purchase> purchasesIterator = Collections.singletonList(purchase).iterator();
            
            Mockito.when(daoMock.getPurchasesDAO(ArgumentMatchers.eq(user), 
                    ArgumentMatchers.any(Date.class), ArgumentMatchers.any(Date.class))
            ).thenReturn(purchasesIterator);
            
            float bonus = forumBL.getBonus(validDNI);
            assertEquals(expectedBonus, bonus, 0.01, "Bonus calculation for multiple articles did not match expected value");
            
        } catch (Exception e) {
            fail("No exception expected, but got: " + e.getMessage());
        }
    }*/

}