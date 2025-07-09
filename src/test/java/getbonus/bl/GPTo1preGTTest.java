package getbonus.bl;
import getbonus.db.ForumDAOInterface;
import getbonus.domain.Article;
import getbonus.domain.User;
import getbonus.exceptions.NullParameterException;
import getbonus.exceptions.QuantityLessThan1Exception;
import getbonus.exceptions.UserNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import getbonus.bl.ForumBL;

class GPTo1preGTTest {

    private ForumDAOInterface dao;
    private ForumBL forumBL;

    @BeforeEach
    void setUp() {
        dao = mock(ForumDAOInterface.class);
        forumBL = new ForumBL(dao);
    }

    @Test
    void testAddUserValid() throws Exception {
        when(dao.existUserDAO("123")).thenReturn(false);
        forumBL.addUser("123", "John Doe", "555-1234");
        verify(dao).addUserDAO("123", "John Doe", "555-1234");
    }

    @Test
    void testAddUserNullParameters() {
        assertThrows(NullParameterException.class, () -> forumBL.addUser(null, "Name", "Tel"));
    }

    @Test
    void testAddUserAlreadyExists() {
        when(dao.existUserDAO("123")).thenReturn(true);
        assertThrows(UserNotFoundException.class, () -> forumBL.addUser("123", "John Doe", "555-1234"));
    }

    @Test
    void testRemoveUserValid() {
        forumBL.removeUser("123");
        verify(dao).removeUserDAO("123");
    }

    @Test
    void testAddBasketValid() throws Exception {
        User user = new User("123", "Test", "123");
        Article article = new Article("A1", "Desc", 10, false, 5);
        forumBL.addBasket(user, article, 2);
        verify(dao).addBasketDAO(user, article, 2);
    }

    @Test
    void testAddBasketInvalidQuantity() {
        User user = new User("123", "Test", "123");
        Article article = new Article("A1", "Desc", 10, false, 5);
        assertThrows(QuantityLessThan1Exception.class, () -> forumBL.addBasket(user, article, 0));
    }

    @Test
    void testBuy() {
        User user = new User("123", "Test", "123");
        Date date = new Date();
        forumBL.buy(user, date);
        verify(dao).buyDAO(user, date);
    }
}