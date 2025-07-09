package getbonus.bl;

import java.sql.Date;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import getbonus.db.ForumDAOInterface;
import getbonus.domain.Purchase;
import getbonus.domain.PurchasedArticle;
import getbonus.domain.User;

/**
 * Tests for ForumBL#getBonus(String).
 * - All invalid‐param and null checks
 * - All bonus‐calculation branches:
 *   <=30 → 0, >30 & <=288 → sum*0.1735, >288 → 50
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GPTo4minipreTest {

    private static final String VALID_ID = "00000000T"; // 0 %23 -> "T"
    private ForumDAOInterface daoMock;
    private ForumBL sut;
    private User validUser;

    @BeforeEach
    void setUp() {
        daoMock = mock(ForumDAOInterface.class);
        sut = new ForumBL(daoMock);
        // a user with telephone to pass the null-telephone check
        validUser = new User(VALID_ID, "Alice", "600123456");
        when(daoMock.getUserDAO(VALID_ID)).thenReturn(validUser);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"00000000A", "123", "ABCDEFGHZ"})
    @DisplayName("Throws if id is null or malformed by ValidadorDNI")
    void invalidId_throws(String badId) {
        assertThrows(Exception.class, () -> sut.getBonus(badId),
            "Expect Exception for id = " + badId);
        // no DAO interaction
        verify(daoMock, never()).getUserDAO(anyString());
    }

    @Test
    @DisplayName("Throws if user not in database")
    void userNotFound_throws() {
        when(daoMock.getUserDAO(VALID_ID)).thenReturn(null);
        Exception ex = assertThrows(Exception.class, () -> sut.getBonus(VALID_ID));
        assertEquals("NAN not in Database", ex.getMessage());
    }

    @Test
    @DisplayName("Throws if telephone is not registered")
    void telephoneNull_throws() {
        User uNoTel = new User(VALID_ID, "Bob", null);
        when(daoMock.getUserDAO(VALID_ID)).thenReturn(uNoTel);
        Exception ex = assertThrows(Exception.class, () -> sut.getBonus(VALID_ID));
        assertTrue(ex.getMessage().contains("not registered telephone"));
    }

    @ParameterizedTest(name = "sum={0} → expectedVat={1}")
    @CsvSource({
        "30,   0.0",      // boundary sum == 30 → no vat
        "31,   5.3785",   // just >30 → 31*0.1735
        "288,  49.968",   // boundary sum == 288 → 288*0.1735
        "289,  50.0"      // just >288 → fixed 50
    })
    @DisplayName("Correct bonus for various total purchase sums")
    void bonusCalculation_parameterized(double sum, double expectedVat) throws Exception {
        // Prepare one Purchase mock returning one PurchasedArticle mock
        Purchase purchaseMock = mock(Purchase.class);
        PurchasedArticle paMock = mock(PurchasedArticle.class);

        // Configure PA: non-outlet, price*quantity == sum
        when(paMock.isOutlet()).thenReturn(false);
        when(paMock.getPrice()).thenReturn((float) sum);
        when(paMock.getQuantity()).thenReturn(1);

        // Purchase returns that single PA
        when(purchaseMock.getPurchaseIterator())
            .thenReturn(Collections.singletonList(paMock).iterator());

        // DAO returns one purchase in the date‐range
        when(daoMock.getPurchasesDAO(
            eq(validUser), any(Date.class), any(Date.class)))
          .thenReturn(Collections.singletonList(purchaseMock).iterator());

        float vat = sut.getBonus(VALID_ID);
        assertEquals((float) expectedVat, vat, 1e-6f,
            "For sum=" + sum + " expected vat=" + expectedVat);
    }
}