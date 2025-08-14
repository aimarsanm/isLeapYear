package getbonus.bl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import getbonus.db.ForumDAOInterface;
import getbonus.domain.Article;
import getbonus.domain.Purchase;
import getbonus.domain.PurchasedArticle;
import getbonus.domain.User;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests for ForumBL getBonus method")
class ForumBLGetBonusGemini25proTest {

    @Mock
    private ForumDAOInterface forumDAO;

    @InjectMocks
    private ForumBL sut;

    private User user;
    private final String validId = "12345678A";

    @BeforeEach
    void setUp() {
        user = new User(validId, "Test User", "666555444");
    }

    @Nested
    @DisplayName("Exception Path Tests")
    class ExceptionTests {

        @Test
        @DisplayName("Test getBonus with null ID throws Exception")
        void testGetBonus_NullId_ThrowsException() {
            Exception exception = assertThrows(Exception.class, () -> sut.getBonus(null));
            assertEquals("id null or not valid", exception.getMessage());
        }
/* 
        @Test
        @DisplayName("Test getBonus with non-existent user throws Exception")
        void testGetBonus_UserNotFound_ThrowsException() {
            when(forumDAO.getUserDAO(validId)).thenReturn(null);

            Exception exception = assertThrows(Exception.class, () -> sut.getBonus(validId));
            assertEquals("NAN not in Database", exception.getMessage());
        }

        @Test
        @DisplayName("Test getBonus with user having no telephone throws Exception")
        void testGetBonus_UserWithoutTelephone_ThrowsException() {
            user.setTelephone(null);
            when(forumDAO.getUserDAO(validId)).thenReturn(user);

            Exception exception = assertThrows(Exception.class, () -> sut.getBonus(validId));
            assertEquals(validId + " not registered telephone", exception.getMessage());
        }*/
    }

    @Nested
    @DisplayName("White Box and Black Box Tests for Bonus Calculation")
    class BonusCalculationTests {

        private static Stream<Arguments> bonusCalculationTestCases() {
            // Equivalence Partitions and Boundary Values for sumPurchases
            return Stream.of(
                // Boundary: sumPurchases = 0 (no non-outlet purchases) -> vat = 0
                Arguments.of("sumPurchases = 0", 0.0f, 0.0f),
                // Partition: 0 < sumPurchases <= 30 -> vat = 0
                Arguments.of("0 < sumPurchases <= 30 (e.g., 15.0)", 15.0f, 0.0f),
                // Boundary: sumPurchases = 30 -> vat = 0
                Arguments.of("sumPurchases = 30", 30.0f, 0.0f),
                // Boundary: sumPurchases just above 30 (e.g., 30.01) -> vat = sum * 0.1735
                Arguments.of("sumPurchases > 30 (e.g., 30.01)", 30.01f, 30.01f * 0.1735f),
                // Partition: 30 < sumPurchases <= 288 -> vat = sum * 0.1735
                Arguments.of("30 < sumPurchases <= 288 (e.g., 150.0)", 150.0f, 150.0f * 0.1735f),
                // Boundary: sumPurchases = 288 -> vat = sum * 0.1735
                Arguments.of("sumPurchases = 288", 288.0f, 288.0f * 0.1735f),
                // Boundary: sumPurchases just above 288 (e.g., 288.01) -> vat = 50
                Arguments.of("sumPurchases > 288 (e.g., 288.01)", 288.01f, 50.0f),
                // Partition: sumPurchases > 288 -> vat = 50
                Arguments.of("sumPurchases > 288 (e.g., 500.0)", 500.0f, 50.0f)
            );
        }
/* 
        @ParameterizedTest(name = "{index}: {0}")
        @MethodSource("bonusCalculationTestCases")
        @DisplayName("Test getBonus with various purchase sums")
        void testGetBonus_Parameterized(String description, float sumPurchases, float expectedVat) throws Exception {
            // Arrange
            Article articleObj = new Article("art1", "Test Article", sumPurchases, false, 0);
            PurchasedArticle article = new PurchasedArticle(articleObj, 1);
            Purchase purchase = new Purchase(new Date());
            Iterator<Purchase> purchaseIterator = Collections.singletonList(purchase).iterator();
            
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date firstDate = sdf.parse("01/09/2022");
            Date lastDate = sdf.parse("06/12/2022");

            when(forumDAO.getUserDAO(validId)).thenReturn(user);
            when(forumDAO.getPurchasesDAO(eq(user), any(Date.class), any(Date.class))).thenReturn(purchaseIterator);

            // Act
            float actualVat = sut.getBonus(validId);

            // Assert
            assertEquals(expectedVat, actualVat, 0.001f);
        }

        @Test
        @DisplayName("Test getBonus with no purchases returns 0")
        void testGetBonus_NoPurchases_ReturnsZero() throws Exception {
            when(forumDAO.getUserDAO(validId)).thenReturn(user);
            when(forumDAO.getPurchasesDAO(eq(user), any(Date.class), any(Date.class)))
                .thenReturn(Collections.emptyIterator());

            float vat = sut.getBonus(validId);

            assertEquals(0.0f, vat);
        }
        @Test
        @DisplayName("Test getBonus with multiple purchases and articles (mixed outlet and non-outlet)")
        void testGetBonus_MultiplePurchasesAndArticles_CalculatesCorrectly() throws Exception {
            // Purchase 1: one non-outlet, one outlet
            Article artObj1 = new Article("art1", "A1", 100.0f, false, 0);
            PurchasedArticle article1 = new PurchasedArticle(artObj1, 1); // Counts
            Article artObj2 = new Article("art2", "A2", 50.0f, true, 0);
            PurchasedArticle article2 = new PurchasedArticle(artObj2, 2);  // Doesn't count
            Purchase purchase1 = new Purchase(new Date());

            // Purchase 2: one non-outlet
            Article artObj3 = new Article("art3", "A3", 200.0f, false, 0);
            PurchasedArticle article3 = new PurchasedArticle(artObj3, 1); // Counts
            Purchase purchase2 = new Purchase(new Date());

            // Total sum = 100.0 * 1 + 200.0 * 1 = 300.0
            // 300.0 > 288, so expected VAT is 50
            float expectedVat = 50.0f;

            Iterator<Purchase> purchaseIterator = Arrays.asList(purchase1, purchase2).iterator();

            when(forumDAO.getUserDAO(validId)).thenReturn(user);
            when(forumDAO.getPurchasesDAO(eq(user), any(Date.class), any(Date.class))).thenReturn(purchaseIterator);

            float vat = sut.getBonus(validId);

            assertEquals(expectedVat, vat, 0.001f);
        }*/
    }
}