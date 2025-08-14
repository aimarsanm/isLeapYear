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
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import static org.mockito.ArgumentMatchers.any;
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

@ExtendWith(MockitoExtension.class)
class ForumBLAddUserSonnet37Test {

    @Mock
    private ForumDAOInterface forumDAO;
    
    private ForumBL sut; // System Under Test
    
    @BeforeEach
    void setUp() {
        sut = new ForumBL(forumDAO);
    }
    
    // WHITE BOX TESTING - Condition/Decision Coverage
    
    @Test
    @DisplayName("Test addUser when id is null - Should throw NullParameterException")
    void addUser_WhenIdIsNull_ShouldThrowNullParameterException() {
        // Arrange
        String id = null;
        String name = "John";
        String tel = "123456789";
        
        // Act & Assert
        NullParameterException exception = assertThrows(NullParameterException.class, () -> {
            sut.addUser(id, name, tel);
        });
        
        assertEquals("id or name is null", exception.getMessage());
        verify(forumDAO, never()).existUserDAO(any());
        verify(forumDAO, never()).addUserDAO(any(), any(), any());
        verify(forumDAO, never()).getUserDAO(any());
    }
    
    @Test
    @DisplayName("Test addUser when name is null - Should throw NullParameterException")
    void addUser_WhenNameIsNull_ShouldThrowNullParameterException() {
        // Arrange
        String id = "12345678A";
        String name = null;
        String tel = "123456789";
        
        // Act & Assert
        NullParameterException exception = assertThrows(NullParameterException.class, () -> {
            sut.addUser(id, name, tel);
        });
        
        assertEquals("id or name is null", exception.getMessage());
        verify(forumDAO, never()).existUserDAO(any());
        verify(forumDAO, never()).addUserDAO(any(), any(), any());
        verify(forumDAO, never()).getUserDAO(any());
    }
    
    @Test
    @DisplayName("Test addUser when both id and name are null - Should throw NullParameterException")
    void addUser_WhenBothIdAndNameAreNull_ShouldThrowNullParameterException() {
        // Arrange
        String id = null;
        String name = null;
        String tel = "123456789";
        
        // Act & Assert
        NullParameterException exception = assertThrows(NullParameterException.class, () -> {
            sut.addUser(id, name, tel);
        });
        
        assertEquals("id or name is null", exception.getMessage());
        verify(forumDAO, never()).existUserDAO(any());
        verify(forumDAO, never()).addUserDAO(any(), any(), any());
        verify(forumDAO, never()).getUserDAO(any());
    }
    
