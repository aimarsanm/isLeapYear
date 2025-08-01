package getbonus.bl;

import static org.junit.jupiter.api.Assertions.*;
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
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import getbonus.db.ForumDAOInterface;
import getbonus.domain.Article;
import getbonus.domain.Purchase;
import getbonus.domain.PurchasedArticle;
import getbonus.domain.User;

class ForumBLGetBonusTest {

    @Mock
    private ForumDAOInterface forumDAO;
    
    private ForumBL sut;
    private SimpleDateFormat sdf;
    private Date firstDate;
    private Date lastDate;
    
    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        sut = new ForumBL(forumDAO);
        sdf = new SimpleDateFormat("dd/MM/yyyy");
        firstDate = sdf.parse("01/09/2022");
        lastDate = sdf.parse("06/12/2022");
    }

    @Test
    @DisplayName("Should throw exception when ID is null")
    void testNullId() {
        Exception exception = assertThrows(Exception.class, () -> {
            sut.getBonus(null);
        });
        assertEquals("id null or not valid", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when ID is invalid")
    void testInvalidId() {
        Exception exception = assertThrows(Exception.class, () -> {
            sut.getBonus("12345678");
        });
        assertEquals("id null or not valid", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when user not found")
    void testUserNotFound() {
        when(forumDAO.getUserDAO("12345678Z")).thenReturn(null);
        
        Exception exception = assertThrows(Exception.class, () -> {
            sut.getBonus("12345678Z");
        });
        assertEquals("NAN not in Database", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when telephone is null")
    void testNullTelephone() {
        User user = new User("12345678Z", "Test User", null);
        when(forumDAO.getUserDAO("12345678Z")).thenReturn(user);
        
        Exception exception = assertThrows(Exception.class, () -> {
            sut.getBonus("12345678Z");
        });
        assertEquals("12345678Z not registered telephone", exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource({
        "0, 0",          // No purchases
        "15, 0",         // Below threshold
        "31, 5.37785",   // Just above 30
        "100, 17.35",    // Middle range
        "288, 49.968",   // Just below max
        "289, 50",       // Just above max
        "1000, 50"       // Well above max
    })
    @DisplayName("Should calculate correct bonus for different purchase amounts")
    void testBonusCalculation(float purchaseAmount, float expectedBonus) throws Exception {
        // Setup
        User user = new User("12345678Z", "Test User", "123456789");
        when(forumDAO.getUserDAO("12345678Z")).thenReturn(user);
        
        List<Purchase> purchases = new ArrayList<>();
        if (purchaseAmount > 0) {
            Purchase purchase = new Purchase(firstDate);
            Article article = new Article("1", "Test Article", purchaseAmount, false, 10);
            PurchasedArticle purchasedArticle = new PurchasedArticle(article, 1);
            purchase.addArticle(purchasedArticle);
            purchases.add(purchase);
        }
        
        when(forumDAO.getPurchasesDAO(eq(user), any(Date.class), any(Date.class)))
            .thenReturn(purchases.iterator());

        // Execute
        float result = sut.getBonus("12345678Z");

        // Verify
        assertEquals(expectedBonus, result, 0.001);
    }

    @Test
    @DisplayName("Should exclude outlet articles from bonus calculation")
    void testOutletArticlesExclusion() throws Exception {
        // Setup
        User user = new User("12345678Z", "Test User", "123456789");
        when(forumDAO.getUserDAO("12345678Z")).thenReturn(user);
        
        Purchase purchase = new Purchase(firstDate);
        Article regularArticle = new Article("1", "Regular", 100, false, 10);
        Article outletArticle = new Article("2", "Outlet", 100, true, 10);
        
        purchase.addArticle(new PurchasedArticle(regularArticle, 1));
        purchase.addArticle(new PurchasedArticle(outletArticle, 1));
        
        List<Purchase> purchases = new ArrayList<>();
        purchases.add(purchase);
        
        when(forumDAO.getPurchasesDAO(eq(user), any(Date.class), any(Date.class)))
            .thenReturn(purchases.iterator());

        // Execute
        float result = sut.getBonus("12345678Z");

        // Verify - should only count regular article (100€)
        assertEquals(17.35f, result, 0.001);
    }

    @Test
    @DisplayName("Should calculate bonus for multiple purchases in date range")
    void testMultiplePurchases() throws Exception {
        // Setup
        User user = new User("12345678Z", "Test User", "123456789");
        when(forumDAO.getUserDAO("12345678Z")).thenReturn(user);
        
        List<Purchase> purchases = new ArrayList<>();
        
        // First purchase
        Purchase purchase1 = new Purchase(firstDate);
        Article article1 = new Article("1", "Article 1", 50, false, 10);
        purchase1.addArticle(new PurchasedArticle(article1, 1));
        purchases.add(purchase1);
        
        // Second purchase
        Purchase purchase2 = new Purchase(lastDate);
        Article article2 = new Article("2", "Article 2", 50, false, 10);
        purchase2.addArticle(new PurchasedArticle(article2, 1));
        purchases.add(purchase2);
        
        when(forumDAO.getPurchasesDAO(eq(user), any(Date.class), any(Date.class)))
            .thenReturn(purchases.iterator());

        // Execute
        float result = sut.getBonus("12345678Z");

        // Verify - total purchases 100€
        assertEquals(17.35f, result, 0.001);
    }
}