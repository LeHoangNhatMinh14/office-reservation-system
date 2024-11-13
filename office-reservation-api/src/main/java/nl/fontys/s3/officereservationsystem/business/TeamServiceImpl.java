package nl.fontys.s3.officereservationsystem.business;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import nl.fontys.s3.officereservationsystem.business.converter.TeamConverter;
import nl.fontys.s3.officereservationsystem.business.interfaces.TeamService;
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

    @Transactional
    @Override
    public Team save(Team team) {
        TeamEntity teamEntity = TeamConverter.convert(team);
        TeamEntity savedTeamEntity = this.teamRepository.save(teamEntity);

        return TeamConverter.convert(savedTeamEntity);
    }

    @Override
    public List<Team> findAll() {
        return this.teamRepository.findAll().stream()
                .map(TeamConverter::convert)
                .toList();
    }

    @Override
    public Optional<Team> findById(Long id) {
        return this.teamRepository.findById(id).map(TeamConverter::convert);
    }

    @Transactional
    @Override
    public void update(Team team) {
        if(!this.teamRepository.existsById(team.getId())) {
            throw new IllegalArgumentException("Team with id " + team.getId() + " does not exist.");
        }

        TeamEntity teamEntity = TeamConverter.convert(team);
        this.teamRepository.save(teamEntity);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        if(!this.teamRepository.existsById(id)) {
            throw new IllegalArgumentException("Team with id " + id + " does not exist.");
        }

        this.teamRepository.deleteById(id);
    }

    @Override
    public List<Team> filterByUserId(Long id) {
        return this.teamRepository.findByUserId(id).stream()
                .map(TeamConverter::convert)
                .toList();
    }
}
