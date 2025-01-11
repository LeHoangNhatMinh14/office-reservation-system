package nl.fontys.s3.officereservationsystem.persistence;

import jakarta.persistence.EntityManager;
import nl.fontys.s3.officereservationsystem.domain.ReservationType;
import nl.fontys.s3.officereservationsystem.persistence.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class ReservationRepositoryTest {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private EntityManager entityManager;

    private UserEntity user;
    private TeamEntity team;
    private TableEntity table;

    @BeforeEach
    void setUp() {
        user = UserEntity.builder()
                .firstName("John")
                .lastName("Doe")
                .email("johndoe@example.com")
                .password("password")
                .isAdmin(true)
                .build();

        team = TeamEntity.builder()
                .name("Test Name")
                .users(List.of())
                .teamManagers(List.of(user))
                .build();

        table = TableEntity.builder()
                .verticalPosition(10)
                .horizontalPosition(20)
                .tableType(TableEntityType.SMALL_TABLE)
                .build();

        RoomEntity room = RoomEntity.builder()
                .name("Test Room")
                .width(100)
                .height(200)
                .tables(List.of(table))
                .build();

        entityManager.persist(user);
        entityManager.persist(team);
        entityManager.persist(room);

        entityManager.flush();
        entityManager.clear();
    }

    @Test
    void save_shouldSaveReservationWithAllFields() {
        // Arrange
        ReservationEntity reservation = ReservationEntity.builder()
                .date(LocalDate.now())
                .startTime(LocalTime.of(9, 0))
                .endTime(LocalTime.of(10, 0))
                .reservationType(ReservationType.TEAM)
                .tableId(table.getId())
                .userId(null)
                .teamId(team.getId())
                .build();

        // Act
        ReservationEntity savedReservation = reservationRepository.save(reservation);
        entityManager.flush();
        entityManager.clear();

        Optional<ReservationEntity> foundReservation = reservationRepository.findById(savedReservation.getId());

        // Assert
        assertThat(foundReservation).isPresent();
        assertThat(foundReservation.get().getDate()).isEqualTo(LocalDate.now());
    }

    @Test
    void findAll_shouldReturnAllReservations() {
        // Arrange
        ReservationEntity reservation1 = ReservationEntity.builder()
                .date(LocalDate.now())
                .startTime(LocalTime.of(9, 0))
                .endTime(LocalTime.of(10, 0))
                .reservationType(ReservationType.TEAM)
                .tableId(table.getId())
                .userId(null)
                .teamId(team.getId())
                .build();

        ReservationEntity reservation2 = ReservationEntity.builder()
                .date(LocalDate.now())
                .startTime(LocalTime.of(13, 0))
                .endTime(LocalTime.of(15, 0))
                .reservationType(ReservationType.INDIVIDUAL)
                .tableId(table.getId())
                .userId(user.getId())
                .teamId(null)
                .build();

        reservationRepository.save(reservation1);
        reservationRepository.save(reservation2);

        entityManager.flush();
        entityManager.clear();

        // Act
        List<ReservationEntity> reservations = reservationRepository.findAll();

        // Assert
        assertThat(reservations).hasSize(2);
        assertThat(reservations).extracting("startTime").containsExactlyInAnyOrder(LocalTime.of(9, 0), LocalTime.of(13, 0));
    }

    @Test
    void findAll_shouldReturnNoReservations() {
        // Act
        List<ReservationEntity> reservations = reservationRepository.findAll();

        // Assert
        assertThat(reservations).isEmpty();
    }

    @Test
    void deleteById_shouldDeleteReservation() {
        // Arrange
        ReservationEntity reservation = ReservationEntity.builder()
                .date(LocalDate.now())
                .startTime(LocalTime.of(9, 0))
                .endTime(LocalTime.of(10, 0))
                .reservationType(ReservationType.TEAM)
                .tableId(table.getId())
                .userId(null)
                .teamId(team.getId())
                .build();

        ReservationEntity savedReservation = reservationRepository.save(reservation);
        entityManager.flush();
        entityManager.clear();

        // Act
        reservationRepository.deleteById(savedReservation.getId());
        entityManager.flush();
        entityManager.clear();

        // Assert
        Optional<ReservationEntity> deletedReservation = reservationRepository.findById(savedReservation.getId());
        assertThat(deletedReservation).isEmpty();
    }

    @Test
    void deleteAll_shouldDeleteAllReservations() {
        // Arrange
        ReservationEntity reservation1 = ReservationEntity.builder()
                .date(LocalDate.now())
                .startTime(LocalTime.of(9, 0))
                .endTime(LocalTime.of(10, 0))
                .reservationType(ReservationType.TEAM)
                .tableId(table.getId())
                .userId(null)
                .teamId(team.getId())
                .build();

        ReservationEntity reservation2 = ReservationEntity.builder()
                .date(LocalDate.now())
                .startTime(LocalTime.of(9, 0))
                .endTime(LocalTime.of(10, 0))
                .reservationType(ReservationType.INDIVIDUAL)
                .tableId(table.getId())
                .userId(user.getId())
                .teamId(null)
                .build();

        reservationRepository.save(reservation1);
        reservationRepository.save(reservation2);

        entityManager.flush();
        entityManager.clear();

        // Act
        reservationRepository.deleteAll();
        entityManager.flush();
        entityManager.clear();

        // Assert
        List<ReservationEntity> reservations = reservationRepository.findAll();
        assertThat(reservations).isEmpty();
    }

    @Test
    void count_shouldReturnCountForAllReservations() {
        // Arrange
        ReservationEntity reservation1 = ReservationEntity.builder()
                .date(LocalDate.now())
                .startTime(LocalTime.of(9, 0))
                .endTime(LocalTime.of(10, 0))
                .reservationType(ReservationType.TEAM)
                .tableId(table.getId())
                .userId(null)
                .teamId(team.getId())
                .build();

        ReservationEntity reservation2 = ReservationEntity.builder()
                .date(LocalDate.now())
                .startTime(LocalTime.of(9, 0))
                .endTime(LocalTime.of(10, 0))
                .reservationType(ReservationType.INDIVIDUAL)
                .tableId(table.getId())
                .userId(user.getId())
                .teamId(null)
                .build();

        reservationRepository.save(reservation1);
        reservationRepository.save(reservation2);

        entityManager.flush();
        entityManager.clear();

        // Act
        long count = reservationRepository.count();

        // Assert
        assertThat(count).isEqualTo(2);
    }

    @Test
    void count_shouldReturnZeroForNoReservations() {
        // Act
        long count = reservationRepository.count();

        // Assert
        assertThat(count).isZero();
    }
}