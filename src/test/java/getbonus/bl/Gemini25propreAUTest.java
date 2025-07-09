package getbonus.bl;
import getbonus.db.ForumDAOInterface;
import getbonus.domain.User;
import getbonus.exceptions.NullParameterException;
import getbonus.exceptions.UserNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;





@ExtendWith(MockitoExtension.class)
class Gemini25propreAUTest {

    @Mock
    private ForumDAOInterface dao;

    @InjectMocks
    private ForumBL sut;

    @DisplayName("White Box & Black Box: Test successful user addition")
    @ParameterizedTest(name = "Run {index}: id={0}, name={1}, tel={2}")
    @CsvSource({
            "12345678A, John Doe, 987654321",
            "87654321B, Jane Smith, ''",
            "11223344C, Peter Jones,"
    })
    void addUser_whenUserDoesNotExist_shouldAddAndReturnUser(String id, String name, String tel) {
        // Arrange
        User expectedUser = new User(id, name, tel);
        when(dao.existUserDAO(id)).thenReturn(false);
        when(dao.getUserDAO(id)).thenReturn(expectedUser);

        // Act
        User actualUser = null;
        try {
            actualUser = sut.addUser(id, name, tel);
        } catch (Exception e) {
            fail("Should not throw any exception in this case, but threw: " + e.getClass().getName());
        }

        // Assert
        assertNotNull(actualUser, "The returned user should not be null.");
        assertEquals(expectedUser.getId(), actualUser.getId(), "The user ID should match the input.");
        assertEquals(expectedUser.getName(), actualUser.getName(), "The user name should match the input.");
        assertEquals(expectedUser.getTelephone(), actualUser.getTelephone(), "The user telephone should match the input.");

        // Verify mock interactions
        verify(dao, times(1)).existUserDAO(id);
        verify(dao, times(1)).addUserDAO(id, name, tel);
        verify(dao, times(1)).getUserDAO(id);
    }

    @Test
    @DisplayName("White Box & Black Box: Test adding a user that already exists")
    void addUser_whenUserAlreadyExists_shouldThrowUserNotFoundException() {
        // Arrange
        String id = "12345678A";
        String name = "John Doe";
        String tel = "987654321";
        when(dao.existUserDAO(id)).thenReturn(true);

        // Act & Assert
        UserNotFoundException thrown = assertThrows(UserNotFoundException.class, () -> {
            sut.addUser(id, name, tel);
        }, "UserNotFoundException should be thrown when the user already exists.");

        assertEquals("id no in DB", thrown.getMessage(), "The exception message should be correct.");

        // Verify mock interactions
        verify(dao, times(1)).existUserDAO(id);
        verify(dao, never()).addUserDAO(anyString(), anyString(), anyString());
        verify(dao, never()).getUserDAO(anyString());
    }

    @DisplayName("White Box (Condition/Decision Coverage): Test adding user with null parameters")
    @ParameterizedTest(name = "Run {index}: id={0}, name={1}")
    @CsvSource(value = {
            "null, Jane Doe",
            "11223344C, null",
            "null, null"
    }, nullValues = "null")
    void addUser_whenIdOrNameIsNull_shouldThrowNullParameterException(String id, String name) {
        // Arrange
        String tel = "123456789";

        // Act & Assert
        NullParameterException thrown = assertThrows(NullParameterException.class, () -> {
            sut.addUser(id, name, tel);
        }, "NullParameterException should be thrown when id or name is null.");

        assertEquals("id or name is null", thrown.getMessage(), "The exception message should be correct.");

        // Verify mock interactions
        verifyNoInteractions(dao);
    }
}