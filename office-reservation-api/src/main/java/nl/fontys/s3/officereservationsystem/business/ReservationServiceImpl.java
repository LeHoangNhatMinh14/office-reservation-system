package nl.fontys.s3.officereservationsystem.business;

import lombok.AllArgsConstructor;
import nl.fontys.s3.officereservationsystem.business.converter.ReservationConverter;
import nl.fontys.s3.officereservationsystem.business.interfaces.ReservationService;
import nl.fontys.s3.officereservationsystem.domain.Reservation;
import nl.fontys.s3.officereservationsystem.persistence.ReservationRepository;
import nl.fontys.s3.officereservationsystem.persistence.RoomRepository;
import nl.fontys.s3.officereservationsystem.persistence.entity.ReservationEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;

    @Override
    public void createReservation(Reservation reservation){
        ReservationEntity entity = ReservationConverter.convert(reservation);
        reservationRepository.save(entity);
    }

    @Override
    public void cancelReservation(Long id) {
        if (!reservationRepository.existsById(id)) {
            throw new IllegalArgumentException("Reservation with id " + id + " not found");
        }

        reservationRepository.deleteById(id);
    }

    @Override
    public List<Reservation> getReservationsByRoom(Long roomId) {
        if (!roomRepository.existsById(roomId)) {
            throw new IllegalArgumentException("Room with id " + roomId + " not found");
        }

        return reservationRepository.findByRoomId(roomId).stream()
                .map(ReservationConverter::convert)
                .toList();
    }
}
