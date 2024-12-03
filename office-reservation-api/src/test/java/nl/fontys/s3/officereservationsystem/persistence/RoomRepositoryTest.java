package nl.fontys.s3.officereservationsystem.persistence;

import jakarta.persistence.EntityManager;
import nl.fontys.s3.officereservationsystem.persistence.entity.RoomEntity;
import nl.fontys.s3.officereservationsystem.persistence.entity.TableEntity;
import nl.fontys.s3.officereservationsystem.persistence.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
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
class RoomRepositoryTest {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private EntityManager entityManager;

    private TableEntity table1;
    private TableEntity table2;

    @BeforeEach
    void setUp() {
        table1 = TableEntity.builder()
                .islandNumber(2)
                .build();

        table2 = TableEntity.builder()
                .islandNumber(3)
                .build();
    }

    @Test
    void save_shouldSaveRoomWithAllFields() {
        // Arrange
        RoomEntity room = RoomEntity.builder()
                .name("Test Name")
                .tables(List.of(table1))
                .build();

        // Act
        RoomEntity savedRoom = roomRepository.save(room);
        entityManager.flush();
        entityManager.clear();

        Optional<RoomEntity> foundRoom = roomRepository.findById(savedRoom.getId());

        // Assert
        assertThat(foundRoom).isPresent();
        assertThat(foundRoom.get().getName()).isEqualTo("Test Name");
    }

    @Test
    void findAll_shouldReturnAllRooms() {
        // Arrange
        RoomEntity room1 = RoomEntity.builder()
                .name("Test Name")
                .tables(List.of(table1))
                .build();

        RoomEntity room2 = RoomEntity.builder()
                .name("Name Test")
                .tables(List.of(table2))
                .build();

        roomRepository.save(room1);
        roomRepository.save(room2);

        entityManager.flush();
        entityManager.clear();

        // Act
        List<RoomEntity> rooms = roomRepository.findAll();

        // Assert
        assertThat(rooms).hasSize(2);
        assertThat(rooms).allSatisfy(room -> {
            assertThat(room.getTables()).extracting("islandNumber").containsAnyOf(2, 3);
        });
    }

    @Test
    void findAll_shouldReturnNoRooms() {
        // Act
        List<RoomEntity> rooms = roomRepository.findAll();

        // Assert
        assertThat(rooms).isEmpty();
    }

    @Test
    void deleteById_shouldDeleteRoom() {
        // Arrange
        RoomEntity room = RoomEntity.builder()
                .name("Test Name")
                .tables(List.of(table1))
                .build();

        RoomEntity savedRoom = roomRepository.save(room);
        entityManager.flush();
        entityManager.clear();

        // Act
        roomRepository.deleteById(savedRoom.getId());
        entityManager.flush();
        entityManager.clear();

        // Assert
        Optional<RoomEntity> deletedRoom = roomRepository.findById(savedRoom.getId());
        assertThat(deletedRoom).isEmpty();
    }

    @Test
    void deleteAll_shouldDeleteAllRooms() {
        // Arrange
        RoomEntity room1 = RoomEntity.builder()
                .name("Test Name")
                .tables(List.of(table1))
                .build();

        RoomEntity room2 = RoomEntity.builder()
                .name("Name Test")
                .tables(List.of(table2))
                .build();

        roomRepository.save(room1);
        roomRepository.save(room2);

        entityManager.flush();
        entityManager.clear();

        // Act
        roomRepository.deleteAll();
        entityManager.flush();
        entityManager.clear();

        // Assert
        List<RoomEntity> rooms = roomRepository.findAll();
        assertThat(rooms).isEmpty();
    }

    @Test
    void count_shouldReturnCountForAllRooms() {
        // Arrange
        RoomEntity room1 = RoomEntity.builder()
                .name("Test Name")
                .tables(List.of(table1))
                .build();

        RoomEntity room2 = RoomEntity.builder()
                .name("Name Test")
                .tables(List.of(table2))
                .build();

        roomRepository.save(room1);
        roomRepository.save(room2);

        entityManager.flush();
        entityManager.clear();

        // Act
        long count = roomRepository.count();

        // Assert
        assertThat(count).isEqualTo(2);
    }

    @Test
    void count_shouldReturnZeroForNoRooms() {
        // Act
        long count = roomRepository.count();

        // Assert
        assertThat(count).isZero();
    }

    @Test
    void findByName_shouldReturnRoomWithAllFields() {
        // Arrange
        RoomEntity room = RoomEntity.builder()
                .name("Test Name")
                .tables(List.of(table1))
                .build();

        RoomEntity savedRoom = roomRepository.save(room);
        entityManager.flush();
        entityManager.clear();

        // Act
        Optional<RoomEntity> foundRoom = roomRepository.findByName(savedRoom.getName());

        // Assert
        assertThat(foundRoom).isPresent();
        assertThat(foundRoom.get().getName()).isEqualTo("Test Name");
    }

    @Test
    void findByName_shouldReturnNoRoom() {
        // Act
        Optional<RoomEntity> foundRoom = roomRepository.findByName("Test Name");

        // Assert
        assertThat(foundRoom).isEmpty();
    }

    @Test
    void existsByName_shouldReturnTrue() {
        // Arrange
        RoomEntity room = RoomEntity.builder()
                .name("Test Name")
                .tables(List.of(table1))
                .build();

        RoomEntity savedRoom = roomRepository.save(room);
        entityManager.flush();
        entityManager.clear();

        // Act
        boolean exists = roomRepository.existsByName(savedRoom.getName());

        // Assert
        assertThat(exists).isTrue();
    }

    @Test
    void existsByName_shouldReturnFalse() {
        // Act
        boolean exists = roomRepository.existsByName("Test Name");

        // Assert
        assertThat(exists).isFalse();
    }
}