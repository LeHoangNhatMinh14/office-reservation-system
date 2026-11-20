package nl.fontys.s3.officereservationsystem.persistence;

import nl.fontys.s3.officereservationsystem.persistence.entity.LeaveEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface LeaveRepository extends JpaRepository<LeaveEntity, Long> {
    List<LeaveEntity> findByUserId(Long userId);
}
