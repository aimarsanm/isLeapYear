// file: src/test/java/getbonus/bl/ForumBLTest.java
package getbonus.bl;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import getbonus.db.ForumDAOInterface;
import getbonus.exceptions.NullParameterException;
import getbonus.exceptions.UserNotFoundException;






@ExtendWith(MockitoExtension.class)
class GPTo4minipreAUTest {

    @Mock
    private ForumDAOInterface forumDAO;

    private ForumBL sut;

    @BeforeEach
    void setUp() {
        sut = new ForumBL(forumDAO);
    }

    static Stream<Arguments> nullIdNameProvider() {
        return Stream.of(
            Arguments.of(null, "Alice"),
            Arguments.of("75075708M", null),
            Arguments.of(null, null)
        );
    }

    @ParameterizedTest
    @MethodSource("nullIdNameProvider")
    @DisplayName("addUser throws NullParameterException when id or name is null")
    void addUser_nullIdOrName_throwsNPE(String id, String name) {
        assertThrows(NullParameterException.class, () -> sut.addUser(id, name, "12345"));
        verifyNoInteractions(forumDAO);
    }
/*
    @Test
    @DisplayName("addUser adds new user when id not in DB")
    void addUser_newUser_success() throws NullParameterException, UserNotFoundException {
        String id = "75075708M";
        String name = "John Doe";
        String tel = "555-1234";
        User expected = new User(id, name, tel);

        when(forumDAO.existUserDAO(id)).thenReturn(false);
        doNothing().when(forumDAO).addUserDAO(id, name, tel);
        when(forumDAO.getUserDAO(id)).thenReturn(expected);

        User result = sut.addUser(id, name, tel);

        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals(name, result.getName());
        assertEquals(tel, result.getTelephone());

        verify(forumDAO, times(1)).existUserDAO(id);
        verify(forumDAO, times(1)).addUserDAO(id, name, tel);
        verify(forumDAO, times(1)).getUserDAO(id);
    }
 */
    @Test
    @DisplayName("addUser throws UserNotFoundException when id already exists")
    void addUser_existingUser_throwsUNFE() {
        String id = "75075708M";
        String name = "Jane";
        String tel = "555-0000";

        when(forumDAO.existUserDAO(id)).thenReturn(true);

        assertThrows(UserNotFoundException.class, () -> sut.addUser(id, name, tel));

        verify(forumDAO, times(1)).existUserDAO(id);
        verify(forumDAO, never()).addUserDAO(anyString(), anyString(), anyString());
        verify(forumDAO, never()).getUserDAO(anyString());
    }
}