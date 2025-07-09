package getbonus.bl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import getbonus.db.ForumDAOInterface;
import getbonus.domain.Article;
import getbonus.domain.Purchase;
import getbonus.domain.PurchasedArticle;
import getbonus.domain.User;

class Sonnet4Test {

    @Mock
    private ForumDAOInterface forumDAO;
    
    private ForumBL sut;
    private SimpleDateFormat sdf;
    private Date firstDate;
    private Date lastDate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sut = new ForumBL(forumDAO);
        sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            firstDate = sdf.parse("01/09/2022");
            lastDate = sdf.parse("06/12/2022");
        } catch (Exception e) {
            fail("Setup failed");
        }
    }

    @Test
    @DisplayName("Test 1: Null ID throws exception")
    void testGetBonusNullId() {
        try {
            float result = sut.getBonus(null);
            fail("Should throw exception for null id");
        } catch (Exception e) {
            assertEquals("id null or not valid", e.getMessage());
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "12345678", "1234567890", "12345678A1", "ABCDEFGHI"})
    @DisplayName("Test 2: Invalid DNI format throws exception")
    void testGetBonusInvalidDNI(String invalidDni) {
        try {
            float result = sut.getBonus(invalidDni);
            fail("Should throw exception for invalid DNI");
        } catch (Exception e) {
            assertEquals("id null or not valid", e.getMessage());
        }
    }

    @Test
    @DisplayName("Test 3: Valid DNI format but user not found throws exception")
    void testGetBonusUserNotFound() {
        try {
            String validDni = "12345678Z";
            when(forumDAO.getUserDAO(validDni)).thenReturn(null);
            
            float result = sut.getBonus(validDni);
            fail("Should throw exception when user not found");
        } catch (Exception e) {
            assertEquals("NAN not in Database", e.getMessage());
        }
    }

    @Test
    @DisplayName("Test 4: User without telephone throws exception")
    void testGetBonusUserWithoutTelephone() {
        String validDni = "12345678Z";
        try {
            User user = new User(validDni, "Test User", null);
            when(forumDAO.getUserDAO(validDni)).thenReturn(user);
            
            float result = sut.getBonus(validDni);
            fail("Should throw exception when user has no telephone");
        } catch (Exception e) {
            assertEquals(validDni + " not registered telephone", e.getMessage());
        }
    }

    @Test
    @DisplayName("Test 5: No purchases returns 0 bonus")
    void testGetBonusNoPurchases() {
        try {
            String validDni = "12345678Z";
            User user = new User(validDni, "Test User", "123456789");
            List<Purchase> emptyPurchases = new ArrayList<>();
            Iterator<Purchase> emptyIterator = emptyPurchases.iterator();
            
            when(forumDAO.getUserDAO(validDni)).thenReturn(user);
            when(forumDAO.getPurchasesDAO(eq(user), any(Date.class), any(Date.class)))
                .thenReturn(emptyIterator);
            
            float result = sut.getBonus(validDni);
            assertEquals(0.0f, result, 0.001f);
        } catch (Exception e) {
            fail("Should not throw exception: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Test 6: Purchases sum equals 30 returns 0 bonus")
    void testGetBonusSumEquals30() {
        try {
            String validDni = "12345678Z";
            User user = new User(validDni, "Test User", "123456789");
            
            // Create purchase with sum = 30
            Purchase purchase = createPurchaseWithSum(30.0f);
            List<Purchase> purchases = new ArrayList<>();
            purchases.add(purchase);
            Iterator<Purchase> purchaseIterator = purchases.iterator();
            
            when(forumDAO.getUserDAO(validDni)).thenReturn(user);
            when(forumDAO.getPurchasesDAO(eq(user), any(Date.class), any(Date.class)))
                .thenReturn(purchaseIterator);
            
            float result = sut.getBonus(validDni);
            assertEquals(0.0f, result, 0.001f);
        } catch (Exception e) {
            fail("Should not throw exception: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Test 7: Purchases sum less than 30 returns 0 bonus")
    void testGetBonusSumLessThan30() {
        try {
            String validDni = "12345678Z";
            User user = new User(validDni, "Test User", "123456789");
            
            // Create purchase with sum = 29.99
            Purchase purchase = createPurchaseWithSum(29.99f);
            List<Purchase> purchases = new ArrayList<>();
            purchases.add(purchase);
            Iterator<Purchase> purchaseIterator = purchases.iterator();
            
            when(forumDAO.getUserDAO(validDni)).thenReturn(user);
            when(forumDAO.getPurchasesDAO(eq(user), any(Date.class), any(Date.class)))
                .thenReturn(purchaseIterator);
            
            float result = sut.getBonus(validDni);
            assertEquals(0.0f, result, 0.001f);
        } catch (Exception e) {
            fail("Should not throw exception: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Test 8: Purchases sum just above 30 calculates percentage bonus")
    void testGetBonusSumJustAbove30() {
        try {
            String validDni = "12345678Z";
            User user = new User(validDni, "Test User", "123456789");
            
            // Create purchase with sum = 30.01
            Purchase purchase = createPurchaseWithSum(30.01f);
            List<Purchase> purchases = new ArrayList<>();
            purchases.add(purchase);
            Iterator<Purchase> purchaseIterator = purchases.iterator();
            
            when(forumDAO.getUserDAO(validDni)).thenReturn(user);
            when(forumDAO.getPurchasesDAO(eq(user), any(Date.class), any(Date.class)))
                .thenReturn(purchaseIterator);
            
            float result = sut.getBonus(validDni);
            float expected = (float) (30.01 * 0.1735);
            assertEquals(expected, result, 0.001f);
        } catch (Exception e) {
            fail("Should not throw exception: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Test 9: Purchases sum equals 288 calculates percentage bonus")
    void testGetBonusSumEquals288() {
        try {
            String validDni = "12345678Z";
            User user = new User(validDni, "Test User", "123456789");
            
            // Create purchase with sum = 288
            Purchase purchase = createPurchaseWithSum(288.0f);
            List<Purchase> purchases = new ArrayList<>();
            purchases.add(purchase);
            Iterator<Purchase> purchaseIterator = purchases.iterator();
            
            when(forumDAO.getUserDAO(validDni)).thenReturn(user);
            when(forumDAO.getPurchasesDAO(eq(user), any(Date.class), any(Date.class)))
                .thenReturn(purchaseIterator);
            
            float result = sut.getBonus(validDni);
            float expected = (float) (288.0 * 0.1735);
            assertEquals(expected, result, 0.001f);
        } catch (Exception e) {
            fail("Should not throw exception: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Test 10: Purchases sum just above 288 returns fixed bonus 50")
    void testGetBonusSumJustAbove288() {
        try {
            String validDni = "12345678Z";
            User user = new User(validDni, "Test User", "123456789");
            
            // Create purchase with sum = 288.01
            Purchase purchase = createPurchaseWithSum(288.01f);
            List<Purchase> purchases = new ArrayList<>();
            purchases.add(purchase);
            Iterator<Purchase> purchaseIterator = purchases.iterator();
            
            when(forumDAO.getUserDAO(validDni)).thenReturn(user);
            when(forumDAO.getPurchasesDAO(eq(user), any(Date.class), any(Date.class)))
                .thenReturn(purchaseIterator);
            
            float result = sut.getBonus(validDni);
            assertEquals(50.0f, result, 0.001f);
        } catch (Exception e) {
            fail("Should not throw exception: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Test 11: Purchases sum much greater than 288 returns fixed bonus 50")
    void testGetBonusSumMuchGreaterThan288() {
        try {
            String validDni = "12345678Z";
            User user = new User(validDni, "Test User", "123456789");
            
            // Create purchase with sum = 500
            Purchase purchase = createPurchaseWithSum(500.0f);
            List<Purchase> purchases = new ArrayList<>();
            purchases.add(purchase);
            Iterator<Purchase> purchaseIterator = purchases.iterator();
            
            when(forumDAO.getUserDAO(validDni)).thenReturn(user);
            when(forumDAO.getPurchasesDAO(eq(user), any(Date.class), any(Date.class)))
                .thenReturn(purchaseIterator);
            
            float result = sut.getBonus(validDni);
            assertEquals(50.0f, result, 0.001f);
        } catch (Exception e) {
            fail("Should not throw exception: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Test 12: Multiple purchases with mixed outlet and non-outlet articles")
    void testGetBonusMultiplePurchasesMixedArticles() {
        try {
            String validDni = "12345678Z";
            User user = new User(validDni, "Test User", "123456789");
            
            // Create multiple purchases
            Purchase purchase1 = createPurchaseWithMixedArticles(50.0f, 20.0f); // 50 non-outlet + 20 outlet
            Purchase purchase2 = createPurchaseWithMixedArticles(100.0f, 30.0f); // 100 non-outlet + 30 outlet
            
            List<Purchase> purchases = new ArrayList<>();
            purchases.add(purchase1);
            purchases.add(purchase2);
            Iterator<Purchase> purchaseIterator = purchases.iterator();
            
            when(forumDAO.getUserDAO(validDni)).thenReturn(user);
            when(forumDAO.getPurchasesDAO(eq(user), any(Date.class), any(Date.class)))
                .thenReturn(purchaseIterator);
            
            float result = sut.getBonus(validDni);
            // Only non-outlet articles count: 50 + 100 = 150
            float expected = (float) (150.0 * 0.1735);
            assertEquals(expected, result, 0.001f);
        } catch (Exception e) {
            fail("Should not throw exception: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Test 13: Only outlet articles returns 0 bonus")
    void testGetBonusOnlyOutletArticles() {
        try {
            String validDni = "12345678Z";
            User user = new User(validDni, "Test User", "123456789");
            
            // Create purchase with only outlet articles
            Purchase purchase = createPurchaseWithOnlyOutletArticles(100.0f);
            List<Purchase> purchases = new ArrayList<>();
            purchases.add(purchase);
            Iterator<Purchase> purchaseIterator = purchases.iterator();
            
            when(forumDAO.getUserDAO(validDni)).thenReturn(user);
            when(forumDAO.getPurchasesDAO(eq(user), any(Date.class), any(Date.class)))
                .thenReturn(purchaseIterator);
            
            float result = sut.getBonus(validDni);
            assertEquals(0.0f, result, 0.001f);
        } catch (Exception e) {
            fail("Should not throw exception: " + e.getMessage());
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"12345678Z", "87654321X", "11111111H"})
    @DisplayName("Test 14: Valid DNI formats work correctly")
    void testGetBonusValidDNIFormats(String validDni) {
        try {
            User user = new User(validDni, "Test User", "123456789");
            List<Purchase> emptyPurchases = new ArrayList<>();
            Iterator<Purchase> emptyIterator = emptyPurchases.iterator();
            
            when(forumDAO.getUserDAO(validDni)).thenReturn(user);
            when(forumDAO.getPurchasesDAO(eq(user), any(Date.class), any(Date.class)))
                .thenReturn(emptyIterator);
            
            float result = sut.getBonus(validDni);
            assertEquals(0.0f, result, 0.001f);
        } catch (Exception e) {
            fail("Should not throw exception: " + e.getMessage());
        }
    }
/*
    @Test
    @DisplayName("Test 15: Boundary value - sum exactly at calculation threshold")
    void testGetBonusBoundaryValues() {
        try {
            String validDni = "12345678Z";
            User user = new User(validDni, "Test User", "123456789");
            
            // Test multiple boundary values
            float[] testValues = {30.0f, 30.01f, 287.99f, 288.0f, 288.01f};
            float[] expectedResults = {
                0.0f, 
                (float)(30.01 * 0.1735), 
                (float)(287.99 * 0.1735), 
                (float)(288.0 * 0.1735), 
                50.0f
            };
            
            for (int i = 0; i < testValues.length; i++) {
                Purchase purchase = createPurchaseWithSum(testValues[i]);
                List<Purchase> purchases = new ArrayList<>();
                purchases.add(purchase);
                Iterator<Purchase> purchaseIterator = purchases.iterator();
                
                when(forumDAO.getPurchasesDAO(eq(user), any(Date.class), any(Date.class)))
                    .thenReturn(purchaseIterator);
                
                float result = sut.getBonus(validDni);
                assertEquals(expectedResults[i], result, 0.001f, 
                    "Failed for value: " + testValues[i]);
            }
        } catch (Exception e) {
            fail("Should not throw exception: " + e.getMessage());
        }
    }
 */
    // Helper methods for creating test data
    private Purchase createPurchaseWithSum(float totalSum) {
        Purchase purchase = new Purchase();
        Article article = new Article("1", "Test Article", totalSum, false, 10);
        PurchasedArticle purchasedArticle = new PurchasedArticle(article, 1);
        purchase.addArticle(purchasedArticle);
        return purchase;
    }

    private Purchase createPurchaseWithMixedArticles(float nonOutletSum, float outletSum) {
        Purchase purchase = new Purchase();
        
        // Add non-outlet article
        Article nonOutletArticle = new Article("1", "Non-Outlet Article", nonOutletSum, false, 10);
        PurchasedArticle nonOutletPurchased = new PurchasedArticle(nonOutletArticle, 1);
        purchase.addArticle(nonOutletPurchased);
        
        // Add outlet article
        Article outletArticle = new Article("2", "Outlet Article", outletSum, true, 10);
        PurchasedArticle outletPurchased = new PurchasedArticle(outletArticle, 1);
        purchase.addArticle(outletPurchased);
        
        return purchase;
    }

    private Purchase createPurchaseWithOnlyOutletArticles(float totalSum) {
        Purchase purchase = new Purchase();
        Article article = new Article("1", "Outlet Article", totalSum, true, 10);
        PurchasedArticle purchasedArticle = new PurchasedArticle(article, 1);
        purchase.addArticle(purchasedArticle);
        return purchase;
    }
}