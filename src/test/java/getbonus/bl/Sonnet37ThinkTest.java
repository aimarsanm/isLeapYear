package getbonus.bl;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import getbonus.db.ForumDAOInterface;
import getbonus.domain.User;

class Sonnet37ThinkTest {

    private ForumDAOInterface mockDao;
    private ForumBL forumBL;
    private Date firstDate;
    private Date lastDate;
    private User validUser;
    private static final String VALID_DNI = "12345678Z";
    
    @BeforeEach
    void setUp() throws Exception {
        // Initialize mock and system under test
        mockDao = mock(ForumDAOInterface.class);
        forumBL = new ForumBL(mockDao);
        
        // Setup dates
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        firstDate = sdf.parse("01/09/2022");
        lastDate = sdf.parse("06/12/2022");
        
        // Create a valid user
        validUser = new User(VALID_DNI, "Test User", "123456789");
    }
    
    @Test
    @DisplayName("Test getBonus with null ID")
    void testGetBonusWithNullId() {
        try {
            forumBL.getBonus(null);
            fail("Expected exception was not thrown");
        } catch (Exception e) {
            assertEquals("id null or not valid", e.getMessage());
        }
    }
    
    @ParameterizedTest
    @ValueSource(strings = {"invalid", "1234", "1234567X", "123456789Z"})
    @DisplayName("Test getBonus with invalid DNI format")
    void testGetBonusWithInvalidDNI(String invalidDNI) {
        try {
            forumBL.getBonus(invalidDNI);
            fail("Expected exception was not thrown");
        } catch (Exception e) {
            assertEquals("id null or not valid", e.getMessage());
        }
    }
    
    @Test
    @DisplayName("Test getBonus with valid DNI but user not in database")
    void testGetBonusWithUserNotInDatabase() {
        // Setup mock
        when(mockDao.getUserDAO(VALID_DNI)).thenReturn(null);
        
        try {
            forumBL.getBonus(VALID_DNI);
            fail("Expected exception was not thrown");
        } catch (Exception e) {
            assertEquals("NAN not in Database", e.getMessage());
        }
        
        // Verify the mock was called
        verify(mockDao, times(1)).getUserDAO(VALID_DNI);
    }
    
