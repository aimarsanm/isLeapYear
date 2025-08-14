package getbonus.bl;

import java.util.stream.Stream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance; 
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import getbonus.db.ForumDAOInterface;
import getbonus.domain.User;
import getbonus.exceptions.NullParameterException;
import getbonus.exceptions.UserNotFoundException;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Integration tests for ForumBL.addUser using White and Black Box methods") 
public class ForumBLAddUserGPTo3miniTest {



private ForumDAOInterface mockDao;
private ForumBL sut; // system under test

@BeforeAll
public void beforeAllTests() {
    // Any global initialization if needed.
}

@BeforeEach
public void setUp() {
    mockDao = Mockito.mock(ForumDAOInterface.class);
    sut = new ForumBL(mockDao);
}

@AfterEach
public void tearDown() {
    // Reset mocks if necessary.
    Mockito.reset(mockDao);
}

@AfterAll
public void afterAllTests() {
    // Global cleanup if needed.
}

// Data provider for null parameter cases (id or name null)
static Stream<Arguments> nullParametersProvider() {
    return Stream.of(
        // (id, name, tel) combinations where at least id or name is null
        Arguments.of(null, "Alice", "123456789"),
        Arguments.of("123", null, "987654321"),
        Arguments.of(null, null, "5555555")
    );
}

// Test for null input parameters (white-box: first if condition)
@ParameterizedTest(name = "Test addUser with id={0}, name={1}, tel={2} should throw NullParameterException")
@MethodSource("nullParametersProvider")
@DisplayName("Null Parameter Tests for addUser (White Box)")
public void testAddUser_NullParameters(String id, String name, String tel) {
    try {
        NullParameterException ex = assertThrows(NullParameterException.class, () -> {
            sut.addUser(id, name, tel);
        });
        assertEquals("id or name is null", ex.getMessage());
    } catch (Exception e) {
        fail("Unexpected exception in testAddUser_NullParameters: " + e);
    }
}

// Data provider for valid new user cases 
static Stream<Arguments> validNewUserProvider() {
    return Stream.of(
        // (id, name, tel)
        Arguments.of("123", "Alice", "123456789"),
        Arguments.of("456", "Bob", null),            // tel can be null
        Arguments.of("789", "Charlie", "987654321")
    );
}

// Test for a new user added successfully
@ParameterizedTest(name = "Test addUser with new user: id={0}, name={1}, tel={2}")
@MethodSource("validNewUserProvider")
@DisplayName("New User Tests for addUser (White & Black Box)")
public void testAddUser_NewUser(String id, String name, String tel) {
    try {
        // Stub existUserDAO to return false and stub getUserDAO to return the expected user.
        Mockito.when(mockDao.existUserDAO(id)).thenReturn(false);

        // Create a dummy User instance to be returned by getUserDAO.
        User expectedUser = new User(id, name, tel);
        Mockito.when(mockDao.getUserDAO(id)).thenReturn(expectedUser);

        // Call addUser.
        User actualUser = sut.addUser(id, name, tel);

        // Verify that addUserDAO was called exactly once with the correct parameters.
        Mockito.verify(mockDao, Mockito.times(1)).addUserDAO(id, name, tel);

        // Assert the returned user is as expected.
        assertNotNull(actualUser, "Returned user is null");
        assertEquals(id, actualUser.getId(), "User id mismatch");
        assertEquals(name, actualUser.getName(), "User name mismatch");
        assertEquals(tel, actualUser.getTelephone(), "User telephone mismatch");
    } catch (Exception e) {
        fail("Unexpected exception in testAddUser_NewUser: " + e);
    }
}

// Data provider for duplicate user (user exists) cases.
static Stream<Arguments> duplicateUserProvider() {
    return Stream.of(
        // (id, name, tel) where user already exists
        Arguments.of("321", "Daisy", "111222333"),
        Arguments.of("654", "Edward", null)
    );
}

// Test for duplicate user scenario (DAO returns true for existUserDAO)
@ParameterizedTest(name = "Test addUser with duplicate user: id={0}, name={1}, tel={2} should throw UserNotFoundException")
@MethodSource("duplicateUserProvider")
@DisplayName("Existing User Tests for addUser (White & Black Box)")
public void testAddUser_DuplicateUser(String id, String name, String tel) {
    try {
        // Stub existUserDAO to return true to simulate that the user already exists.
        Mockito.when(mockDao.existUserDAO(id)).thenReturn(true);

        // Expect UserNotFoundException to be thrown.
        UserNotFoundException ex = assertThrows(UserNotFoundException.class, () -> {
            sut.addUser(id, name, tel);
        });
        assertEquals("id no in DB", ex.getMessage());
    } catch (Exception e) {
        fail("Unexpected exception in testAddUser_DuplicateUser: " + e);
    }
}
}