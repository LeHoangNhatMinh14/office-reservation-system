package nl.fontys.s3.officereservationsystem.business;

import lombok.AllArgsConstructor;
import nl.fontys.s3.officereservationsystem.business.interfaces.TeamService;
import nl.fontys.s3.officereservationsystem.domain.Team;
import nl.fontys.s3.officereservationsystem.persistence.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TeamServiceImpl implements TeamService {
    private final TeamRepository teamRepository;

    @Override
    public Team save(Team team) {
        return this.teamRepository.save(team);
    }

    @Override
    public List<Team> findAll() {
        return this.teamRepository.findAll();
    }

    @Override
    public Optional<Team> findById(Long id) {
        return this.teamRepository.findById(id);
    }

    @Override
    public void update(Team team) {
        this.teamRepository.update(team);
    }

    @Override
    public void deleteById(Long id) {
        this.teamRepository.deleteById(id);
    }

    @Override
    public List<Team> filterByUserId(Long id) {
        return this.teamRepository.filterByUserId(id);
    }
}
