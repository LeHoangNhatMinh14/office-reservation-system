package nl.fontys.s3.officereservationsystem.persistence;

import nl.fontys.s3.officereservationsystem.domain.Team;

import java.util.List;
import java.util.Optional;

public interface TeamRepository {
    Team save(Team team);
    List<Team> findAll();
    Optional<Team> findById(Long id);
    void update(Team team);
    void deleteById(Long id);
    List<Team> filterByUserId(Long id);
}
