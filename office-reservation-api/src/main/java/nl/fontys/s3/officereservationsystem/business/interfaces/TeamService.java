package nl.fontys.s3.officereservationsystem.business.interfaces;

import nl.fontys.s3.officereservationsystem.domain.Team;

import java.util.List;
import java.util.Optional;

public interface TeamService {
    Team createTeam(Team team);
    List<Team> getAllTeams();
    Optional<Team> getTeamById(Long id);
    List<Team> getTeamsByUserId(Long id);
    void updateTeam(Team team);
    void deleteTeam(Long id);
}
