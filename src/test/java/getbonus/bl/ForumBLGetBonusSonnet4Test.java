package getbonus.bl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import getbonus.db.ForumDAOInterface;
import getbonus.domain.Article;
import getbonus.domain.Purchase;
import getbonus.domain.PurchasedArticle;
import getbonus.domain.User;

class ForumBLGetBonusSonnet4Test {

    @Mock
    private ForumDAOInterface forumDAO;
    
    private ForumBL sut;
    private SimpleDateFormat sdf;
    private Date firstDate;
    private Date lastDate;

    @BeforeEach
    void setUp() {
        try {
            MockitoAnnotations.openMocks(this);
            sut = new ForumBL(forumDAO);
            sdf = new SimpleDateFormat("dd/MM/yyyy");
            firstDate = sdf.parse("01/09/2022");
            lastDate = sdf.parse("06/12/2022");
        } catch (Exception e) {
            fail("Setup failed: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Test 1: Exception thrown when id is null")
    void testGetBonusWithNullId() {
        try {
            Exception exception = assertThrows(Exception.class, () -> sut.getBonus(null));
            assertEquals("id null or not valid", exception.getMessage());
        } catch (Exception e) {
            fail("Test failed with unexpected exception: " + e.getMessage());
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "123", "12345678", "1234567890", "12345678A1", "ABCDEFGHI", "12345678-"})
    @DisplayName("Test 2: Exception thrown when id has invalid DNI format")
    void testGetBonusWithInvalidDNI(String invalidDni) {
        try {
            Exception exception = assertThrows(Exception.class, () -> sut.getBonus(invalidDni));
            assertEquals("id null or not valid", exception.getMessage());
        } catch (Exception e) {
            fail("Test failed with unexpected exception: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Test 3: Exception thrown when user not found in database")
    void testGetBonusUserNotInDatabase() {
        try {
            String validDni = "12345678Z";
            when(forumDAO.getUserDAO(validDni)).thenReturn(null);
            
            Exception exception = assertThrows(Exception.class, () -> sut.getBonus(validDni));
            assertEquals("NAN not in Database", exception.getMessage());
            
            verify(forumDAO, times(1)).getUserDAO(validDni);
        } catch (Exception e) {
            fail("Test failed with unexpected exception: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Test 4: Exception thrown when user has no telephone registered")
    void testGetBonusUserWithoutTelephone() {
        try {
            String validDni = "12345678Z";
            User user = new User(validDni, "Test User", null);
            
            when(forumDAO.getUserDAO(validDni)).thenReturn(user);
            
            Exception exception = assertThrows(Exception.class, () -> sut.getBonus(validDni));
            assertEquals(validDni + " not registered telephone", exception.getMessage());
            
            verify(forumDAO, times(1)).getUserDAO(validDni);
        } catch (Exception e) {
            fail("Test failed with unexpected exception: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Test 5: Returns 0 when user has no purchases")
    void testGetBonusUserWithNoPurchases() {
        try {
            String validDni = "12345678Z";
            User user = new User(validDni, "Test User", "123456789");
            List<Purchase> emptyPurchases = new ArrayList<>();
            
            when(forumDAO.getUserDAO(validDni)).thenReturn(user);
            when(forumDAO.getPurchasesDAO(eq(user), any(Date.class), any(Date.class)))
                .thenReturn(emptyPurchases.iterator());
            
            float result = sut.getBonus(validDni);
            assertEquals(0.0f, result, 0.001f);
            
            verify(forumDAO, times(1)).getUserDAO(validDni);
            verify(forumDAO, times(1)).getPurchasesDAO(eq(user), any(Date.class), any(Date.class));
        } catch (Exception e) {
            fail("Test failed with unexpected exception: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Test 6: Returns 0 when sumPurchases equals 30 (boundary)")
    void testGetBonusSumPurchasesEquals30() {
        try {
            String validDni = "12345678Z";
            User user = new User(validDni, "Test User", "123456789");
            
            List<Purchase> purchases = createPurchasesWithSum(30.0f);
            
            when(forumDAO.getUserDAO(validDni)).thenReturn(user);
            when(forumDAO.getPurchasesDAO(eq(user), any(Date.class), any(Date.class)))
                .thenReturn(purchases.iterator());
            
            float result = sut.getBonus(validDni);
            assertEquals(0.0f, result, 0.001f);
            
            verify(forumDAO, times(1)).getUserDAO(validDni);
            verify(forumDAO, times(1)).getPurchasesDAO(eq(user), any(Date.class), any(Date.class));
        } catch (Exception e) {
            fail("Test failed with unexpected exception: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Test 7: Returns 0 when sumPurchases is less than 30")
    void testGetBonusSumPurchasesLessThan30() {
        try {
            String validDni = "12345678Z";
            User user = new User(validDni, "Test User", "123456789");
            
            List<Purchase> purchases = createPurchasesWithSum(25.0f);
            
            when(forumDAO.getUserDAO(validDni)).thenReturn(user);
            when(forumDAO.getPurchasesDAO(eq(user), any(Date.class), any(Date.class)))
                .thenReturn(purchases.iterator());
            
            float result = sut.getBonus(validDni);
            assertEquals(0.0f, result, 0.001f);
            
            verify(forumDAO, times(1)).getUserDAO(validDni);
            verify(forumDAO, times(1)).getPurchasesDAO(eq(user), any(Date.class), any(Date.class));
        } catch (Exception e) {
            fail("Test failed with unexpected exception: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Test 8: Returns percentage when sumPurchases is between 30 and 288")
    void testGetBonusSumPurchasesBetween30And288() {
        try {
            String validDni = "12345678Z";
            User user = new User(validDni, "Test User", "123456789");
            float sumValue = 100.0f;
            
            List<Purchase> purchases = createPurchasesWithSum(sumValue);
            
            when(forumDAO.getUserDAO(validDni)).thenReturn(user);
            when(forumDAO.getPurchasesDAO(eq(user), any(Date.class), any(Date.class)))
                .thenReturn(purchases.iterator());
            
            float result = sut.getBonus(validDni);
            float expected = (float) (sumValue * 0.1735);
            assertEquals(expected, result, 0.001f);
            
            verify(forumDAO, times(1)).getUserDAO(validDni);
            verify(forumDAO, times(1)).getPurchasesDAO(eq(user), any(Date.class), any(Date.class));
        } catch (Exception e) {
            fail("Test failed with unexpected exception: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Test 9: Returns percentage when sumPurchases equals 288 (boundary)")
    void testGetBonusSumPurchasesEquals288() {
        try {
            String validDni = "12345678Z";
            User user = new User(validDni, "Test User", "123456789");
            float sumValue = 288.0f;
            
            List<Purchase> purchases = createPurchasesWithSum(sumValue);
            
            when(forumDAO.getUserDAO(validDni)).thenReturn(user);
            when(forumDAO.getPurchasesDAO(eq(user), any(Date.class), any(Date.class)))
                .thenReturn(purchases.iterator());
            
            float result = sut.getBonus(validDni);
            float expected = (float) (sumValue * 0.1735);
            assertEquals(expected, result, 0.001f);
            
            verify(forumDAO, times(1)).getUserDAO(validDni);
            verify(forumDAO, times(1)).getPurchasesDAO(eq(user), any(Date.class), any(Date.class));
        } catch (Exception e) {
            fail("Test failed with unexpected exception: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Test 10: Returns 50 when sumPurchases is greater than 288")
    void testGetBonusSumPurchasesGreaterThan288() {
        try {
            String validDni = "12345678Z";
            User user = new User(validDni, "Test User", "123456789");
            
            List<Purchase> purchases = createPurchasesWithSum(300.0f);
            
            when(forumDAO.getUserDAO(validDni)).thenReturn(user);
            when(forumDAO.getPurchasesDAO(eq(user), any(Date.class), any(Date.class)))
                .thenReturn(purchases.iterator());
            
            float result = sut.getBonus(validDni);
            assertEquals(50.0f, result, 0.001f);
            
            verify(forumDAO, times(1)).getUserDAO(validDni);
            verify(forumDAO, times(1)).getPurchasesDAO(eq(user), any(Date.class), any(Date.class));
        } catch (Exception e) {
            fail("Test failed with unexpected exception: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Test 11: Ignores outlet articles in sum calculation")
    void testGetBonusIgnoresOutletArticles() {
        try {
            String validDni = "12345678Z";
            User user = new User(validDni, "Test User", "123456789");
            
            List<Purchase> purchases = createPurchasesWithOutletArticles();
            
            when(forumDAO.getUserDAO(validDni)).thenReturn(user);
            when(forumDAO.getPurchasesDAO(eq(user), any(Date.class), any(Date.class)))
                .thenReturn(purchases.iterator());
            
            float result = sut.getBonus(validDni);
            // Only non-outlet articles should be counted: 50 * 1 = 50
            float expected = (float) (50.0 * 0.1735);
            assertEquals(expected, result, 0.001f);
            
            verify(forumDAO, times(1)).getUserDAO(validDni);
            verify(forumDAO, times(1)).getPurchasesDAO(eq(user), any(Date.class), any(Date.class));
        } catch (Exception e) {
            fail("Test failed with unexpected exception: " + e.getMessage());
        }
    }

    @ParameterizedTest
    @ValueSource(floats = {30.1f, 50.0f, 150.0f, 287.9f})
    @DisplayName("Test 12: Parameterized test for values between 30 and 288")
    void testGetBonusParameterizedBetween30And288(float sumValue) {
        try {
            String validDni = "12345678Z";
            User user = new User(validDni, "Test User", "123456789");
            
            List<Purchase> purchases = createPurchasesWithSum(sumValue);
            
            when(forumDAO.getUserDAO(validDni)).thenReturn(user);
            when(forumDAO.getPurchasesDAO(eq(user), any(Date.class), any(Date.class)))
                .thenReturn(purchases.iterator());
            
            float result = sut.getBonus(validDni);
            float expected = (float) (sumValue * 0.1735);
            assertEquals(expected, result, 0.001f);
            
            verify(forumDAO, atLeastOnce()).getUserDAO(validDni);
            verify(forumDAO, atLeastOnce()).getPurchasesDAO(eq(user), any(Date.class), any(Date.class));
        } catch (Exception e) {
            fail("Test failed with unexpected exception: " + e.getMessage());
        }
    }

    @ParameterizedTest
    @ValueSource(floats = {289.0f, 350.0f, 500.0f, 1000.0f})
    @DisplayName("Test 13: Parameterized test for values greater than 288")
    void testGetBonusParameterizedGreaterThan288(float sumValue) {
        try {
            String validDni = "12345678Z";
            User user = new User(validDni, "Test User", "123456789");
            
            List<Purchase> purchases = createPurchasesWithSum(sumValue);
            
            when(forumDAO.getUserDAO(validDni)).thenReturn(user);
            when(forumDAO.getPurchasesDAO(eq(user), any(Date.class), any(Date.class)))
                .thenReturn(purchases.iterator());
            
            float result = sut.getBonus(validDni);
            assertEquals(50.0f, result, 0.001f);
            
            verify(forumDAO, atLeastOnce()).getUserDAO(validDni);
            verify(forumDAO, atLeastOnce()).getPurchasesDAO(eq(user), any(Date.class), any(Date.class));
        } catch (Exception e) {
            fail("Test failed with unexpected exception: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Test 14: Multiple purchases with mixed article types")
    void testGetBonusMultiplePurchasesWithMixedArticles() {
        try {
            String validDni = "12345678Z";
            User user = new User(validDni, "Test User", "123456789");
            
            List<Purchase> purchases = createMultiplePurchasesWithMixedArticles();
            
            when(forumDAO.getUserDAO(validDni)).thenReturn(user);
            when(forumDAO.getPurchasesDAO(eq(user), any(Date.class), any(Date.class)))
                .thenReturn(purchases.iterator());
            
            float result = sut.getBonus(validDni);
            // Expected sum: (10*2) + (30*1) = 50 (non-outlet articles only)
            float expected = (float) (50.0 * 0.1735);
            assertEquals(expected, result, 0.001f);
            
            verify(forumDAO, times(1)).getUserDAO(validDni);
            verify(forumDAO, times(1)).getPurchasesDAO(eq(user), any(Date.class), any(Date.class));
        } catch (Exception e) {
            fail("Test failed with unexpected exception: " + e.getMessage());
        }
    }

    // Helper methods
    private List<Purchase> createPurchasesWithSum(float totalSum) {
        List<Purchase> purchases = new ArrayList<>();
        Purchase purchase = new Purchase();
        
        Article article = new Article("ART001", "Test Article", totalSum, false, 10);
        PurchasedArticle purchasedArticle = new PurchasedArticle(article, 1);
        
        purchase.addArticle(purchasedArticle);
        purchases.add(purchase);
        
        return purchases;
    }

    private List<Purchase> createPurchasesWithOutletArticles() {
        List<Purchase> purchases = new ArrayList<>();
        Purchase purchase = new Purchase();
        
        // Non-outlet article (should be counted)
        Article nonOutletArticle = new Article("ART001", "Regular Article", 50.0f, false, 10);
        PurchasedArticle nonOutletPurchased = new PurchasedArticle(nonOutletArticle, 1);
        purchase.addArticle(nonOutletPurchased);
        
        // Outlet article (should be ignored)
        Article outletArticle = new Article("ART002", "Outlet Article", 100.0f, true, 10);
        PurchasedArticle outletPurchased = new PurchasedArticle(outletArticle, 1);
        purchase.addArticle(outletPurchased);
        
        purchases.add(purchase);
        return purchases;
    }

    private List<Purchase> createMultiplePurchasesWithMixedArticles() {
        List<Purchase> purchases = new ArrayList<>();
        
        // First purchase
        Purchase purchase1 = new Purchase();
        Article article1 = new Article("ART001", "Article 1", 10.0f, false, 10);
        PurchasedArticle purchased1 = new PurchasedArticle(article1, 2);
        purchase1.addArticle(purchased1);
        
        Article outletArticle1 = new Article("ART002", "Outlet Article 1", 20.0f, true, 10);
        PurchasedArticle outletPurchased1 = new PurchasedArticle(outletArticle1, 3);
        purchase1.addArticle(outletPurchased1);
        
        // Second purchase
        Purchase purchase2 = new Purchase();
        Article article2 = new Article("ART003", "Article 2", 30.0f, false, 10);
        PurchasedArticle purchased2 = new PurchasedArticle(article2, 1);
        purchase2.addArticle(purchased2);
        
        Article outletArticle2 = new Article("ART004", "Outlet Article 2", 50.0f, true, 10);
        PurchasedArticle outletPurchased2 = new PurchasedArticle(outletArticle2, 2);
        purchase2.addArticle(outletPurchased2);
        
        purchases.add(purchase1);
        purchases.add(purchase2);
        
        return purchases;
    }
}