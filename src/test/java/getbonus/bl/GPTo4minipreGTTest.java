package getbonus.bl;
import getbonus.db.ForumDAOInterface;
import getbonus.domain.Article;
import getbonus.domain.Purchase;
import getbonus.domain.User;
import getbonus.exceptions.NullParameterException;
import getbonus.exceptions.QuantityLessThan1Exception;
import getbonus.exceptions.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import static org.junit.jupiter.api.Assertions.*;






public class GPTo4minipreGTTest {

    private ForumBL forumBL;
    private StubDAO stubDao;

    static class StubDAO implements ForumDAOInterface {
        // flags and captured parameters
        boolean existUser = false;
        String addedId, addedName, addedTel;
        String removedUserId;
        User userToReturn;
        User basketUser;
        Article basketArticle;
        int basketQuantity;
        User buyUser;
        Date buyDate;
        Iterator<Purchase> purchasesToReturn = Collections.emptyIterator();
        Article stockToReturn;
        String removedStockId;

        @Override
        public boolean existUserDAO(String id) {
            return existUser;
        }

        @Override
        public User addUserDAO(String id, String name, String tel) {
            this.addedId = id;
            this.addedName = name;
            this.addedTel = tel;
            return userToReturn;
        }

        @Override
        public User getUserDAO(String id) {
            return userToReturn;
        }

        @Override
        public User removeUserDAO(String id) {
            this.removedUserId = id;
            return userToReturn;
        }

        @Override
        public void addBasketDAO(User u, Article art, int quantity) {
            this.basketUser = u;
            this.basketArticle = art;
            this.basketQuantity = quantity;
        }

        @Override
        public void buyDAO(User u, Date d) {
            this.buyUser = u;
            this.buyDate = d;
        }

        @Override
        public Iterator<Purchase> getPurchasesDAO(User u, Date firstDate, Date lastDate) {
            return purchasesToReturn;
        }

        @Override
        public Article addStockDAO(String id, String desc, int precio, boolean isOutlet, int stock) {
            return stockToReturn;
        }

        @Override
        public Article removeStockDAO(String id) {
            this.removedStockId = id;
            return stockToReturn;
        }
    }

    @BeforeEach
    void setUp() {
        stubDao = new StubDAO();
        forumBL = new ForumBL(stubDao);
    }

    @Test
    void testAddUserSuccess() throws Exception {
        stubDao.existUser = false;
        User dummy = new User("u1", "Alice", "123");
        stubDao.userToReturn = dummy;

        User result = forumBL.addUser("u1", "Alice", "123");
        assertSame(dummy, result);
        assertEquals("u1", stubDao.addedId);
        assertEquals("Alice", stubDao.addedName);
        assertEquals("123", stubDao.addedTel);
    }

    @Test
    void testAddUserNullIdOrName() {
        assertThrows(NullParameterException.class, () -> forumBL.addUser(null, "name", "tel"));
        assertThrows(NullParameterException.class, () -> forumBL.addUser("id", null, "tel"));
    }

    @Test
    void testAddUserAlreadyExists() {
        stubDao.existUser = true;
        assertThrows(UserNotFoundException.class, () -> forumBL.addUser("u1", "Alice", "123"));
    }

    @Test
    void testRemoveUser() {
        boolean ret = forumBL.removeUser("uX");
        assertFalse(ret);
        assertEquals("uX", stubDao.removedUserId);
    }

    @Test
    void testGetUser() {
        User dummy = new User("u2", "Bob", "456");
        stubDao.userToReturn = dummy;
        User got = forumBL.getUser("u2");
        assertSame(dummy, got);
    }

    @Test
    void testAddBasketValid() throws Exception {
        User u = new User("u3", "Carol", "789");
        Article a = new Article("a1", "desc", 10, false, 5);
        forumBL.addBasket(u, a, 2);
        assertSame(u, stubDao.basketUser);
        assertSame(a, stubDao.basketArticle);
        assertEquals(2, stubDao.basketQuantity);
    }

    @Test
    void testAddBasketInvalidQuantityOrNull() {
        User u = new User("u3", "Carol", "789");
        Article a = new Article("a1", "desc", 10, false, 5);
        assertThrows(QuantityLessThan1Exception.class, () -> forumBL.addBasket(u, a, 0));
        assertThrows(NullParameterException.class, () -> forumBL.addBasket(null, a, 1));
        assertThrows(NullParameterException.class, () -> forumBL.addBasket(u, null, 1));
    }

    @Test
    void testBuy() {
        User u = new User("u4", "Dan", "000");
        Date now = new Date();
        forumBL.buy(u, now);
        assertSame(u, stubDao.buyUser);
        assertSame(now, stubDao.buyDate);
    }

    @Test
    void testGetPurchases() {
        Iterator<Purchase> it = Collections.emptyIterator();
        stubDao.purchasesToReturn = it;
        Iterator<Purchase> got = forumBL.getPurchases(new User("x", "Y", "Z"), new Date(), new Date());
        assertSame(it, got);
    }

    @Test
    void testAddAndRemoveStock() {
        Article art = new Article("i1", "d", 5, true, 10);
        stubDao.stockToReturn = art;
        Article added = forumBL.addStock("i1", "d", 5, true, 10);
        assertSame(art, added);
        Article removed = forumBL.removeStock("i1");
        assertSame(art, removed);
        assertEquals("i1", stubDao.removedStockId);
    }

    @Test
    void testGetBonusNullId() {
        assertThrows(Exception.class, () -> forumBL.getBonus(null));
    }
}