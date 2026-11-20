package nl.fontys.s3.officereservationsystem.persistence;

import nl.fontys.s3.officereservationsystem.persistence.entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<RoomEntity, Long> {
    Optional<RoomEntity> findByName(String name);
    boolean existsByName(String name);
}
