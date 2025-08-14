package getbonus.bl;

import getbonus.db.ForumDAOInterface;
import getbonus.domain.Article;
import getbonus.domain.Purchase;
import getbonus.domain.PurchasedArticle;
import getbonus.domain.User;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ForumBL getBonus Method Tests")
class ForumBLGetBonusSonnet37Test {

    @Mock
    private ForumDAOInterface forumDAO;

    private ForumBL forumBL;
    private SimpleDateFormat dateFormat;
    private Date firstDate;
    private Date lastDate;

    @BeforeEach
    void setUp() throws Exception {
        forumBL = new ForumBL(forumDAO);
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        firstDate = dateFormat.parse("01/09/2022");
        lastDate = dateFormat.parse("06/12/2022");
    }

    // ----- WHITE BOX TESTING (Condition/Decision coverage) -----

    @Test
    @DisplayName("When ID is null, throw exception")
    void testGetBonus_NullId() {
        // Test
        Exception exception = assertThrows(Exception.class, () -> forumBL.getBonus(null));
        
        // Verify
        assertEquals("id null or not valid", exception.getMessage());
        verify(forumDAO, never()).getUserDAO(any());
    }
/* 
    @ParameterizedTest
    @ValueSource(strings = {"12345678Z", "1234567A", "invalid"})
    @DisplayName("When ID is invalid, throw exception")
    void testGetBonus_InvalidId(String invalidId) {
        // Test
        Exception exception = assertThrows(Exception.class, () -> forumBL.getBonus(invalidId));
        
        // Verify
        assertEquals("id null or not valid", exception.getMessage());
        verify(forumDAO, never()).getUserDAO(any());
    }

    @Test
    @DisplayName("When user does not exist in database, throw exception")
    void testGetBonus_UserNotInDatabase() {
        // Setup
        String validId = "12345678A";
        when(forumDAO.getUserDAO(validId)).thenReturn(null);
        
        // Test
        Exception exception = assertThrows(Exception.class, () -> forumBL.getBonus(validId));
        
        // Verify
        assertEquals("NAN not in Database", exception.getMessage());
        verify(forumDAO, times(1)).getUserDAO(validId);
    }

    @Test
    @DisplayName("When user has no telephone, throw exception")
    void testGetBonus_NoTelephone() {
        // Setup
        String validId = "12345678A";
        User user = new User(validId, "Test User", null);
        when(forumDAO.getUserDAO(validId)).thenReturn(user);
        
        // Test
        Exception exception = assertThrows(Exception.class, () -> forumBL.getBonus(validId));
        
        // Verify
        assertEquals(validId + " not registered telephone", exception.getMessage());
        verify(forumDAO, times(1)).getUserDAO(validId);
    }

    @Test
    @DisplayName("When purchase sum is 0, return 0 bonus")
    void testGetBonus_NoPurchases() throws Exception {
        // Setup
        String validId = "12345678A";
        User user = new User(validId, "Test User", "123456789");
        when(forumDAO.getUserDAO(validId)).thenReturn(user);
        
        List<Purchase> emptyPurchases = new ArrayList<>();
        when(forumDAO.getPurchasesDAO(eq(user), any(Date.class), any(Date.class)))
            .thenReturn(emptyPurchases.iterator());
        
        // Test
        float result = forumBL.getBonus(validId);
        
        // Verify
        assertEquals(0.0f, result, 0.001f);
        verify(forumDAO, times(1)).getUserDAO(validId);
        verify(forumDAO, times(1)).getPurchasesDAO(eq(user), any(Date.class), any(Date.class));
    }

    @Test
    @DisplayName("When purchase sum is less than or equal to 30, return 0 bonus")
    void testGetBonus_PurchasesBelow30() throws Exception {
        // Setup
        String validId = "12345678A";
        User user = new User(validId, "Test User", "123456789");
        when(forumDAO.getUserDAO(validId)).thenReturn(user);
        
        // Create purchases with total of 30
        List<Purchase> purchases = createPurchasesList(30.0f, false);
        when(forumDAO.getPurchasesDAO(eq(user), any(Date.class), any(Date.class)))
            .thenReturn(purchases.iterator());
        
        // Test
        float result = forumBL.getBonus(validId);
        
        // Verify
        assertEquals(0.0f, result, 0.001f);
        verify(forumDAO, times(1)).getUserDAO(validId);
        verify(forumDAO, times(1)).getPurchasesDAO(eq(user), any(Date.class), any(Date.class));
    }

    @ParameterizedTest
    @MethodSource("purchaseValuesBetween30And288")
    @DisplayName("When purchase sum is between 30 and 288, bonus is 17.35% of sum")
    void testGetBonus_PurchasesBetween30And288(float purchaseSum, float expectedBonus) throws Exception {
        // Setup
        String validId = "12345678A";
        User user = new User(validId, "Test User", "123456789");
        when(forumDAO.getUserDAO(validId)).thenReturn(user);
        
        // Create purchases
        List<Purchase> purchases = createPurchasesList(purchaseSum, false);
        when(forumDAO.getPurchasesDAO(eq(user), any(Date.class), any(Date.class)))
            .thenReturn(purchases.iterator());
        
        // Test
        float result = forumBL.getBonus(validId);
        
        // Verify
        assertEquals(expectedBonus, result, 0.001f);
        verify(forumDAO, times(1)).getUserDAO(validId);
        verify(forumDAO, times(1)).getPurchasesDAO(eq(user), any(Date.class), any(Date.class));
    }

    @Test
    @DisplayName("When purchase sum is exactly 288, bonus is 17.35% of sum")
    void testGetBonus_PurchasesExactly288() throws Exception {
        // Setup
        String validId = "12345678A";
        User user = new User(validId, "Test User", "123456789");
        when(forumDAO.getUserDAO(validId)).thenReturn(user);
        
        // Create purchases with total of 288
        List<Purchase> purchases = createPurchasesList(288.0f, false);
        when(forumDAO.getPurchasesDAO(eq(user), any(Date.class), any(Date.class)))
            .thenReturn(purchases.iterator());
        
        // Test
        float result = forumBL.getBonus(validId);
        
        // Verify
        assertEquals(288.0f * 0.1735f, result, 0.001f);
        verify(forumDAO, times(1)).getUserDAO(validId);
        verify(forumDAO, times(1)).getPurchasesDAO(eq(user), any(Date.class), any(Date.class));
    }

    @ParameterizedTest
    @MethodSource("purchaseValuesAbove288")
    @DisplayName("When purchase sum is greater than 288, bonus is fixed 50")
    void testGetBonus_PurchasesAbove288(float purchaseSum) throws Exception {
        // Setup
        String validId = "12345678A";
        User user = new User(validId, "Test User", "123456789");
        when(forumDAO.getUserDAO(validId)).thenReturn(user);
        
        // Create purchases
        List<Purchase> purchases = createPurchasesList(purchaseSum, false);
        when(forumDAO.getPurchasesDAO(eq(user), any(Date.class), any(Date.class)))
            .thenReturn(purchases.iterator());
        
        // Test
        float result = forumBL.getBonus(validId);
        
        // Verify
        assertEquals(50.0f, result, 0.001f);
        verify(forumDAO, times(1)).getUserDAO(validId);
        verify(forumDAO, times(1)).getPurchasesDAO(eq(user), any(Date.class), any(Date.class));
    }

    @Test
    @DisplayName("When purchases contain outlet articles, they should not count toward bonus")
    void testGetBonus_OutletArticlesNotCounted() throws Exception {
        // Setup
        String validId = "12345678A";
        User user = new User(validId, "Test User", "123456789");
        when(forumDAO.getUserDAO(validId)).thenReturn(user);
        
        // Create mixed purchases
        Purchase purchase = new Purchase(new Date());
        Article article1 = new Article("art1", "Article 1", 100, false, 0);
        Article article2 = new Article("art2", "Article 2", 200, true, 0);
        purchase.addArticle(new PurchasedArticle(article1, 1));
        purchase.addArticle(new PurchasedArticle(article2, 1));

        List<Purchase> purchases = new ArrayList<>();
        purchases.add(purchase);
        
        when(forumDAO.getPurchasesDAO(eq(user), any(Date.class), any(Date.class)))
            .thenReturn(purchases.iterator());
        
        // Test
        float result = forumBL.getBonus(validId);
        
        // Verify - only the 100€ regular article should count
        assertEquals(100.0f * 0.1735f, result, 0.001f);
        verify(forumDAO, times(1)).getUserDAO(validId);
        verify(forumDAO, times(1)).getPurchasesDAO(eq(user), any(Date.class), any(Date.class));
    }

    // ----- BLACK BOX TESTING (Equivalence partitioning and boundary value analysis) -----

    @ParameterizedTest
    @ValueSource(strings = {"12345678A", "98765432Z", "00000000T"})
    @DisplayName("Valid DNIs should be accepted")
    void testGetBonus_ValidDNIFormats(String validId) throws Exception {
        // Setup
        User user = new User(validId, "Test User", "123456789");
        when(forumDAO.getUserDAO(validId)).thenReturn(user);
        
        List<Purchase> emptyPurchases = new ArrayList<>();
        when(forumDAO.getPurchasesDAO(eq(user), any(Date.class), any(Date.class)))
            .thenReturn(emptyPurchases.iterator());
        
        // Test
        float result = forumBL.getBonus(validId);
        
        // Verify
        assertEquals(0.0f, result, 0.001f); // Expecting 0 since no purchases
        verify(forumDAO, times(1)).getUserDAO(validId);
    }

    @Test
    @DisplayName("Date range should be correctly used in query")
    void testGetBonus_DateRangeUsed() throws Exception {
        // Setup
        String validId = "12345678A";
        User user = new User(validId, "Test User", "123456789");
        when(forumDAO.getUserDAO(validId)).thenReturn(user);
        
        List<Purchase> emptyPurchases = new ArrayList<>();
        when(forumDAO.getPurchasesDAO(any(), any(), any()))
            .thenReturn(emptyPurchases.iterator());
        
        // Test
        forumBL.getBonus(validId);
        
        // Capture the arguments
        ArgumentCaptor<Date> firstDateCaptor = ArgumentCaptor.forClass(Date.class);
        ArgumentCaptor<Date> lastDateCaptor = ArgumentCaptor.forClass(Date.class);
        
        // Verify date range is correct
        verify(forumDAO).getPurchasesDAO(eq(user), firstDateCaptor.capture(), lastDateCaptor.capture());
        
        // Format dates for comparison
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        assertEquals("01/09/2022", sdf.format(firstDateCaptor.getValue()));
        assertEquals("06/12/2022", sdf.format(lastDateCaptor.getValue()));
    }
 
    @Test
    @DisplayName("Multiple purchases should be accumulated correctly")
    void testGetBonus_MultiplePurchasesAccumulated() throws Exception {
        // Setup
        String validId = "12345678A";
        User user = new User(validId, "Test User", "123456789");
        when(forumDAO.getUserDAO(validId)).thenReturn(user);
        
        // Create multiple purchases
        List<Purchase> purchases = new ArrayList<>();
        
        Purchase purchase1 = new Purchase(new Date());
        Article article1 = new Article("art1", "Article 1", 20, false, 0);
        purchase1.addArticle(new PurchasedArticle(article1, 1));
        purchases.add(purchase1);
        
        Purchase purchase2 = new Purchase(new Date());
        Article article2 = new Article("art2", "Article 2", 30, false, 0);
        purchase2.addArticle(new PurchasedArticle(article2, 1));
        purchases.add(purchase2);
        
        when(forumDAO.getPurchasesDAO(eq(user), any(Date.class), any(Date.class)))
            .thenReturn(purchases.iterator());
        
        // Test
        float result = forumBL.getBonus(validId);
        
        // Verify - total should be 50€ with 17.35% bonus
        assertEquals(50.0f * 0.1735f, result, 0.001f);
        verify(forumDAO, times(1)).getUserDAO(validId);
        verify(forumDAO, times(1)).getPurchasesDAO(eq(user), any(Date.class), any(Date.class));
    }

    @Test
    @DisplayName("Quantity greater than 1 should be calculated correctly")
    void testGetBonus_QuantityMultiplied() throws Exception {
        // Setup
        String validId = "12345678A";
        User user = new User(validId, "Test User", "123456789");
        when(forumDAO.getUserDAO(validId)).thenReturn(user);
        
        Purchase purchase = new Purchase(new Date());
        Article article = new Article("art1", "Article 1", 10, false, 0);
        purchase.addArticle(new PurchasedArticle(article, 5)); // 10€ x 5 quantity = 50€
        
        List<Purchase> purchases = new ArrayList<>();
        purchases.add(purchase);
        
        when(forumDAO.getPurchasesDAO(eq(user), any(Date.class), any(Date.class)))
            .thenReturn(purchases.iterator());
        
        // Test
        float result = forumBL.getBonus(validId);
        
        // Verify - total should be 50€ with 17.35% bonus
        assertEquals(50.0f * 0.1735f, result, 0.001f);
        verify(forumDAO, times(1)).getUserDAO(validId);
        verify(forumDAO, times(1)).getPurchasesDAO(eq(user), any(Date.class), any(Date.class));
    }

    // ----- Helper methods for test data -----

    private static Stream<Arguments> purchaseValuesBetween30And288() {
        return Stream.of(
            Arguments.of(31.0f, 31.0f * 0.1735f),
            Arguments.of(100.0f, 100.0f * 0.1735f),
            Arguments.of(287.99f, 287.99f * 0.1735f)
        );
    }

    private static Stream<Arguments> purchaseValuesAbove288() {
        return Stream.of(
            Arguments.of(288.01f),
            Arguments.of(300.0f),
            Arguments.of(1000.0f)
        );
    }
 
    private List<Purchase> createPurchasesList(float totalAmount, boolean isOutlet) {
        Purchase purchase = new Purchase(new Date());
        Article article = new Article("art1", "Test Article", totalAmount, isOutlet, 0);
        purchase.addArticle(new PurchasedArticle(article, 1));
        
        List<Purchase> purchases = new ArrayList<>();
        purchases.add(purchase);
        return purchases;
    }*/
}