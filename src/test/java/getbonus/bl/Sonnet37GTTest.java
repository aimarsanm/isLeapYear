package getbonus.bl;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import org.junit.jupiter.api.Test;

import getbonus.db.ForumDAOInterface;
import getbonus.domain.Article;
import getbonus.domain.Purchase;
import getbonus.domain.User;
import getbonus.exceptions.NullParameterException;
import getbonus.exceptions.QuantityLessThan1Exception;
import getbonus.exceptions.UserNotFoundException;






public class Sonnet37GTTest {

    private ForumBL forumBL;
    private ForumDAOInterface mockDAO;
    private User testUser;
    private Article testArticle;

    @BeforeEach
    public void setUp() {
        mockDAO = Mockito.mock(ForumDAOInterface.class);
        forumBL = new ForumBL(mockDAO);
        // Use a real valid DNI for tests: 12345678Z (12345678 % 23 == 14 -> Z)
        testUser = new User("12345678Z", "Test User", "123456789");
        testArticle = new Article("ART001", "Test Article", 100, false, 10);
    }

    @Test
    public void testAddUserSuccess() throws NullParameterException, UserNotFoundException {
        Mockito.when(mockDAO.existUserDAO("12345678A")).thenReturn(false);
        Mockito.when(mockDAO.getUserDAO("12345678A")).thenReturn(testUser);
        
        User result = forumBL.addUser("12345678A", "Test User", "123456789");
        
        assertEquals(testUser, result);
        Mockito.verify(mockDAO).addUserDAO("12345678A", "Test User", "123456789");
    }
/*
    @Test
    public void testAddUserNullId() throws NullParameterException, UserNotFoundException {
        forumBL.addUser(null, "Test User", "123456789");
    }

    @Test
    public void testAddUserNullName() throws NullParameterException, UserNotFoundException {
        forumBL.addUser("12345678A", null, "123456789");
    }

    @Test
    public void testAddUserAlreadyExists() throws NullParameterException, UserNotFoundException {
        Mockito.when(mockDAO.existUserDAO("12345678A")).thenReturn(true);
        
        forumBL.addUser("12345678A", "Test User", "123456789");
    }
 */
    @Test
    public void testRemoveUser() {
        boolean result = forumBL.removeUser("12345678A");
        
        assertFalse(result);
        Mockito.verify(mockDAO).removeUserDAO("12345678A");
    }

    @Test
    public void testGetUser() {
        Mockito.when(mockDAO.getUserDAO("12345678A")).thenReturn(testUser);
        
        User result = forumBL.getUser("12345678A");
        
        assertEquals(testUser, result);
    }

    @Test
    public void testAddBasketSuccess() throws QuantityLessThan1Exception, NullParameterException {
        forumBL.addBasket(testUser, testArticle, 5);
        
        Mockito.verify(mockDAO).addBasketDAO(testUser, testArticle, 5);
    }
/* 
    @Test
    public void testAddBasketZeroQuantity() throws QuantityLessThan1Exception, NullParameterException {
        forumBL.addBasket(testUser, testArticle, 0);
    }

    @Test
    public void testAddBasketNegativeQuantity() throws QuantityLessThan1Exception, NullParameterException {
        forumBL.addBasket(testUser, testArticle, -1);
    }

    @Test
    public void testAddBasketNullUser() throws QuantityLessThan1Exception, NullParameterException {
        forumBL.addBasket(null, testArticle, 5);
    }

    @Test
    public void testAddBasketNullArticle() throws QuantityLessThan1Exception, NullParameterException {
        forumBL.addBasket(testUser, null, 5);
    }
*/
    @Test
    public void testBuy() {
        Date testDate = new Date();
        forumBL.buy(testUser, testDate);
        
        Mockito.verify(mockDAO).buyDAO(testUser, testDate);
    }

    @Test
    public void testGetPurchases() {
        Date firstDate = new Date();
        Date lastDate = new Date();
        Iterator<Purchase> mockIterator = new ArrayList<Purchase>().iterator();
        
        Mockito.when(mockDAO.getPurchasesDAO(testUser, firstDate, lastDate)).thenReturn(mockIterator);
        
        Iterator<Purchase> result = forumBL.getPurchases(testUser, firstDate, lastDate);
        
        assertEquals(mockIterator, result);
    }

    @Test
    public void testAddStock() {
        Mockito.when(mockDAO.addStockDAO("ART001", "Test Article", 100, false, 10)).thenReturn(testArticle);
        
        Article result = forumBL.addStock("ART001", "Test Article", 100, false, 10);
        
        assertEquals(testArticle, result);
    }

    @Test
    public void testRemoveStock() {
        Mockito.when(mockDAO.removeStockDAO("ART001")).thenReturn(testArticle);
        
        Article result = forumBL.removeStock("ART001");
        
        assertEquals(testArticle, result);
    }

