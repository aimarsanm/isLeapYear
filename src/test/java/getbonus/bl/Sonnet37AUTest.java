package getbonus.bl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import getbonus.db.ForumDAOInterface;
import getbonus.domain.User;
import getbonus.exceptions.NullParameterException;
import getbonus.exceptions.UserNotFoundException;
import java.util.stream.Stream;






@ExtendWith(MockitoExtension.class)
class Sonnet37AUTest {

    @Mock
    private ForumDAOInterface mockDao;
    
    private ForumBL forumBL;
    
    @BeforeEach
    void setUp() {
        forumBL = new ForumBL(mockDao);
    }
    
    // White Box Testing - Condition/Decision Coverage

    @Test
    @DisplayName("When id and name are valid and user doesn't exist, user should be added successfully")
    void addUser_ValidInputsAndUserDoesNotExist_ShouldAddUser() {
        // Arrange
        String id = "12345678A";
        String name = "John Doe";
        String tel = "123456789";
        User expectedUser = new User(id, name, tel);
        
        when(mockDao.existUserDAO(id)).thenReturn(false);
        when(mockDao.getUserDAO(id)).thenReturn(expectedUser);
        
        // Act
        User actualUser = assertDoesNotThrow(() -> forumBL.addUser(id, name, tel));
        
        // Assert
        assertEquals(expectedUser, actualUser);
        
        // Verify DAO methods were called correctly
        verify(mockDao).existUserDAO(id);
        verify(mockDao).addUserDAO(id, name, tel);
        verify(mockDao).getUserDAO(id);
    }
    
    @Test
    @DisplayName("When id and name are valid but user exists, UserNotFoundException should be thrown")
    void addUser_ValidInputsButUserExists_ShouldThrowUserNotFoundException() {
        // Arrange
        String id = "12345678A";
        String name = "John Doe";
        String tel = "123456789";
        
        when(mockDao.existUserDAO(id)).thenReturn(true);
        
        // Act & Assert
        UserNotFoundException exception = assertThrows(
            UserNotFoundException.class,
            () -> forumBL.addUser(id, name, tel)
        );
        
        assertEquals("id no in DB", exception.getMessage());
        
        // Verify DAO methods
        verify(mockDao).existUserDAO(id);
        verify(mockDao, never()).addUserDAO(anyString(), anyString(), anyString());
        verify(mockDao, never()).getUserDAO(anyString());
    }
    
    @ParameterizedTest
    @MethodSource("nullParameterProvider")
    @DisplayName("When any required parameter is null, NullParameterException should be thrown")
    void addUser_NullParameters_ShouldThrowNullParameterException(String id, String name, String tel) {
        // Act & Assert
        NullParameterException exception = assertThrows(
            NullParameterException.class,
            () -> forumBL.addUser(id, name, tel)
        );
        
        assertEquals("id or name is null", exception.getMessage());
        
        // Verify no DAO methods were called for null parameters
        verify(mockDao, never()).existUserDAO(anyString());
        verify(mockDao, never()).addUserDAO(anyString(), anyString(), anyString());
        verify(mockDao, never()).getUserDAO(anyString());
    }
    
    static Stream<Arguments> nullParameterProvider() {
        return Stream.of(
            Arguments.of(null, "John Doe", "123456789"), // null id
            Arguments.of("12345678A", null, "123456789"), // null name
            Arguments.of(null, null, "123456789")         // both null
        );
    }
    
    // Black Box Testing - Equivalence Partitioning
    
    @ParameterizedTest
    @ValueSource(strings = {"12345678A", "87654321B", "11111111C"})
    @DisplayName("Adding user with different valid IDs should succeed")
    void addUser_DifferentValidIds_ShouldSucceed(String id) {
        // Arrange
        String name = "Test User";
        String tel = "123456789";
        User expectedUser = new User(id, name, tel);
        
        when(mockDao.existUserDAO(id)).thenReturn(false);
        when(mockDao.getUserDAO(id)).thenReturn(expectedUser);
        
        // Act
        User actualUser = assertDoesNotThrow(() -> forumBL.addUser(id, name, tel));
        
        // Assert
        assertEquals(expectedUser, actualUser);
        
        // Verify DAO interactions
        verify(mockDao).existUserDAO(id);
        verify(mockDao).addUserDAO(id, name, tel);
        verify(mockDao).getUserDAO(id);
    }
    
