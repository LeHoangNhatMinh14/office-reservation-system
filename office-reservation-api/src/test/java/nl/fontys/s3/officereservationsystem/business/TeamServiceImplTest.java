package nl.fontys.s3.officereservationsystem.business;

import nl.fontys.s3.officereservationsystem.business.exception.EntityNotFoundException;
import nl.fontys.s3.officereservationsystem.business.exception.InvalidFieldException;
import nl.fontys.s3.officereservationsystem.business.exception.NameAlreadyExistsException;
import nl.fontys.s3.officereservationsystem.business.exception.TeamManagerRequirementException;
import nl.fontys.s3.officereservationsystem.business.validator.TeamValidator;
import nl.fontys.s3.officereservationsystem.domain.Team;
import nl.fontys.s3.officereservationsystem.domain.User;
import nl.fontys.s3.officereservationsystem.persistence.TeamRepository;
import nl.fontys.s3.officereservationsystem.persistence.entity.TeamEntity;
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
class TeamServiceImplTest {

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private TeamValidator teamValidator;

    @InjectMocks
    private TeamServiceImpl teamService;

    @Test
    void createTeam_shouldCreateTeam() {
        // Arrange
        User user = User.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("johndoe@example.com")
                .password("password")
                .isAdmin(false)
                .build();

        Team team = Team.builder()
                .name("Test Name")
                .users(List.of())
                .teamManagers(List.of(user))
                .build();

        UserEntity userEntity = UserEntity.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("johndoe@example.com")
                .password("password")
                .isAdmin(false)
                .build();

        TeamEntity teamEntity = TeamEntity.builder()
                .name("Test Name")
                .users(List.of())
                .teamManagers(List.of(userEntity))
                .build();

        when(teamRepository.save(any(TeamEntity.class)))
                .thenReturn(teamEntity);

        // Act
        teamService.createTeam(team);

        // Assert
        verify(teamValidator, times(1)).validateTeamForCreation(team);
        verify(teamRepository, times(1)).save(any(TeamEntity.class));
    }

    @ParameterizedTest
    @MethodSource("provideForCreate_shouldThrowException_whenInvalidDataIsSupplied")
    void createTeam_shouldThrowException_whenInvalidDataIsSupplied(Team team, Class<? extends RuntimeException> expectedException, String fieldName) {
        // Arrange
        String expectedMessage;

        if (expectedException.equals(NameAlreadyExistsException.class)) {
            expectedMessage = String.format(
                    "400 BAD_REQUEST \"%s_NAME_ALREADY_EXISTS\"",
                    fieldName.toUpperCase()
            );

            doThrow(new NameAlreadyExistsException(fieldName))
                    .when(teamValidator).validateTeamForCreation(team);
        }
        else if (expectedException.equals(InvalidFieldException.class)) {
            expectedMessage = String.format(
                    "400 BAD_REQUEST \"INVALID_%s: %s cannot be blank or null.\"",
                    fieldName.toUpperCase(),
                    fieldName
            );

            doThrow(new InvalidFieldException(fieldName))
                    .when(teamValidator).validateTeamForCreation(team);
        }
        else if (expectedException.equals(EntityNotFoundException.class)) {
            expectedMessage = String.format(
                    "404 NOT_FOUND \"%s_NOT_FOUND: ID %s does not exist.\"",
                    fieldName.toUpperCase(),
                    team.getId()
            );

            doThrow(new EntityNotFoundException(fieldName, team.getId()))
                    .when(teamValidator).validateTeamForCreation(team);
        }
        else {
            expectedMessage = "400 BAD_REQUEST \"TEAM_MANAGER_REQUIREMENT_NOT_MET: At least one manager is required.\"";

            doThrow(new TeamManagerRequirementException())
                    .when(teamValidator).validateTeamForCreation(team);
        }

        // Act
        Exception exception = assertThrows(expectedException,
                () -> teamService.createTeam(team));

        // Assert
        assertEquals(expectedMessage, exception.getMessage());
        verify(teamValidator, times(1)).validateTeamForCreation(team);
        verify(teamRepository, times(0)).save(any(TeamEntity.class));
    }

