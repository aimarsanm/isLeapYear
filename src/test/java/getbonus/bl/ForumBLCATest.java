package getbonus.bl;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import getbonus.db.ForumDAOInterface;
import getbonus.domain.Article;
import getbonus.domain.Purchase;
import getbonus.domain.PurchasedArticle;
import getbonus.domain.User;

class ForumBLCATest {

    @Mock
    private ForumDAOInterface dao;

    @InjectMocks
    private ForumBL forumBL;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        forumBL = new ForumBL(dao);
    }

    // --- Helper methods for test setup ---

    private static User createUser(String id, String name, String tel) {
        return new User(id, name, tel);
    }

    private static PurchasedArticle createPurchasedArticle(float price, int quantity, boolean isOutlet) {
        Article art = new Article("A1", "desc", price, isOutlet, 10);
        PurchasedArticle pa = new PurchasedArticle(art, quantity);
        pa.setPrice(price);
        pa.setOutlet(isOutlet);
        return pa;
    }

    private static Purchase createPurchase(Date date, List<PurchasedArticle> articles) {
        Purchase purchase = new Purchase(date);
        for (PurchasedArticle pa : articles) {
            purchase.addArticle(pa);
        }
        return purchase;
    }

    private static Iterator<Purchase> createPurchasesIterator(Purchase... purchases) {
        return Arrays.asList(purchases).iterator();
    }

    // --- White Box: Condition/Decision Coverage ---

    @Test
    @DisplayName("getBonus: Throws Exception when id is null")
    void testGetBonus_IdNull_ThrowsException() {
        try {
            forumBL.getBonus(null);
            fail("Exception expected for null id");
        } catch (Exception e) {
            assertEquals("id null or not valid", e.getMessage());
        }
    }

    @Test
    @DisplayName("getBonus: Throws Exception when id is invalid (ValidadorDNI returns false)")
    void testGetBonus_IdInvalid_ThrowsException() throws Exception {
        String invalidId = "12345678A";
        // Spy ValidadorDNI to force validar() to return false
        try (MockedConstruction<ValidadorDNI> mocked = Mockito.mockConstruction(ValidadorDNI.class,
                (mock, context) -> when(mock.validar()).thenReturn(false))) {
            forumBL.getBonus(invalidId);
            fail("Exception expected for invalid id");
        } catch (Exception e) {
            assertEquals("id null or not valid", e.getMessage());
        }
    }

    @Test
    @DisplayName("getBonus: Throws Exception when user not found in DB")
    void testGetBonus_UserNotFound_ThrowsException() throws Exception {
        String validId = "12345678Z";
        try (MockedConstruction<ValidadorDNI> mocked = Mockito.mockConstruction(ValidadorDNI.class,
                (mock, context) -> when(mock.validar()).thenReturn(true))) {
            when(dao.getUserDAO(validId)).thenReturn(null);
            forumBL.getBonus(validId);
            fail("Exception expected for user not found");
        } catch (Exception e) {
            assertEquals("NAN not in Database", e.getMessage());
        }
    }

    @Test
    @DisplayName("getBonus: Throws Exception when user has no telephone")
    void testGetBonus_UserNoTelephone_ThrowsException() throws Exception {
        String validId = "12345678Z";
        User user = createUser(validId, "Test", null);
        try (MockedConstruction<ValidadorDNI> mocked = Mockito.mockConstruction(ValidadorDNI.class,
                (mock, context) -> when(mock.validar()).thenReturn(true))) {
            when(dao.getUserDAO(validId)).thenReturn(user);
            forumBL.getBonus(validId);
            fail("Exception expected for user with no telephone");
        } catch (Exception e) {
            assertEquals(validId + " not registered telephone", e.getMessage());
        }
    }

    // --- Black Box: Equivalence Partitioning & Boundary Value Analysis ---

    static Stream<Arguments> bonusCalculationCases() throws Exception {
        // Dates for purchases
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date purchaseDate = sdf.parse("02/09/2022");

        // Partition 1: sumPurchases <= 30 (no bonus)
        List<PurchasedArticle> articles1 = List.of(createPurchasedArticle(10f, 3, false)); // 30
        Purchase purchase1 = createPurchase(purchaseDate, articles1);

        // Partition 2: 30 < sumPurchases <= 288 (bonus = sum * 0.1735)
        List<PurchasedArticle> articles2 = List.of(createPurchasedArticle(20f, 2, false)); // 40
        Purchase purchase2 = createPurchase(purchaseDate, articles2);

        // Partition 3: sumPurchases > 288 (bonus = 50)
        List<PurchasedArticle> articles3 = List.of(createPurchasedArticle(100f, 3, false)); // 300
        Purchase purchase3 = createPurchase(purchaseDate, articles3);

        // Partition 4: sumPurchases with outlet articles only (should be ignored)
        List<PurchasedArticle> articles4 = List.of(createPurchasedArticle(100f, 3, true)); // 0
        Purchase purchase4 = createPurchase(purchaseDate, articles4);

        // Partition 5: sumPurchases with mix of outlet and non-outlet
        List<PurchasedArticle> articles5 = List.of(
                createPurchasedArticle(10f, 1, true), // ignored
                createPurchasedArticle(15f, 2, false) // 30
        );
        Purchase purchase5 = createPurchase(purchaseDate, articles5);

        return Stream.of(
                // sumPurchases = 0 (no purchases)
                Arguments.of(List.of(), 0f),
                // sumPurchases = 0 (only outlet)
                Arguments.of(List.of(purchase4), 0f),
                // sumPurchases = 30 (boundary, no bonus)
                Arguments.of(List.of(purchase1), 0f),
                // sumPurchases = 40 (bonus = 40*0.1735)
                Arguments.of(List.of(purchase2), 6.94f),
                // sumPurchases = 300 (bonus = 50)
                Arguments.of(List.of(purchase3), 50f),
                // sumPurchases = 30 (mix, boundary, no bonus)
                Arguments.of(List.of(purchase5), 0f)
        );
    }

    @ParameterizedTest
    @MethodSource("bonusCalculationCases")
    @DisplayName("getBonus: Calculates correct bonus for all partitions and boundaries")
    void testGetBonus_BonusCalculation(List<Purchase> purchases, float expectedBonus) throws Exception {
        String validId = "12345678Z";
        User user = createUser(validId, "Test", "600000000");

        try (MockedConstruction<ValidadorDNI> mocked = Mockito.mockConstruction(ValidadorDNI.class,
                (mock, context) -> when(mock.validar()).thenReturn(true))) {
            when(dao.getUserDAO(validId)).thenReturn(user);

            // Mock purchases iterator
            Iterator<Purchase> purchaseIterator = purchases.iterator();
            when(dao.getPurchasesDAO(any(User.class), any(Date.class), any(Date.class)))
                    .thenReturn(purchaseIterator);

            float result = forumBL.getBonus(validId);
            // Use delta for float comparison
            assertEquals(expectedBonus, result, 0.01f);
        }
    }

    // --- White Box: sumPurchases > 30 and <= 288, sumPurchases > 288, sumPurchases <= 30 ---

    @ParameterizedTest
    @CsvSource({
            "31,5.38",   // 31*0.1735
            "288,49.97", // 288*0.1735
            "289,50.0"   // >288, should be 50
    })
    @DisplayName("getBonus: Boundary values for sumPurchases > 30 and > 288")
    void testGetBonus_BoundaryValues(float sumPurchases, float expectedBonus) throws Exception {
        String validId = "12345678Z";
        User user = createUser(validId, "Test", "600000000");

        // Create a purchase with a single non-outlet article to reach the sumPurchases
        float price = sumPurchases;
        int quantity = 1;
        List<PurchasedArticle> articles = List.of(createPurchasedArticle(price, quantity, false));
        Purchase purchase = createPurchase(new SimpleDateFormat("dd/MM/yyyy").parse("02/09/2022"), articles);

        try (MockedConstruction<ValidadorDNI> mocked = Mockito.mockConstruction(ValidadorDNI.class,
                (mock, context) -> when(mock.validar()).thenReturn(true))) {
            when(dao.getUserDAO(validId)).thenReturn(user);
            when(dao.getPurchasesDAO(any(User.class), any(Date.class), any(Date.class)))
                    .thenReturn(createPurchasesIterator(purchase));

            float result = forumBL.getBonus(validId);
            assertEquals(expectedBonus, result, 0.01f);
        }
    }

    // --- Clean up ---

    @AfterEach
    void tearDown() {
        Mockito.framework().clearInlineMocks();
    }
}