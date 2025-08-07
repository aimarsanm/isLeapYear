package getbonus.bl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import getbonus.db.ForumDAOInterface;
import getbonus.domain.Purchase;
import getbonus.domain.PurchasedArticle;
import getbonus.domain.User;






@ExtendWith(MockitoExtension.class)
class ForumBLAskTest {

    @Mock
    ForumDAOInterface dao;

    @InjectMocks
    ForumBL sut;

    @Mock
    ValidadorDNI validadorDNI;

    @BeforeEach
    void setup() {
        // SUT is injected with mocks
    }

    // Helper to create User
    private User createUser(String id, String name, String tel) {
        User user = mock(User.class);
        when(user.getId()).thenReturn(id);
        when(user.getName()).thenReturn(name);
        when(user.getTelephone()).thenReturn(tel);
        return user;
    }

    // Helper to create PurchasedArticle
    private PurchasedArticle createPurchasedArticle(float price, int quantity, boolean isOutlet) {
        PurchasedArticle pa = mock(PurchasedArticle.class);
        when(pa.getPrice()).thenReturn(price);
        when(pa.getQuantity()).thenReturn(quantity);
        when(pa.isOutlet()).thenReturn(isOutlet);
        return pa;
    }

    // Helper to create Purchase
    private Purchase createPurchase(List<PurchasedArticle> articles) {
        Purchase purchase = mock(Purchase.class);
        Iterator<PurchasedArticle> iterator = articles.iterator();
        when(purchase.getPurchaseIterator()).thenReturn(iterator);
        return purchase;
    }

    // Helper to create purchases iterator
    private Iterator<Purchase> createPurchasesIterator(List<Purchase> purchases) {
        return purchases.iterator();
    }

    // --- White Box: Condition/Decision Coverage ---

