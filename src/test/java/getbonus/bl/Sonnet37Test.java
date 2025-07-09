package getbonus.bl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.mockito.Mockito.mock;

import getbonus.db.ForumDAOInterface;
import getbonus.domain.Article;
import getbonus.domain.Purchase;
import getbonus.domain.PurchasedArticle;
import getbonus.domain.User;

@DisplayName("ForumBL getBonus Method Tests")
class Sonnet37Test {
    
    private ForumDAOInterface mockDao;
    private ForumBL forumBL;
    private SimpleDateFormat sdf;
    
    @BeforeEach
    void setUp() {
        mockDao = mock(ForumDAOInterface.class);
        forumBL = new ForumBL(mockDao);
        sdf = new SimpleDateFormat("dd/MM/yyyy");
    }
    
    // Helper method to create a valid user
    private User createValidUser(String id, String name, String telephone) {
        return new User(id, name, telephone);
    }
    
    // Helper method to create a purchase with articles
    private Purchase createPurchase(Date date, List<PurchasedArticle> articles) {
        Purchase purchase = new Purchase(date);
        for (PurchasedArticle article : articles) {
            purchase.addArticle(article);
        }
        return purchase;
    }
    
    // Helper method to create a purchased article
    private PurchasedArticle createPurchasedArticle(String id, String description, float price, 
                                                   boolean isOutlet, int stock, int quantity) {
        Article article = new Article(id, description, price, isOutlet, stock);
        return new PurchasedArticle(article, quantity);
    }

    @Test
    @DisplayName("Test getBonus with null ID should throw Exception")
    void testGetBonusWithNullId() {
        try {
            forumBL.getBonus(null);
            fail("Expected Exception for null ID was not thrown");
        } catch (Exception e) {
            assertEquals("id null or not valid", e.getMessage());
        }
    }
    
