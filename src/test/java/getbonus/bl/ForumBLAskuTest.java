package getbonus.bl;

import getbonus.db.ForumDAOInterface;
import getbonus.domain.User;
import getbonus.exceptions.NullParameterException;
import getbonus.exceptions.UserNotFoundException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

@DisplayName("ForumBL.addUser Integration Tests")
class ForumBLAskuTest {

    private ForumDAOInterface forumDAO;
    private ForumBL forumBL;

    @BeforeEach
    void setUp() {
        forumDAO = Mockito.mock(ForumDAOInterface.class);
        forumBL = new ForumBL(forumDAO);
    }

    // --- WHITE BOX: Condition/Decision Coverage ---

    @ParameterizedTest
    @CsvSource({
            "null,John,123456789", // id null
            "75075708M,null,123456789" // name null
    })
    @DisplayName("addUser throws NullParameterException when id or name is null")
    void testAddUser_NullParameterException(String id, String name, String tel) {
        if ("null".equals(id)) id = null;
        if ("null".equals(name)) name = null;
        try {
            forumBL.addUser(id, name, tel);
            fail("Expected NullParameterException was not thrown");
        } catch (NullParameterException e) {
            assertEquals("id or name is null", e.getMessage());
        } catch (Exception e) {
            fail("Unexpected exception type: " + e);
        }
        Mockito.verify(forumDAO, Mockito.never()).existUserDAO(anyString());
        Mockito.verify(forumDAO, Mockito.never()).addUserDAO(anyString(), anyString(), anyString());
        Mockito.verify(forumDAO, Mockito.never()).getUserDAO(anyString());
    }
/* 
    @Test
    @DisplayName("addUser adds and returns user when user does not exist (happy path)")
    void testAddUser_UserDoesNotExist_AddsAndReturnsUser() {
        String id = "75075708M";
        String name = "John";
        String tel = "123456789";
        User expectedUser = new User(id, name, tel);

        Mockito.doReturn(false).when(forumDAO).existUserDAO(id);
        Mockito.doNothing().when(forumDAO).addUserDAO(id, name, tel);
        Mockito.doReturn(expectedUser).when(forumDAO).getUserDAO(id);

        try {
            User result = forumBL.addUser(id, name, tel);
            assertNotNull(result);
            assertEquals(expectedUser.getId(), result.getId());
            assertEquals(expectedUser.getName(), result.getName());
            assertEquals(expectedUser.getTelephone(), result.getTelephone());
        } catch (Exception e) {
            fail("No exception expected, but got: " + e);
        }
        Mockito.verify(forumDAO, Mockito.times(1)).existUserDAO(id);
        Mockito.verify(forumDAO, Mockito.times(1)).addUserDAO(id, name, tel);
        Mockito.verify(forumDAO, Mockito.times(1)).getUserDAO(id);
    }
*/
    @Test
    @DisplayName("addUser throws UserNotFoundException when user already exists")
    void testAddUser_UserAlreadyExists_ThrowsUserNotFoundException() {
        String id = "75075708M";
        String name = "John";
        String tel = "123456789";

        Mockito.doReturn(true).when(forumDAO).existUserDAO(id);

        try {
            forumBL.addUser(id, name, tel);
            fail("Expected UserNotFoundException was not thrown");
        } catch (UserNotFoundException e) {
            assertEquals("id no in DB", e.getMessage());
        } catch (Exception e) {
            fail("Unexpected exception type: " + e);
        }
        Mockito.verify(forumDAO, Mockito.times(1)).existUserDAO(id);
        Mockito.verify(forumDAO, Mockito.never()).addUserDAO(anyString(), anyString(), anyString());
        Mockito.verify(forumDAO, Mockito.never()).getUserDAO(anyString());
    }

    // --- BLACK BOX: Equivalence Partitioning & Boundary Value Analysis ---
/*
    @ParameterizedTest
    @CsvSource({
            "A,John,123456789", // id minimum length (boundary)
            "75075708M,John,123456789", // id typical valid
            "75075708M,John,''", // tel null (allowed)
            "75075708M,John, '' " // tel empty string (allowed)
    })
    @DisplayName("addUser accepts valid id and name, various tel values")
    void testAddUser_ValidInputs(String id, String name, String tel) {
        if ("''".equals(tel) || tel == null) tel = null;
        User expectedUser = new User(id, name, tel);

        Mockito.doReturn(false).when(forumDAO).existUserDAO(id);
        Mockito.doNothing().when(forumDAO).addUserDAO(id, name, tel);
        Mockito.doReturn(expectedUser).when(forumDAO).getUserDAO(id);

        try {
            User result = forumBL.addUser(id, name, tel);
            assertNotNull(result);
            assertEquals(expectedUser.getId(), result.getId());
            assertEquals(expectedUser.getName(), result.getName());
            assertEquals(expectedUser.getTelephone(), result.getTelephone());
        } catch (Exception e) {
            fail("No exception expected, but got: " + e);
        }
        Mockito.verify(forumDAO, Mockito.times(1)).existUserDAO(id);
        Mockito.verify(forumDAO, Mockito.times(1)).addUserDAO(id, name, tel);
        Mockito.verify(forumDAO, Mockito.times(1)).getUserDAO(id);
    }
*/
    @ParameterizedTest
    @CsvSource({
            "null,John,123456789", // id null
            "75075708M,null,123456789", // name null
            "null,null,123456789" // both null
    })
    @DisplayName("addUser throws NullParameterException for invalid id or name (black box)")
    void testAddUser_InvalidInputs_NullParameterException(String id, String name, String tel) {
        if ("null".equals(id)) id = null;
        if ("null".equals(name)) name = null;
        try {
            forumBL.addUser(id, name, tel);
            fail("Expected NullParameterException was not thrown");
        } catch (NullParameterException e) {
            assertEquals("id or name is null", e.getMessage());
        } catch (Exception e) {
            fail("Unexpected exception type: " + e);
        }
        Mockito.verify(forumDAO, Mockito.never()).existUserDAO(anyString());
        Mockito.verify(forumDAO, Mockito.never()).addUserDAO(anyString(), anyString(), anyString());
        Mockito.verify(forumDAO, Mockito.never()).getUserDAO(anyString());
    }
/*
    @Test
    @DisplayName("addUser boundary: id and name are single character")
    void testAddUser_Boundary_IdAndNameSingleChar() {
        String id = "A";
        String name = "B";
        String tel = "123";
        User expectedUser = new User(id, name, tel);

        Mockito.doReturn(false).when(forumDAO).existUserDAO(id);
        Mockito.doNothing().when(forumDAO).addUserDAO(id, name, tel);
        Mockito.doReturn(expectedUser).when(forumDAO).getUserDAO(id);

        try {
            User result = forumBL.addUser(id, name, tel);
            assertNotNull(result);
            assertEquals(id, result.getId());
            assertEquals(name, result.getName());
            assertEquals(tel, result.getTelephone());
        } catch (Exception e) {
            fail("No exception expected, but got: " + e);
        }
        Mockito.verify(forumDAO, Mockito.times(1)).existUserDAO(id);
        Mockito.verify(forumDAO, Mockito.times(1)).addUserDAO(id, name, tel);
        Mockito.verify(forumDAO, Mockito.times(1)).getUserDAO(id);
    }*/

    @AfterEach
    void tearDown() {
        forumDAO = null;
        forumBL = null;
    }
}