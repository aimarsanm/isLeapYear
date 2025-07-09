package getbonus.bl;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import getbonus.db.ForumDAOInterface;
import getbonus.exceptions.NullParameterException;
import getbonus.exceptions.UserNotFoundException;







@ExtendWith(MockitoExtension.class)
@DisplayName("Integration Tests for addUser in ForumBL")
public class GPTo3miniAUTest {

    @Mock
    private ForumDAOInterface forumDAO;

    private ForumBL sut;

    @BeforeAll
    static void initAll() {
        // global initialization if needed
    }

    @BeforeEach
    void init() {
        sut = new ForumBL(forumDAO);
    }

    @AfterEach
    void tearDown() {
        // cleanup if needed
    }

    @AfterAll
    static void tearDownAll() {
        // global cleanup if needed
    }
/*
    @ParameterizedTest(name = "[{index}] Valid addUser invokes DAO correctly with id={0}, name={1}, tel={2}")
    @CsvSource({
        "75075708M, John, 123456789",
        "85075709N, Alice, 987654321"
    })
    @DisplayName("White/Black Box: addUser with valid parameters and non-existing user")
    void testAddUser_Valid(String id, String name, String tel) {
        try {
            // Configure DAO for valid user non-existence
            when(forumDAO.existUserDAO(id)).thenReturn(false);
            // Create expected user object (assuming a constructor User(String id, String name, String tel))
            User expectedUser = new User(id, name, tel);
            // Simulate addUserDAO behavior (do nothing) and return expected user
            doNothing().when(forumDAO).addUserDAO(id, name, tel);
            when(forumDAO.getUserDAO(id)).thenReturn(expectedUser);
            
            // Invoke SUT method
            User result = sut.addUser(id, name, tel);
            
            // Assertions on returned User
            assertNotNull(result, "The returned user should not be null");
            assertEquals(id, result.getId(), "User id should match");
            assertEquals(name, result.getName(), "User name should match");
            assertEquals(tel, result.getTelephone(), "User telephone should match");

            // Verify interactions with the DAO mock
            verify(forumDAO, times(1)).existUserDAO(id);
            verify(forumDAO, times(1)).addUserDAO(id, name, tel);
            verify(forumDAO, times(1)).getUserDAO(id);
        } catch (Exception e) {
            fail("Unexpected exception thrown: " + e.getMessage());
        }
    } */

    @ParameterizedTest(name = "[{index}] addUser with null parameters id={0}, name={1}, tel={2}")
    @MethodSource("provideNullParameters")
    @DisplayName("White/Black Box: addUser with null id or name should throw NullParameterException")
    void testAddUser_NullParameters(String id, String name, String tel) {
        try {
            NullParameterException exception = assertThrows(NullParameterException.class, () -> {
                sut.addUser(id, name, tel);
            }, "Expected NullParameterException when id or name is null");
            assertEquals("id or name is null", exception.getMessage(), "Exception message should match");
            // Verify that none of the DAO methods were invoked
            verify(forumDAO, never()).existUserDAO(any());
            verify(forumDAO, never()).addUserDAO(anyString(), anyString(), anyString());
            verify(forumDAO, never()).getUserDAO(anyString());
        } catch (Exception e) {
            fail("Unexpected exception during test: " + e.getMessage());
        }
    }

    // Method source for null parameter cases
    static Stream<Arguments> provideNullParameters() {
        return Stream.of(
            Arguments.of(null, "John", "123456789"),
            Arguments.of("75075708M", null, "123456789"),
            Arguments.of(null, null, "123456789")
        );
    }

    @ParameterizedTest(name = "[{index}] addUser when user already exists with id={0}, name={1}, tel={2}")
    @CsvSource({
        "75075708M, John, 123456789",
        "85075709N, Alice, 987654321"
    })
    @DisplayName("White/Black Box: addUser with existing user should throw UserNotFoundException")
    void testAddUser_UserAlreadyExists(String id, String name, String tel) {
        try {
            // Configure DAO to simulate that user exists
            when(forumDAO.existUserDAO(id)).thenReturn(true);

            UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
                sut.addUser(id, name, tel);
            }, "Expected UserNotFoundException when the user already exists");
            assertEquals("id no in DB", exception.getMessage(), "Exception message should match");

            // Verify only the exist check is performed and other DAO methods are not called
            verify(forumDAO, times(1)).existUserDAO(id);
            verify(forumDAO, never()).addUserDAO(anyString(), anyString(), anyString());
            verify(forumDAO, never()).getUserDAO(anyString());
        } catch (Exception e) {
            fail("Unexpected exception during test: " + e.getMessage());
        }
    }
}