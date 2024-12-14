package nl.fontys.s3.officereservationsystem.business;

import nl.fontys.s3.officereservationsystem.business.exception.EntityNotFoundException;
import nl.fontys.s3.officereservationsystem.configuration.security.token.AccessTokenEncoder;
import nl.fontys.s3.officereservationsystem.configuration.security.token.impl.AccessTokenImpl;
import nl.fontys.s3.officereservationsystem.domain.Credential;
import nl.fontys.s3.officereservationsystem.persistence.UserRepository;
import nl.fontys.s3.officereservationsystem.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SignInServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AccessTokenEncoder accessTokenEncoder;

    @InjectMocks
    private SignInServiceImpl signInService;

    @Test
    void signIn_shouldReturnAccessToken_whenCredentialsAreValid() {
        // Arrange
        String email = "user@example.com";
        String rawPassword = "password123";
        String encodedPassword = "$2a$10$...";
        String expectedToken = "access-token";

        UserEntity user = UserEntity.builder()
                .id(1L)
                .email(email)
                .password(encodedPassword)
                .isAdmin(false)
                .build();

        Credential credential = Credential.builder()
                .email(email)
                .password(rawPassword)
                .build();

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(true);
        when(accessTokenEncoder.encode(any(AccessTokenImpl.class))).thenReturn(expectedToken);

        // Act
        String actualToken = signInService.signIn(credential);

        // Assert
        assertEquals(expectedToken, actualToken);
        verify(userRepository, times(1)).findByEmail(email);
        verify(passwordEncoder, times(1)).matches(rawPassword, encodedPassword);
        verify(accessTokenEncoder, times(1)).encode(any(AccessTokenImpl.class));
    }

    @Test
    void signIn_shouldThrowException_whenUserNotFound() {
        // Arrange
        String email = "nonexistent@example.com";

        Credential credential = Credential.builder()
                .email(email)
                .password("password123")
                .build();

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Act
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> signInService.signIn(credential));

        // Assert
        assertEquals("USER_NOT_FOUND: " + email + " does not exist.", exception.getReason());
        verify(userRepository, times(1)).findByEmail(email);
        verifyNoInteractions(passwordEncoder, accessTokenEncoder);
    }

    @Test
    void signIn_shouldThrowException_whenPasswordDoesNotMatch() {
        // Arrange
        String email = "user@example.com";
        String rawPassword = "wrongpassword";
        String encodedPassword = "$2a$10$...";

        UserEntity user = UserEntity.builder()
                .id(1L)
                .email(email)
                .password(encodedPassword)
                .isAdmin(false)
                .build();

        Credential credential = Credential.builder()
                .email(email)
                .password(rawPassword)
                .build();

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(false);

        // Act
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> signInService.signIn(credential));

        // Assert
        assertEquals("USER_NOT_FOUND: " + rawPassword + " does not exist.", exception.getReason());
        verify(userRepository, times(1)).findByEmail(email);
        verify(passwordEncoder, times(1)).matches(rawPassword, encodedPassword);
        verifyNoInteractions(accessTokenEncoder);
    }
}
