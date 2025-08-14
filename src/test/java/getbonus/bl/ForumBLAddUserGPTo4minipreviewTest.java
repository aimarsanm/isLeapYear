package getbonus.bl;

import static org.junit.jupiter.api.Assertions.*;
import java.util.stream.Stream;

import getbonus.db.ForumDAOInterface;
import getbonus.domain.User;
import getbonus.exceptions.NullParameterException;
import getbonus.exceptions.UserNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ForumBLAddUserGPTo4minipreviewTest {

    @Mock
    private ForumDAOInterface dao;

    private ForumBL sut;

    @BeforeEach
    void setUp() {
        sut = new ForumBL(dao);
    }

    static Stream<Arguments> nullParamsProvider() {
        return Stream.of(
            Arguments.of(null, null),
            Arguments.of(null, "Alice"),
            Arguments.of("12345X", null)
        );
    }

    @ParameterizedTest
    @MethodSource("nullParamsProvider")
    @DisplayName("addUser throws NullParameterException if id or name is null")
    void addUser_NullIdOrName_ThrowsNullParameterException(String id, String name) {
        assertThrows(
            NullParameterException.class,
            () -> sut.addUser(id, name, "555-1234"),
            "Expected NullParameterException when id or name is null"
        );
    }

    @Test
    @DisplayName("addUser throws UserNotFoundException when DAO reports user already exists")
    void addUser_ExistingId_ThrowsUserNotFoundException() {
        String id = "EXIST123";
        String name = "Bob";
        String tel = "800-2000";

        // DAO says user exists
        Mockito.when(dao.existUserDAO(id)).thenReturn(true);

        assertThrows(
            UserNotFoundException.class,
            () -> sut.addUser(id, name, tel),
            "Expected UserNotFoundException when DAO.existUserDAO returns true"
        );

        // verify no add or get calls on DAO
        Mockito.verify(dao, Mockito.times(1)).existUserDAO(id);
        Mockito.verify(dao, Mockito.never()).addUserDAO(Mockito.anyString(), Mockito.anyString(), Mockito.any());
        Mockito.verify(dao, Mockito.never()).getUserDAO(Mockito.anyString());
    }
/* 
    @Test
    @DisplayName("addUser returns non-null User when DAO reports id missing")
    void addUser_NewId_ReturnsNonNullUser() {
        String id = "NEW123";
        String name = "Carol";
        String tel = null; // telephone may be null

        User mockUser = prepareSuccess(id, name, tel);

        User result = null;
        try {
            result = sut.addUser(id, name, tel);
        } catch (Exception e) {
            fail("No exception expected, but got: " + e.getMessage());
        }
        assertNotNull(result, "Returned User must not be null");
    }

    @Test
    @DisplayName("addUser returns User with correct id when DAO reports id missing")
    void addUser_NewId_ReturnsUserWithCorrectId() {
        String id = "NEW123";
        String name = "Carol";
        String tel = null;

        User mockUser = prepareSuccess(id, name, tel);

        User result = null;
        try {
            result = sut.addUser(id, name, tel);
        } catch (Exception e) {
            fail("No exception expected, but got: " + e.getMessage());
        }
        assertEquals(id, result.getId(), "User.id must match input id");
    }

    @Test
    @DisplayName("addUser returns User with correct name when DAO reports id missing")
    void addUser_NewId_ReturnsUserWithCorrectName() {
        String id = "NEW123";
        String name = "Carol";
        String tel = null;

        User mockUser = prepareSuccess(id, name, tel);

        User result = null;
        try {
            result = sut.addUser(id, name, tel);
        } catch (Exception e) {
            fail("No exception expected, but got: " + e.getMessage());
        }
        assertEquals(name, result.getName(), "User.name must match input name");
    }

    @Test
    @DisplayName("addUser returns User with correct telephone when DAO reports id missing")
    void addUser_NewId_ReturnsUserWithCorrectTelephone() {
        String id = "NEW123";
        String name = "Carol";
        String tel = null;

        User mockUser = prepareSuccess(id, name, tel);

        User result = null;
        try {
            result = sut.addUser(id, name, tel);
        } catch (Exception e) {
            fail("No exception expected, but got: " + e.getMessage());
        }
        assertNull(result.getTelephone(), "User.telephone must match input telephone (null)");
    }
*/
    /**
     * Common setup for the "new user" success path:
     * configure DAO.existUserDAO → false,
     * DAO.addUserDAO → do nothing,
     * DAO.getUserDAO → return a User(id,name,tel).
     */
    private User prepareSuccess(String id, String name, String tel) {
        User u = new User(id, name, tel);
        Mockito.when(dao.existUserDAO(id)).thenReturn(false);
        Mockito.doNothing().when(dao).addUserDAO(id, name, tel);
        Mockito.when(dao.getUserDAO(id)).thenReturn(u);
        return u;
    }
}