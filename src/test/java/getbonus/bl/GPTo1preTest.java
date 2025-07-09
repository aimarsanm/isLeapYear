//// filepath: c:\Users\a.sanmartin\Desktop\IsLeapYearVisual\isLeapYear\src\test\java\getbonus\bl\ForumBLGetBonusTest.java
package getbonus.bl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.Mockito;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import getbonus.db.ForumDAOInterface;
import getbonus.domain.Purchase;
import getbonus.domain.PurchasedArticle;
import getbonus.domain.User;

/**
 * This class contains integration tests (White Box: Condition/Decision coverage
 * + Black Box: Equivalence partitioning and boundary value analysis)
 * for the getBonus method in ForumBL.
 */
class GPTo1preTest {

    private static ForumDAOInterface forumDAO;
    private static ForumBL sut; // System Under Test

    @BeforeAll
    static void setUpBeforeAll() {
        // Global initialization if needed
    }

    @AfterAll
    static void tearDownAfterAll() {
        // Global resource releasing if needed
    }

    @BeforeEach
    void setUp() {
        forumDAO = Mockito.mock(ForumDAOInterface.class);
        sut = new ForumBL(forumDAO);
    }

    @AfterEach
    void tearDown() {
        // Reset after each test
        Mockito.reset(forumDAO);
    }

    /**
     * White Box / Condition coverage:
     * Testing (id == null) in (id == null || !new ValidadorDNI(id).validar())
     * Expect exception thrown with correct message.
     */
    @Test
    @DisplayName("Test getBonus with null ID -> Expect exception")
    void testGetBonusNullId() {
        try {
            sut.getBonus(null);
            fail("Expected exception for null ID but none thrown.");
        } catch (Exception e) {
            assertEquals("id null or not valid", e.getMessage());
        }
    }

    /**
     * White Box / Condition coverage:
     * Testing invalid ID in (id == null || !new ValidadorDNI(id).validar())
     * Expect exception thrown with correct message.
     */
    @Test
    @DisplayName("Test getBonus with invalid ID -> Expect exception")
    void testGetBonusInvalidId() {
        try {
            sut.getBonus("12345678A"); 
            fail("Expected exception for invalid ID but none thrown.");
        } catch (Exception e) {
            assertEquals("id null or not valid", e.getMessage());
        }
    }

    /**
     * White Box coverage:
     * Testing if (u == null) -> throws new Exception("NAN not in Database")
     */
    @Test
    @DisplayName("Test getBonus when user is not found -> Expect exception")
    void testGetBonusUserNotFound() {
        try {
            when(forumDAO.getUserDAO("11111111H")).thenReturn(null);
            sut.getBonus("11111111H");
            fail("Expected exception for user == null but none thrown.");
        } catch (Exception e) {
            assertEquals("NAN not in Database", e.getMessage());
        }
    }

    /**
     * White Box coverage:
     * Testing if (u.getTelephone() == null) -> throws new Exception(id + " not registered telephone")
     */
    @Test
    @DisplayName("Test getBonus with user phone null -> Expect exception")
    void testGetBonusTelephoneNull() {
        try {
            User mockedUser = mock(User.class);
            when(mockedUser.getTelephone()).thenReturn(null);
            when(forumDAO.getUserDAO("22222222X")).thenReturn(mockedUser);

            sut.getBonus("22222222X");
            fail("Expected exception for null telephone but none thrown.");
        } catch (Exception e) {
            assertTrue(e.getMessage().contains(" not registered telephone"));
        }
    }

    /**
     * Black Box + White Box coverage:
     * We parameterize tests for boundary values of sumPurchases.
     * - sumPurchases <= 30  => vat=0
     * - 31 <= sumPurchases <= 288 => vat=sumPurchases*0.1735
     * - sumPurchases > 288 => vat=50
     * We also test the behavior when isOutlet is set to true or false.
     */
    @ParameterizedTest
    @MethodSource("sumPurchasesProvider")
    @DisplayName("Test getBonus with valid ID and telephone -> checking sumPurchases boundary conditions")
    void testGetBonusValidIdAndPhone(float price, boolean isOutlet, float expectedVat) {
        try {
            User mockedUser = mock(User.class);
            when(mockedUser.getTelephone()).thenReturn("666666666");
            when(forumDAO.getUserDAO("11111111H")).thenReturn(mockedUser);

            // Mock a single purchase
            Purchase mockedPurchase = mock(Purchase.class);
            Iterator<Purchase> purchaseIterator = mock(Iterator.class);

            when(purchaseIterator.hasNext()).thenReturn(true, false);
            when(purchaseIterator.next()).thenReturn(mockedPurchase);

            // Mock a single purchased article
            PurchasedArticle mockedArticle = mock(PurchasedArticle.class);
            Iterator<PurchasedArticle> articlesIterator = mock(Iterator.class);

            when(articlesIterator.hasNext()).thenReturn(true, false);
            when(articlesIterator.next()).thenReturn(mockedArticle);

            when(mockedArticle.isOutlet()).thenReturn(isOutlet);
            when(mockedArticle.getPrice()).thenReturn(price);
            when(mockedArticle.getQuantity()).thenReturn(1);

            when(mockedPurchase.getPurchaseIterator()).thenReturn(articlesIterator);

            when(forumDAO.getPurchasesDAO(eq(mockedUser), any(), any())).thenReturn(purchaseIterator);

            float actualVat = sut.getBonus("11111111H");
            assertEquals(expectedVat, actualVat, 0.0001f);
        } catch (Exception e) {
            fail("Did not expect an exception, but got: " + e.getMessage());
        }
    }

    /**
     * Provides parameters for boundary analysis:
     *  sum <= 30 => vat=0
     *  31 <= sum <= 288 => vat=sum * 0.1735
     *  sum > 288 => vat=50
     * isOutlet=true => that price won't add to sum
     */
    static List<Object[]> sumPurchasesProvider() {
        List<Object[]> params = new ArrayList<>();

        // (price, isOutlet, expectedVat)
        // sum=0 => isOutlet=true => no addition to sum => vat=0
        params.add(new Object[]{0f, true, 0f});

        // sum=30 => isOutlet=false => vat=0
        params.add(new Object[]{30f, false, 0f});

        // sum=31 => isOutlet=false => vat=31*0.1735 => 5.3735
        params.add(new Object[]{31f, false, 31f * 0.1735f});

        // sum=288 => isOutlet=false => vat=288*0.1735 => boundary of the middle range
        params.add(new Object[]{288f, false, 288f * 0.1735f});

        // sum=289 => isOutlet=false => vat=50 => sumPurchases > 288
        params.add(new Object[]{289f, false, 50f});

        return params;
    }
}