    @ParameterizedTest
    @ValueSource(strings = {"12345678", "1234567Z", "123456789Z", "ABCDEFGHI", "12345678A"})
    @DisplayName("Test getBonus with invalid DNI format should throw Exception")
    void testGetBonusWithInvalidDniFormat(String invalidDni) {
        try {
            forumBL.getBonus(invalidDni);
            fail("Expected Exception for invalid DNI was not thrown");
        } catch (Exception e) {
            assertEquals("id null or not valid", e.getMessage());
        }
    }
    /*
    @Test
    @DisplayName("Test getBonus with valid DNI but user not in database should throw Exception")
    void testGetBonusWithUserNotInDatabase() {
        // Given
        String validDni = "12345678Z";
        when(mockDao.getUserDAO(validDni)).thenReturn(null);
        
        // Mock ValidadorDNI to return true for this test
        try (var mocked = mockStatic(ValidadorDNI.class)) {
            mocked.when(() -> new ValidadorDNI(validDni).validar()).thenReturn(true);
            
            // When & Then
            try {
                forumBL.getBonus(validDni);
                fail("Expected Exception for user not in database was not thrown");
            } catch (Exception e) {
                assertEquals("NAN not in Database", e.getMessage());
            }
        }
    }
    
    @Test
    @DisplayName("Test getBonus with valid user but null telephone should throw Exception")
    void testGetBonusWithNullTelephone() {
        // Given
        String validDni = "12345678Z";
        User user = createValidUser(validDni, "Test User", null);
        when(mockDao.getUserDAO(validDni)).thenReturn(user);
        
        // Mock ValidadorDNI to return true for this test
        try (var mocked = mockStatic(ValidadorDNI.class)) {
            mocked.when(() -> new ValidadorDNI(validDni).validar()).thenReturn(true);
            
            // When & Then
            try {
                forumBL.getBonus(validDni);
                fail("Expected Exception for null telephone was not thrown");
            } catch (Exception e) {
                assertEquals(validDni + " not registered telephone", e.getMessage());
            }
        }
    }
    
    @Test
    @DisplayName("Test getBonus with purchases sum less than or equal to 30 should return 0")
    void testGetBonusWithPurchasesSumLessThanOrEqualTo30() throws Exception {
        // Given
        String validDni = "12345678Z";
        User user = createValidUser(validDni, "Test User", "123456789");
        when(mockDao.getUserDAO(validDni)).thenReturn(user);
        
        // Create purchases with sum <= 30
        List<Purchase> purchases = new ArrayList<>();
        List<PurchasedArticle> articles = new ArrayList<>();
        articles.add(createPurchasedArticle("A1", "Article 1", 10.0f, false, 10, 2)); // 20€ non-outlet
        articles.add(createPurchasedArticle("A2", "Article 2", 5.0f, false, 10, 2)); // 10€ non-outlet
        
        Date purchaseDate = sdf.parse("15/09/2022");
        purchases.add(createPurchase(purchaseDate, articles));
        
        when(mockDao.getPurchasesDAO(eq(user), any(Date.class), any(Date.class)))
            .thenReturn(purchases.iterator());
        
        // Mock ValidadorDNI to return true for this test
        try (var mocked = mockStatic(ValidadorDNI.class)) {
            mocked.when(() -> new ValidadorDNI(validDni).validar()).thenReturn(true);
            
            // When
            float result = forumBL.getBonus(validDni);
            
            // Then
            assertEquals(0.0f, result, 0.001f);
            
            // Verify the date range used
            ArgumentCaptor<Date> firstDateCaptor = ArgumentCaptor.forClass(Date.class);
            ArgumentCaptor<Date> lastDateCaptor = ArgumentCaptor.forClass(Date.class);
            verify(mockDao).getPurchasesDAO(eq(user), firstDateCaptor.capture(), lastDateCaptor.capture());
            
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            assertEquals("01/09/2022", sdf.format(firstDateCaptor.getValue()));
            assertEquals("06/12/2022", sdf.format(lastDateCaptor.getValue()));
        }
    }
    
    @Test
    @DisplayName("Test getBonus with purchases sum between 30 and 288 should return 17.35% of sum")
    void testGetBonusWithPurchasesSumBetween30And288() throws Exception {
        // Given
        String validDni = "12345678Z";
        User user = createValidUser(validDni, "Test User", "123456789");
        when(mockDao.getUserDAO(validDni)).thenReturn(user);
        
        // Create purchases with sum between 30 and 288
        List<Purchase> purchases = new ArrayList<>();
        List<PurchasedArticle> articles = new ArrayList<>();
        articles.add(createPurchasedArticle("A1", "Article 1", 50.0f, false, 10, 2)); // 100€ non-outlet
        articles.add(createPurchasedArticle("A2", "Article 2", 20.0f, false, 10, 1)); // 20€ non-outlet
        articles.add(createPurchasedArticle("A3", "Article 3", 30.0f, true, 10, 2)); // 60€ outlet (should be ignored)
        
        Date purchaseDate = sdf.parse("15/09/2022");
        purchases.add(createPurchase(purchaseDate, articles));
        
        when(mockDao.getPurchasesDAO(eq(user), any(Date.class), any(Date.class)))
            .thenReturn(purchases.iterator());
        
        // Mock ValidadorDNI to return true for this test
        try (var mocked = mockStatic(ValidadorDNI.class)) {
            mocked.when(() -> new ValidadorDNI(validDni).validar()).thenReturn(true);
            
            // When
            float result = forumBL.getBonus(validDni);
            
            // Then
            float expectedBonus = 120.0f * 0.1735f; // 20.82
            assertEquals(expectedBonus, result, 0.001f);
        }
    }
    
    @Test
    @DisplayName("Test getBonus with purchases sum greater than 288 should return 50")
    void testGetBonusWithPurchasesSumGreaterThan288() throws Exception {
        // Given
        String validDni = "12345678Z";
        User user = createValidUser(validDni, "Test User", "123456789");
        when(mockDao.getUserDAO(validDni)).thenReturn(user);
        
        // Create purchases with sum > 288
        List<Purchase> purchases = new ArrayList<>();
        List<PurchasedArticle> articles = new ArrayList<>();
        articles.add(createPurchasedArticle("A1", "Article 1", 100.0f, false, 10, 3)); // 300€ non-outlet
        articles.add(createPurchasedArticle("A2", "Article 2", 50.0f, true, 10, 2)); // 100€ outlet (should be ignored)
        
        Date purchaseDate = sdf.parse("15/09/2022");
        purchases.add(createPurchase(purchaseDate, articles));
        
        when(mockDao.getPurchasesDAO(eq(user), any(Date.class), any(Date.class)))
            .thenReturn(purchases.iterator());
        
        // Mock ValidadorDNI to return true for this test
        try (var mocked = mockStatic(ValidadorDNI.class)) {
            mocked.when(() -> new ValidadorDNI(validDni).validar()).thenReturn(true);
            
            // When
            float result = forumBL.getBonus(validDni);
            
            // Then
            assertEquals(50.0f, result, 0.001f);
        }
    }
    
    @Test
    @DisplayName("Test getBonus ignores outlet articles")
    void testGetBonusIgnoresOutletArticles() throws Exception {
        // Given
        String validDni = "12345678Z";
        User user = createValidUser(validDni, "Test User", "123456789");
        when(mockDao.getUserDAO(validDni)).thenReturn(user);
        
        // Create purchases with only outlet articles
        List<Purchase> purchases = new ArrayList<>();
        List<PurchasedArticle> articles = new ArrayList<>();
        articles.add(createPurchasedArticle("A1", "Article 1", 100.0f, true, 10, 3)); // 300€ outlet (should be ignored)
        articles.add(createPurchasedArticle("A2", "Article 2", 50.0f, true, 10, 2)); // 100€ outlet (should be ignored)
        
        Date purchaseDate = sdf.parse("15/09/2022");
        purchases.add(createPurchase(purchaseDate, articles));
        
        when(mockDao.getPurchasesDAO(eq(user), any(Date.class), any(Date.class)))
            .thenReturn(purchases.iterator());
        
        // Mock ValidadorDNI to return true for this test
        try (var mocked = mockStatic(ValidadorDNI.class)) {
            mocked.when(() -> new ValidadorDNI(validDni).validar()).thenReturn(true);
            
            // When
            float result = forumBL.getBonus(validDni);
            
            // Then
            assertEquals(0.0f, result, 0.001f); // No bonus because all articles are outlet
        }
    }
    
    @Test
    @DisplayName("Test getBonus with multiple purchases in date range")
    void testGetBonusWithMultiplePurchasesInDateRange() throws Exception {
        // Given
        String validDni = "12345678Z";
        User user = createValidUser(validDni, "Test User", "123456789");
        when(mockDao.getUserDAO(validDni)).thenReturn(user);
        
        // Create multiple purchases in date range
        List<Purchase> purchases = new ArrayList<>();
        
        // First purchase
        List<PurchasedArticle> articles1 = new ArrayList<>();
        articles1.add(createPurchasedArticle("A1", "Article 1", 20.0f, false, 10, 1)); // 20€ non-outlet
        Date purchaseDate1 = sdf.parse("15/09/2022");
        purchases.add(createPurchase(purchaseDate1, articles1));
        
        // Second purchase
        List<PurchasedArticle> articles2 = new ArrayList<>();
        articles2.add(createPurchasedArticle("A2", "Article 2", 25.0f, false, 10, 2)); // 50€ non-outlet
        Date purchaseDate2 = sdf.parse("20/10/2022");
        purchases.add(createPurchase(purchaseDate2, articles2));
        
        when(mockDao.getPurchasesDAO(eq(user), any(Date.class), any(Date.class)))
            .thenReturn(purchases.iterator());
        
        // Mock ValidadorDNI to return true for this test
        try (var mocked = mockStatic(ValidadorDNI.class)) {
            mocked.when(() -> new ValidadorDNI(validDni).validar()).thenReturn(true);
            
            // When
            float result = forumBL.getBonus(validDni);
            
            // Then
            float expectedBonus = 70.0f * 0.1735f; // 12.145
            assertEquals(expectedBonus, result, 0.001f);
        }
    }
    
    @Test
    @DisplayName("Test getBonus with no purchases returns 0")
    void testGetBonusWithNoPurchases() throws Exception {
        // Given
        String validDni = "12345678Z";
        User user = createValidUser(validDni, "Test User", "123456789");
        when(mockDao.getUserDAO(validDni)).thenReturn(user);
        
        // Empty purchases list
        List<Purchase> purchases = new ArrayList<>();
        when(mockDao.getPurchasesDAO(eq(user), any(Date.class), any(Date.class)))
            .thenReturn(purchases.iterator());
        
        // Mock ValidadorDNI to return true for this test
        try (var mocked = mockStatic(ValidadorDNI.class)) {
            mocked.when(() -> new ValidadorDNI(validDni).validar()).thenReturn(true);
            
            // When
            float result = forumBL.getBonus(validDni);
            
            // Then
            assertEquals(0.0f, result, 0.001f);
        }
    }
    
    @ParameterizedTest
    @MethodSource("purchaseDataProvider")
    @DisplayName("Test getBonus with various purchase totals")
    void testGetBonusWithVariousPurchaseTotals(float purchaseTotal, float expectedBonus) throws Exception {
        // Given
        String validDni = "12345678Z";
        User user = createValidUser(validDni, "Test User", "123456789");
        when(mockDao.getUserDAO(validDni)).thenReturn(user);
        
        // Create purchases with specified total
        List<Purchase> purchases = new ArrayList<>();
        List<PurchasedArticle> articles = new ArrayList<>();
        articles.add(createPurchasedArticle("A1", "Article 1", purchaseTotal, false, 10, 1));
        
        Date purchaseDate = sdf.parse("15/09/2022");
        purchases.add(createPurchase(purchaseDate, articles));
        
        when(mockDao.getPurchasesDAO(eq(user), any(Date.class), any(Date.class)))
            .thenReturn(purchases.iterator());
        
        // Mock ValidadorDNI to return true for this test
        try (var mocked = mockStatic(ValidadorDNI.class)) {
            mocked.when(() -> new ValidadorDNI(validDni).validar()).thenReturn(true);
            
            // When
            float result = forumBL.getBonus(validDni);
            
            // Then
            assertEquals(expectedBonus, result, 0.001f);
        }
    }
    
    // Method source for parameterized test
    static Stream<Arguments> purchaseDataProvider() {
        return Stream.of(
            // purchaseTotal, expectedBonus
            Arguments.of(0.0f, 0.0f),            // Below boundary (≤ 30)
            Arguments.of(30.0f, 0.0f),           // Boundary (= 30)
            Arguments.of(30.01f, 5.21f),         // Just above boundary (> 30)
            Arguments.of(100.0f, 17.35f),        // Middle value between 30 and 288
            Arguments.of(287.99f, 49.97f),       // Just below upper boundary (< 288)
            Arguments.of(288.0f, 49.97f),        // Boundary (= 288)
            Arguments.of(288.01f, 50.0f),        // Just above boundary (> 288)
            Arguments.of(500.0f, 50.0f)          // Well above boundary (>> 288)
        );
    }
    
    @Test
    @DisplayName("Test getBonus with purchases having multiple articles")
    void testGetBonusWithPurchasesHavingMultipleArticles() throws Exception {
        // Given
        String validDni = "12345678Z";
        User user = createValidUser(validDni, "Test User", "123456789");
        when(mockDao.getUserDAO(validDni)).thenReturn(user);
        
        // Create purchases with multiple articles
        List<Purchase> purchases = new ArrayList<>();
        
        List<PurchasedArticle> articles = new ArrayList<>();
        articles.add(createPurchasedArticle("A1", "Article 1", 10.0f, false, 10, 2)); // 20€ non-outlet
        articles.add(createPurchasedArticle("A2", "Article 2", 15.0f, false, 10, 1)); // 15€ non-outlet
        articles.add(createPurchasedArticle("A3", "Article 3", 25.0f, true, 10, 2));  // 50€ outlet (should be ignored)
        articles.add(createPurchasedArticle("A4", "Article 4", 20.0f, false, 10, 3)); // 60€ non-outlet
        
        Date purchaseDate = sdf.parse("15/09/2022");
        purchases.add(createPurchase(purchaseDate, articles));
        
        when(mockDao.getPurchasesDAO(eq(user), any(Date.class), any(Date.class)))
            .thenReturn(purchases.iterator());
        
        // Mock ValidadorDNI to return true for this test
        try (var mocked = mockStatic(ValidadorDNI.class)) {
            mocked.when(() -> new ValidadorDNI(validDni).validar()).thenReturn(true);
            
            // When
            float result = forumBL.getBonus(validDni);
            
            // Then
            float expectedPurchaseSum = 20.0f + 15.0f + 60.0f; // 95€ (excluding outlet)
            float expectedBonus = expectedPurchaseSum * 0.1735f; // 16.48€
            assertEquals(expectedBonus, result, 0.001f);
        }
    }
    
    @Test
    @DisplayName("Test getBonus with boundary value 30")
    void testGetBonusWithBoundaryValue30() throws Exception {
        // Given
        String validDni = "12345678Z";
        User user = createValidUser(validDni, "Test User", "123456789");
        when(mockDao.getUserDAO(validDni)).thenReturn(user);
        
        // Create purchase with exactly 30€
        List<Purchase> purchases = new ArrayList<>();
        List<PurchasedArticle> articles = new ArrayList<>();
        articles.add(createPurchasedArticle("A1", "Article 1", 15.0f, false, 10, 2)); // 30€ non-outlet
        
        Date purchaseDate = sdf.parse("15/09/2022");
        purchases.add(createPurchase(purchaseDate, articles));
        
        when(mockDao.getPurchasesDAO(eq(user), any(Date.class), any(Date.class)))
            .thenReturn(purchases.iterator());
        
        // Mock ValidadorDNI to return true for this test
        try (var mocked = mockStatic(ValidadorDNI.class)) {
            mocked.when(() -> new ValidadorDNI(validDni).validar()).thenReturn(true);
            
            // When
            float result = forumBL.getBonus(validDni);
            
            // Then
            assertEquals(0.0f, result, 0.001f); // No bonus because sum equals 30
        }
    }
    
    @Test
    @DisplayName("Test getBonus with boundary value 288")
    void testGetBonusWithBoundaryValue288() throws Exception {
        // Given
        String validDni = "12345678Z";
        User user = createValidUser(validDni, "Test User", "123456789");
        when(mockDao.getUserDAO(validDni)).thenReturn(user);
        
        // Create purchase with exactly 288€
        List<Purchase> purchases = new ArrayList<>();
        List<PurchasedArticle> articles = new ArrayList<>();
        articles.add(createPurchasedArticle("A1", "Article 1", 144.0f, false, 10, 2)); // 288€ non-outlet
        
        Date purchaseDate = sdf.parse("15/09/2022");
        purchases.add(createPurchase(purchaseDate, articles));
        
        when(mockDao.getPurchasesDAO(eq(user), any(Date.class), any(Date.class)))
            .thenReturn(purchases.iterator());
        
        // Mock ValidadorDNI to return true for this test
        try (var mocked = mockStatic(ValidadorDNI.class)) {
            mocked.when(() -> new ValidadorDNI(validDni).validar()).thenReturn(true);
            
            // When
            float result = forumBL.getBonus(validDni);
            
            // Then
            float expectedBonus = 288.0f * 0.1735f; // 49.97€
            assertEquals(expectedBonus, result, 0.001f);
        }
    } */
}