package nl.fontys.s3.officereservationsystem.persistence;

import nl.fontys.s3.officereservationsystem.domain.Reservation;

import java.util.List;

public interface ReservationRepository {
    List<Reservation> findByTableId(Long tableId);
    List<Reservation> getReservationsByRoom(Long roomId);
    void deleteById(Long id);
    void create(Reservation reservation);
}