    @Test
    public void testGetBonusGreaterThan288() throws Exception {
        // Arrange DAO to return testUser for valid DNI
        Mockito.when(mockDAO.getUserDAO("12345678Z")).thenReturn(testUser);
        
        // Create mock purchases with non-outlet articles over 288 euros
        ArrayList<Purchase> purchases = new ArrayList<>();
        Purchase purchase = new Purchase();
        // testArticle price=100, quantity=3 -> sum=300
        purchase.addBasket(testArticle, 3);
        purchases.add(purchase);
        
        Mockito.when(mockDAO.getPurchasesDAO(Mockito.eq(testUser), Mockito.any(Date.class), Mockito.any(Date.class)))
               .thenReturn(purchases.iterator());
        
        float result = forumBL.getBonus("12345678Z");
        
        assertEquals(50.0f, result, 0.001);
    }

    @Test
    public void testGetBonusBetween30And288() throws Exception {
        // Arrange DAO to return testUser for valid DNI
        Mockito.when(mockDAO.getUserDAO("12345678Z")).thenReturn(testUser);
        
        // Create mock purchases with non-outlet articles between 30 and 288 euros
        ArrayList<Purchase> purchases = new ArrayList<>();
        Purchase purchase = new Purchase();
        // testArticle price=100, quantity=1 -> sum=100
        purchase.addBasket(testArticle, 1);
        purchases.add(purchase);
        
        Mockito.when(mockDAO.getPurchasesDAO(Mockito.eq(testUser), Mockito.any(Date.class), Mockito.any(Date.class)))
               .thenReturn(purchases.iterator());
        
        float result = forumBL.getBonus("12345678Z");
        
        assertEquals(100 * 0.1735f, result, 0.001);
    }

    @Test
    public void testGetBonusLessThan30() throws Exception {
        // Arrange DAO to return testUser for valid DNI
        Mockito.when(mockDAO.getUserDAO("12345678Z")).thenReturn(testUser);
        
        // Create mock purchases with non-outlet articles less than 30 euros
        ArrayList<Purchase> purchases = new ArrayList<>();
        Purchase purchase = new Purchase();
        // use a cheap article price=20, quantity=1 -> sum=20
        Article cheap = new Article("ART002", "Cheap Article", 20, false, 10);
        purchase.addBasket(cheap, 1);
        purchases.add(purchase);
        
        Mockito.when(mockDAO.getPurchasesDAO(Mockito.eq(testUser), Mockito.any(Date.class), Mockito.any(Date.class)))
               .thenReturn(purchases.iterator());
        
        float result = forumBL.getBonus("12345678Z");
        
        assertEquals(0.0f, result, 0.001);
    }
/*
    @Test
    public void testGetBonusInvalidDNI() throws Exception {
        forumBL.getBonus("invalid");
    }

    @Test
    public void testGetBonusNullDNI() throws Exception {
        forumBL.getBonus(null);
    }

    @Test
    public void testGetBonusUserNotFound() throws Exception {
        ValidadorDNI mockValidator = Mockito.mock(ValidadorDNI.class);
        Mockito.when(mockValidator.validar()).thenReturn(true);
        
        Mockito.when(mockDAO.getUserDAO("12345678Z")).thenReturn(null);
        
        forumBL.getBonus("12345678Z");
    }

    @Test
    public void testGetBonusNoTelephone() throws Exception {
        ValidadorDNI mockValidator = Mockito.mock(ValidadorDNI.class);
        Mockito.when(mockValidator.validar()).thenReturn(true);
        
        User userWithoutPhone = new User("12345678Z", "Test User", null);
        Mockito.when(mockDAO.getUserDAO("12345678Z")).thenReturn(userWithoutPhone);
        
        forumBL.getBonus("12345678Z");
    }
 */
    @Test
    public void testGetBonusIgnoreOutletArticles() throws Exception {
        // Arrange DAO to return testUser for valid DNI
        Mockito.when(mockDAO.getUserDAO("12345678Z")).thenReturn(testUser);
        
        // Create mock purchases with outlet and non-outlet articles
        ArrayList<Purchase> purchases = new ArrayList<>();
        Purchase purchase = new Purchase();
        // regular article contributes, outlet article ignored
        Article regular = new Article("ART001", "Regular Article", 100, false, 10);
        Article outlet = new Article("ART002", "Outlet Article", 200, true, 10);
        purchase.addBasket(regular, 1);
        purchase.addBasket(outlet, 1);
        purchases.add(purchase);
        
        Mockito.when(mockDAO.getPurchasesDAO(Mockito.eq(testUser), Mockito.any(Date.class), Mockito.any(Date.class)))
               .thenReturn(purchases.iterator());
        
        float result = forumBL.getBonus("12345678Z");
        
        // Only the regular article should be counted
        assertEquals(100 * 0.1735f, result, 0.001);
    }
}