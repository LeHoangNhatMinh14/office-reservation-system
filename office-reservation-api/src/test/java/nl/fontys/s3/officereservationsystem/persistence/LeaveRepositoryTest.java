package nl.fontys.s3.officereservationsystem.persistence;

import jakarta.persistence.EntityManager;
import nl.fontys.s3.officereservationsystem.persistence.entity.LeaveEntity;
import nl.fontys.s3.officereservationsystem.persistence.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class LeaveRepositoryTest {

    @Autowired
    private LeaveRepository leaveRepository;

    @Autowired
    private EntityManager entityManager;

    private UserEntity user1;
    private UserEntity user2;

    @BeforeEach
    void setUp() {
        user1 = UserEntity.builder()
                .firstName("John")
                .lastName("Doe")
                .email("johndoe@example.com")
                .password("password")
                .isAdmin(true)
                .build();

        user2 = UserEntity.builder()
                .firstName("Jane")
                .lastName("Doe")
                .email("janedoe@example.com")
                .password("password")
                .isAdmin(false)
                .build();

        entityManager.persist(user1);
        entityManager.persist(user2);

        entityManager.flush();
        entityManager.clear();
    }

    @Test
    void save_shouldSaveLeaveWithAllFields() {
        // Arrange
        LeaveEntity leave = LeaveEntity.builder()
                .userId(user1.getId())
                .startDate(LocalDate.of(2024, 11, 10))
                .endDate(LocalDate.of(2024, 12, 10))
                .reason("reason")
                .build();

        // Act
        LeaveEntity savedLeave = leaveRepository.save(leave);
        entityManager.flush();
        entityManager.clear();

        Optional<LeaveEntity> foundLeave = leaveRepository.findById(savedLeave.getId());

        // Assert
        assertThat(foundLeave).isPresent();
        assertThat(foundLeave.get().getStartDate()).isEqualTo(LocalDate.of(2024, 11, 10));
    }

    @Test
    void findAll_shouldReturnAllLeaves() {
        // Arrange
        LeaveEntity leave1 = LeaveEntity.builder()
                .userId(user1.getId())
                .startDate(LocalDate.of(2024, 11, 10))
                .endDate(LocalDate.of(2024, 12, 10))
                .reason("reason")
                .build();

        LeaveEntity leave2 = LeaveEntity.builder()
                .userId(user2.getId())
                .startDate(LocalDate.of(2024, 10, 20))
                .endDate(LocalDate.of(2024, 11, 20))
                .reason("reason")
                .build();

        leaveRepository.save(leave1);
        leaveRepository.save(leave2);

        entityManager.flush();
        entityManager.clear();

        // Act
        List<LeaveEntity> leaveDays = leaveRepository.findAll();

        // Assert
        assertThat(leaveDays).hasSize(2);
        assertThat(leaveDays).extracting("startDate").containsExactlyInAnyOrder(LocalDate.of(2024, 10, 20), LocalDate.of(2024, 11, 10));
    }

    @Test
    void findAll_shouldReturnNoLeaves() {
        // Act
        List<LeaveEntity> leaveDays = leaveRepository.findAll();

        // Assert
        assertThat(leaveDays).isEmpty();
    }

    @Test
    void deleteById_shouldDeleteLeave() {
        // Arrange
        LeaveEntity leave = LeaveEntity.builder()
                .userId(user1.getId())
                .startDate(LocalDate.of(2024, 11, 10))
                .endDate(LocalDate.of(2024, 12, 10))
                .reason("reason")
                .build();

        LeaveEntity savedLeave = leaveRepository.save(leave);
        entityManager.flush();
        entityManager.clear();

        // Act
        leaveRepository.deleteById(savedLeave.getId());
        entityManager.flush();
        entityManager.clear();

        // Assert
        Optional<LeaveEntity> deletedLeave = leaveRepository.findById(savedLeave.getId());
        assertThat(deletedLeave).isEmpty();
    }

    @Test
    void deleteAll_shouldDeleteAllLeaves() {
        // Arrange
        LeaveEntity leave1 = LeaveEntity.builder()
                .userId(user1.getId())
                .startDate(LocalDate.of(2024, 11, 10))
                .endDate(LocalDate.of(2024, 12, 10))
                .reason("reason")
                .build();

        LeaveEntity leave2 = LeaveEntity.builder()
                .userId(user2.getId())
                .startDate(LocalDate.of(2024, 10, 20))
                .endDate(LocalDate.of(2024, 11, 20))
                .reason("reason")
                .build();

        leaveRepository.save(leave1);
        leaveRepository.save(leave2);

        entityManager.flush();
        entityManager.clear();

        // Act
        leaveRepository.deleteAll();
        entityManager.flush();
        entityManager.clear();

        // Assert
        List<LeaveEntity> leaveDays = leaveRepository.findAll();
        assertThat(leaveDays).isEmpty();
    }

    @Test
    void count_shouldReturnCountForAllLeaves() {
        // Arrange
        LeaveEntity leave1 = LeaveEntity.builder()
                .userId(user1.getId())
                .startDate(LocalDate.of(2024, 11, 10))
                .endDate(LocalDate.of(2024, 12, 10))
                .reason("reason")
                .build();

        LeaveEntity leave2 = LeaveEntity.builder()
                .userId(user2.getId())
                .startDate(LocalDate.of(2024, 10, 20))
                .endDate(LocalDate.of(2024, 11, 20))
                .reason("reason")
                .build();

        leaveRepository.save(leave1);
        leaveRepository.save(leave2);

        entityManager.flush();
        entityManager.clear();

        // Act
        long count = leaveRepository.count();

        // Assert
        assertThat(count).isEqualTo(2);
    }

    @Test
    void count_shouldReturnZeroForNoLeaves() {
        // Act
        long count = leaveRepository.count();

        // Assert
        assertThat(count).isZero();
    }
}