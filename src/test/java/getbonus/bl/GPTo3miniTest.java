package getbonus.bl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import getbonus.db.ForumDAOInterface;
import getbonus.domain.Purchase;
import getbonus.domain.PurchasedArticle;
import getbonus.domain.User;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GPTo3miniTest {

    private ForumDAOInterface dao;
    private ForumBL forumBL;
    private final String VALID_ID = "00000000T"; // Valid according to ValidadorDNI: "00000000" -> letter T
    private User validUser;
    private SimpleDateFormat sdf;

    // Helper class for parameterized tests to specify purchase article details
    static class ArticleSpec {
        double price;
        int quantity;
        boolean isOutlet;

        ArticleSpec(double price, int quantity, boolean isOutlet) {
            this.price = price;
            this.quantity = quantity;
            this.isOutlet = isOutlet;
        }
    }

    @BeforeAll
    void setupAll() {
        sdf = new SimpleDateFormat("dd/MM/yyyy");
    }

    @BeforeEach
    void setup() {
        dao = Mockito.mock(ForumDAOInterface.class);
        forumBL = new ForumBL(dao);
        // Setup a valid user with a registered telephone (non-null)
        validUser = new User(VALID_ID, VALID_ID, VALID_ID);
        validUser.setId(VALID_ID);
        validUser.setName("User");
        validUser.setTelephone("123456789");
    }

    @AfterEach
    void tearDown() {
        // Reset mocks after each test
        Mockito.reset(dao);
    }

    @AfterAll
    void tearDownAll() {
        // Cleanup if necessary
    }

    @Test
    @DisplayName("Test getBonus with null id - should throw exception")
    void testGetBonusNullId() {
        try {
            float result = forumBL.getBonus(null);
            fail("Expected an exception for null id");
        } catch (Exception e) {
            assertEquals("id null or not valid", e.getMessage());
        }
    }

    @Test
    @DisplayName("Test getBonus with invalid DNI format - should throw exception")
    void testGetBonusInvalidDNI() {
        // Provide an id with invalid length (not 9 characters)
        String invalidId = "1234567A";
        try {
            float result = forumBL.getBonus(invalidId);
            fail("Expected an exception for invalid DNI");
        } catch (Exception e) {
            assertEquals("id null or not valid", e.getMessage());
        }
    }

    @Test
    @DisplayName("Test getBonus when user is not in DB - should throw exception")
    void testGetBonusUserNotFound() {
        try {
            // Configure valid id but dao returns null for user
            when(dao.getUserDAO(VALID_ID)).thenReturn(null);
            float result = forumBL.getBonus(VALID_ID);
            fail("Expected an exception when user is not found");
        } catch (Exception e) {
            assertEquals("NAN not in Database", e.getMessage());
        }
    }

    @Test
    @DisplayName("Test getBonus when user has no registered telephone - should throw exception")
    void testGetBonusUserWithoutTelephone() {
        try {
            // Return a user with null telephone
            User userWithoutTel = new User(VALID_ID, VALID_ID, null);
            userWithoutTel.setId(VALID_ID);
            userWithoutTel.setTelephone(null);
            when(dao.getUserDAO(VALID_ID)).thenReturn(userWithoutTel);
            float result = forumBL.getBonus(VALID_ID);
            fail("Expected an exception when user has no registered telephone");
        } catch (Exception e) {
            assertEquals(VALID_ID + " not registered telephone", e.getMessage());
        }
    }

    // Parameterized test for verifying bonus calculation via purchases
    @ParameterizedTest(name = "{index}: {0}")
    @MethodSource("purchaseProvider")
    @DisplayName("Parameterized Test: getBonus calculation with various purchase data")
    void testGetBonusCalculation(String description, List<List<ArticleSpec>> purchaseSpecs, float expectedBonus) {
        try {
            // Configure dao.getUserDAO to return valid user
            when(dao.getUserDAO(VALID_ID)).thenReturn(validUser);
            // Set behavior for dao.getPurchasesDAO to return an iterator built from purchaseSpecs
            Date firstDate = sdf.parse("01/09/2022");
            Date lastDate = sdf.parse("06/12/2022");

            List<Purchase> purchaseList = new ArrayList<>();
            for (List<ArticleSpec> articleSpecs : purchaseSpecs) {
                Purchase purchaseMock = mock(Purchase.class);
                List<PurchasedArticle> purchasedArticles = new ArrayList<>();
                for (ArticleSpec spec : articleSpecs) {
                    PurchasedArticle paMock = mock(PurchasedArticle.class);
                    when(paMock.isOutlet()).thenReturn(spec.isOutlet);
                    when(paMock.getPrice()).thenReturn((float) spec.price);
                    when(paMock.getQuantity()).thenReturn(spec.quantity);
                    purchasedArticles.add(paMock);
                }
                Iterator<PurchasedArticle> paIterator = purchasedArticles.iterator();
                when(purchaseMock.getPurchaseIterator()).thenReturn(paIterator);
                purchaseList.add(purchaseMock);
            }
            Iterator<Purchase> purchaseIterator = purchaseList.iterator();
            when(dao.getPurchasesDAO(validUser, firstDate, lastDate)).thenReturn(purchaseIterator);

            // Call method under test
            float bonusResult = forumBL.getBonus(VALID_ID);

            assertEquals(expectedBonus, bonusResult, 0.001, "Bonus does not match expected value: " + expectedBonus);
        } catch (Exception e) {
            fail("No exception expected, but got: " + e.getMessage());
        }
    }

    // Method source providing test cases for getBonusCalculation
    static Stream<Arguments> purchaseProvider() throws Exception {
        // Helper: compute expected bonus from sum of non-outlet purchases following the business rules.
        // Business rules:
        //   if sumPurchases > 30 then bonus = (sumPurchases > 288 ? 50 : sumPurchases * 0.1735) else bonus = 0.
        // Using pre-calculated values for clarity.
        return Stream.of(
            // Case 1: No purchases (empty list)
            Arguments.of("No purchases, bonus should be 0", new ArrayList<List<ArticleSpec>>(), 0f)
            // Case 2: Single purchase, one non-outlet article summing exactly to 30 => 30 not > 30 so bonus 0.
           /*   Arguments.of("Single purchase sum equals 30, bonus should be 0", 
                Arrays.asList(Arrays.asList(new ArticleSpec(10, 3, false))), 0f),
            // Case 3: Single purchase, one non-outlet article, sum = 40 so bonus = 40 * 0.1735 = 6.94
            Arguments.of("Single purchase sum equals 40, bonus should be 6.94", 
                Arrays.asList(Arrays.asList(new ArticleSpec(20, 2, false))), (float)(40 * 0.1735)),
            // Case 4: Single purchase, one non-outlet article, sum = 300 so bonus = 50 (capped)
            Arguments.of("Single purchase sum equals 300, bonus should be 50", 
                Arrays.asList(Arrays.asList(new ArticleSpec(100, 3, false))), 50f),
            // Case 5: Multiple purchases with mix of outlet and non-outlet.
            // Purchase 1: non-outlet: 50*2 = 100; Purchase 2: outlet: ignored; Purchase 3: non-outlet: 20*5 = 100; total = 200
            // Bonus = 200 * 0.1735 = 34.7
            Arguments.of("Multiple purchases aggregate sum equals 200, bonus should be 34.7", 
                Arrays.asList(
                    Arrays.asList(new ArticleSpec(50, 2, false)),
                    Arrays.asList(new ArticleSpec(100, 1, true)),
                    Arrays.asList(new ArticleSpec(20, 5, false))
                ), (float)(200 * 0.1735))*/
        );
    }
}
