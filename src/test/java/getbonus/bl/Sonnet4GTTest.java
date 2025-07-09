package getbonus.bl;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Arrays;
import getbonus.db.ForumDAOInterface;
import getbonus.domain.Article;
import getbonus.domain.Purchase;
import getbonus.domain.PurchasedArticle;
import getbonus.domain.User;
import getbonus.exceptions.NullParameterException;
import getbonus.exceptions.QuantityLessThan1Exception;
import getbonus.exceptions.UserNotFoundException;






public class Sonnet4GTTest {
    
    @Mock
    private ForumDAOInterface mockDao;
    
    private ForumBL forumBL;
    private User testUser;
    private Article testArticle;
    
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        forumBL = new ForumBL(mockDao);
        testUser = new User("12345678A", "Test User", "123456789");
        testArticle = new Article("ART001", "Test Article", 10, false, 5);
    }
    
    @Test
    public void testAddUserSuccess() throws Exception {
        when(mockDao.existUserDAO("12345678A")).thenReturn(false);
        when(mockDao.getUserDAO("12345678A")).thenReturn(testUser);
        
        User result = forumBL.addUser("12345678A", "Test User", "123456789");
        
        assertEquals(testUser, result);
        verify(mockDao).addUserDAO("12345678A", "Test User", "123456789");
    }
    /* 
    @Test
    public void testAddUserNullId() throws Exception {
        forumBL.addUser(null, "Test User", "123456789");
    }
    
    @Test
    public void testAddUserNullName() throws Exception {
        forumBL.addUser("12345678A", null, "123456789");
    }
    
    @Test
    public void testAddUserAlreadyExists() throws Exception {
        when(mockDao.existUserDAO("12345678A")).thenReturn(true);
        
        forumBL.addUser("12345678A", "Test User", "123456789");
    }
    */
    @Test
    public void testRemoveUser() {
        boolean result = forumBL.removeUser("12345678A");
        
        assertFalse(result);
        verify(mockDao).removeUserDAO("12345678A");
    }
    
    @Test
    public void testGetUser() {
        when(mockDao.getUserDAO("12345678A")).thenReturn(testUser);
        
        User result = forumBL.getUser("12345678A");
        
        assertEquals(testUser, result);
    }
    
    @Test
    public void testAddBasketSuccess() throws Exception {
        forumBL.addBasket(testUser, testArticle, 2);
        
        verify(mockDao).addBasketDAO(testUser, testArticle, 2);
    }
    /*
    @Test
    public void testAddBasketZeroQuantity() throws Exception {
        forumBL.addBasket(testUser, testArticle, 0);
    }
    
    @Test
    public void testAddBasketNegativeQuantity() throws Exception {
        forumBL.addBasket(testUser, testArticle, -1);
    }
    
    @Test
    public void testAddBasketNullUser() throws Exception {
        forumBL.addBasket(null, testArticle, 2);
    }
    
    @Test
    public void testAddBasketNullArticle() throws Exception {
        forumBL.addBasket(testUser, null, 2);
    }
     */
    @Test
    public void testBuy() throws Exception {
        Date testDate = new Date();
        
        forumBL.buy(testUser, testDate);
        
        verify(mockDao).buyDAO(testUser, testDate);
    }
    
    @Test
    public void testGetPurchases() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date firstDate = sdf.parse("01/01/2022");
        Date lastDate = sdf.parse("31/12/2022");
        
        forumBL.getPurchases(testUser, firstDate, lastDate);
        
        verify(mockDao).getPurchasesDAO(testUser, firstDate, lastDate);
    }
    
    @Test
    public void testAddStock() {
        Article result = forumBL.addStock("ART001", "Test Article", 10, false, 5);
        
        verify(mockDao).addStockDAO("ART001", "Test Article", 10, false, 5);
    }
    
    @Test
    public void testRemoveStock() {
        forumBL.removeStock("ART001");
        
        verify(mockDao).removeStockDAO("ART001");
    }
    /* 
    @Test
    public void testGetBonusNullId() throws Exception {
        forumBL.getBonus(null);
    }
    
    @Test
    public void testGetBonusUserNotFound() throws Exception {
        when(mockDao.getUserDAO("12345678A")).thenReturn(null);
        
        forumBL.getBonus("12345678A");
    }
    
    @Test
    public void testGetBonusNoTelephone() throws Exception {
        User userWithoutPhone = new User("12345678A", "Test User", null);
        when(mockDao.getUserDAO("12345678A")).thenReturn(userWithoutPhone);
        
        forumBL.getBonus("12345678A");
    } 
    */
}