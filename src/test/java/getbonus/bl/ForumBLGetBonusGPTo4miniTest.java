package getbonus.bl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import getbonus.db.ForumDAOInterface;
import getbonus.domain.Article;
import getbonus.domain.Purchase;
import getbonus.domain.PurchasedArticle;
import getbonus.domain.User;

@ExtendWith(MockitoExtension.class)
@DisplayName("ForumBL.getBonus() – White/Black Box Integration Tests")
class ForumBLGetBonusGPTo4miniTest {

    private static final String VALID_ID = "00000000T";

    @Mock
    private ForumDAOInterface dao;

    private ForumBL sut;

    @BeforeEach
    void setUp() {
        sut = new ForumBL(dao);
    }

    @Test
    @DisplayName("Null ID ⇒ Exception “id null or not valid”")
    void testGetBonus_NullId_Throws() {
        Exception ex = assertThrows(Exception.class, () -> sut.getBonus(null));
        assertEquals("id null or not valid", ex.getMessage());
    }

    @Test
    @DisplayName("Malformed ID ⇒ Exception “id null or not valid”")
    void testGetBonus_InvalidId_Throws() {
        String bad = "123"; // too short ⇒ invalid by ValidadorDNI
        Exception ex = assertThrows(Exception.class, () -> sut.getBonus(bad));
        assertEquals("id null or not valid", ex.getMessage());
    }

    @Test
    @DisplayName("Valid ID but user missing ⇒ Exception “NAN not in Database”")
    void testGetBonus_UserMissing_Throws() {
        // valid DNI format but DAO returns null
        when(dao.getUserDAO(VALID_ID)).thenReturn(null);

        Exception ex = assertThrows(Exception.class, () -> sut.getBonus(VALID_ID));
        assertEquals("NAN not in Database", ex.getMessage());
    }

    @Test
    @DisplayName("Valid ID + user without tel ⇒ Exception “<id> not registered telephone”")
    void testGetBonus_NoTelephone_Throws() {
        User u = new User(VALID_ID, "Alice", null);
        when(dao.getUserDAO(VALID_ID)).thenReturn(u);

        Exception ex = assertThrows(Exception.class, () -> sut.getBonus(VALID_ID));
        assertEquals(VALID_ID + " not registered telephone", ex.getMessage());
    }

    @Test
    @DisplayName("Purchases all outlet ⇒ bonus = 0")
    void testGetBonus_OnlyOutlet_NoBonus() throws Exception {
        User u = new User(VALID_ID, "Bob", "555-1234");
        when(dao.getUserDAO(VALID_ID)).thenReturn(u);

        Article outlet = new Article("A1", "desc", 50f, true, 10);
        PurchasedArticle pa = new PurchasedArticle(outlet, 2);
        Purchase p = new Purchase();
        p.addArticle(pa);
        Iterator<Purchase> it = Collections.singletonList(p).iterator();
        when(dao.getPurchasesDAO(eq(u), any(Date.class), any(Date.class))).thenReturn(it);

        float bonus = sut.getBonus(VALID_ID);
        assertEquals(0f, bonus);
    }

    @ParameterizedTest(name = "price={0}, qty={1} ⇒ expected bonus={2}")
    @CsvSource({
        "10.0, 3, 0.0",     // sum = 30 ⇒ branch sum>30 = false
        "20.0, 5, 17.35",   // sum = 100 ⇒ 100*0.1735 = 17.35
        "100.0, 3, 50.0"    // sum = 300 ⇒ >288 ⇒ fixed 50
    })
    @DisplayName("Non-outlet sums ⇒ correct bonus")
    void testGetBonus_SumPartitions(float price, int qty, float expected) throws Exception {
        User u = new User(VALID_ID, "Carol", "555-0000");
        when(dao.getUserDAO(VALID_ID)).thenReturn(u);

        Article art = new Article("A2", "desc", price, false, qty);
        PurchasedArticle pa = new PurchasedArticle(art, qty);
        Purchase p = new Purchase();
        p.addArticle(pa);

        Iterator<Purchase> it = Collections.singletonList(p).iterator();
        when(dao.getPurchasesDAO(eq(u), any(Date.class), any(Date.class))).thenReturn(it);

        float bonus = sut.getBonus(VALID_ID);
        assertEquals(expected, bonus, 0.0001);
    }
}