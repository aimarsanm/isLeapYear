package getbonus.bl;

import getbonus.db.ForumDAOInterface;
import getbonus.domain.User;
import getbonus.exceptions.NullParameterException;
import getbonus.exceptions.UserNotFoundException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

@DisplayName("Integration tests for ForumBL.addUser using White Box and Black Box methods")
class ForumBLCAuTest {

    private ForumDAOInterface forumDAO;
    private ForumBL sut;

    @BeforeEach
    void setUp() {
        forumDAO = Mockito.mock(ForumDAOInterface.class);
        sut = new ForumBL(forumDAO);
    }

    // --- White Box: Condition/Decision Coverage ---

    @ParameterizedTest
    @CsvSource({
            "null,validName,123456789", // id null
            "validId,null,123456789"    // name null
    })
    @DisplayName("Should throw NullParameterException when id or name is null")
    void testAddUser_NullIdOrName_ThrowsNullParameterException(String id, String name, String tel) {
        if ("null".equals(id)) id = null;
        if ("null".equals(name)) name = null;
        try {
            sut.addUser(id, name, tel);
            fail("Expected NullParameterException was not thrown");
        } catch (NullParameterException e) {
            assertEquals("id or name is null", e.getMessage());
        } catch (Exception e) {
            fail("Unexpected exception type: " + e);
        }
        Mockito.verifyNoInteractions(forumDAO);
    }

    @ParameterizedTest
    @CsvSource({
            "user1,John,123456789",
            "user2,Ana,987654321"
    })
    @DisplayName("Should add and return new user when user does not exist in DB")
    void testAddUser_UserDoesNotExist_AddsAndReturnsUser(String id, String name, String tel) {
        try {
            Mockito.doReturn(false).when(forumDAO).existUserDAO(id);
            User expectedUser = new User(id, name, tel);
            Mockito.doReturn(expectedUser).when(forumDAO).getUserDAO(id);

            User result = sut.addUser(id, name, tel);

            assertNotNull(result);
            assertEquals(id, result.getId());
            assertEquals(name, result.getName());
            assertEquals(tel, result.getTelephone());

            Mockito.verify(forumDAO, Mockito.times(1)).existUserDAO(id);
            Mockito.verify(forumDAO, Mockito.times(1)).addUserDAO(id, name, tel);
            Mockito.verify(forumDAO, Mockito.times(1)).getUserDAO(id);
        } catch (Exception e) {
            fail("No exception should be thrown, but got: " + e);
        }
    }

    @ParameterizedTest
    @CsvSource({
            "user1,John,123456789",
            "user2,Ana,987654321"
    })
    @DisplayName("Should throw UserNotFoundException when user already exists in DB")
    void testAddUser_UserExists_ThrowsUserNotFoundException(String id, String name, String tel) {
        try {
            Mockito.doReturn(true).when(forumDAO).existUserDAO(id);

            sut.addUser(id, name, tel);
            fail("Expected UserNotFoundException was not thrown");
        } catch (UserNotFoundException e) {
            assertEquals("id no in DB", e.getMessage());
            Mockito.verify(forumDAO, Mockito.times(1)).existUserDAO(id);
            Mockito.verify(forumDAO, Mockito.never()).addUserDAO(anyString(), anyString(), any());
            Mockito.verify(forumDAO, Mockito.never()).getUserDAO(anyString());
        } catch (Exception e) {
            fail("Unexpected exception type: " + e);
        }
    }

    // --- Black Box: Equivalence Partitioning & Boundary Value Analysis ---

    @ParameterizedTest
    @CsvSource({
            "user3,ValidName,555555555", // valid
            "user4,ValidName,''"         // valid, tel is empty
    })
    @DisplayName("Should add user for valid id and name (boundary and equivalence)")
    void testAddUser_ValidInputs_BoundaryAndEquivalence(String id, String name, String tel) {
        try {
            Mockito.doReturn(false).when(forumDAO).existUserDAO(id);
            User expectedUser = new User(id, name, tel);
            Mockito.doReturn(expectedUser).when(forumDAO).getUserDAO(id);

            User result = sut.addUser(id, name, tel);

            assertNotNull(result);
            assertEquals(id, result.getId());
            assertEquals(name, result.getName());
            assertEquals(tel, result.getTelephone());

            Mockito.verify(forumDAO, Mockito.times(1)).existUserDAO(id);
            Mockito.verify(forumDAO, Mockito.times(1)).addUserDAO(id, name, tel);
            Mockito.verify(forumDAO, Mockito.times(1)).getUserDAO(id);
        } catch (Exception e) {
            fail("No exception should be thrown, but got: " + e);
        }
    }

    @ParameterizedTest
    @CsvSource({
            "null,ValidName,555555555", // id null
            "ValidId,null,555555555"    // name null
    })
    @DisplayName("Should throw NullParameterException for null id or name (black box equivalence)")
    void testAddUser_NullInputs_BlackBox(String id, String name, String tel) {
        if ("null".equals(id)) id = null;
        if ("null".equals(name)) name = null;
        try {
            sut.addUser(id, name, tel);
            fail("Expected NullParameterException was not thrown");
        } catch (NullParameterException e) {
            assertEquals("id or name is null", e.getMessage());
        } catch (Exception e) {
            fail("Unexpected exception type: " + e);
        }
        Mockito.verifyNoInteractions(forumDAO);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(forumDAO);
    }
}