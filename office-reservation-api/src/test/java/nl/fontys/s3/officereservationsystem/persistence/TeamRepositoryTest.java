package nl.fontys.s3.officereservationsystem.persistence;

import jakarta.persistence.EntityManager;
import nl.fontys.s3.officereservationsystem.persistence.entity.TeamEntity;
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
class TeamRepositoryTest {

    @Autowired
    private TeamRepository teamRepository;

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
    void save_shouldSaveTeamWithAllFields() {
        // Arrange
        TeamEntity team = TeamEntity.builder()
                .name("Test Name")
                .users(List.of(user1))
                .teamManagers(List.of(user2))
                .build();

        // Act
        TeamEntity savedTeam = teamRepository.save(team);
        entityManager.flush();
        entityManager.clear();

        Optional<TeamEntity> foundTeam = teamRepository.findById(savedTeam.getId());

        // Assert
        assertThat(foundTeam).isPresent();
        assertThat(foundTeam.get().getName()).isEqualTo("Test Name");
    }

    @Test
    void findAll_shouldReturnAllTeams() {
        // Arrange
        TeamEntity team1 = TeamEntity.builder()
                .name("Test Name")
                .users(List.of(user1))
                .teamManagers(List.of(user2))
                .build();

        TeamEntity team2 = TeamEntity.builder()
                .name("Name Test")
                .users(List.of(user2))
                .teamManagers(List.of(user1))
                .build();

        teamRepository.save(team1);
        teamRepository.save(team2);

        entityManager.flush();
        entityManager.clear();

        // Act
        List<TeamEntity> teams = teamRepository.findAll();

        // Assert
        assertThat(teams).hasSize(2);
        assertThat(teams).extracting("name").containsExactlyInAnyOrder("Test Name", "Name Test");
    }

    @Test
    void findAll_shouldReturnNoTeams() {
        // Act
        List<TeamEntity> teams = teamRepository.findAll();

        // Assert
        assertThat(teams).isEmpty();
    }

    @Test
    void deleteById_shouldDeleteTeam() {
        // Arrange
        TeamEntity team = TeamEntity.builder()
                .name("Test Name")
                .users(List.of(user1))
                .teamManagers(List.of(user2))
                .build();

        TeamEntity savedTeam = teamRepository.save(team);
        entityManager.flush();
        entityManager.clear();

        // Act
        teamRepository.deleteById(savedTeam.getId());
        entityManager.flush();
        entityManager.clear();

        // Assert
        Optional<TeamEntity> deletedTeam = teamRepository.findById(savedTeam.getId());
        assertThat(deletedTeam).isEmpty();
    }

    @Test
    void deleteAll_shouldDeleteAllTeams() {
        // Arrange
        TeamEntity team1 = TeamEntity.builder()
                .name("Test Name")
                .users(List.of(user1))
                .teamManagers(List.of(user2))
                .build();

        TeamEntity team2 = TeamEntity.builder()
                .name("Name Test")
                .users(List.of(user2))
                .teamManagers(List.of(user1))
                .build();

        teamRepository.save(team1);
        teamRepository.save(team2);

        entityManager.flush();
        entityManager.clear();

        // Act
        teamRepository.deleteAll();
        entityManager.flush();
        entityManager.clear();

        // Assert
        List<TeamEntity> teams = teamRepository.findAll();
        assertThat(teams).isEmpty();
    }

    @Test
    void count_shouldReturnCountForAllTeams() {
        // Arrange
        TeamEntity team1 = TeamEntity.builder()
                .name("Test Name")
                .users(List.of(user1))
                .teamManagers(List.of(user2))
                .build();

        TeamEntity team2 = TeamEntity.builder()
                .name("Name Test")
                .users(List.of(user2))
                .teamManagers(List.of(user1))
                .build();

        teamRepository.save(team1);
        teamRepository.save(team2);

        entityManager.flush();
        entityManager.clear();

        // Act
        long count = teamRepository.count();

        // Assert
        assertThat(count).isEqualTo(2);
    }

    @Test
    void count_shouldReturnZeroForNoTeams() {
        // Act
        long count = teamRepository.count();

        // Assert
        assertThat(count).isZero();
    }

    @Test
    void findByUserId_shouldReturnAllTeams() {
        // Arrange
        TeamEntity team = TeamEntity.builder()
                .name("Test Name")
                .users(List.of(user1))
                .teamManagers(List.of(user2))
                .build();

        teamRepository.save(team);
        entityManager.flush();
        entityManager.clear();

        // Act
        List<TeamEntity> teams = teamRepository.findByUserId(user1.getId());

        // Assert
        assertThat(teams).hasSize(1);
        assertThat(teams).extracting("name").containsExactly("Test Name");
    }

    @Test
    void findByUserId_shouldReturnNoTeams() {
        // Act
        List<TeamEntity> teams = teamRepository.findByUserId(1L);

        // Assert
        assertThat(teams).isEmpty();
    }

    @Test
    void existsByName_shouldReturnTrue() {
        // Arrange
        TeamEntity team = TeamEntity.builder()
                .name("Test Name")
                .users(List.of(user1))
                .teamManagers(List.of(user2))
                .build();

        teamRepository.save(team);
        entityManager.flush();
        entityManager.clear();

        // Act
        boolean exists = teamRepository.existsByName(team.getName());

        // Assert
        assertThat(exists).isTrue();
    }

    @Test
    void existsByName_shouldReturnFalse() {
        // Act
        boolean exists = teamRepository.existsByName("Test Name");

        // Assert
        assertThat(exists).isFalse();
    }
}