    @ParameterizedTest
    @ValueSource(strings = {"John", "Jane", "Alex"})
    @DisplayName("Adding user with different valid names should succeed")
    void addUser_DifferentValidNames_ShouldSucceed(String name) {
        // Arrange
        String id = "12345678A";
        String tel = "123456789";
        User expectedUser = new User(id, name, tel);
        
        when(mockDao.existUserDAO(id)).thenReturn(false);
        when(mockDao.getUserDAO(id)).thenReturn(expectedUser);
        
        // Act
        User actualUser = assertDoesNotThrow(() -> forumBL.addUser(id, name, tel));
        
        // Assert
        assertEquals(expectedUser, actualUser);
        
        // Verify DAO interactions
        verify(mockDao).existUserDAO(id);
        verify(mockDao).addUserDAO(id, name, tel);
        verify(mockDao).getUserDAO(id);
    }
    
    @ParameterizedTest
    @ValueSource(strings = {"123456789", "987654321", ""})
    @NullAndEmptySource
    @DisplayName("Adding user with different telephone values (including null and empty) should succeed")
    void addUser_DifferentTelephoneValues_ShouldSucceed(String tel) {
        // Arrange
        String id = "12345678A";
        String name = "Test User";
        User expectedUser = new User(id, name, tel);
        
        when(mockDao.existUserDAO(id)).thenReturn(false);
        when(mockDao.getUserDAO(id)).thenReturn(expectedUser);
        
        // Act
        User actualUser = assertDoesNotThrow(() -> forumBL.addUser(id, name, tel));
        
        // Assert
        assertEquals(expectedUser, actualUser);
        
        // Verify DAO interactions
        verify(mockDao).existUserDAO(id);
        verify(mockDao).addUserDAO(id, name, tel);
        verify(mockDao).getUserDAO(id);
    }
    
    // Black Box Testing - Boundary Value Analysis
    
    @Test
    @DisplayName("Adding user with empty string values for id and name should succeed")
    void addUser_EmptyStringValues_ShouldSucceed() {
        // Arrange
        String id = "";
        String name = "";
        String tel = "";
        User expectedUser = new User(id, name, tel);
        
        when(mockDao.existUserDAO(id)).thenReturn(false);
        when(mockDao.getUserDAO(id)).thenReturn(expectedUser);
        
        // Act
        User actualUser = assertDoesNotThrow(() -> forumBL.addUser(id, name, tel));
        
        // Assert
        assertEquals(expectedUser, actualUser);
        
        // Verify DAO interactions
        verify(mockDao).existUserDAO(id);
        verify(mockDao).addUserDAO(id, name, tel);
        verify(mockDao).getUserDAO(id);
    }
    
    @Test
    @DisplayName("Verify correct parameters are passed to DAO methods")
    void addUser_VerifyCorrectParametersPassedToDAO() {
        // Arrange
        String id = "12345678A";
        String name = "Test User";
        String tel = "123456789";
        User expectedUser = new User(id, name, tel);
        
        when(mockDao.existUserDAO(id)).thenReturn(false);
        when(mockDao.getUserDAO(id)).thenReturn(expectedUser);
        
        // Setup argument captors
        ArgumentCaptor<String> idCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> nameCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> telCaptor = ArgumentCaptor.forClass(String.class);
        
        // Act
        User actualUser = assertDoesNotThrow(() -> forumBL.addUser(id, name, tel));
        
        // Assert
        verify(mockDao).addUserDAO(idCaptor.capture(), nameCaptor.capture(), telCaptor.capture());
        
        assertEquals(id, idCaptor.getValue());
        assertEquals(name, nameCaptor.getValue());
        assertEquals(tel, telCaptor.getValue());
        assertEquals(expectedUser, actualUser);
    }
}