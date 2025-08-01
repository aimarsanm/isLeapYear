package getbonus.bl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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

class ForumBLTest {

    @Mock
    private ForumDAOInterface forumDAO;
    
    private ForumBL sut;
    private SimpleDateFormat sdf;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sut = new ForumBL(forumDAO);
        sdf = new SimpleDateFormat("dd/MM/yyyy");
    }

    // White Box Testing - Decision Coverage for DNI validation
    @ParameterizedTest
    @CsvSource({
        "12345678Z, true",  // Valid DNI
        "1234567,   false", // Invalid length
        "1234567A8, false", // Invalid format
        "12345678A, false"  // Invalid letter
    })
    @DisplayName("Test DNI validation with different formats")
    void testDNIValidation(String dni, boolean isValid) {
        try {
            if (!isValid) {
                when(forumDAO.getUserDAO(dni)).thenReturn(null);
                assertThrows(Exception.class, () -> sut.getBonus(dni));
            }
        } catch (Exception e) {
            fail("Test should not throw exception here");
        }
    }

    @Test
    @DisplayName("Test null DNI throws exception")
    void testNullDNI() {
        assertThrows(Exception.class, () -> sut.getBonus(null));
    }

    @Test
    @DisplayName("Test user not found in database")
    void testUserNotFound() {
        String validDNI = "12345678Z";
        when(forumDAO.getUserDAO(validDNI)).thenReturn(null);
        
        assertThrows(Exception.class, () -> sut.getBonus(validDNI));
    }

    @Test
    @DisplayName("Test user without telephone")
    void testUserWithoutTelephone() {
        String validDNI = "12345678Z";
        User user = new User(validDNI, "Test User", null);
        when(forumDAO.getUserDAO(validDNI)).thenReturn(user);
        
        assertThrows(Exception.class, () -> sut.getBonus(validDNI));
    }

    // Black Box Testing - Equivalence Partitioning and Boundary Analysis for bonus calculation
    @ParameterizedTest
    @CsvSource({
        "0,    0",     // No purchases
        "30,   0",     // Boundary - no bonus
        "30.01, 5.21", // Just above minimum for bonus (30 * 0.1735)
        "100,  17.35", // Middle range
        "288,  49.97", // Boundary - maximum percentage bonus
        "288.01, 50",  // Just above maximum percentage
        "1000, 50"     // Well above maximum
    })
    @DisplayName("Test bonus calculation for different purchase amounts")
    void testBonusCalculation(float purchaseAmount, float expectedBonus) throws Exception {
        String validDNI = "12345678Z";
        User user = new User(validDNI, "Test User", "123456789");
        when(forumDAO.getUserDAO(validDNI)).thenReturn(user);
        
        // Create a purchase with the test amount
        List<Purchase> purchases = new ArrayList<>();
        Purchase purchase = new Purchase(sdf.parse("01/10/2022"));
        Article article = new Article("1", "Test Article", purchaseAmount, false, 1);
        PurchasedArticle purchasedArticle = new PurchasedArticle(article, 1);
        purchase.addArticle(purchasedArticle);
        purchases.add(purchase);
        
        when(forumDAO.getPurchasesDAO(eq(user), any(Date.class), any(Date.class)))
            .thenReturn(purchases.iterator());

        float actualBonus = sut.getBonus(validDNI);
        assertEquals(expectedBonus, actualBonus, 0.01f);
    }

    @Test
    @DisplayName("Test outlet items are not counted for bonus")
    void testOutletItemsNotCounted() throws Exception {
        String validDNI = "12345678Z";
        User user = new User(validDNI, "Test User", "123456789");
        when(forumDAO.getUserDAO(validDNI)).thenReturn(user);
        
        List<Purchase> purchases = new ArrayList<>();
        Purchase purchase = new Purchase(sdf.parse("01/10/2022"));
        
        // Add regular and outlet items
        Article regularArticle = new Article("1", "Regular", 100, false, 1);
        Article outletArticle = new Article("2", "Outlet", 200, true, 1);
        
        purchase.addArticle(new PurchasedArticle(regularArticle, 1));
        purchase.addArticle(new PurchasedArticle(outletArticle, 1));
        purchases.add(purchase);
        
        when(forumDAO.getPurchasesDAO(eq(user), any(Date.class), any(Date.class)))
            .thenReturn(purchases.iterator());

        float actualBonus = sut.getBonus(validDNI);
        assertEquals(17.35f, actualBonus, 0.01f); // Only 100€ counted, not 300€
    }

    @Test
    @DisplayName("Test purchases outside date range are not counted")
    void testPurchasesOutsideDateRange() throws Exception {
        String validDNI = "12345678Z";
        User user = new User(validDNI, "Test User", "123456789");
        when(forumDAO.getUserDAO(validDNI)).thenReturn(user);
        
        List<Purchase> purchases = new ArrayList<>();
        // Purchase outside range
        Purchase outsidePurchase = new Purchase(sdf.parse("01/01/2022"));
        Article article = new Article("1", "Test Article", 1000, false, 1);
        outsidePurchase.addArticle(new PurchasedArticle(article, 1));
        purchases.add(outsidePurchase);
        
        when(forumDAO.getPurchasesDAO(eq(user), any(Date.class), any(Date.class)))
            .thenReturn(new ArrayList<Purchase>().iterator()); // Empty iterator for date range

        float actualBonus = sut.getBonus(validDNI);
        assertEquals(0, actualBonus, 0.01f);
    }
/* 
    @Test
    @DisplayName("Test multiple purchases are summed correctly")
    void testMultiplePurchases() throws Exception {
        String validDNI = "12345678Z";
        User user = new User(validDNI, "Test User", "123456789");
        when(forumDAO.getUserDAO(validDNI)).thenReturn(user);
        
        List<Purchase> purchases = new ArrayList<>();
        Purchase purchase1 = new Purchase(sdf.parse("01/10/2022"));
        Purchase purchase2 = new Purchase(sdf.parse("02/10/2022"));
        
        Article article1 = new Article("1", "Article 1", 20, false, 1);
        Article article2 = new Article("2", "Article 2", 15, false, 1);
        
        purchase1.addArticle(new PurchasedArticle(article1, 1));
        purchase2.addArticle(new PurchasedArticle(article2, 1));
        purchases.add(purchase1);
        purchases.add(purchase2);
        
        when(forumDAO.getPurchasesDAO(eq(user), any(Date.class), any(Date.class)))
            .thenReturn(purchases.iterator());

        float actualBonus = sut.getBonus(validDNI);
         assertEquals(0, actualBonus, 0.01f); // 35€ total, but under minimum 30€ per purchase
    }*/
}