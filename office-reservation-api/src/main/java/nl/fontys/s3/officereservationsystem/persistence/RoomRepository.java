package nl.fontys.s3.officereservationsystem.persistence;

import nl.fontys.s3.officereservationsystem.persistence.entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<RoomEntity, Long> {
}
