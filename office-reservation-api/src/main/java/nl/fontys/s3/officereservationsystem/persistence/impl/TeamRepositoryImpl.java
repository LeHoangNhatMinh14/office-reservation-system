package nl.fontys.s3.officereservationsystem.persistence.impl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.officereservationsystem.domain.Team;
import nl.fontys.s3.officereservationsystem.persistence.TeamRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class TeamRepositoryImpl implements TeamRepository {
    private static long NEXT_ID = 1;
    private final List<Team> savedTeams;

    @Override
    public Team save(Team team) {
        team.setId(NEXT_ID);
        this.savedTeams.add(team);
        NEXT_ID++;
        return team;
    }

    @Override
    public List<Team> findAll() {
        return this.savedTeams;
    }

    @Override
    public Optional<Team> findById(Long id) {
        return this.savedTeams.stream()
                .filter(team -> team.getId().equals(id))
                .findFirst();
    }

    @Override
    public void update(Team team) {
        this.deleteById(team.getId());
        this.savedTeams.add(team);
    }

    @Override
    public void deleteById(Long id) {
        this.savedTeams.removeIf(team -> team.getId().equals(id));
    }

    @Override
    public List<Team> filterByUserId(Long id) {
        return this.savedTeams.stream()
                .filter(team -> team.getUsers().stream()
                        .anyMatch(user -> user.getId().equals(id)))
                .toList();
    }
}
