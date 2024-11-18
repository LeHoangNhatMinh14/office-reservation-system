package nl.fontys.s3.officereservationsystem.persistence;

import nl.fontys.s3.officereservationsystem.persistence.entity.TeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TeamRepository extends JpaRepository<TeamEntity, Long> {
    @Query("SELECT t FROM TeamEntity t JOIN t.users u JOIN t.teamManagers m WHERE u.id = :userId OR m.id = :userId")
    List<TeamEntity> findByUserId(@Param("userId") Long userId);
}
