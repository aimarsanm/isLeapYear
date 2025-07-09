package getbonus.bl;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import getbonus.db.ForumDAOInterface;
import getbonus.domain.Article;
import getbonus.domain.Purchase;
import getbonus.domain.PurchasedArticle;
import getbonus.domain.User;
import getbonus.exceptions.NullParameterException;
import getbonus.exceptions.QuantityLessThan1Exception;
import getbonus.exceptions.UserNotFoundException;



class GPT4oGTTest {

    @Mock
    private ForumDAOInterface dao;

    private ForumBL forumBL;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        forumBL = new ForumBL(dao);
    }

    @Test
    void testAddUserSuccess() throws Exception {
        String id = "123";
        String name = "John Doe";
        String tel = "123456789";
        User user = new User(id, name, tel);

        when(dao.existUserDAO(id)).thenReturn(false);
        when(dao.getUserDAO(id)).thenReturn(user);

        User result = forumBL.addUser(id, name, tel);

        assertEquals(user, result);
        verify(dao).addUserDAO(id, name, tel);
    }

    @Test
    void testAddUserThrowsNullParameterException() {
        assertThrows(NullParameterException.class, () -> forumBL.addUser(null, "John Doe", "123456789"));
    }

    @Test
    void testAddUserThrowsUserNotFoundException() {
        String id = "123";
        when(dao.existUserDAO(id)).thenReturn(true);

        assertThrows(UserNotFoundException.class, () -> forumBL.addUser(id, "John Doe", "123456789"));
    }

    @Test
    void testRemoveUser() {
        String id = "123";

        boolean result = forumBL.removeUser(id);

        assertFalse(result);
        verify(dao).removeUserDAO(id);
    }

    @Test
    void testGetUser() {
        String id = "123";
        User user = new User(id, "John Doe", "123456789");

        when(dao.getUserDAO(id)).thenReturn(user);

        User result = forumBL.getUser(id);

        assertEquals(user, result);
    }

    @Test
    void testAddBasketSuccess() throws Exception {
        User user = new User("123", "John Doe", "123456789");
        Article article = new Article("456", "Sample Article", 100, false, 10);

        forumBL.addBasket(user, article, 2);

        verify(dao).addBasketDAO(user, article, 2);
    }

    @Test
    void testAddBasketThrowsQuantityLessThan1Exception() {
        User user = new User("123", "John Doe", "123456789");
        Article article = new Article("456", "Sample Article", 100, false, 10);

        assertThrows(QuantityLessThan1Exception.class, () -> forumBL.addBasket(user, article, 0));
    }

    @Test
    void testAddBasketThrowsNullParameterException() {
        assertThrows(NullParameterException.class, () -> forumBL.addBasket(null, null, 2));
    }

    @Test
    void testBuy() {
        User user = new User("123", "John Doe", "123456789");
        Date date = new Date();

        forumBL.buy(user, date);

        verify(dao).buyDAO(user, date);
    }
/* 
    @Test
    void testGetPurchases() {
        User user = new User("123", "John Doe", "123456789");
        Date firstDate = new Date();
        Date lastDate = new Date();
        Iterator<Purchase> purchases = mock(Iterator.class);

        when(dao.getPurchasesDAO(user, firstDate, lastDate)).thenReturn(purchases);

        Iterator<Purchase> result = forumBL.getPurchases(user, firstDate, lastDate);

        assertEquals(purchases, result);
    }
*/
    @Test
    void testAddStock() {
        Article article = new Article("456", "Sample Article", 100, false, 10);

        when(dao.addStockDAO("456", "Sample Article", 100, false, 10)).thenReturn(article);

        Article result = forumBL.addStock("456", "Sample Article", 100, false, 10);

        assertEquals(article, result);
    }

    @Test
    void testRemoveStock() {
        Article article = new Article("456", "Sample Article", 100, false, 10);

        when(dao.removeStockDAO("456")).thenReturn(article);

        Article result = forumBL.removeStock("456");

        assertEquals(article, result);
    }
/*
    @Test
    void testGetBonusSuccess() throws Exception {
        String id = "123";
        User user = new User(id, "John Doe", "123456789");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date firstDate = sdf.parse("01/09/2022");
        Date lastDate = sdf.parse("06/12/2022");
        Iterator<Purchase> purchases = mock(Iterator.class);

        when(dao.getUserDAO(id)).thenReturn(user);
        when(dao.getPurchasesDAO(user, firstDate, lastDate)).thenReturn(purchases);

        float result = forumBL.getBonus(id);

        assertTrue(result >= 0);
    }
*/
    @Test
    void testGetBonusThrowsExceptionForInvalidId() {
        assertThrows(Exception.class, () -> forumBL.getBonus(null));
    }
}