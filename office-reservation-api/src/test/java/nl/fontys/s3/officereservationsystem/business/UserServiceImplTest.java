package nl.fontys.s3.officereservationsystem.business;

import nl.fontys.s3.officereservationsystem.domain.User;
import nl.fontys.s3.officereservationsystem.persistence.entity.UserRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepositoryImpl userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllUsers() {
        User user1 = User.builder()
                .id(1L)
                .firstName("Alice")
                .lastName("Smith")
                .email("alice@example.com")
                .isAdmin(false)
                .build();
        User user2 = User.builder()
                .id(2L)
                .firstName("Bob")
                .lastName("Johnson")
                .email("bob@example.com")
                .isAdmin(true)
                .build();
        when(userRepository.findAll()).thenReturn(List.of(user1, user2));

        List<User> users = userService.getAllUsers();

        assertEquals(2, users.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testGetUserById_UserExists() {
        User user = User.builder()
                .id(1L)
                .firstName("Alice")
                .lastName("Smith")
                .email("alice@example.com")
                .isAdmin(false)
                .build();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.getUserById(1L);

        assertEquals(user, result);
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testGetUserById_UserDoesNotExist() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> userService.getUserById(1L));

        assertEquals("User with id 1 does not exist", exception.getMessage());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testGetUserByEmail_UserExists() {
        User user = User.builder()
                .id(1L)
                .firstName("Alice")
                .lastName("Smith")
                .email("alice@example.com")
                .isAdmin(false)
                .build();
        when(userRepository.findByEmail("alice@example.com")).thenReturn(Optional.of(user));

        User result = userService.getUserByEmail("alice@example.com");

        assertEquals(user, result);
        verify(userRepository, times(1)).findByEmail("alice@example.com");
    }

    @Test
    void testGetUserByEmail_UserDoesNotExist() {
        when(userRepository.findByEmail("alice@example.com")).thenReturn(Optional.empty());

        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> userService.getUserByEmail("alice@example.com"));

        assertEquals("User with email alice@example.com does not exist", exception.getMessage());
        verify(userRepository, times(1)).findByEmail("alice@example.com");
    }

    @Test
    void testCreateUser_Success() {
        User user = User.builder()
                .firstName("Alice")
                .lastName("Smith")
                .email("alice@example.com")
                .password("password123")
                .isAdmin(false)
                .build();
        when(userRepository.save(user)).thenReturn(user);

        User result = userService.createUser(user);

        assertEquals(user, result);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testCreateUser_UserAlreadyExists() {
        User user = User.builder()
                .id(1L)
                .firstName("Alice")
                .lastName("Smith")
                .email("alice@example.com")
                .password("password123")
                .isAdmin(false)
                .build();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userService.createUser(user));

        assertEquals("User with id 1 already exists", exception.getMessage());
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, never()).save(user);
    }

    @Test
    void testUpdateUser_UserExists() {
        User user = User.builder()
                .id(1L)
                .firstName("Alice")
                .lastName("Smith")
                .email("alice@example.com")
                .isAdmin(false)
                .build();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.updateUser(1L, user);

        verify(userRepository, times(1)).deleteById(1L);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testUpdateUser_UserDoesNotExist() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        User updatedUser = User.builder()
                .id(1L)
                .firstName("Alice")
                .lastName("Smith")
                .email("alice@example.com")
                .isAdmin(false)
                .build();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userService.updateUser(1L, updatedUser));

        assertEquals("User with id 1 does not exist", exception.getMessage());
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, never()).save(updatedUser);
    }

    @Test
    void testDeleteUser_UserExists() {
        User user = User.builder()
                .id(1L)
                .firstName("Alice")
                .lastName("Smith")
                .email("alice@example.com")
                .isAdmin(false)
                .build();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.deleteUser(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteUser_UserDoesNotExist() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userService.deleteUser(1L));

        assertEquals("User with id 1 does not exist", exception.getMessage());
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, never()).deleteById(anyLong());
    }
}
