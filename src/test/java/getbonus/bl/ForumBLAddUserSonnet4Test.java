package getbonus.bl;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.Mockito;

import getbonus.db.ForumDAOInterface;
import getbonus.domain.User;
import getbonus.exceptions.NullParameterException;
import getbonus.exceptions.UserNotFoundException;

@DisplayName("ForumBL addUser method integration tests")
public class ForumBLAddUserSonnet4Test {

    private ForumDAOInterface forumDAO;
    private ForumBL sut;

    @BeforeEach
    void setUp() {
        forumDAO = Mockito.mock(ForumDAOInterface.class);
        sut = new ForumBL(forumDAO);
    }

    // WHITE BOX TESTING - Condition/Decision Coverage Tests

    @Test
    @DisplayName("Test 1: Both id and name are null - should throw NullParameterException")
    void testAddUser_BothIdAndNameNull_ThrowsNullParameterException() {
        try {
            // Test parameters
            String id = null;
            String name = null;
            String telephone = "123456789";

            // Call SUT - should throw exception before any DAO calls
            assertThrows(NullParameterException.class, () -> {
                sut.addUser(id, name, telephone);
            });

            // Verify no DAO methods were called
            Mockito.verify(forumDAO, Mockito.never()).existUserDAO(anyString());
            Mockito.verify(forumDAO, Mockito.never()).addUserDAO(anyString(), anyString(), anyString());
            Mockito.verify(forumDAO, Mockito.never()).getUserDAO(anyString());

        } catch (Exception e) {
            fail("Test should not throw unexpected exceptions");
        }
    }

    @Test
    @DisplayName("Test 2: Id is null and name is not null - should throw NullParameterException")
    void testAddUser_IdNullNameNotNull_ThrowsNullParameterException() {
        try {
            // Test parameters
            String id = null;
            String name = "John Doe";
            String telephone = "123456789";

            // Call SUT - should throw exception before any DAO calls
            assertThrows(NullParameterException.class, () -> {
                sut.addUser(id, name, telephone);
            });

            // Verify no DAO methods were called
            Mockito.verify(forumDAO, Mockito.never()).existUserDAO(anyString());
            Mockito.verify(forumDAO, Mockito.never()).addUserDAO(anyString(), anyString(), anyString());
            Mockito.verify(forumDAO, Mockito.never()).getUserDAO(anyString());

        } catch (Exception e) {
            fail("Test should not throw unexpected exceptions");
        }
    }

    @Test
    @DisplayName("Test 3: Id is not null and name is null - should throw NullParameterException")
    void testAddUser_IdNotNullNameNull_ThrowsNullParameterException() {
        try {
            // Test parameters
            String id = "75075708M";
            String name = null;
            String telephone = "123456789";

            // Call SUT - should throw exception before any DAO calls
            assertThrows(NullParameterException.class, () -> {
                sut.addUser(id, name, telephone);
            });

            // Verify no DAO methods were called
            Mockito.verify(forumDAO, Mockito.never()).existUserDAO(anyString());
            Mockito.verify(forumDAO, Mockito.never()).addUserDAO(anyString(), anyString(), anyString());
            Mockito.verify(forumDAO, Mockito.never()).getUserDAO(anyString());

        } catch (Exception e) {
            fail("Test should not throw unexpected exceptions");
        }
    }

