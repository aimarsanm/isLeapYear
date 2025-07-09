package getbonus.bl;

import java.util.*;
import getbonus.domain.User;
import getbonus.domain.Article;
import getbonus.domain.Purchase;
import getbonus.domain.PurchasedArticle;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import getbonus.db.ForumDAOInterface;
import getbonus.exceptions.NullParameterException;
import getbonus.exceptions.QuantityLessThan1Exception;
import getbonus.exceptions.UserNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.DecimalFormat;
import java.text.ParseException;




// Tests for ForumBL
public class GPTo3miniGTTest {

    private ForumBL forumBL;
    private FakeForumDAO dao;

    @BeforeEach
    public void setUp() {
        dao = new FakeForumDAO();
        forumBL = new ForumBL(dao);
    }

    @Test
    public void testAddUserSuccess() throws Exception {
        String id = "12345678A";
        User user = forumBL.addUser(id, "Alice", "555-1234");
        assertNotNull(user);
        assertEquals(id, user.getId());
        // Check that DAO has the user too
        User daoUser = dao.getUserDAO(id);
        assertNotNull(daoUser);
        assertEquals("Alice", daoUser.getName());
    }
/* 
    @Test
    public void testAddUserNullParameter() throws Exception {
        forumBL.addUser(null, "Bob", "555-0000");
    }

    @Test
    public void testAddUserAlreadyExist() throws Exception {
        String id = "12345678A";
        forumBL.addUser(id, "Alice", "555-1234");
        // adding again should throw exception
        forumBL.addUser(id, "Alice", "555-1234");
    }
*/
    @Test
    public void testRemoveUser() throws Exception {
        String id = "12345678A";
        forumBL.addUser(id, "Alice", "555-1234");
        forumBL.removeUser(id);
        assertNull(dao.getUserDAO(id));
    }

    @Test
    public void testGetUser() throws Exception {
        String id = "12345678A";
        forumBL.addUser(id, "Alice", "555-1234");
        User user = forumBL.getUser(id);
        assertNotNull(user);
        assertEquals("Alice", user.getName());
    }

    @Test
    public void testAddBasketSuccess() throws Exception {
        String id = "12345678A";
        User user = forumBL.addUser(id, "Alice", "555-1234");
        Article article = new Article("ART1", "Test Article", 20, false, 10);
        // Add basket item with quantity 2
        forumBL.addBasket(user, article, 2);
        Iterator<Purchase> purchases = dao.getPurchasesDAO(user, new Date(), new Date());
        assertTrue(purchases.hasNext());
        Purchase p = purchases.next();
        Iterator<PurchasedArticle> paIt = p.getPurchaseIterator();
        assertTrue(paIt.hasNext());
        PurchasedArticle pa = paIt.next();
        assertEquals(20, pa.getPrice());
        assertEquals(2, pa.getQuantity());
    }
/* 
    @Test
    public void testAddBasketQuantityLessThan1() throws Exception {
        String id = "12345678A";
        User user = forumBL.addUser(id, "Alice", "555-1234");
        Article article = new Article("ART1", "Test Article", 20, false, 10);
        forumBL.addBasket(user, article, 0);
    }
*/
    @Test
    public void testBuy() throws Exception {
        String id = "12345678A";
        User user = forumBL.addUser(id, "Alice", "555-1234");
        Date buyDate = new Date();
        // For testing, simply call buy; FakeForumDAO does nothing
        forumBL.buy(user, buyDate);
    }
/* 
    @Test
    public void testGetBonus() throws Exception {
        // Create a valid user with telephone info
        String id = "12345678A"; // Assume this is a valid DNI
        User user = forumBL.addUser(id, "Alice", "555-1234");
        
        // Add a basket item that results in total purchases > 30 but less than 288
        Article article = new Article("ART1", "Test Article", 10, false, 10);
        forumBL.addBasket(user, article, 5);  // Total = 50
        
        // getBonus computes VAT as sum * 0.1735 when sum > 30 and <= 288
        float bonus = forumBL.getBonus(id);
        // Expected bonus = 50 * 0.1735 = 8.675 (allow a small delta for float arithmetic)
        assertEquals(8.675f, bonus, 0.01f);
    }
*/
    @Test
    public void testAddAndRemoveStock() {
        Article art = forumBL.addStock("ART1", "Stock Article", 30, false, 100);
        assertNotNull(art);
        assertEquals("ART1", art.getId());
        Article removed = forumBL.removeStock("ART1");
        assertNotNull(removed);
        assertEquals("ART1", removed.getId());
        // Further removal should return null
        assertNull(forumBL.removeStock("ART1"));
    }

    // ----- Fake implementations for testing -----

    // Fake ForumDAOInterface implementation
    private class FakeForumDAO implements ForumDAOInterface {
        private Map<String, User> users = new HashMap<>();
        private Map<String, Article> articles = new HashMap<>();
        private Map<User, List<Purchase>> purchases = new HashMap<>();

        @Override
        public boolean existUserDAO(String id) {
            return users.containsKey(id);
        }

        @Override
        public User addUserDAO(String id, String name, String tel) {
            User newUser = new User(id, name, tel);
            users.put(id, newUser);
            return newUser;
        }

        @Override
        public User getUserDAO(String id) {
            return users.get(id);
        }

        @Override
        public User removeUserDAO(String id) {
            return users.remove(id);
        }

        @Override
        public void addBasketDAO(User u, Article art, int quantity) {
            List<Purchase> list = purchases.getOrDefault(u, new ArrayList<>());
            Purchase purchase = new Purchase();
            purchase.addBasket(art, quantity);
            list.add(purchase);
            purchases.put(u, list);
        }

        @Override
        public void buyDAO(User u, Date d) {
            // For testing, no specific operation is required.
        }

        @Override
        public Iterator<Purchase> getPurchasesDAO(User u, Date firstDate, Date lastDate) {
            List<Purchase> list = purchases.getOrDefault(u, new ArrayList<>());
            return list.iterator();
        }

        @Override
        public Article addStockDAO(String id, String desc, int precio, boolean isOutlet, int stock) {
            Article art = new Article(id, desc, precio, isOutlet, stock);
            articles.put(id, art);
            return art;
        }

        @Override
        public Article removeStockDAO(String id) {
            return articles.remove(id);
        }



}
}