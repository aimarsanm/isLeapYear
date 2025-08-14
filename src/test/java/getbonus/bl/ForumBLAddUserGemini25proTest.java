package getbonus.bl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentCaptor;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import getbonus.db.ForumDAOInterface;
import getbonus.domain.User;
import getbonus.exceptions.NullParameterException;
import getbonus.exceptions.UserNotFoundException;

/**
 * Test suite for the addUser method in the ForumBL class.
 * This class uses White Box (Condition/Decision Coverage) and Black Box (Equivalence Partitioning) testing techniques.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("ForumBL - addUser Method Tests")
class ForumBLAddUserGemini25proTest {

    @Mock
    private ForumDAOInterface forumDAO;

    @InjectMocks
    private ForumBL sut; // System Under Test

    @Captor
    private ArgumentCaptor<String> idCaptor;

    @Captor
    private ArgumentCaptor<String> nameCaptor;

    @Captor
    private ArgumentCaptor<String> telCaptor;

    private User expectedUser;

    @BeforeEach
    void setUp() {
        // This setup can be used for common test objects, though for addUser, parameterization is heavily used.
        expectedUser = new User("12345678A", "John Doe", "654321987");
    }

    /**
     * White Box tests focusing on Condition/Decision coverage for the null checks.
     */
    @Nested
    @DisplayName("White Box: Null Parameter Checks")
    class NullParameterTests {

        @ParameterizedTest(name = "Test with id={0}, name={1} - Should throw NullParameterException")
        @CsvSource({
            "null, ValidName",
            "ValidId, null",
            "null, null"
        })
        @DisplayName("Should throw NullParameterException for null id or name")
        void addUser_whenIdOrNameIsNull_thenThrowsNullParameterException(String id, String name) {
            // To handle the "null" string from CsvSource
            String actualId = "null".equalsIgnoreCase(id) ? null : id;
            String actualName = "null".equalsIgnoreCase(name) ? null : name;

            // Assert that the expected exception is thrown
            assertThrows(NullParameterException.class, () -> {
                sut.addUser(actualId, actualName, "123456789");
            }, "A NullParameterException should be thrown when id or name is null.");

            // Verify that no DAO methods were called
            verify(forumDAO, never()).existUserDAO(anyString());
            verify(forumDAO, never()).addUserDAO(anyString(), anyString(), anyString());
            verify(forumDAO, never()).getUserDAO(anyString());
        }
    }

    /**
     * Tests covering the logic when the user already exists in the database.
     */
    @Nested
    @DisplayName("White Box & Black Box: Existing User Scenarios")
    class ExistingUserTests {

        @Test
        @DisplayName("Should throw UserNotFoundException when user already exists")
        void addUser_whenUserExists_thenThrowsUserNotFoundException() {
            // Arrange: Mock the DAO to indicate the user already exists
            when(forumDAO.existUserDAO(expectedUser.getId())).thenReturn(true);

            // Act & Assert: Expect a UserNotFoundException
            assertThrows(UserNotFoundException.class, () -> {
                sut.addUser(expectedUser.getId(), expectedUser.getName(), expectedUser.getTelephone());
            }, "A UserNotFoundException should be thrown for an existing user ID.");

            // Verify interactions: existUserDAO is called, but others are not
            verify(forumDAO, times(1)).existUserDAO(expectedUser.getId());
            verify(forumDAO, never()).addUserDAO(anyString(), anyString(), anyString());
            verify(forumDAO, never()).getUserDAO(anyString());
        }
    }

    /**
     * Black Box and White Box tests for successfully adding a new user.
     * This covers the valid equivalence classes and the remaining path from the decision coverage.
     */
    @Nested
    @DisplayName("Black Box & White Box: New User Scenarios")
    class NewUserTests {

        @ParameterizedTest(name = "Add user with id={0}, name={1}, tel={2}")
        @CsvSource({
            "12345678A, John Doe, 654321987", // Valid case with telephone
            "87654321B, Jane Smith, null"     // Valid case without telephone (boundary)
        })
        @DisplayName("Should add user successfully when user does not exist")
        void addUser_whenUserDoesNotExist_thenAddsAndReturnsUser(String id, String name, String tel) throws NullParameterException, UserNotFoundException {
            // To handle the "null" string from CsvSource
            String actualTel = "null".equalsIgnoreCase(tel) ? null : tel;
            User newUser = new User(id, name, actualTel);

            // Arrange: Mock DAO to indicate user does not exist and mock the return of the new user
            when(forumDAO.existUserDAO(id)).thenReturn(false);
            when(forumDAO.getUserDAO(id)).thenReturn(newUser);

            // Act: Call the method under test
            User resultUser = sut.addUser(id, name, actualTel);

            // Assert: Check if the returned user is correct
            assertNotNull(resultUser, "The returned user should not be null.");
            assertEquals(id, resultUser.getId(), "The user ID should match the input.");
            assertEquals(name, resultUser.getName(), "The user name should match the input.");
            assertEquals(actualTel, resultUser.getTelephone(), "The user telephone should match the input.");

            // Verify DAO interactions and capture arguments
            verify(forumDAO, times(1)).existUserDAO(id);
            verify(forumDAO, times(1)).addUserDAO(idCaptor.capture(), nameCaptor.capture(), telCaptor.capture());
            verify(forumDAO, times(1)).getUserDAO(id);

            // Assert captured arguments are correct
            assertEquals(id, idCaptor.getValue(), "The ID passed to addUserDAO should be correct.");
            assertEquals(name, nameCaptor.getValue(), "The name passed to addUserDAO should be correct.");
            assertEquals(actualTel, telCaptor.getValue(), "The telephone passed to addUserDAO should be correct.");
        }
    }
}