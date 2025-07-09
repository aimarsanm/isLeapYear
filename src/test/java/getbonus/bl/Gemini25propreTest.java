package getbonus.bl;

import getbonus.db.ForumDAOInterface;
import getbonus.domain.Article;
import getbonus.domain.Purchase;
import getbonus.domain.PurchasedArticle;
import getbonus.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class Gemini25propreTest {

    @Mock
    private ForumDAOInterface forumDAO;

    @InjectMocks
    private ForumBL sut;

    private User user;
    private static final String VALID_ID = "12345678Z";

    @BeforeEach
    void setUp() {
        user = new User(VALID_ID, "Test User", "600123123");
    }

    @Nested
    @DisplayName("White Box & Black Box: Exception Path Tests")
    class ExceptionTests {

        @Test
        @DisplayName("getBonus throws Exception when ID is null")
        void getBonus_withNullId_throwsException() {
            Exception exception = assertThrows(Exception.class, () -> sut.getBonus(null));
            assertEquals("id null or not valid", exception.getMessage());
        }

        @ParameterizedTest
        @ValueSource(strings = { "12345678", "12345678A", "invalid-id" })
        @DisplayName("getBonus throws Exception for invalid ID formats")
        void getBonus_withInvalidId_throwsException(String invalidId) {
            Exception exception = assertThrows(Exception.class, () -> sut.getBonus(invalidId));
            assertEquals("id null or not valid", exception.getMessage());
        }

        @Test
        @DisplayName("getBonus throws Exception when user is not found in DB")
        void getBonus_withNonExistentUser_throwsException() {
            when(forumDAO.getUserDAO(anyString())).thenReturn(null);

            Exception exception = assertThrows(Exception.class, () -> sut.getBonus(VALID_ID));
            assertEquals("NAN not in Database", exception.getMessage());
        }

        @Test
        @DisplayName("getBonus throws Exception when user has no registered telephone")
        void getBonus_withUserWithoutTelephone_throwsException() {
            user.setTelephone(null);
            when(forumDAO.getUserDAO(VALID_ID)).thenReturn(user);

            Exception exception = assertThrows(Exception.class, () -> sut.getBonus(VALID_ID));
            assertEquals(VALID_ID + " not registered telephone", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("White Box & Black Box: Happy Path & Boundary Tests")
    class BonusCalculationTests {

        private Date firstDate;
        private Date lastDate;

        @BeforeEach
        void setupDates() throws ParseException {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            firstDate = sdf.parse("01/09/2022");
            lastDate = sdf.parse("06/12/2022");
        }

        private Purchase createPurchaseWithArticles(List<PurchasedArticle> articles) {
            Purchase purchase = new Purchase(new Date());
            articles.forEach(purchase::addArticle);
            return purchase;
        }

        @Test
        @DisplayName("getBonus returns 0 when user has no purchases in the date range")
        void getBonus_withNoPurchases_returnsZero() throws Exception {
            when(forumDAO.getUserDAO(VALID_ID)).thenReturn(user);
            when(forumDAO.getPurchasesDAO(user, firstDate, lastDate)).thenReturn(Collections.emptyIterator());

            float bonus = sut.getBonus(VALID_ID);

            assertEquals(0.0f, bonus);
        }

        @Test
        @DisplayName("getBonus returns 0 when purchase has no articles")
        void getBonus_withPurchaseButNoArticles_returnsZero() throws Exception {
            Purchase emptyPurchase = new Purchase(new Date());
            List<Purchase> purchases = List.of(emptyPurchase);

            when(forumDAO.getUserDAO(VALID_ID)).thenReturn(user);
            when(forumDAO.getPurchasesDAO(user, firstDate, lastDate)).thenReturn(purchases.iterator());

            float bonus = sut.getBonus(VALID_ID);

            assertEquals(0.0f, bonus);
        }

        @Test
        @DisplayName("getBonus returns 0 when all purchased articles are outlet products")
        void getBonus_withOnlyOutletArticles_returnsZero() throws Exception {
            Article outletArticle = new Article("A01", "Outlet Item", 50, true, 10);
            PurchasedArticle pa = new PurchasedArticle(outletArticle, 1);
            Purchase purchase = createPurchaseWithArticles(List.of(pa));

            when(forumDAO.getUserDAO(VALID_ID)).thenReturn(user);
            when(forumDAO.getPurchasesDAO(user, firstDate, lastDate)).thenReturn(List.of(purchase).iterator());

            float bonus = sut.getBonus(VALID_ID);

            assertEquals(0.0f, bonus);
        }

        @ParameterizedTest
        @CsvSource({
                "30.0, 0.0",    // Boundary: sum <= 30 -> 0 bonus
                "20.0, 0.0"     // Equivalence Partition: sum < 30 -> 0 bonus
        })
        @DisplayName("getBonus returns 0 when total sum is 30 or less")
        void getBonus_whenSumIs30OrLess_returnsZero(float price, float expectedBonus) throws Exception {
            Article article = new Article("A01", "Item", price, false, 10);
            PurchasedArticle pa = new PurchasedArticle(article, 1);
            Purchase purchase = createPurchaseWithArticles(List.of(pa));

            when(forumDAO.getUserDAO(VALID_ID)).thenReturn(user);
            when(forumDAO.getPurchasesDAO(any(User.class), any(Date.class), any(Date.class))).thenReturn(List.of(purchase).iterator());

            float bonus = sut.getBonus(VALID_ID);

            assertEquals(expectedBonus, bonus, 0.001f);
        }

        @ParameterizedTest
        @CsvSource({
                "30.01, 5.206735",  // Boundary: sum > 30
                "100.0, 17.35",     // Equivalence Partition: 30 < sum <= 288
                "288.0, 49.968"     // Boundary: sum <= 288
        })
        @DisplayName("getBonus calculates 17.35% when sum is between 30 and 288")
        void getBonus_whenSumBetween30And288_calculatesCorrectBonus(float price, float expectedBonus) throws Exception {
            Article article = new Article("A01", "Item", price, false, 10);
            PurchasedArticle pa = new PurchasedArticle(article, 1);
            Purchase purchase = createPurchaseWithArticles(List.of(pa));

            when(forumDAO.getUserDAO(VALID_ID)).thenReturn(user);
            when(forumDAO.getPurchasesDAO(any(User.class), any(Date.class), any(Date.class))).thenReturn(List.of(purchase).iterator());

            float bonus = sut.getBonus(VALID_ID);

            assertEquals(expectedBonus, bonus, 0.001f);
        }

        @ParameterizedTest
        @CsvSource({
                "288.01, 50.0", // Boundary: sum > 288
                "500.0, 50.0"   // Equivalence Partition: sum > 288
        })
        @DisplayName("getBonus returns 50 when total sum is greater than 288")
        void getBonus_whenSumGreaterThan288_returnsFifty(float price, float expectedBonus) throws Exception {
            Article article = new Article("A01", "Item", price, false, 10);
            PurchasedArticle pa = new PurchasedArticle(article, 1);
            Purchase purchase = createPurchaseWithArticles(List.of(pa));

            when(forumDAO.getUserDAO(VALID_ID)).thenReturn(user);
            when(forumDAO.getPurchasesDAO(any(User.class), any(Date.class), any(Date.class))).thenReturn(List.of(purchase).iterator());

            float bonus = sut.getBonus(VALID_ID);

            assertEquals(expectedBonus, bonus, 0.001f);
        }

        @Test
        @DisplayName("getBonus correctly sums multiple articles in multiple purchases")
        void getBonus_withMultipleArticlesAndPurchases_calculatesCorrectBonus() throws Exception {
            // Purchase 1
            Article article1 = new Article("A01", "Item 1", 20, false, 10);
            Article article2 = new Article("A02", "Item 2", 25, false, 10);
            Article outletArticle = new Article("A03", "Outlet", 100, true, 5);
            PurchasedArticle pa1 = new PurchasedArticle(article1, 2); // 20*2 = 40
            PurchasedArticle pa2 = new PurchasedArticle(article2, 1); // 25*1 = 25
            PurchasedArticle paOutlet = new PurchasedArticle(outletArticle, 1); // Ignored
            Purchase purchase1 = createPurchaseWithArticles(List.of(pa1, pa2, paOutlet));

            // Purchase 2
            Article article3 = new Article("A04", "Item 3", 50, false, 10);
            PurchasedArticle pa3 = new PurchasedArticle(article3, 3); // 50*3 = 150
            Purchase purchase2 = createPurchaseWithArticles(List.of(pa3));

            // Total sum = 40 + 25 + 150 = 215
            // Expected bonus = 215 * 0.1735 = 37.3025
            List<Purchase> purchases = List.of(purchase1, purchase2);

            when(forumDAO.getUserDAO(VALID_ID)).thenReturn(user);
            when(forumDAO.getPurchasesDAO(any(User.class), any(Date.class), any(Date.class))).thenReturn(purchases.iterator());

            float bonus = sut.getBonus(VALID_ID);

            assertEquals(37.3025f, bonus, 0.001f);
        }
    }
}