    @Test
    void getAllTeams_shouldReturnTeams() {
        // Arrange
        UserEntity user = UserEntity.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("johndoe@example.com")
                .password("password")
                .isAdmin(false)
                .build();

        TeamEntity team = TeamEntity.builder()
                .id(1L)
                .name("Test Name")
                .users(List.of())
                .teamManagers(List.of(user))
                .build();

        User expectedUser = User.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("johndoe@example.com")
                .password("password")
                .isAdmin(false)
                .build();

        Team expectedTeam = Team.builder()
                .id(1L)
                .name("Test Name")
                .users(List.of())
                .teamManagers(List.of(expectedUser))
                .build();

        when(teamRepository.findAll())
                .thenReturn(List.of(team));

        // Act
        List<Team> result = teamService.getAllTeams();

        // Assert
        assertTrue(result.contains(expectedTeam));
        verify(teamRepository, times(1)).findAll();
    }

    @Test
    void getTeamById_shouldReturnTeam() {
        // Arrange
        UserEntity user = UserEntity.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("johndoe@example.com")
                .password("password")
                .isAdmin(false)
                .build();

        TeamEntity team = TeamEntity.builder()
                .id(1L)
                .name("Test Name")
                .users(List.of())
                .teamManagers(List.of(user))
                .build();

        User expectedUser = User.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("johndoe@example.com")
                .password("password")
                .isAdmin(false)
                .build();

        Team expectedTeam = Team.builder()
                .id(1L)
                .name("Test Name")
                .users(List.of())
                .teamManagers(List.of(expectedUser))
                .build();

        when(teamRepository.findById(team.getId()))
                .thenReturn(Optional.of(team));

        // Act
        Optional<Team> result = teamService.getTeamById(team.getId());

        // Assert
        assertTrue(result.isPresent());
        assertEquals(expectedTeam, result.get());
        verify(teamValidator, times(1)).validateIdExists(team.getId());
        verify(teamRepository, times(1)).findById(team.getId());
    }

    @Test
    void getTeamById_shouldThrowException_whenTeamNotFound() {
        // Arrange
        Long teamId = 1L;

        String expectedMessage = String.format(
                "404 NOT_FOUND \"TEAM_NOT_FOUND: ID %s does not exist.\"",
                teamId
        );

        doThrow(new EntityNotFoundException("Team", teamId))
                .when(teamValidator).validateIdExists(teamId);

        // Act
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> teamService.getTeamById(teamId));

        // Assert
        assertEquals(expectedMessage, exception.getMessage());
        verify(teamValidator, times(1)).validateIdExists(teamId);
        verify(teamRepository, times(0)).findById(teamId);
    }

    @Test
    void deleteTeam_shouldDeleteTeam() {
        // Arrange
        Long teamId = 1L;

        // Act
        teamService.deleteTeam(teamId);

        // Assert
        verify(teamRepository, times(1)).deleteById(teamId);
    }

    @Test
    void deleteTeam_shouldThrowException_whenTeamNotFound() {
        // Arrange
        Long teamId = 1L;

        String expectedMessage = String.format(
                "404 NOT_FOUND \"TEAM_NOT_FOUND: ID %s does not exist.\"",
                teamId
        );

        doThrow(new EntityNotFoundException("Team", teamId))
                .when(teamValidator).validateIdExists(teamId);

        // Act
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> teamService.deleteTeam(teamId));

        // Assert
        assertEquals(expectedMessage, exception.getMessage());
        verify(teamValidator, times(1)).validateIdExists(teamId);
        verify(teamRepository, times(0)).deleteById(teamId);
    }

    private static Stream<Arguments> provideForCreate_shouldThrowException_whenInvalidDataIsSupplied() {
        return Stream.of(
                Arguments.of(null, InvalidFieldException.class, "Team"),
                Arguments.of(new Team(null, null, List.of(), List.of(new User(1L, "John", "Doe", "johndoe@example.com", "password", false))), InvalidFieldException.class, "Team name"),
                Arguments.of(new Team(null, "Test Name", List.of(), null), InvalidFieldException.class, "Team managers"),
                Arguments.of(new Team(null, "Test Name", null, List.of(new User(1L, "John", "Doe", "johndoe@example.com", "password", false))), InvalidFieldException.class, "Team members"),
                Arguments.of(new Team(null, "Test Name", List.of(), List.of(new User(1L, "John", "Doe", "johndoe@example.com", "password", false))), NameAlreadyExistsException.class, "Team"),
                Arguments.of(new Team(null, "Test Name", List.of(), List.of()), TeamManagerRequirementException.class, "Team"),
                Arguments.of(new Team(null, "Test Name", List.of(), List.of(new User(1L, "John", "Doe", "johndoe@example.com", "password", false))), EntityNotFoundException.class, "User")
        );
    }
}