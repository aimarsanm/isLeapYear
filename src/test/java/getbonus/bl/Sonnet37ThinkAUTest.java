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
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import getbonus.db.ForumDAOInterface;
import getbonus.domain.User;
import getbonus.exceptions.NullParameterException;
import getbonus.exceptions.UserNotFoundException;

import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
class Sonnet37ThinkAUTest {

    @Mock
    private ForumDAOInterface forumDAO;

    private ForumBL forumBL;

    @BeforeEach
    void setup() {
        forumBL = new ForumBL(forumDAO);
    }

    // WHITE BOX TESTING: CONDITION/DECISION COVERAGE
    
    @ParameterizedTest
    @DisplayName("Should throw NullParameterException when id or name is null")
    @MethodSource("nullIdOrNameParameters")
    void shouldThrowExceptionWhenIdOrNameIsNull(String id, String name, String tel) {
        // Act & Assert
        NullParameterException exception = assertThrows(NullParameterException.class, () -> {
            forumBL.addUser(id, name, tel);
        });
        
        assertEquals("id or name is null", exception.getMessage());
        
        // Verify no interactions with DAO
        verifyNoInteractions(forumDAO);
    }

    private static Stream<Arguments> nullIdOrNameParameters() {
        return Stream.of(
            // Truth table for condition "id == null || name == null"
            // id == null, name != null -> true
            Arguments.of(null, "John Doe", "123456789"),
            // id != null, name == null -> true
            Arguments.of("12345678A", null, "123456789"),
            // id == null, name == null -> true
            Arguments.of(null, null, "123456789")
        );
    }

    @Test
    @DisplayName("Should throw UserNotFoundException when user already exists")
    void shouldThrowExceptionWhenUserExists() {
        // Arrange
        String id = "12345678A";
        String name = "John Doe";
        String tel = "123456789";
        
        // Mock the existUserDAO to return true (user exists)
        when(forumDAO.existUserDAO(id)).thenReturn(true);

        // Act & Assert
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            forumBL.addUser(id, name, tel);
        });
        
        assertEquals("id no in DB", exception.getMessage());
        
        // Verify interactions
        verify(forumDAO, times(1)).existUserDAO(id);
        verify(forumDAO, never()).addUserDAO(anyString(), anyString(), anyString());
        verify(forumDAO, never()).getUserDAO(anyString());
    }

    // BLACK BOX TESTING: EQUIVALENCE PARTITIONING AND BOUNDARY VALUE ANALYSIS
    
    @ParameterizedTest
    @DisplayName("Should add user and return it when parameters are valid and user does not exist")
    @MethodSource("validUserParameters")
    void shouldAddUserAndReturnItWhenParametersAreValidAndUserDoesNotExist(String id, String name, String tel) {
        // Arrange
        User expectedUser = new User(id, name, tel);
        
        // Mock DAO behavior
        when(forumDAO.existUserDAO(id)).thenReturn(false);
        when(forumDAO.getUserDAO(id)).thenReturn(expectedUser);

        // Act
        User result = null;
        try {
            result = forumBL.addUser(id, name, tel);
        } catch (Exception e) {
            fail("Should not throw exception: " + e.getMessage());
        }

        // Assert
        assertNotNull(result);
        assertEquals(expectedUser, result);
        
        // Verify interactions
        verify(forumDAO, times(1)).existUserDAO(id);
        verify(forumDAO, times(1)).addUserDAO(id, name, tel);
        verify(forumDAO, times(1)).getUserDAO(id);
    }

    private static Stream<Arguments> validUserParameters() {
        return Stream.of(
            // Equivalence partitioning - valid inputs
            Arguments.of("12345678A", "John Doe", "123456789"),  // All valid
            Arguments.of("12345678A", "John Doe", null),         // Valid with null telephone
            
            // Boundary value analysis
            Arguments.of("", "John Doe", "123456789"),          // Empty id
            Arguments.of("12345678A", "", "123456789"),         // Empty name
            Arguments.of("12345678A", "John Doe", "")           // Empty telephone
        );
    }

    @Test
    @DisplayName("Should pass correct parameters to DAO methods")
    void shouldPassCorrectParametersToDAOMethods() {
        // Arrange
        String id = "12345678A";
        String name = "John Doe";
        String tel = "123456789";
        User expectedUser = new User(id, name, tel);
        
        // Mock DAO behavior
        when(forumDAO.existUserDAO(id)).thenReturn(false);
        when(forumDAO.getUserDAO(id)).thenReturn(expectedUser);
        
        // Define argument captors to verify parameters
        ArgumentCaptor<String> idCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> nameCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> telCaptor = ArgumentCaptor.forClass(String.class);

        // Act
        User result = null;
        try {
            result = forumBL.addUser(id, name, tel);
        } catch (Exception e) {
            fail("Should not throw exception: " + e.getMessage());
        }

        // Assert
        assertNotNull(result);
        
        // Verify parameters passed to DAO methods
        verify(forumDAO).existUserDAO(idCaptor.capture());
        verify(forumDAO).addUserDAO(idCaptor.capture(), nameCaptor.capture(), telCaptor.capture());
        verify(forumDAO).getUserDAO(idCaptor.capture());
        
        // Verify each captured value separately for better test isolation
        assertEquals(id, idCaptor.getAllValues().get(0), "First id parameter should match");
        assertEquals(id, idCaptor.getAllValues().get(1), "Second id parameter should match");
        assertEquals(id, idCaptor.getAllValues().get(2), "Third id parameter should match");
        assertEquals(name, nameCaptor.getValue(), "Name parameter should match");
        assertEquals(tel, telCaptor.getValue(), "Telephone parameter should match");
    }
    
    @Test
    @DisplayName("Should verify returned user properties match input parameters")
    void shouldVerifyReturnedUserPropertiesMatchInputParameters() {
        // Arrange
        String id = "12345678A";
        String name = "John Doe";
        String tel = "123456789";
        User mockUser = new User(id, name, tel);
        
        when(forumDAO.existUserDAO(id)).thenReturn(false);
        when(forumDAO.getUserDAO(id)).thenReturn(mockUser);

        // Act
        User result = null;
        try {
            result = forumBL.addUser(id, name, tel);
        } catch (Exception e) {
            fail("Should not throw exception: " + e.getMessage());
        }

        // Assert each property individually for better test isolation
        assertNotNull(result, "Result should not be null");
        assertEquals(id, result.getId(), "User ID should match input");
        assertEquals(name, result.getName(), "User name should match input");
        assertEquals(tel, result.getTelephone(), "User telephone should match input");
    }
}