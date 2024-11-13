package nl.fontys.s3.officereservationsystem.persistence;

import nl.fontys.s3.officereservationsystem.domain.ReservationType;
import nl.fontys.s3.officereservationsystem.persistence.entity.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {
    List<ReservationEntity> findByTableId(Long tableId);
    List<ReservationEntity> findByReservationType(ReservationType reservationType);
}
