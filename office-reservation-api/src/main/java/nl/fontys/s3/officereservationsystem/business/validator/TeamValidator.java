package nl.fontys.s3.officereservationsystem.business.validator;

import lombok.AllArgsConstructor;
import nl.fontys.s3.officereservationsystem.domain.Team;
import nl.fontys.s3.officereservationsystem.domain.User;
import nl.fontys.s3.officereservationsystem.business.exception.InvalidFieldException;
import nl.fontys.s3.officereservationsystem.business.exception.NameAlreadyExistsException;
import nl.fontys.s3.officereservationsystem.business.exception.EntityNotFoundException;
import nl.fontys.s3.officereservationsystem.business.exception.TeamManagerRequirementException;
import nl.fontys.s3.officereservationsystem.persistence.TeamRepository;
import nl.fontys.s3.officereservationsystem.persistence.UserRepository;
import nl.fontys.s3.officereservationsystem.persistence.entity.TeamEntity;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TeamValidator {
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    public void validateTeamForCreation(Team team) {
        validateTeamFields(team);
        validateUniqueName(team);
        validateTeamManagers(team);
        validateTeamUsers(team);
    }

    public void validateTeamForUpdate(Long id, Team team) {
        validateIdExists(id);
        validateTeamFields(team);
        validateUniqueNameForUpdate(id,team);
        validateTeamManagers(team);
        validateTeamUsers(team);
    }

    private void validateTeamFields(Team team) {
        if (team == null) throw new InvalidFieldException("Team");

        if (team.getName() == null || team.getName().trim().isEmpty()) {
            throw new InvalidFieldException("Team name");
        }
        if (team.getTeamManagers() == null) {
            throw new InvalidFieldException("Team managers");
        }
        if (team.getUsers() == null) {
            throw new InvalidFieldException("Team members");
        }
    }

    private void validateUniqueName(Team team) {
        if (teamRepository.existsByName(team.getName())) {
            throw new NameAlreadyExistsException("Team");
        }
    }

    private void validateUniqueNameForUpdate(Long teamId, Team team) {
        if (teamRepository.existsByNameAndIdNot(team.getName(), teamId)) {
            throw new NameAlreadyExistsException("Team");
        }
    }

    public void validateIdExists(Long id) {
        if (id == null) throw new InvalidFieldException("Team ID");
        if (!teamRepository.existsById(id)) {
            throw new EntityNotFoundException("Team", id);
        }
    }

    private void validateTeamManagers(Team team) {
        if (team.getTeamManagers().isEmpty()) {
            throw new TeamManagerRequirementException();
        }

        for (User manager : team.getTeamManagers()) {
            if (manager.getId() == null || !userRepository.existsById(manager.getId())) {
                throw new EntityNotFoundException("User", manager.getId());
            }
        }
    }

    private void validateTeamUsers(Team team) {
        if (team.getUsers() != null) {
            for (User member : team.getUsers()) {
                if (member.getId() == null || !userRepository.existsById(member.getId())) {
                    throw new EntityNotFoundException("User", member.getId());
                }
            }
        }
    }
}