    @Test
    @DisplayName("getBonus throws Exception when id is null")
    void testGetBonus_IdNull_ThrowsException() {
        assertThrows(Exception.class, () -> sut.getBonus(null));
    }
/* 
    @Test
    @DisplayName("getBonus throws Exception when id is not valid")
    void testGetBonus_IdNotValid_ThrowsException() throws Exception {
        String id = "invalid";
        ForumBL spySut = spy(new ForumBL(dao));
        doReturn(false).when(spySut).getValidadorDNI(id);
        assertThrows(Exception.class, () -> {
            spySut.getBonus(id);
        });
    }

    @Test
    @DisplayName("getBonus throws Exception when user not found")
    void testGetBonus_UserNotFound_ThrowsException() throws Exception {
        String id = "75075708M";
        ForumBL spySut = spy(new ForumBL(dao));
        doReturn(true).when(spySut).getValidadorDNI(id);
        when(dao.getUserDAO(id)).thenReturn(null);
        assertThrows(Exception.class, () -> {
            spySut.getBonus(id);
        });
    }

    @Test
    @DisplayName("getBonus throws Exception when user telephone is null")
    void testGetBonus_UserTelephoneNull_ThrowsException() throws Exception {
        String id = "75075708M";
        ForumBL spySut = spy(new ForumBL(dao));
        doReturn(true).when(spySut).getValidadorDNI(id);
        User user = createUser(id, "John", null);
        when(dao.getUserDAO(id)).thenReturn(user);
        assertThrows(Exception.class, () -> {
            spySut.getBonus(id);
        });
    }

    @Test
    @DisplayName("getBonus returns 0 when purchases iterator is empty")
    void testGetBonus_NoPurchases_ReturnsZero() throws Exception {
        String id = "75075708M";
        ForumBL spySut = spy(new ForumBL(dao));
        doReturn(true).when(spySut).getValidadorDNI(id);
        User user = createUser(id, "John", "123456789");
        when(dao.getUserDAO(id)).thenReturn(user);
        when(dao.getPurchasesDAO(eq(user), any(Date.class), any(Date.class)))
                .thenReturn(Collections.emptyIterator());
        float result = spySut.getBonus(id);
        assertEquals(0, result, 0.0001);
    }

    @Test
    @DisplayName("getBonus returns 0 when all purchases are outlet articles")
    void testGetBonus_AllOutletArticles_ReturnsZero() throws Exception {
        String id = "75075708M";
        ForumBL spySut = spy(new ForumBL(dao));
        doReturn(true).when(spySut).getValidadorDNI(id);
        User user = createUser(id, "John", "123456789");
        when(dao.getUserDAO(id)).thenReturn(user);

        PurchasedArticle pa1 = createPurchasedArticle(100, 1, true);
        PurchasedArticle pa2 = createPurchasedArticle(200, 2, true);
        Purchase purchase = createPurchase(Arrays.asList(pa1, pa2));
        Iterator<Purchase> purchasesIterator = createPurchasesIterator(Arrays.asList(purchase));
        when(dao.getPurchasesDAO(eq(user), any(Date.class), any(Date.class)))
                .thenReturn(purchasesIterator);

        float result = spySut.getBonus(id);
        assertEquals(0, result, 0.0001);
    }

    // --- Black Box: Equivalence Partitioning & Boundary Value Analysis ---

    @ParameterizedTest
    @MethodSource("provideSumPurchasesCases")
    @DisplayName("getBonus returns correct bonus for boundary and equivalence cases")
    void testGetBonus_BonusCalculation(float[] prices, int[] quantities, boolean[] isOutlet, float expectedBonus) throws Exception {
        String id = "75075708M";
        ForumBL spySut = spy(new ForumBL(dao));
        doReturn(true).when(spySut).getValidadorDNI(id);
        User user = createUser(id, "John", "123456789");
        when(dao.getUserDAO(id)).thenReturn(user);

        List<PurchasedArticle> articles = new ArrayList<>();
        for (int i = 0; i < prices.length; i++) {
            articles.add(createPurchasedArticle(prices[i], quantities[i], isOutlet[i]));
        }
        Purchase purchase = createPurchase(articles);
        Iterator<Purchase> purchasesIterator = createPurchasesIterator(Arrays.asList(purchase));
        when(dao.getPurchasesDAO(eq(user), any(Date.class), any(Date.class)))
                .thenReturn(purchasesIterator);

        float result = spySut.getBonus(id);
        assertEquals(expectedBonus, result, 0.0001);
    }

    static Stream<Arguments> provideSumPurchasesCases() {
        // sumPurchases = 0 (all outlet)
        // sumPurchases = 30 (boundary)
        // sumPurchases = 30.01 (just above boundary)
        // sumPurchases = 288 (boundary)
        // sumPurchases = 288.01 (just above boundary)
        return Stream.of(
                Arguments.of(new float[]{10, 20}, new int[]{1, 1}, new boolean[]{true, true}, 0f), // all outlet
                Arguments.of(new float[]{15, 15}, new int[]{1, 1}, new boolean[]{false, false}, 0f), // sum = 30
                Arguments.of(new float[]{15.01, 15}, new int[]{1, 1}, new boolean[]{false, false}, (float)((15.01+15)*1*0.1735)), // sum > 30, < 288
                Arguments.of(new float[]{144, 144}, new int[]{1, 1}, new boolean[]{false, false}, (float)((144+144)*1*0.1735)), // sum = 288
                Arguments.of(new float[]{200, 100}, new int[]{1, 1}, new boolean[]{false, false}, 50f) // sum > 288
        );
    }

    @Test
    @DisplayName("getBonus returns correct bonus for mixed outlet and non-outlet articles")
    void testGetBonus_MixedOutletNonOutletArticles() throws Exception {
        String id = "75075708M";
        ForumBL spySut = spy(new ForumBL(dao));
        doReturn(true).when(spySut).getValidadorDNI(id);
        User user = createUser(id, "John", "123456789");
        when(dao.getUserDAO(id)).thenReturn(user);

        // Only non-outlet articles count
        PurchasedArticle pa1 = createPurchasedArticle(10, 1, false); // 10
        PurchasedArticle pa2 = createPurchasedArticle(20, 1, true);  // outlet, ignored
        PurchasedArticle pa3 = createPurchasedArticle(25, 1, false); // 25
        Purchase purchase = createPurchase(Arrays.asList(pa1, pa2, pa3));
        Iterator<Purchase> purchasesIterator = createPurchasesIterator(Arrays.asList(purchase));
        when(dao.getPurchasesDAO(eq(user), any(Date.class), any(Date.class)))
                .thenReturn(purchasesIterator);

        float expectedSum = 10 + 25;
        float expectedBonus = 0f; // sum = 35, so bonus = 35*0.1735
        if (expectedSum > 30 && expectedSum <= 288) {
            expectedBonus = (float)(expectedSum * 0.1735);
        }
        float result = spySut.getBonus(id);
        assertEquals(expectedBonus, result, 0.0001);
    }
*/
    // --- Utility for ValidadorDNI mocking ---
    // Since ValidadorDNI is instantiated inside getBonus, we need to spy and override it
    // Add this method to ForumBL for testability:
    //protected ValidadorDNI getValidadorDNI(String id) { return new ValidadorDNI(id); }
    // Then spy and override in tests

    // --- End of tests ---
}