package nl.fontys.s3.officereservationsystem.persistence;

import nl.fontys.s3.officereservationsystem.domain.Reservation;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository {
    List<Reservation> findByTableId(Long tableId);
    List<Reservation> getReservationsByRoom(Long roomId);
    void deleteById(Long id);
    void create(Reservation reservation);
}
