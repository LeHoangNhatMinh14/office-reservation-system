package nl.fontys.s3.officereservationsystem.persistence;

import jakarta.persistence.EntityManager;
import nl.fontys.s3.officereservationsystem.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void save_shouldSaveUserWithAllFields() {
        // Arrange
        UserEntity user = UserEntity.builder()
                .firstName("John")
                .lastName("Doe")
                .email("johndoe@example.com")
                .password("password")
                .isAdmin(true)
                .build();

        // Act
        UserEntity savedUser = userRepository.save(user);
        entityManager.flush();
        entityManager.clear();

        Optional<UserEntity> foundUser = userRepository.findById(savedUser.getId());

        // Assert
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getEmail()).isEqualTo("johndoe@example.com");
    }

    @Test
    void findAll_shouldReturnAllUsers() {
        // Arrange
        UserEntity user1 = UserEntity.builder()
                .firstName("John")
                .lastName("Doe")
                .email("johndoe@example.com")
                .password("password")
                .isAdmin(true)
                .build();

        UserEntity user2 = UserEntity.builder()
                .firstName("Jane")
                .lastName("Doe")
                .email("janedoe@example.com")
                .password("password")
                .isAdmin(false)
                .build();

        userRepository.save(user1);
        userRepository.save(user2);

        entityManager.flush();
        entityManager.clear();

        // Act
        List<UserEntity> users = userRepository.findAll();

        // Assert
        assertThat(users).hasSize(2);
        assertThat(users).extracting("email").containsExactlyInAnyOrder("johndoe@example.com", "janedoe@example.com");
    }

    @Test
    void findAll_shouldReturnNoUsers() {
        // Act
        List<UserEntity> users = userRepository.findAll();

        // Assert
        assertThat(users).isEmpty();
    }

    @Test
    void deleteById_shouldDeleteUser() {
        // Arrange
        UserEntity user = UserEntity.builder()
                .firstName("John")
                .lastName("Doe")
                .email("johndoe@example.com")
                .password("password")
                .isAdmin(true)
                .build();

        UserEntity savedUser = userRepository.save(user);
        entityManager.flush();
        entityManager.clear();

        // Act
        userRepository.deleteById(savedUser.getId());
        entityManager.flush();
        entityManager.clear();

        // Assert
        Optional<UserEntity> deletedUser = userRepository.findById(savedUser.getId());
        assertThat(deletedUser).isEmpty();
    }

    @Test
    void deleteAll_shouldDeleteAllUsers() {
        // Arrange
        UserEntity user1 = UserEntity.builder()
                .firstName("John")
                .lastName("Doe")
                .email("johndoe@example.com")
                .password("password")
                .isAdmin(true)
                .build();

        UserEntity user2 = UserEntity.builder()
                .firstName("Jane")
                .lastName("Doe")
                .email("janedoe@example.com")
                .password("password")
                .isAdmin(false)
                .build();

        userRepository.save(user1);
        userRepository.save(user2);

        entityManager.flush();
        entityManager.clear();

        // Act
        userRepository.deleteAll();
        entityManager.flush();
        entityManager.clear();

        // Assert
        List<UserEntity> users = userRepository.findAll();
        assertThat(users).isEmpty();
    }

    @Test
    void count_shouldReturnCountForAllUsers() {
        // Arrange
        UserEntity user1 = UserEntity.builder()
                .firstName("John")
                .lastName("Doe")
                .email("johndoe@example.com")
                .password("password")
                .isAdmin(true)
                .build();

        UserEntity user2 = UserEntity.builder()
                .firstName("Jane")
                .lastName("Doe")
                .email("janedoe@example.com")
                .password("password")
                .isAdmin(false)
                .build();

        userRepository.save(user1);
        userRepository.save(user2);

        entityManager.flush();
        entityManager.clear();

        // Act
        long count = userRepository.count();

        // Assert
        assertThat(count).isEqualTo(2);
    }

    @Test
    void count_shouldReturnZeroForNoUsers() {
        // Act
        long count = userRepository.count();

        // Assert
        assertThat(count).isZero();
    }

    @Test
    void findByEmail_shouldReturnUserWithAllFields() {
        // Arrange
        UserEntity user = UserEntity.builder()
                .firstName("John")
                .lastName("Doe")
                .email("johndoe@example.com")
                .password("password")
                .isAdmin(true)
                .build();

        UserEntity savedUser = userRepository.save(user);
        entityManager.flush();
        entityManager.clear();

        // Act
        Optional<UserEntity> foundUser = userRepository.findByEmail(savedUser.getEmail());

        // Assert
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getEmail()).isEqualTo("johndoe@example.com");
    }

    @Test
    void findByEmail_shouldReturnNoUser() {
        // Act
        Optional<UserEntity> foundUser = userRepository.findByEmail("johndoe@example.com");

        // Assert
        assertThat(foundUser).isEmpty();
    }

    @Test
    void existsByEmail_shouldReturnTrue() {
        // Arrange
        UserEntity user = UserEntity.builder()
                .firstName("John")
                .lastName("Doe")
                .email("johndoe@example.com")
                .password("password")
                .isAdmin(true)
                .build();

        userRepository.save(user);
        entityManager.flush();
        entityManager.clear();

        // Act
        boolean exists = userRepository.existsByEmail(user.getEmail());

        // Assert
        assertThat(exists).isTrue();
    }

    @Test
    void existsByEmail_shouldReturnFalse() {
        // Act
        boolean exists = userRepository.existsByEmail("johndoe@example.com");

        // Assert
        assertThat(exists).isFalse();
    }
}