    @Test
    @DisplayName("Test addUser when user exists - Should throw UserNotFoundException")
    void addUser_WhenUserExists_ShouldThrowUserNotFoundException() {
        // Arrange
        String id = "12345678A";
        String name = "John";
        String tel = "123456789";
        
        when(forumDAO.existUserDAO(id)).thenReturn(true);
        
        // Act & Assert
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            sut.addUser(id, name, tel);
        });
        
        assertEquals("id no in DB", exception.getMessage());
        verify(forumDAO, times(1)).existUserDAO(id);
        verify(forumDAO, never()).addUserDAO(any(), any(), any());
        verify(forumDAO, never()).getUserDAO(any());
    }
    
    @Test
    @DisplayName("Test addUser when user doesn't exist - Should add user and return it")
    void addUser_WhenUserDoesNotExist_ShouldAddUserAndReturnIt() {
        try {
            // Arrange
            String id = "12345678A";
            String name = "John";
            String tel = "123456789";
            User expectedUser = new User(id, name, tel);
            
            when(forumDAO.existUserDAO(id)).thenReturn(false);
            when(forumDAO.getUserDAO(id)).thenReturn(expectedUser);
            
            // Act
            User actualUser = sut.addUser(id, name, tel);
            
            // Assert
            assertNotNull(actualUser);
            assertEquals(expectedUser, actualUser);
            
            // Verify DAO interactions
            verify(forumDAO, times(1)).existUserDAO(id);
            verify(forumDAO, times(1)).addUserDAO(id, name, tel);
            verify(forumDAO, times(1)).getUserDAO(id);
        } catch (Exception e) {
            fail("If any exception is thrown, it is not working properly");
        }
    }
    
    @Test
    @DisplayName("Test addUser when telephone is null - Should add user with null telephone")
    void addUser_WhenTelephoneIsNull_ShouldAddUserWithNullTelephone() {
        try {
            // Arrange
            String id = "12345678A";
            String name = "John";
            String tel = null;
            User expectedUser = new User(id, name, tel);
            
            when(forumDAO.existUserDAO(id)).thenReturn(false);
            when(forumDAO.getUserDAO(id)).thenReturn(expectedUser);
            
            // Act
            User actualUser = sut.addUser(id, name, tel);
            
            // Assert
            assertNotNull(actualUser);
            assertEquals(expectedUser, actualUser);
            assertNull(actualUser.getTelephone());
            
            // Verify DAO interactions
            verify(forumDAO, times(1)).existUserDAO(id);
            verify(forumDAO, times(1)).addUserDAO(id, name, tel);
            verify(forumDAO, times(1)).getUserDAO(id);
        } catch (Exception e) {
            fail("If any exception is thrown, it is not working properly");
        }
    }
    
    // BLACK BOX TESTING - Equivalence Partitioning and Boundary Value Analysis
    
    @ParameterizedTest
    @DisplayName("Test addUser with valid inputs - Should add user successfully")
    @MethodSource("validUserInputProvider")
    void addUser_WithValidInputs_ShouldAddUserSuccessfully(String id, String name, String tel) {
        try {
            // Arrange
            User expectedUser = new User(id, name, tel);
            
            when(forumDAO.existUserDAO(id)).thenReturn(false);
            when(forumDAO.getUserDAO(id)).thenReturn(expectedUser);
            
            // Act
            User actualUser = sut.addUser(id, name, tel);
            
            // Assert
            assertNotNull(actualUser);
            assertEquals(id, actualUser.getId());
            assertEquals(name, actualUser.getName());
            assertEquals(tel, actualUser.getTelephone());
            
            // Verify DAO interactions
            verify(forumDAO, times(1)).existUserDAO(id);
            verify(forumDAO, times(1)).addUserDAO(id, name, tel);
            verify(forumDAO, times(1)).getUserDAO(id);
        } catch (Exception e) {
            fail("If any exception is thrown, it is not working properly: " + e.getMessage());
        }
    }
    
    static Stream<Arguments> validUserInputProvider() {
        return Stream.of(
            Arguments.of("12345678A", "John Doe", "123456789"), // Normal case
            Arguments.of("00000000T", "Jane Doe", ""), // Empty telephone
            Arguments.of("99999999R", "Min Name", null), // Null telephone
            Arguments.of("12345678Z", "Max Name Length User With Very Long Name For Testing", "999999999") // Long name
        );
    }
    /* 
    @ParameterizedTest
    @DisplayName("Test addUser with invalid id - Should throw NullParameterException")
    @NullAndEmptySource
    void addUser_WithInvalidId_ShouldThrowNullParameterException(String id) {
        // Arrange
        String name = "John";
        String tel = "123456789";
        
        // Act & Assert
        NullParameterException exception = assertThrows(NullParameterException.class, () -> {
            sut.addUser(id, name, tel);
        });
        
        assertEquals("id or name is null", exception.getMessage());
    }
    
    @ParameterizedTest
    @DisplayName("Test addUser with invalid name - Should throw NullParameterException")
    @NullAndEmptySource
    void addUser_WithInvalidName_ShouldThrowNullParameterException(String name) {
        // Arrange
        String id = "12345678A";
        String tel = "123456789";
        
        // Act & Assert
        NullParameterException exception = assertThrows(NullParameterException.class, () -> {
            sut.addUser(id, name, tel);
        });
        
        assertEquals("id or name is null", exception.getMessage());
    }
    */
    @Test
    @DisplayName("Test addUser with correct parameter capture - Verify DAO method calls")
    void addUser_VerifyParameterCapture_ShouldCallDAOMethodsWithCorrectParameters() {
        try {
            // Arrange
            String id = "12345678A";
            String name = "John";
            String tel = "123456789";
            User expectedUser = new User(id, name, tel);
            
            when(forumDAO.existUserDAO(id)).thenReturn(false);
            when(forumDAO.getUserDAO(id)).thenReturn(expectedUser);
            
            // Act
            User actualUser = sut.addUser(id, name, tel);
            
            // Assert
            assertNotNull(actualUser);
            
            // Verify parameters with ArgumentCaptor
            ArgumentCaptor<String> idCaptor = ArgumentCaptor.forClass(String.class);
            ArgumentCaptor<String> nameCaptor = ArgumentCaptor.forClass(String.class);
            ArgumentCaptor<String> telCaptor = ArgumentCaptor.forClass(String.class);
            
            verify(forumDAO).addUserDAO(idCaptor.capture(), nameCaptor.capture(), telCaptor.capture());
            
            assertEquals(id, idCaptor.getValue());
            assertEquals(name, nameCaptor.getValue());
            assertEquals(tel, telCaptor.getValue());
        } catch (Exception e) {
            fail("If any exception is thrown, it is not working properly");
        }
    }
}