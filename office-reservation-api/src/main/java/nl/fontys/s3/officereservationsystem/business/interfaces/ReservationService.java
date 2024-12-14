package nl.fontys.s3.officereservationsystem.business.interfaces;

import nl.fontys.s3.officereservationsystem.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ReservationService {
    void createReservation(Reservation reservation);
    List<Reservation> getReservationsByRoomId(Long roomId);
    void cancelReservation(Long id);
    List<Reservation> getAllReservationsWeekly(LocalDate date);
    boolean isTableAvailable(Long tableId, LocalDate date, LocalTime startTime, LocalTime endTime);
}
