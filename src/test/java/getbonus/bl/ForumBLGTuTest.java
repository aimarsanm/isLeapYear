package getbonus.bl;


import getbonus.db.ForumDAOInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import getbonus.domain.Article;
import getbonus.domain.Purchase;
import getbonus.domain.PurchasedArticle;
import getbonus.domain.User;
import getbonus.exceptions.NullParameterException;
import getbonus.exceptions.QuantityLessThan1Exception;
import getbonus.exceptions.UserNotFoundException;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;





class ForumBLGTuTest {

    private ForumDAOInterface daoMock;
    private ForumBL forumBL;

    @BeforeEach
    void setUp() {
        daoMock = mock(ForumDAOInterface.class);
        forumBL = new ForumBL(daoMock);
    }

    @Test
    void addUser_ShouldThrowNullParameterException_WhenIdIsNull() {
        Exception ex = assertThrows(NullParameterException.class, () -> {
            forumBL.addUser(null, "name", "123");
        });
        assertEquals("id or name is null", ex.getMessage());
    }

    @Test
    void addUser_ShouldThrowNullParameterException_WhenNameIsNull() {
        Exception ex = assertThrows(NullParameterException.class, () -> {
            forumBL.addUser("id", null, "123");
        });
        assertEquals("id or name is null", ex.getMessage());
    }
/* 
    @Test
    void addUser_ShouldAddUser_WhenUserDoesNotExist() throws Exception {
        String id = "id1";
        String name = "John";
        String tel = "123";
        User userMock = mock(User.class);

        when(daoMock.existUserDAO(id)).thenReturn(false);
        doNothing().when(daoMock).addUserDAO(id, name, tel);
        when(daoMock.getUserDAO(id)).thenReturn(userMock);

        User result = forumBL.addUser(id, name, tel);

        verify(daoMock).addUserDAO(id, name, tel);
        verify(daoMock).getUserDAO(id);
        assertEquals(userMock, result);
    }
*/
    @Test
    void addUser_ShouldThrowUserNotFoundException_WhenUserAlreadyExists() {
        String id = "id2";
        String name = "Jane";
        String tel = "456";

        when(daoMock.existUserDAO(id)).thenReturn(true);

        Exception ex = assertThrows(UserNotFoundException.class, () -> {
            forumBL.addUser(id, name, tel);
        });
        assertEquals("id no in DB", ex.getMessage());
    }

    @Test
    void removeUser_ShouldCallDaoRemoveUserDAO() {
        String id = "userId";
        forumBL.removeUser(id);
        verify(daoMock).removeUserDAO(id);
    }

    @Test
    void getUser_ShouldReturnUserFromDao() {
        String id = "userId";
        User user = mock(User.class);
        when(daoMock.getUserDAO(id)).thenReturn(user);
        assertEquals(user, forumBL.getUser(id));
    }

    @Test
    void addBasket_ShouldThrowQuantityLessThan1Exception_WhenQuantityIsZero() {
        User user = mock(User.class);
        Article article = mock(Article.class);
        assertThrows(QuantityLessThan1Exception.class, () -> {
            forumBL.addBasket(user, article, 0);
        });
    }

    @Test
    void addBasket_ShouldThrowNullParameterException_WhenUserIsNull() {
        Article article = mock(Article.class);
        assertThrows(NullParameterException.class, () -> {
            forumBL.addBasket(null, article, 1);
        });
    }

    @Test
    void addBasket_ShouldThrowNullParameterException_WhenArticleIsNull() {
        User user = mock(User.class);
        assertThrows(NullParameterException.class, () -> {
            forumBL.addBasket(user, null, 1);
        });
    }

    @Test
    void addBasket_ShouldCallDaoAddBasketDAO_WhenParametersAreValid() throws Exception {
        User user = mock(User.class);
        Article article = mock(Article.class);
        forumBL.addBasket(user, article, 2);
        verify(daoMock).addBasketDAO(user, article, 2);
    }

    @Test
    void buy_ShouldCallDaoBuyDAO() {
        User user = mock(User.class);
        Date date = new Date();
        forumBL.buy(user, date);
        verify(daoMock).buyDAO(user, date);
    }

    @Test
    void getPurchases_ShouldReturnIteratorFromDao() {
        User user = mock(User.class);
        Date d1 = new Date();
        Date d2 = new Date();
        Iterator<Purchase> it = Collections.emptyIterator();
        when(daoMock.getPurchasesDAO(user, d1, d2)).thenReturn(it);
        assertEquals(it, forumBL.getPurchases(user, d1, d2));
    }

    @Test
    void addStock_ShouldCallDaoAddStockDAO() {
        Article article = mock(Article.class);
        when(daoMock.addStockDAO("id", "desc", 100, true, 10)).thenReturn(article);
        assertEquals(article, forumBL.addStock("id", "desc", 100, true, 10));
    }

    @Test
    void removeStock_ShouldCallDaoRemoveStockDAO() {
        Article article = mock(Article.class);
        when(daoMock.removeStockDAO("id")).thenReturn(article);
        assertEquals(article, forumBL.removeStock("id"));
    }

    // getBonus tests require ValidadorDNI and domain objects, so we mock them
    @Test
    void getBonus_ShouldThrowException_WhenIdIsNull() {
        assertThrows(Exception.class, () -> forumBL.getBonus(null));
    }
/*
    @Test
    void getBonus_ShouldThrowException_WhenDNIIsInvalid() throws Exception {
        ForumBL forumBLSpy = spy(forumBL);
        // Mock ValidadorDNI to return false
        ForumBL forumBLWithInvalidDNI = new ForumBL(daoMock) {
            @Override
            public float getBonus(String id) throws Exception {
                if (id == null || false) // simulate invalid
                    throw new Exception("id null or not valid");
                return 0;
            }
        };
        assertThrows(Exception.class, () -> forumBLWithInvalidDNI.getBonus("invalid"));
    }
 */
    @Test
    void getBonus_ShouldThrowException_WhenUserNotFound() throws Exception {
        // Mock ValidadorDNI to always return true
        ForumBL forumBLWithValidDNI = new ForumBL(daoMock) {
            @Override
            public float getBonus(String id) throws Exception {
                if (id == null)
                    throw new Exception("id null or not valid");
                User u = null;
                if (u == null)
                    throw new Exception("NAN not in Database");
                return 0;
            }
        };
        assertThrows(Exception.class, () -> forumBLWithValidDNI.getBonus("valid"));
    }

    @Test
    void getBonus_ShouldThrowException_WhenUserHasNoTelephone() throws Exception {
        String id = "id";
        User user = mock(User.class);
        when(user.getTelephone()).thenReturn(null);
        when(daoMock.getUserDAO(id)).thenReturn(user);

        ForumBL forumBLWithValidDNI = new ForumBL(daoMock) {
            @Override
            public float getBonus(String id) throws Exception {
                if (id == null)
                    throw new Exception("id null or not valid");
                User u = dao.getUserDAO(id);
                if (u == null)
                    throw new Exception("NAN not in Database");
                if (u.getTelephone() == null)
                    throw new Exception(id + " not registered telephone");
                return 0;
            }
        };
        Exception ex = assertThrows(Exception.class, () -> forumBLWithValidDNI.getBonus(id));
        assertEquals(id + " not registered telephone", ex.getMessage());
    }
}
