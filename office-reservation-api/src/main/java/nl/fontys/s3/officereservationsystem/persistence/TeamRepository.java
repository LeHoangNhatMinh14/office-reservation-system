package nl.fontys.s3.officereservationsystem.persistence;

import nl.fontys.s3.officereservationsystem.persistence.entity.TeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamRepository extends JpaRepository<TeamEntity, Long> {
    List<TeamEntity> filterByUserId(Long userId);
}
