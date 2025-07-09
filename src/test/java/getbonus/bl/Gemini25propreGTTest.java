package getbonus.bl;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import getbonus.bl.ForumBL;
import getbonus.db.ForumDAOInterface;
import getbonus.domain.Article;
import getbonus.domain.Purchase;
import getbonus.domain.PurchasedArticle;
import getbonus.domain.User;
import getbonus.exceptions.NullParameterException;
import getbonus.exceptions.QuantityLessThan1Exception;
import getbonus.exceptions.UserNotFoundException;

public class Gemini25propreGTTest {

    @Mock
    private ForumDAOInterface dao;

    @InjectMocks
    private ForumBL forumBL;

    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        user = new User("12345678A", "Test User", "654321987");
    }

    @Test
    public void testAddUser() throws NullParameterException, UserNotFoundException {
        when(dao.existUserDAO("12345678A")).thenReturn(false);
        when(dao.getUserDAO("12345678A")).thenReturn(user);

        User result = forumBL.addUser("12345678A", "Test User", "654321987");

        assertNotNull(result);
        assertEquals("12345678A", result.getId());
        verify(dao, times(1)).addUserDAO("12345678A", "Test User", "654321987");
    }
/* 
    @Test
    public void testAddUserExisting() throws NullParameterException, UserNotFoundException {
        when(dao.existUserDAO("12345678A")).thenReturn(true);
        forumBL.addUser("12345678A", "Test User", "654321987");
    }

    @Test
    public void testAddUserNullId() throws NullParameterException, UserNotFoundException {
        forumBL.addUser(null, "Test User", "654321987");
    }
*/
    @Test
    public void testAddBasket() throws QuantityLessThan1Exception, NullParameterException {
        Article article = new Article("A1", "Test Article", 10, false, 100);
        forumBL.addBasket(user, article, 2);
        verify(dao, times(1)).addBasketDAO(user, article, 2);
    }
/* 
    @Test
    public void testAddBasketInvalidQuantity() throws QuantityLessThan1Exception, NullParameterException {
        Article article = new Article("A1", "Test Article", 10, false, 100);
        forumBL.addBasket(user, article, 0);
    }

    @Test
    public void testAddBasketNullUser() throws QuantityLessThan1Exception, NullParameterException {
        Article article = new Article("A1", "Test Article", 10, false, 100);
        forumBL.addBasket(null, article, 1);
    }
*/
    @Test
    public void testBuy() {
        Date date = new Date();
        forumBL.buy(user, date);
        verify(dao, times(1)).buyDAO(user, date);
    }
/* 
    @Test
    public void testGetBonusNullId() throws Exception {
        forumBL.getBonus(null);
    }

    @Test
    public void testGetBonusUserNotFound() throws Exception {
        // Assuming ValidadorDNI is not mockable and "11111111H" is a valid format
        when(dao.getUserDAO("11111111H")).thenReturn(null);
        forumBL.getBonus("11111111H");
    }

    @Test
    public void testGetBonusUserNoTelephone() throws Exception {
        User userNoTel = new User("11111111H", "No Tel User", null);
        when(dao.getUserDAO("11111111H")).thenReturn(userNoTel);
        forumBL.getBonus("11111111H");
    }
*/
    private Iterator<Purchase> createMockPurchases(float price, int quantity, boolean isOutlet) {
        List<Purchase> purchases = new ArrayList<>();
        Purchase purchase = new Purchase();
        // Create an article with sufficient stock
        Article art = new Article("Art1", "Desc1", (int)price, isOutlet, quantity);
        // Add to purchase using domain logic
        purchase.addBasket(art, quantity);
        purchases.add(purchase);
        return purchases.iterator();
    }
/* 
    @Test
    public void testGetBonusAmountLessThan30() throws Exception {
        when(dao.getUserDAO("12345678A")).thenReturn(user);
        Iterator<Purchase> mockPurchases = createMockPurchases(10, 2, false); // Total 20
        when(dao.getPurchasesDAO(eq(user), any(Date.class), any(Date.class))).thenReturn(mockPurchases);

        float bonus = forumBL.getBonus("12345678A");
        assertEquals(0.0f, bonus, 0.001);
    }

    @Test
    public void testGetBonusAmountBetween30And288() throws Exception {
        when(dao.getUserDAO("12345678A")).thenReturn(user);
        Iterator<Purchase> mockPurchases = createMockPurchases(50, 2, false); // Total 100
        when(dao.getPurchasesDAO(eq(user), any(Date.class), any(Date.class))).thenReturn(mockPurchases);

        float bonus = forumBL.getBonus("12345678A");
        assertEquals(100 * 0.1735f, bonus, 0.001);
    }

    @Test
    public void testGetBonusAmountGreaterThan288() throws Exception {
        when(dao.getUserDAO("12345678A")).thenReturn(user);
        Iterator<Purchase> mockPurchases = createMockPurchases(150, 2, false); // Total 300
        when(dao.getPurchasesDAO(eq(user), any(Date.class), any(Date.class))).thenReturn(mockPurchases);

        float bonus = forumBL.getBonus("12345678A");
        assertEquals(50.0f, bonus, 0.001);
    }

    @Test
    public void testGetBonusWithOutletArticles() throws Exception {
        when(dao.getUserDAO("12345678A")).thenReturn(user);
        // Create a purchase with both normal and outlet articles
        List<Purchase> purchases = new ArrayList<>();
        // Build purchase via domain logic
        Purchase purchase = new Purchase();
        Article art1 = new Article("Art1", "Normal", 100, false, 10);
        purchase.addBasket(art1, 1); // 100
        Article art2 = new Article("Art2", "Outlet", 200, true, 10);
        purchase.addBasket(art2, 1); // should be ignored in bonus calculation
        purchases.add(purchase);

        when(dao.getPurchasesDAO(eq(user), any(Date.class), any(Date.class))).thenReturn(purchases.iterator());

        float bonus = forumBL.getBonus("12345678A");
        assertEquals(100 * 0.1735f, bonus, 0.001);
    }

    @Test
    public void testGetBonusNoPurchases() throws Exception {
        when(dao.getUserDAO("12345678A")).thenReturn(user);
        when(dao.getPurchasesDAO(eq(user), any(Date.class), any(Date.class))).thenReturn(new ArrayList<Purchase>().iterator());

        float bonus = forumBL.getBonus("12345678A");
        assertEquals(0.0f, bonus, 0.001);
    }
        */
}