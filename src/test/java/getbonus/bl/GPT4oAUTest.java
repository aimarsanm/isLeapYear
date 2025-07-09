package getbonus.bl;

import java.util.stream.Stream;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import getbonus.db.ForumDAOInterface;
import getbonus.domain.User;
import getbonus.exceptions.NullParameterException;
import getbonus.exceptions.UserNotFoundException;

@DisplayName("ForumBL AddUser Method Tests")
class GPT4oAUTest {

    private ForumDAOInterface mockDao;
    private ForumBL forumBL;

    @BeforeEach
    void setUp() {
        mockDao = mock(ForumDAOInterface.class);
        forumBL = new ForumBL(mockDao);
    }

    @AfterEach
    void tearDown() {
        mockDao = null;
        forumBL = null;
    }

    // Test data provider for parameterized tests
    static Stream<Arguments> provideAddUserParameters() {
        return Stream.of(
            // Valid inputs
            //Arguments.of("123", "John Doe", "123456789", false, true, null),
            // Null id
            Arguments.of(null, "John Doe", "123456789", false, false, NullParameterException.class),
            // Null name
            Arguments.of("123", null, "123456789", false, false, NullParameterException.class),
            // User already exists
            Arguments.of("123", "John Doe", "123456789", true, false, UserNotFoundException.class)
        );
    }

    @ParameterizedTest
    @MethodSource("provideAddUserParameters")
    @DisplayName("Parameterized Test for AddUser Method")
    void testAddUser(String id, String name, String tel, boolean userExists, boolean shouldSucceed, Class<? extends Exception> expectedException) {
        try {
            // Mock behavior
            when(mockDao.existUserDAO(id)).thenReturn(userExists);
            if (!userExists && id != null && name != null) {
                User mockUser = new User(id, name, tel);
                doNothing().when(mockDao).addUserDAO(id, name, tel);
                when(mockDao.getUserDAO(id)).thenReturn(mockUser);
            }

            // Call the method
            User result = forumBL.addUser(id, name, tel);

            // Assertions for successful execution
            if (shouldSucceed) {
                assertNotNull(result);
                assertEquals(id, result.getId());
                assertEquals(name, result.getName());
                assertEquals(tel, result.getTelephone());

                // Verify DAO interactions
                verify(mockDao, times(1)).existUserDAO(id);
                verify(mockDao, times(1)).addUserDAO(id, name, tel);
                verify(mockDao, times(1)).getUserDAO(id);
            }
        } catch (Exception e) {
            // Assertions for exceptions
            if (expectedException != null) {
                assertEquals(expectedException, e.getClass());
            } else {
                fail("Unexpected exception thrown: " + e.getMessage());
            }
        }
    }

    @Test
    @DisplayName("Test AddUser Throws NullParameterException When ID or Name is Null")
    void testAddUserThrowsNullParameterException() {
        try {
            forumBL.addUser(null, "John Doe", "123456789");
            fail("Expected NullParameterException was not thrown");
        } catch (NullParameterException e) {
            assertEquals("id or name is null", e.getMessage());
        } catch (Exception e) {
            fail("Unexpected exception thrown: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Test AddUser Throws UserNotFoundException When User Already Exists")
    void testAddUserThrowsUserNotFoundException() {
        try {
            // Mock behavior
            when(mockDao.existUserDAO("123")).thenReturn(true);

            forumBL.addUser("123", "John Doe", "123456789");
            fail("Expected UserNotFoundException was not thrown");
        } catch (UserNotFoundException e) {
            assertEquals("id no in DB", e.getMessage());
        } catch (Exception e) {
            fail("Unexpected exception thrown: " + e.getMessage());
        }
    }
/*
    @Test
    @DisplayName("Test AddUser Successfully Adds a New User")
    void testAddUserSuccessfully() {
        try {
            // Mock behavior
            String id = "123";
            String name = "John Doe";
            String tel = "123456789";
            when(mockDao.existUserDAO(id)).thenReturn(false);
            doNothing().when(mockDao).addUserDAO(id, name, tel);
            User mockUser = new User(id, name, tel);
            when(mockDao.getUserDAO(id)).thenReturn(mockUser);

            // Call the method
            User result = forumBL.addUser(id, name, tel);

            // Assertions
            assertNotNull(result);
            assertEquals(id, result.getId());
            assertEquals(name, result.getName());
            assertEquals(tel, result.getTelephone());

            // Verify DAO interactions
            verify(mockDao, times(1)).existUserDAO(id);
            verify(mockDao, times(1)).addUserDAO(id, name, tel);
            verify(mockDao, times(1)).getUserDAO(id);
        } catch (Exception e) {
            fail("Unexpected exception thrown: " + e.getMessage());
        } 
    }*/
}