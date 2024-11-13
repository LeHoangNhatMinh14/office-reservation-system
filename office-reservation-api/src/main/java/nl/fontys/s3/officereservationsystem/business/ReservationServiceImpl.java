package nl.fontys.s3.officereservationsystem.business;

import lombok.AllArgsConstructor;
import nl.fontys.s3.officereservationsystem.business.interfaces.ReservationService;
import nl.fontys.s3.officereservationsystem.domain.Reservation;
import nl.fontys.s3.officereservationsystem.persistence.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

    public void createReservation(Reservation reservation){
        reservationRepository.create(reservation);
    }

    public void cancelReservation(Long id) {
        try {
            reservationRepository.deleteById(id);
        } catch (Exception e) {
            throw new IllegalArgumentException("Reservation with id " + id + " not found");
        }
    }

    public List<Reservation> getReservationsByRoom(Long roomId) {
        try {
            return reservationRepository.getReservationsByRoom(roomId);
        } catch (Exception e) {
            throw new IllegalArgumentException("Room with id " + roomId + " not found");
        }
    }

}
