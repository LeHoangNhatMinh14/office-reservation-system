package nl.fontys.s3.officereservationsystem.business;

import nl.fontys.s3.officereservationsystem.business.exception.EmailAlreadyExistsException;
import nl.fontys.s3.officereservationsystem.business.exception.EntityNotFoundException;
import nl.fontys.s3.officereservationsystem.business.exception.InvalidFieldException;
import nl.fontys.s3.officereservationsystem.business.validator.UserValidator;
import nl.fontys.s3.officereservationsystem.domain.User;
import nl.fontys.s3.officereservationsystem.persistence.UserRepository;
import nl.fontys.s3.officereservationsystem.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserValidator userValidator;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void createUser_shouldCreateUser() {
        // Arrange
        User user = User.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("johndoe@example.com")
                .password("password")
                .isAdmin(false)
                .build();

        UserEntity userEntity = UserEntity.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("johndoe@example.com")
                .password("password")
                .isAdmin(false)
                .build();

        when(userRepository.save(any(UserEntity.class)))
                .thenReturn(userEntity);

        // Act
        userService.createUser(user);

        // Assert
        verify(userValidator, times(1)).validateUserForCreation(user);
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @ParameterizedTest
    @MethodSource("provideForCreate_shouldThrowException_whenInvalidDataIsSupplied")
    void createUser_shouldThrowException_whenInvalidDataIsSupplied(User user, Class<? extends RuntimeException> expectedException, String fieldName) {
        // Arrange
        String expectedMessage;

        if (expectedException.equals(InvalidFieldException.class)) {
            expectedMessage = String.format(
                    "400 BAD_REQUEST \"INVALID_%s: %s cannot be blank or null.\"",
                    fieldName.toUpperCase(),
                    fieldName
            );

            doThrow(new InvalidFieldException(fieldName))
                    .when(userValidator).validateUserForCreation(user);
        }
        else {
            expectedMessage = "400 BAD_REQUEST \"EMAIL_ALREADY_EXISTS\"";

            doThrow(new EmailAlreadyExistsException())
                    .when(userValidator).validateUserForCreation(user);
        }

        // Act
        Exception exception = assertThrows(expectedException,
                () -> userService.createUser(user));

        // Assert
        assertEquals(expectedMessage, exception.getMessage());
        verify(userValidator, times(1)).validateUserForCreation(user);
        verify(userRepository, times(0)).save(any(UserEntity.class));
    }

    @Test
    void getAllUsers_shouldReturnUsers() {
        // Arrange
        UserEntity user = UserEntity.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("johndoe@example.com")
                .password("password")
                .isAdmin(false)
                .build();

        User expectedUser = User.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("johndoe@example.com")
                .password("password")
                .isAdmin(false)
                .build();

        when(userRepository.findAll())
                .thenReturn(List.of(user));

        // Act
        List<User> result = userService.getAllUsers();

        // Assert
        assertTrue(result.contains(expectedUser));
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void getUserById_shouldReturnUser() {
        // Arrange
        UserEntity user = UserEntity.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("johndoe@example.com")
                .password("password")
                .isAdmin(false)
                .build();

        User expectedUser = User.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("johndoe@example.com")
                .password("password")
                .isAdmin(false)
                .build();

        when(userRepository.findById(user.getId()))
                .thenReturn(Optional.of(user));

        // Act
        Optional<User> result = userService.getUserById(user.getId());

        // Assert
        assertTrue(result.isPresent());
        assertEquals(expectedUser, result.get());
        verify(userValidator, times(1)).validateIdExists(user.getId());
        verify(userRepository, times(1)).findById(user.getId());
    }

    @Test
    void getUserById_shouldThrowException_whenUserNotFound() {
        // Arrange
        Long userId = 1L;

        String expectedMessage = String.format(
                "404 NOT_FOUND \"USER_NOT_FOUND: ID %s does not exist.\"",
                userId
        );

        doThrow(new EntityNotFoundException("User", userId))
                .when(userValidator).validateIdExists(userId);

        // Act
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> userService.getUserById(userId));

        // Assert
        assertEquals(expectedMessage, exception.getMessage());
        verify(userValidator, times(1)).validateIdExists(userId);
        verify(userRepository, times(0)).findById(userId);
    }

    @Test
    void getUserByEmail_shouldReturnUser() {
        // Arrange
        UserEntity user = UserEntity.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("johndoe@example.com")
                .password("password")
                .isAdmin(false)
                .build();

        User expectedUser = User.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("johndoe@example.com")
                .password("password")
                .isAdmin(false)
                .build();

        when(userRepository.findByEmail(user.getEmail()))
                .thenReturn(Optional.of(user));

        // Act
        Optional<User> result = userService.getUserByEmail(user.getEmail());

        // Assert
        assertTrue(result.isPresent());
        assertEquals(expectedUser, result.get());
        verify(userRepository, times(1)).findByEmail(user.getEmail());
    }

    @Test
    void deleteUser_shouldDeleteUser() {
        // Arrange
        Long userId = 1L;

        // Act
        userService.deleteUser(userId);

        // Assert
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void deleteUser_shouldThrowException_whenUserNotFound() {
        // Arrange
        Long userId = 1L;

        String expectedMessage = String.format(
                "404 NOT_FOUND \"USER_NOT_FOUND: ID %s does not exist.\"",
                userId
        );

        doThrow(new EntityNotFoundException("User", userId))
                .when(userValidator).validateIdExists(userId);

        // Act
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> userService.deleteUser(userId));

        // Assert
        assertEquals(expectedMessage, exception.getMessage());
        verify(userValidator, times(1)).validateIdExists(userId);
        verify(userRepository, times(0)).deleteById(userId);
    }

    private static Stream<Arguments> provideForCreate_shouldThrowException_whenInvalidDataIsSupplied() {
        return Stream.of(
                Arguments.of(null, InvalidFieldException.class, "User"),
                Arguments.of(new User(null, "John", "Doe", " ", "password", false), InvalidFieldException.class, "User email"),
                Arguments.of(new User(null, " ", "Doe", "johndoe@example.com", "password", false), InvalidFieldException.class, "First name"),
                Arguments.of(new User(null, "John", " ", "johndoe@example.com", "password", false), InvalidFieldException.class, "Last name"),
                Arguments.of(new User(null, "John", "Doe", "johndoe@example.com", " ", false), InvalidFieldException.class, "Password"),
                Arguments.of(new User(null, "John", "Doe", "johndoe@example.com", "password", false), EmailAlreadyExistsException.class, "Email")
        );
    }
}