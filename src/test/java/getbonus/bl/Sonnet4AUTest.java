package getbonus.bl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import getbonus.db.ForumDAOInterface;
import getbonus.domain.User;
import getbonus.exceptions.NullParameterException;
import getbonus.exceptions.UserNotFoundException;





@DisplayName("ForumBL addUser method tests")
class Sonnet4AUTest {

    @Mock
    private ForumDAOInterface forumDAO;
    
    private ForumBL sut;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sut = new ForumBL(forumDAO);
    }

    @Test
    @DisplayName("Should throw NullParameterException when both id and name are null")
    void testAddUser_BothIdAndNameNull_ThrowsNullParameterException() {
        try {
            NullParameterException exception = assertThrows(NullParameterException.class, 
                () -> sut.addUser(null, null, "123456789"));
            
            assertEquals("id or name is null", exception.getMessage());
            
            verifyNoInteractions(forumDAO);
        } catch (Exception e) {
            fail("Unexpected exception thrown: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Should throw NullParameterException when id is null and name is valid")
    void testAddUser_IdNullNameValid_ThrowsNullParameterException() {
        try {
            NullParameterException exception = assertThrows(NullParameterException.class, 
                () -> sut.addUser(null, "John Doe", "123456789"));
            
            assertEquals("id or name is null", exception.getMessage());
            
            verifyNoInteractions(forumDAO);
        } catch (Exception e) {
            fail("Unexpected exception thrown: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Should throw NullParameterException when id is valid and name is null")
    void testAddUser_IdValidNameNull_ThrowsNullParameterException() {
        try {
            NullParameterException exception = assertThrows(NullParameterException.class, 
                () -> sut.addUser("75075708M", null, "123456789"));
            
            assertEquals("id or name is null", exception.getMessage());
            
            verifyNoInteractions(forumDAO);
        } catch (Exception e) {
            fail("Unexpected exception thrown: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Should throw UserNotFoundException when user already exists in database")
    void testAddUser_UserExists_ThrowsUserNotFoundException() {
        try {
            String id = "75075708M";
            String name = "John Doe";
            String tel = "123456789";
            
            when(forumDAO.existUserDAO(id)).thenReturn(true);
            
            UserNotFoundException exception = assertThrows(UserNotFoundException.class, 
                () -> sut.addUser(id, name, tel));
            
            assertEquals("id no in DB", exception.getMessage());
            
            verify(forumDAO, times(1)).existUserDAO(id);
            verify(forumDAO, never()).addUserDAO(anyString(), anyString(), anyString());
            verify(forumDAO, never()).getUserDAO(anyString());
        } catch (Exception e) {
            fail("Unexpected exception thrown: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Should successfully add user when user doesn't exist and return created user")
    void testAddUser_UserDoesNotExist_SuccessfullyAddsUser() {
        try {
            String id = "75075708M";
            String name = "John Doe";
            String tel = "123456789";
            User expectedUser = new User(id, name, tel);
            
            when(forumDAO.existUserDAO(id)).thenReturn(false);
            when(forumDAO.getUserDAO(id)).thenReturn(expectedUser);
            
            User result = sut.addUser(id, name, tel);
            
            assertNotNull(result);
            assertEquals(id, result.getId());
            assertEquals(name, result.getName());
            assertEquals(tel, result.getTelephone());
            
            verify(forumDAO, times(1)).existUserDAO(id);
            verify(forumDAO, times(1)).addUserDAO(id, name, tel);
            verify(forumDAO, times(1)).getUserDAO(id);
        } catch (Exception e) {
            fail("Unexpected exception thrown: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Should successfully add user with null telephone")
    void testAddUser_NullTelephone_SuccessfullyAddsUser() {
        try {
            String id = "12345678Z";
            String name = "Jane Smith";
            String tel = null;
            User expectedUser = new User(id, name, tel);
            
            when(forumDAO.existUserDAO(id)).thenReturn(false);
            when(forumDAO.getUserDAO(id)).thenReturn(expectedUser);
            
            User result = sut.addUser(id, name, tel);
            
            assertNotNull(result);
            assertEquals(id, result.getId());
            assertEquals(name, result.getName());
            assertNull(result.getTelephone());
            
            verify(forumDAO, times(1)).existUserDAO(id);
            verify(forumDAO, times(1)).addUserDAO(id, name, tel);
            verify(forumDAO, times(1)).getUserDAO(id);
        } catch (Exception e) {
            fail("Unexpected exception thrown: " + e.getMessage());
        }
    }

    @ParameterizedTest
    @CsvSource({
        "87654321A, Alice Johnson, 987654321",
        "11111111B, Bob Wilson, 555000111",
        "99999999C, Charlie Brown, 444333222"
    })
    @DisplayName("Should successfully add users with different valid parameters")
    void testAddUser_VariousValidParameters_SuccessfullyAddsUsers(String id, String name, String tel) {
        try {
            User expectedUser = new User(id, name, tel);
            
            when(forumDAO.existUserDAO(id)).thenReturn(false);
            when(forumDAO.getUserDAO(id)).thenReturn(expectedUser);
            
            User result = sut.addUser(id, name, tel);
            
            assertNotNull(result);
            assertEquals(id, result.getId());
            assertEquals(name, result.getName());
            assertEquals(tel, result.getTelephone());
            
            verify(forumDAO, times(1)).existUserDAO(id);
            verify(forumDAO, times(1)).addUserDAO(id, name, tel);
            verify(forumDAO, times(1)).getUserDAO(id);
        } catch (Exception e) {
            fail("Unexpected exception thrown: " + e.getMessage());
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  ", "\t", "\n"})
    @DisplayName("Should successfully add user with edge case valid id values")
    void testAddUser_EdgeCaseValidIds_SuccessfullyAddsUser(String id) {
        try {
            String name = "Test User";
            String tel = "123456789";
            User expectedUser = new User(id, name, tel);
            
            when(forumDAO.existUserDAO(id)).thenReturn(false);
            when(forumDAO.getUserDAO(id)).thenReturn(expectedUser);
            
            User result = sut.addUser(id, name, tel);
            
            assertNotNull(result);
            assertEquals(id, result.getId());
            assertEquals(name, result.getName());
            assertEquals(tel, result.getTelephone());
            
            verify(forumDAO, times(1)).existUserDAO(id);
            verify(forumDAO, times(1)).addUserDAO(id, name, tel);
            verify(forumDAO, times(1)).getUserDAO(id);
        } catch (Exception e) {
            fail("Unexpected exception thrown: " + e.getMessage());
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  ", "\t", "\n"})
    @DisplayName("Should successfully add user with edge case valid name values")
    void testAddUser_EdgeCaseValidNames_SuccessfullyAddsUser(String name) {
        try {
            String id = "75075708M";
            String tel = "123456789";
            User expectedUser = new User(id, name, tel);
            
            when(forumDAO.existUserDAO(id)).thenReturn(false);
            when(forumDAO.getUserDAO(id)).thenReturn(expectedUser);
            
            User result = sut.addUser(id, name, tel);
            
            assertNotNull(result);
            assertEquals(id, result.getId());
            assertEquals(name, result.getName());
            assertEquals(tel, result.getTelephone());
            
            verify(forumDAO, times(1)).existUserDAO(id);
            verify(forumDAO, times(1)).addUserDAO(id, name, tel);
            verify(forumDAO, times(1)).getUserDAO(id);
        } catch (Exception e) {
            fail("Unexpected exception thrown: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Should successfully add user with empty string telephone")
    void testAddUser_EmptyTelephone_SuccessfullyAddsUser() {
        try {
            String id = "75075708M";
            String name = "Test User";
            String tel = "";
            User expectedUser = new User(id, name, tel);
            
            when(forumDAO.existUserDAO(id)).thenReturn(false);
            when(forumDAO.getUserDAO(id)).thenReturn(expectedUser);
            
            User result = sut.addUser(id, name, tel);
            
            assertNotNull(result);
            assertEquals(id, result.getId());
            assertEquals(name, result.getName());
            assertEquals(tel, result.getTelephone());
            
            verify(forumDAO, times(1)).existUserDAO(id);
            verify(forumDAO, times(1)).addUserDAO(id, name, tel);
            verify(forumDAO, times(1)).getUserDAO(id);
        } catch (Exception e) {
            fail("Unexpected exception thrown: " + e.getMessage());
        }
    }
}