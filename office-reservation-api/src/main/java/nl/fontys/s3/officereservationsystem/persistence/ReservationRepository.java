package nl.fontys.s3.officereservationsystem.persistence;

import nl.fontys.s3.officereservationsystem.domain.ReservationType;
import nl.fontys.s3.officereservationsystem.persistence.entity.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {
    @Query(value = "SELECT r.* FROM reservation r JOIN room_table t ON r.table_id = t.id WHERE t.room_id = :roomId", nativeQuery = true)
    List<ReservationEntity> findByRoomId(@Param("roomId") Long roomId);
    List<ReservationEntity> findByTableId(Long tableId);
    List<ReservationEntity> findByReservationType(ReservationType reservationType);
}

