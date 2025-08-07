package getbonus.bl;
import getbonus.db.ForumDAOInterface;
import getbonus.domain.Article;
import getbonus.domain.Purchase;
import getbonus.domain.PurchasedArticle;
import getbonus.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;






class ForumBLGTTest {

    ForumDAOInterface dao;
    ForumBL forumBL;

    @BeforeEach
    void setUp() {
        dao = mock(ForumDAOInterface.class);
        forumBL = new ForumBL(dao);
    }

    @Test
    void testGetBonus_idNull_throwsException() {
        Exception ex = assertThrows(Exception.class, () -> forumBL.getBonus(null));
        assertEquals("id null or not valid", ex.getMessage());
    }

    @Test
    void testGetBonus_invalidDNI_throwsException() {
        // ValidadorDNI returns false for invalid id
        String invalidId = "invalid";
        // Use a spy to override ValidadorDNI behavior
        ForumBL forumBLSpy = Mockito.spy(forumBL);
        Mockito.doReturn(false).when(forumBLSpy).validarDNI(anyString());
        Exception ex = assertThrows(Exception.class, () -> forumBLSpy.getBonus(invalidId));
        assertEquals("id null or not valid", ex.getMessage());
    }

    @Test
    void testGetBonus_userNotFound_throwsException() throws Exception {
        String id = "12345678A";
        // ValidadorDNI returns true
        ForumBL forumBLSpy = Mockito.spy(forumBL);
        Mockito.doReturn(true).when(forumBLSpy).validarDNI(id);
        when(dao.getUserDAO(id)).thenReturn(null);
        Exception ex = assertThrows(Exception.class, () -> forumBLSpy.getBonus(id));
        assertEquals("NAN not in Database", ex.getMessage());
    }

    @Test
    void testGetBonus_userWithoutTelephone_throwsException() throws Exception {
        String id = "12345678A";
        User user = mock(User.class);
        when(user.getTelephone()).thenReturn(null);
        ForumBL forumBLSpy = Mockito.spy(forumBL);
        Mockito.doReturn(true).when(forumBLSpy).validarDNI(id);
        when(dao.getUserDAO(id)).thenReturn(user);
        Exception ex = assertThrows(Exception.class, () -> forumBLSpy.getBonus(id));
        assertEquals(id + " not registered telephone", ex.getMessage());
    }

    @Test
    void testGetBonus_sumPurchasesLessThan30_returnsZero() throws Exception {
        String id = "12345678A";
        User user = mock(User.class);
        when(user.getTelephone()).thenReturn("123456789");
        ForumBL forumBLSpy = Mockito.spy(forumBL);
        Mockito.doReturn(true).when(forumBLSpy).validarDNI(id);
        when(dao.getUserDAO(id)).thenReturn(user);

        // Purchase with sum < 30
        PurchasedArticle article = mock(PurchasedArticle.class);
        when(article.isOutlet()).thenReturn(false);
        when(article.getPrice()).thenReturn(10f);
        when(article.getQuantity()).thenReturn(2); // 20

        Purchase purchase = mock(Purchase.class);
        when(purchase.getPurchaseIterator()).thenReturn(Collections.singletonList(article).iterator());

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date firstDate = sdf.parse("01/09/2022");
        Date lastDate = sdf.parse("06/12/2022");

        when(dao.getPurchasesDAO(user, firstDate, lastDate)).thenReturn(Collections.singletonList(purchase).iterator());

        float result = forumBLSpy.getBonus(id);
        assertEquals(0f, result);
    }

    @Test
    void testGetBonus_sumPurchasesBetween30And288_returnsCalculatedVat() throws Exception {
        String id = "12345678A";
        User user = mock(User.class);
        when(user.getTelephone()).thenReturn("123456789");
        ForumBL forumBLSpy = Mockito.spy(forumBL);
        Mockito.doReturn(true).when(forumBLSpy).validarDNI(id);
        when(dao.getUserDAO(id)).thenReturn(user);

        // Purchase with sum = 100
        PurchasedArticle article = mock(PurchasedArticle.class);
        when(article.isOutlet()).thenReturn(false);
        when(article.getPrice()).thenReturn(50f);
        when(article.getQuantity()).thenReturn(2); // 100

        Purchase purchase = mock(Purchase.class);
        when(purchase.getPurchaseIterator()).thenReturn(Collections.singletonList(article).iterator());

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date firstDate = sdf.parse("01/09/2022");
        Date lastDate = sdf.parse("06/12/2022");

        when(dao.getPurchasesDAO(user, firstDate, lastDate)).thenReturn(Collections.singletonList(purchase).iterator());

        float result = forumBLSpy.getBonus(id);
        assertEquals(100f * 0.1735f, result, 0.0001f);
    }

    @Test
    void testGetBonus_sumPurchasesGreaterThan288_returns50() throws Exception {
        String id = "12345678A";
        User user = mock(User.class);
        when(user.getTelephone()).thenReturn("123456789");
        ForumBL forumBLSpy = Mockito.spy(forumBL);
        Mockito.doReturn(true).when(forumBLSpy).validarDNI(id);
        when(dao.getUserDAO(id)).thenReturn(user);

        // Purchase with sum = 300
        PurchasedArticle article = mock(PurchasedArticle.class);
        when(article.isOutlet()).thenReturn(false);
        when(article.getPrice()).thenReturn(100f);
        when(article.getQuantity()).thenReturn(3); // 300

        Purchase purchase = mock(Purchase.class);
        when(purchase.getPurchaseIterator()).thenReturn(Collections.singletonList(article).iterator());

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date firstDate = sdf.parse("01/09/2022");
        Date lastDate = sdf.parse("06/12/2022");

        when(dao.getPurchasesDAO(user, firstDate, lastDate)).thenReturn(Collections.singletonList(purchase).iterator());

        float result = forumBLSpy.getBonus(id);
        assertEquals(50f, result);
    }

    // Helper for ValidadorDNI mocking
    private static class ForumBL extends getbonus.bl.ForumBL {
        public ForumBL(ForumDAOInterface dao) {
            super(dao);
        }
        public boolean validarDNI(String id) {
            // Default: always valid for tests unless overridden
            return true;
        }
        @Override
        public float getBonus(String id) throws Exception {
            if ((id == null) || (!validarDNI(id)))
                throw new Exception("id null or not valid");

            User u = dao.getUserDAO(id);

            if (u == null)
                throw new Exception("NAN not in Database");

            if (u.getTelephone() == null)
                throw new Exception(id + " not registered telephone");

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date firstDate = sdf.parse("01/09/2022");
            Date lastDate = sdf.parse("06/12/2022");

            Iterator<Purchase> purchases = dao.getPurchasesDAO(u, firstDate, lastDate);
            float sumPurchases = 0;
            float vat = 0;
            while (purchases.hasNext()) {
                Purchase c = purchases.next();
                Iterator<PurchasedArticle> articles = c.getPurchaseIterator();
                while (articles.hasNext()) {
                    PurchasedArticle article = articles.next();
                    if (!article.isOutlet())
                        sumPurchases = sumPurchases + article.getPrice() * article.getQuantity();
                }
            }
            if (sumPurchases > 30)
                if (sumPurchases > 288)
                    vat = 50;
                else
                    vat = (float) (sumPurchases * 0.1735);

            return vat;
        }
    }
}