    @Test
    @DisplayName("Test getBonus with user having no telephone")
    void testGetBonusWithNoTelephone() {
        // Create user with null telephone
        User userNoPhone = new User(VALID_DNI, "Test User", null);
        
        // Setup mock
        when(mockDao.getUserDAO(VALID_DNI)).thenReturn(userNoPhone);
        
        try {
            forumBL.getBonus(VALID_DNI);
            fail("Expected exception was not thrown");
        } catch (Exception e) {
            assertEquals(VALID_DNI + " not registered telephone", e.getMessage());
        }
        
        // Verify the mock was called
        verify(mockDao, times(1)).getUserDAO(VALID_DNI);
    }
    /* 
    @Test
    @DisplayName("Test getBonus with no purchases")
    void testGetBonusWithNoPurchases() throws Exception {
        // Setup mock
        when(mockDao.getUserDAO(VALID_DNI)).thenReturn(validUser);
        
        // Create empty iterator for purchases
        @SuppressWarnings("unchecked")
        Iterator<Purchase> emptyIterator = mock(Iterator.class);
        when(emptyIterator.hasNext()).thenReturn(false);
        
        when(mockDao.getPurchasesDAO(eq(validUser), eq(firstDate), eq(lastDate)))
            .thenReturn(emptyIterator);
        
        // Execute method and verify results
        float result = forumBL.getBonus(VALID_DNI);
        
        // Assert
        assertEquals(0.0f, result, 0.001f, "Bonus should be 0 with no purchases");
        
        // Verify the mocks were called
        verify(mockDao, times(1)).getUserDAO(VALID_DNI);
        verify(mockDao, times(1)).getPurchasesDAO(eq(validUser), eq(firstDate), eq(lastDate));
        verify(emptyIterator, times(1)).hasNext();
    }
    
    @Test
    @DisplayName("Test getBonus with only outlet purchases")
    void testGetBonusWithOnlyOutletPurchases() throws Exception {
        // Setup mock
        when(mockDao.getUserDAO(VALID_DNI)).thenReturn(validUser);
        
        // Setup purchase with only outlet articles
        Purchase purchase = new Purchase();
        purchase.setPurchasedDate(new Date(firstDate.getTime() + 1000));
        
        Article outletArticle = new Article("A1", "Outlet Article", 100.0f, true, 10);
        PurchasedArticle purchasedOutletArticle = new PurchasedArticle(outletArticle, 2);
        purchase.addArticle(purchasedOutletArticle);
        
        // Create iterator for purchases
        @SuppressWarnings("unchecked")
        Iterator<Purchase> purchasesIterator = mock(Iterator.class);
        when(purchasesIterator.hasNext()).thenReturn(true, false);
        when(purchasesIterator.next()).thenReturn(purchase);
        
        when(mockDao.getPurchasesDAO(eq(validUser), eq(firstDate), eq(lastDate)))
            .thenReturn(purchasesIterator);
        
        // Execute method and verify results
        float result = forumBL.getBonus(VALID_DNI);
        
        // Assert
        assertEquals(0.0f, result, 0.001f, "Bonus should be 0 with only outlet purchases");
        
        // Verify the mocks were called
        verify(mockDao, times(1)).getUserDAO(VALID_DNI);
        verify(mockDao, times(1)).getPurchasesDAO(eq(validUser), eq(firstDate), eq(lastDate));
        verify(purchasesIterator, times(2)).hasNext();
        verify(purchasesIterator, times(1)).next();
    }
    
    @ParameterizedTest
    @MethodSource("purchaseSumProvider")
    @DisplayName("Test getBonus with different purchase amounts")
    void testGetBonusWithDifferentPurchaseAmounts(float purchaseAmount, int quantity, float expectedBonus) throws Exception {
        // Setup mock
        when(mockDao.getUserDAO(VALID_DNI)).thenReturn(validUser);
        
        // Setup purchase with non-outlet article
        Purchase purchase = new Purchase();
        purchase.setPurchasedDate(new Date(firstDate.getTime() + 1000));
        
        float unitPrice = purchaseAmount / quantity;
        Article regularArticle = new Article("A1", "Regular Article", unitPrice, false, 10);
        PurchasedArticle purchasedRegularArticle = new PurchasedArticle(regularArticle, quantity);
        purchase.addArticle(purchasedRegularArticle);
        
        // Create iterator for purchases
        @SuppressWarnings("unchecked")
        Iterator<Purchase> purchasesIterator = mock(Iterator.class);
        when(purchasesIterator.hasNext()).thenReturn(true, false);
        when(purchasesIterator.next()).thenReturn(purchase);
        
        when(mockDao.getPurchasesDAO(eq(validUser), eq(firstDate), eq(lastDate)))
            .thenReturn(purchasesIterator);
        
        // Execute method and verify results
        float result = forumBL.getBonus(VALID_DNI);
        
        // Assert
        assertEquals(expectedBonus, result, 0.001f, "Bonus calculation incorrect for purchase amount: " + purchaseAmount);
        
        // Verify the mocks were called
        verify(mockDao, times(1)).getUserDAO(VALID_DNI);
        verify(mockDao, times(1)).getPurchasesDAO(eq(validUser), eq(firstDate), eq(lastDate));
    }
    
    @Test
    @DisplayName("Test getBonus with mixed outlet and non-outlet purchases")
    void testGetBonusWithMixedPurchases() throws Exception {
        // Setup mock
        when(mockDao.getUserDAO(VALID_DNI)).thenReturn(validUser);
        
        // Setup purchase with both outlet and non-outlet articles
        Purchase purchase = new Purchase();
        purchase.setPurchasedDate(new Date(firstDate.getTime() + 1000));
        
        Article outletArticle = new Article("A1", "Outlet Article", 100.0f, true, 10);
        PurchasedArticle purchasedOutletArticle = new PurchasedArticle(outletArticle, 2);
        
        Article regularArticle = new Article("A2", "Regular Article", 100.0f, false, 10);
        PurchasedArticle purchasedRegularArticle = new PurchasedArticle(regularArticle, 2);
        
        purchase.addArticle(purchasedOutletArticle);
        purchase.addArticle(purchasedRegularArticle);
        
        // Create iterator for purchases
        @SuppressWarnings("unchecked")
        Iterator<Purchase> purchasesIterator = mock(Iterator.class);
        when(purchasesIterator.hasNext()).thenReturn(true, false);
        when(purchasesIterator.next()).thenReturn(purchase);
        
        when(mockDao.getPurchasesDAO(eq(validUser), eq(firstDate), eq(lastDate)))
            .thenReturn(purchasesIterator);
        
        // Execute method and verify results
        float result = forumBL.getBonus(VALID_DNI);
        
        // Expected: 200 (non-outlet purchases) * 0.1735 = 34.7
        float expected = 200.0f * 0.1735f;
        
        // Assert
        assertEquals(expected, result, 0.001f, "Bonus calculation incorrect for mixed purchases");
        
        // Verify the mocks were called
        verify(mockDao, times(1)).getUserDAO(VALID_DNI);
        verify(mockDao, times(1)).getPurchasesDAO(eq(validUser), eq(firstDate), eq(lastDate));
    }
    
    @Test
    @DisplayName("Test getBonus with multiple purchases")
    void testGetBonusWithMultiplePurchases() throws Exception {
        // Setup mock
        when(mockDao.getUserDAO(VALID_DNI)).thenReturn(validUser);
        
        // Setup first purchase
        Purchase purchase1 = new Purchase();
        purchase1.setPurchasedDate(new Date(firstDate.getTime() + 1000));
        
        Article article1 = new Article("A1", "Article 1", 50.0f, false, 10);
        PurchasedArticle purchasedArticle1 = new PurchasedArticle(article1, 2);
        purchase1.addArticle(purchasedArticle1);
        
        // Setup second purchase
        Purchase purchase2 = new Purchase();
        purchase2.setPurchasedDate(new Date(firstDate.getTime() + 2000));
        
        Article article2 = new Article("A2", "Article 2", 100.0f, false, 5);
        PurchasedArticle purchasedArticle2 = new PurchasedArticle(article2, 3);
        purchase2.addArticle(purchasedArticle2);
        
        // Create iterator for purchases
        @SuppressWarnings("unchecked")
        Iterator<Purchase> purchasesIterator = mock(Iterator.class);
        when(purchasesIterator.hasNext()).thenReturn(true, true, false);
        when(purchasesIterator.next()).thenReturn(purchase1, purchase2);
        
        when(mockDao.getPurchasesDAO(eq(validUser), eq(firstDate), eq(lastDate)))
            .thenReturn(purchasesIterator);
        
        // Execute method and verify results
        float result = forumBL.getBonus(VALID_DNI);
        
        // Expected purchases sum: (50*2) + (100*3) = 100 + 300 = 400 > 288, so bonus is 50
        float expected = 50.0f;
        
        // Assert
        assertEquals(expected, result, 0.001f, "Bonus calculation incorrect for multiple purchases");
        
        // Verify the mocks were called
        verify(mockDao, times(1)).getUserDAO(VALID_DNI);
        verify(mockDao, times(1)).getPurchasesDAO(eq(validUser), eq(firstDate), eq(lastDate));
        verify(purchasesIterator, times(3)).hasNext();
        verify(purchasesIterator, times(2)).next();
    }
    
    @Test
    @DisplayName("Test getBonus with purchase exactly at boundary (30)")
    void testGetBonusAtLowerBoundary() throws Exception {
        // Setup mock
        when(mockDao.getUserDAO(VALID_DNI)).thenReturn(validUser);
        
        // Setup purchase at boundary
        Purchase purchase = new Purchase();
        purchase.setPurchasedDate(new Date(firstDate.getTime() + 1000));
        
        Article article = new Article("A1", "Article", 30.0f, false, 10);
        PurchasedArticle purchasedArticle = new PurchasedArticle(article, 1);
        purchase.addArticle(purchasedArticle);
        
        // Create iterator for purchases
        @SuppressWarnings("unchecked")
        Iterator<Purchase> purchasesIterator = mock(Iterator.class);
        when(purchasesIterator.hasNext()).thenReturn(true, false);
        when(purchasesIterator.next()).thenReturn(purchase);
        
        when(mockDao.getPurchasesDAO(eq(validUser), eq(firstDate), eq(lastDate)))
            .thenReturn(purchasesIterator);
        
        // Execute method and verify results
        float result = forumBL.getBonus(VALID_DNI);
        
        // Expected: 30 is not > 30, so bonus is 0
        float expected = 0.0f;
        
        // Assert
        assertEquals(expected, result, 0.001f, "Bonus calculation incorrect at lower boundary");
    }
    
    @Test
    @DisplayName("Test getBonus with purchase just above lower boundary (30.01)")
    void testGetBonusJustAboveLowerBoundary() throws Exception {
        // Setup mock
        when(mockDao.getUserDAO(VALID_DNI)).thenReturn(validUser);
        
        // Setup purchase just above boundary
        Purchase purchase = new Purchase();
        purchase.setPurchasedDate(new Date(firstDate.getTime() + 1000));
        
        Article article = new Article("A1", "Article", 30.01f, false, 10);
        PurchasedArticle purchasedArticle = new PurchasedArticle(article, 1);
        purchase.addArticle(purchasedArticle);
        
        // Create iterator for purchases
        @SuppressWarnings("unchecked")
        Iterator<Purchase> purchasesIterator = mock(Iterator.class);
        when(purchasesIterator.hasNext()).thenReturn(true, false);
        when(purchasesIterator.next()).thenReturn(purchase);
        
        when(mockDao.getPurchasesDAO(eq(validUser), eq(firstDate), eq(lastDate)))
            .thenReturn(purchasesIterator);
        
        // Execute method and verify results
        float result = forumBL.getBonus(VALID_DNI);
        
        // Expected: 30.01 * 0.1735 = 5.20674
        float expected = 30.01f * 0.1735f;
        
        // Assert
        assertEquals(expected, result, 0.001f, "Bonus calculation incorrect just above lower boundary");
    }
    
    @Test
    @DisplayName("Test getBonus with purchase exactly at upper boundary (288)")
    void testGetBonusAtUpperBoundary() throws Exception {
        // Setup mock
        when(mockDao.getUserDAO(VALID_DNI)).thenReturn(validUser);
        
        // Setup purchase at boundary
        Purchase purchase = new Purchase();
        purchase.setPurchasedDate(new Date(firstDate.getTime() + 1000));
        
        Article article = new Article("A1", "Article", 288.0f, false, 10);
        PurchasedArticle purchasedArticle = new PurchasedArticle(article, 1);
        purchase.addArticle(purchasedArticle);
        
        // Create iterator for purchases
        @SuppressWarnings("unchecked")
        Iterator<Purchase> purchasesIterator = mock(Iterator.class);
        when(purchasesIterator.hasNext()).thenReturn(true, false);
        when(purchasesIterator.next()).thenReturn(purchase);
        
        when(mockDao.getPurchasesDAO(eq(validUser), eq(firstDate), eq(lastDate)))
            .thenReturn(purchasesIterator);
        
        // Execute method and verify results
        float result = forumBL.getBonus(VALID_DNI);
        
        // Expected: 288 * 0.1735 = 49.968
        float expected = 288.0f * 0.1735f;
        
        // Assert
        assertEquals(expected, result, 0.001f, "Bonus calculation incorrect at upper boundary");
    }
    
    @Test
    @DisplayName("Test getBonus with purchase just above upper boundary (288.01)")
    void testGetBonusJustAboveUpperBoundary() throws Exception {
        // Setup mock
        when(mockDao.getUserDAO(VALID_DNI)).thenReturn(validUser);
        
        // Setup purchase just above boundary
        Purchase purchase = new Purchase();
        purchase.setPurchasedDate(new Date(firstDate.getTime() + 1000));
        
        Article article = new Article("A1", "Article", 288.01f, false, 10);
        PurchasedArticle purchasedArticle = new PurchasedArticle(article, 1);
        purchase.addArticle(purchasedArticle);
        
        // Create iterator for purchases
        @SuppressWarnings("unchecked")
        Iterator<Purchase> purchasesIterator = mock(Iterator.class);
        when(purchasesIterator.hasNext()).thenReturn(true, false);
        when(purchasesIterator.next()).thenReturn(purchase);
        
        when(mockDao.getPurchasesDAO(eq(validUser), eq(firstDate), eq(lastDate)))
            .thenReturn(purchasesIterator);
        
        // Execute method and verify results
        float result = forumBL.getBonus(VALID_DNI);
        
        // Expected: purchase > 288, so bonus is fixed at 50
        float expected = 50.0f;
        
        // Assert
        assertEquals(expected, result, 0.001f, "Bonus calculation incorrect just above upper boundary");
    }
    
    @Test
    @DisplayName("Test getBonus date range is correctly passed to DAO")
    void testGetBonusDateRangeCorrect() throws Exception {
        // Setup mock
        when(mockDao.getUserDAO(VALID_DNI)).thenReturn(validUser);
        
        // Create empty iterator for purchases
        @SuppressWarnings("unchecked")
        Iterator<Purchase> emptyIterator = mock(Iterator.class);
        when(emptyIterator.hasNext()).thenReturn(false);
        
        // Setup capture for date parameters
        ArgumentCaptor<Date> firstDateCaptor = ArgumentCaptor.forClass(Date.class);
        ArgumentCaptor<Date> lastDateCaptor = ArgumentCaptor.forClass(Date.class);
        
        when(mockDao.getPurchasesDAO(eq(validUser), firstDateCaptor.capture(), lastDateCaptor.capture()))
            .thenReturn(emptyIterator);
        
        // Execute method
        forumBL.getBonus(VALID_DNI);
        
        // Verify dates
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        assertEquals("01/09/2022", sdf.format(firstDateCaptor.getValue()), "First date should be 01/09/2022");
        assertEquals("06/12/2022", sdf.format(lastDateCaptor.getValue()), "Last date should be 06/12/2022");
    }
    
    // Data provider for different purchase amounts
    static Stream<Arguments> purchaseSumProvider() {
        return Stream.of(
            // purchaseAmount, quantity, expectedBonus
            Arguments.of(0.0f, 1, 0.0f),               // Zero amount
            Arguments.of(15.0f, 1, 0.0f),              // Below threshold
            Arguments.of(30.0f, 1, 0.0f),              // At lower threshold
            Arguments.of(30.01f, 1, 30.01f * 0.1735f), // Just above lower threshold
            Arguments.of(100.0f, 1, 100.0f * 0.1735f), // Middle value
            Arguments.of(288.0f, 1, 288.0f * 0.1735f), // At upper threshold
            Arguments.of(288.01f, 1, 50.0f),           // Just above upper threshold
            Arguments.of(500.0f, 1, 50.0f),            // Well above upper threshold
            Arguments.of(200.0f, 2, 200.0f * 0.1735f)  // Testing with quantity > 1
        );
    }*/
}