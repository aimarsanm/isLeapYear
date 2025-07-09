package getbonus.bl;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mockito;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import getbonus.db.ForumDAOInterface;
import getbonus.domain.Article;
import getbonus.domain.Purchase;
import getbonus.domain.PurchasedArticle;
import getbonus.domain.User;

@DisplayName("ForumBL - getBonus Method Tests")
class GPT4oTest {

    private ForumDAOInterface mockDao;
    private ForumBL forumBL;

    @BeforeEach
    void setUp() {
        mockDao = Mockito.mock(ForumDAOInterface.class);
        forumBL = new ForumBL(mockDao);
    }

    @ParameterizedTest
    @CsvSource({
        "75075708M, true, true, true, 50.0", // Valid DNI, purchases > 288
        "75075708M, true, true, true, 30.0", // Valid DNI, purchases > 30 but <= 288
        "75075708M, true, true, true, 0.0",  // Valid DNI, purchases <= 30
        "75075708M, false, false, false, 0.0" // Invalid DNI
    })
    @DisplayName("Parameterized Test for getBonus Method")
    void testGetBonus(String id, boolean validDNI, boolean userExists, boolean hasTelephone, float expectedBonus) {
        try {
            // Mock ValidadorDNI behavior
            ValidadorDNI mockValidator = Mockito.mock(ValidadorDNI.class);
            when(mockValidator.validar()).thenReturn(validDNI);

            // Mock DAO behavior
            User mockUser = validDNI && userExists ? new User(id, "John Doe", hasTelephone ? "123456789" : null) : null;
            when(mockDao.getUserDAO(id)).thenReturn(mockUser);

            if (validDNI && userExists && hasTelephone) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date firstDate = sdf.parse("01/09/2022");
                Date lastDate = sdf.parse("06/12/2022");

                Purchase mockPurchase = new Purchase();
                PurchasedArticle mockArticle = new PurchasedArticle(new Article("A1", "Desc", 100, false, 10), 3);
                mockPurchase.addArticle(mockArticle);

                Iterator<Purchase> mockPurchases = Arrays.asList(mockPurchase).iterator();
                when(mockDao.getPurchasesDAO(mockUser, firstDate, lastDate)).thenReturn(mockPurchases);
            }

            // Call the method under test
            float bonus = forumBL.getBonus(id);

            // Assertions
            assertEquals(expectedBonus, bonus, 0.01);

            // Verify interactions
            if (validDNI && userExists) {
                verify(mockDao, times(1)).getUserDAO(id);
                if (hasTelephone) {
                    verify(mockDao, times(1)).getPurchasesDAO(any(User.class), any(Date.class), any(Date.class));
                }
            }
        } catch (Exception e) {
            fail("No exception should be thrown: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Test Exception for Null or Invalid DNI")
    void testGetBonusInvalidDNI() {
        try {
            // Mock ValidadorDNI behavior
            ValidadorDNI mockValidator = Mockito.mock(ValidadorDNI.class);
            when(mockValidator.validar()).thenReturn(false);

            // Call the method under test
            Exception exception = assertThrows(Exception.class, () -> forumBL.getBonus(null));

            // Assertions
            assertEquals("id null or not valid", exception.getMessage());
        } catch (Exception e) {
            fail("No exception should be thrown: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Test Exception for User Not Found")
    void testGetBonusUserNotFound() {
        try {
            // Mock ValidadorDNI behavior
            ValidadorDNI mockValidator = Mockito.mock(ValidadorDNI.class);
            when(mockValidator.validar()).thenReturn(true);

            // Mock DAO behavior
            when(mockDao.getUserDAO("75075708M")).thenReturn(null);

            // Call the method under test
            Exception exception = assertThrows(Exception.class, () -> forumBL.getBonus("75075708M"));

            // Assertions
            assertEquals("NAN not in Database", exception.getMessage());
        } catch (Exception e) {
            fail("No exception should be thrown: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Test Exception for Missing Telephone")
    void testGetBonusMissingTelephone() {
        try {
            // Mock ValidadorDNI behavior
            ValidadorDNI mockValidator = Mockito.mock(ValidadorDNI.class);
            when(mockValidator.validar()).thenReturn(true);

            // Mock DAO behavior
            User mockUser = new User("75075708M", "John Doe", null);
            when(mockDao.getUserDAO("75075708M")).thenReturn(mockUser);

            // Call the method under test
            Exception exception = assertThrows(Exception.class, () -> forumBL.getBonus("75075708M"));

            // Assertions
            assertEquals("75075708M not registered telephone", exception.getMessage());
        } catch (Exception e) {
            fail("No exception should be thrown: " + e.getMessage());
        }
    }

    @AfterEach
    void tearDown() {
        mockDao = null;
        forumBL = null;
    }
}