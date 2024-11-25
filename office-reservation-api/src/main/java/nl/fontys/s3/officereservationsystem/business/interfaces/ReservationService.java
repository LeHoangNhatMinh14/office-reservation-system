package nl.fontys.s3.officereservationsystem.business.interfaces;

import nl.fontys.s3.officereservationsystem.domain.Reservation;

import java.util.List;

public interface ReservationService {
    void createReservation(Reservation reservation);
    List<Reservation> getReservationsByRoomId(Long roomId);
    void cancelReservation(Long id);
    List<Reservation> getReservationsByTableId(Long tableid);
}