    @Test
    @DisplayName("Test 4: Valid id and name, user doesn't exist - should add user and return User object")
    void testAddUser_ValidParametersUserNotExists_ReturnsUser() {
        try {
            // Test parameters
            String id = "75075708M";
            String name = "John Doe";
            String telephone = "123456789";
            User expectedUser = new User(id, name, telephone);

            // Configure mock
            Mockito.doReturn(false).when(forumDAO).existUserDAO(eq(id));
            Mockito.doReturn(expectedUser).when(forumDAO).getUserDAO(eq(id));

            // Call SUT
            User result = sut.addUser(id, name, telephone);

            // Verify results
            assertNotNull(result);
            assertEquals(id, result.getId());
            assertEquals(name, result.getName());
            assertEquals(telephone, result.getTelephone());

            // Verify DAO method calls
            Mockito.verify(forumDAO, Mockito.times(1)).existUserDAO(eq(id));
            Mockito.verify(forumDAO, Mockito.times(1)).addUserDAO(eq(id), eq(name), eq(telephone));
            Mockito.verify(forumDAO, Mockito.times(1)).getUserDAO(eq(id));

        } catch (Exception e) {
            fail("Test should not throw unexpected exceptions: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Test 5: Valid id and name, user already exists - should throw UserNotFoundException")
    void testAddUser_ValidParametersUserExists_ThrowsUserNotFoundException() {
        try {
            // Test parameters
            String id = "75075708M";
            String name = "John Doe";
            String telephone = "123456789";

            // Configure mock - user exists
            Mockito.doReturn(true).when(forumDAO).existUserDAO(eq(id));

            // Call SUT - should throw exception
            assertThrows(UserNotFoundException.class, () -> {
                sut.addUser(id, name, telephone);
            });

            // Verify DAO method calls
            Mockito.verify(forumDAO, Mockito.times(1)).existUserDAO(eq(id));
            Mockito.verify(forumDAO, Mockito.never()).addUserDAO(anyString(), anyString(), anyString());
            Mockito.verify(forumDAO, Mockito.never()).getUserDAO(anyString());

        } catch (Exception e) {
            fail("Test should not throw unexpected exceptions: " + e.getMessage());
        }
    }

    // BLACK BOX TESTING - Equivalence Partitioning and Boundary Value Analysis

    @Test
    @DisplayName("Test 6: Valid parameters with null telephone - should add user successfully")
    void testAddUser_ValidParametersNullTelephone_ReturnsUser() {
        try {
            // Test parameters
            String id = "12345678Z";
            String name = "Jane Smith";
            String telephone = null;
            User expectedUser = new User(id, name, telephone);

            // Configure mock
            Mockito.doReturn(false).when(forumDAO).existUserDAO(eq(id));
            Mockito.doReturn(expectedUser).when(forumDAO).getUserDAO(eq(id));

            // Call SUT
            User result = sut.addUser(id, name, telephone);

            // Verify results
            assertNotNull(result);
            assertEquals(id, result.getId());
            assertEquals(name, result.getName());
            assertNull(result.getTelephone());

            // Verify DAO method calls
            Mockito.verify(forumDAO, Mockito.times(1)).existUserDAO(eq(id));
            Mockito.verify(forumDAO, Mockito.times(1)).addUserDAO(eq(id), eq(name), eq(telephone));
            Mockito.verify(forumDAO, Mockito.times(1)).getUserDAO(eq(id));

        } catch (Exception e) {
            fail("Test should not throw unexpected exceptions: " + e.getMessage());
        }
    }

    // PARAMETERIZED TESTS for efficiency and comprehensive coverage

    @ParameterizedTest
    @DisplayName("Test 7: Various null parameter combinations - should throw NullParameterException")
    @MethodSource("provideNullParameterCombinations")
    void testAddUser_NullParameterCombinations_ThrowsNullParameterException(String id, String name, String telephone) {
        try {
            // Call SUT - should throw exception
            assertThrows(NullParameterException.class, () -> {
                sut.addUser(id, name, telephone);
            });

            // Verify no DAO methods were called
            Mockito.verify(forumDAO, Mockito.never()).existUserDAO(anyString());
            Mockito.verify(forumDAO, Mockito.never()).addUserDAO(anyString(), anyString(), anyString());
            Mockito.verify(forumDAO, Mockito.never()).getUserDAO(anyString());

        } catch (Exception e) {
            fail("Test should not throw unexpected exceptions: " + e.getMessage());
        }
    }

    private static Stream<Arguments> provideNullParameterCombinations() {
        return Stream.of(
            Arguments.of(null, null, "123456789"),
            Arguments.of(null, null, null),
            Arguments.of(null, "John", "123456789"),
            Arguments.of(null, "John", null),
            Arguments.of("12345678Z", null, "123456789"),
            Arguments.of("12345678Z", null, null)
        );
    }

    @ParameterizedTest
    @DisplayName("Test 8: Valid user data when user doesn't exist - should add user successfully")
    @MethodSource("provideValidUserData")
    void testAddUser_ValidUserDataUserNotExists_ReturnsUser(String id, String name, String telephone) {
        try {
            User expectedUser = new User(id, name, telephone);

            // Configure mock
            Mockito.doReturn(false).when(forumDAO).existUserDAO(eq(id));
            Mockito.doReturn(expectedUser).when(forumDAO).getUserDAO(eq(id));

            // Call SUT
            User result = sut.addUser(id, name, telephone);

            // Verify results
            assertNotNull(result);
            assertEquals(id, result.getId());
            assertEquals(name, result.getName());
            assertEquals(telephone, result.getTelephone());

            // Verify DAO method calls
            Mockito.verify(forumDAO, Mockito.times(1)).existUserDAO(eq(id));
            Mockito.verify(forumDAO, Mockito.times(1)).addUserDAO(eq(id), eq(name), eq(telephone));
            Mockito.verify(forumDAO, Mockito.times(1)).getUserDAO(eq(id));

        } catch (Exception e) {
            fail("Test should not throw unexpected exceptions: " + e.getMessage());
        }
    }

    private static Stream<Arguments> provideValidUserData() {
        return Stream.of(
            Arguments.of("12345678A", "Alice Johnson", "987654321"),
            Arguments.of("87654321B", "Bob Wilson", null),
            Arguments.of("11111111C", "Charlie Brown", "555555555"),
            Arguments.of("99999999D", "Diana Prince", ""),
            Arguments.of("ABC123DEF", "Eve Adams", "123-456-789")
        );
    }

    @ParameterizedTest
    @DisplayName("Test 9: Valid user data when user exists - should throw UserNotFoundException")
    @MethodSource("provideValidUserDataForExistingUser")
    void testAddUser_ValidUserDataUserExists_ThrowsUserNotFoundException(String id, String name, String telephone) {
        try {
            // Configure mock - user exists
            Mockito.doReturn(true).when(forumDAO).existUserDAO(eq(id));

            // Call SUT - should throw exception
            assertThrows(UserNotFoundException.class, () -> {
                sut.addUser(id, name, telephone);
            });

            // Verify DAO method calls
            Mockito.verify(forumDAO, Mockito.times(1)).existUserDAO(eq(id));
            Mockito.verify(forumDAO, Mockito.never()).addUserDAO(anyString(), anyString(), anyString());
            Mockito.verify(forumDAO, Mockito.never()).getUserDAO(anyString());

        } catch (Exception e) {
            fail("Test should not throw unexpected exceptions: " + e.getMessage());
        }
    }

    private static Stream<Arguments> provideValidUserDataForExistingUser() {
        return Stream.of(
            Arguments.of("75075708M", "Existing User", "123456789"),
            Arguments.of("12345678Z", "Another User", null),
            Arguments.of("ABCD1234E", "Third User", "987654321")
        );
    }

    // BOUNDARY VALUE ANALYSIS TESTS

    @Test
    @DisplayName("Test 10: Empty string parameters - should add user successfully")
    void testAddUser_EmptyStringParameters_ReturnsUser() {
        try {
            // Test parameters - empty strings are valid (not null)
            String id = "";
            String name = "";
            String telephone = "";
            User expectedUser = new User(id, name, telephone);

            // Configure mock
            Mockito.doReturn(false).when(forumDAO).existUserDAO(eq(id));
            Mockito.doReturn(expectedUser).when(forumDAO).getUserDAO(eq(id));

            // Call SUT
            User result = sut.addUser(id, name, telephone);

            // Verify results
            assertNotNull(result);
            assertEquals(id, result.getId());
            assertEquals(name, result.getName());
            assertEquals(telephone, result.getTelephone());

            // Verify DAO method calls
            Mockito.verify(forumDAO, Mockito.times(1)).existUserDAO(eq(id));
            Mockito.verify(forumDAO, Mockito.times(1)).addUserDAO(eq(id), eq(name), eq(telephone));
            Mockito.verify(forumDAO, Mockito.times(1)).getUserDAO(eq(id));

        } catch (Exception e) {
            fail("Test should not throw unexpected exceptions: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Test 11: Very long string parameters - should add user successfully")
    void testAddUser_VeryLongStringParameters_ReturnsUser() {
        try {
            // Test parameters - very long strings
            String id = "A".repeat(1000);
            String name = "B".repeat(1000);
            String telephone = "1".repeat(1000);
            User expectedUser = new User(id, name, telephone);

            // Configure mock
            Mockito.doReturn(false).when(forumDAO).existUserDAO(eq(id));
            Mockito.doReturn(expectedUser).when(forumDAO).getUserDAO(eq(id));

            // Call SUT
            User result = sut.addUser(id, name, telephone);

            // Verify results
            assertNotNull(result);
            assertEquals(id, result.getId());
            assertEquals(name, result.getName());
            assertEquals(telephone, result.getTelephone());

            // Verify DAO method calls
            Mockito.verify(forumDAO, Mockito.times(1)).existUserDAO(eq(id));
            Mockito.verify(forumDAO, Mockito.times(1)).addUserDAO(eq(id), eq(name), eq(telephone));
            Mockito.verify(forumDAO, Mockito.times(1)).getUserDAO(eq(id));

        } catch (Exception e) {
            fail("Test should not throw unexpected exceptions: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Test 12: Special characters in parameters - should add user successfully")
    void testAddUser_SpecialCharactersInParameters_ReturnsUser() {
        try {
            // Test parameters with special characters
            String id = "!@#$%^&*()_+-=[]{}|;':\",./<>?";
            String name = "José María O'Connor-Smith";
            String telephone = "+34-123-456-789";
            User expectedUser = new User(id, name, telephone);

            // Configure mock
            Mockito.doReturn(false).when(forumDAO).existUserDAO(eq(id));
            Mockito.doReturn(expectedUser).when(forumDAO).getUserDAO(eq(id));

            // Call SUT
            User result = sut.addUser(id, name, telephone);

            // Verify results
            assertNotNull(result);
            assertEquals(id, result.getId());
            assertEquals(name, result.getName());
            assertEquals(telephone, result.getTelephone());

            // Verify DAO method calls
            Mockito.verify(forumDAO, Mockito.times(1)).existUserDAO(eq(id));
            Mockito.verify(forumDAO, Mockito.times(1)).addUserDAO(eq(id), eq(name), eq(telephone));
            Mockito.verify(forumDAO, Mockito.times(1)).getUserDAO(eq(id));

        } catch (Exception e) {
            fail("Test should not throw unexpected exceptions: " + e.getMessage());
        }
    }
}