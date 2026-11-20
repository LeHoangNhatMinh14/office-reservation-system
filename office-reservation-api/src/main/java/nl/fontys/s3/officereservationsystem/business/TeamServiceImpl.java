package nl.fontys.s3.officereservationsystem.business;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import nl.fontys.s3.officereservationsystem.business.converter.TeamConverter;
import nl.fontys.s3.officereservationsystem.business.interfaces.TeamService;
import nl.fontys.s3.officereservationsystem.business.validator.TeamValidator;
import nl.fontys.s3.officereservationsystem.domain.Team;
import nl.fontys.s3.officereservationsystem.persistence.TeamRepository;
import nl.fontys.s3.officereservationsystem.persistence.entity.TeamEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TeamServiceImpl implements TeamService {
    private final TeamRepository teamRepository;
    private final TeamValidator teamValidator;

    @Transactional
    @Override
    public Team createTeam(Team team) {
        teamValidator.validateTeamForCreation(team);
        TeamEntity teamEntity = TeamConverter.convert(team);
        TeamEntity savedTeamEntity = teamRepository.save(teamEntity);
        return TeamConverter.convert(savedTeamEntity);
    }

    @Override
    public List<Team> getAllTeams() {
        return teamRepository.findAll().stream()
                .map(TeamConverter::convert)
                .toList();
    }

    @Override
    public Optional<Team> getTeamById(Long id) {
        teamValidator.validateIdExists(id);
        return teamRepository.findById(id).map(TeamConverter::convert);
    }

    @Override
    public List<Team> getTeamsByUserId(Long userId) {
        return teamRepository.findByUserId(userId).stream()
                .map(TeamConverter::convert)
                .toList();
    }

    @Transactional
    @Override
    public void updateTeam(Team team) {
        teamValidator.validateTeamForUpdate(team.getId(), team);
        TeamEntity teamEntity = TeamConverter.convert(team);
        teamRepository.save(teamEntity);
    }

    @Transactional
    @Override
    public void deleteTeam(Long id) {
        teamValidator.validateIdExists(id);
        teamRepository.deleteById(id);
    }
}
