package nl.fontys.s3.officereservationsystem.business;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import nl.fontys.s3.officereservationsystem.business.converter.ReservationConverter;
import nl.fontys.s3.officereservationsystem.business.interfaces.ReservationService;
import nl.fontys.s3.officereservationsystem.business.validator.ReservationValidator;
import nl.fontys.s3.officereservationsystem.business.validator.RoomValidator;
import nl.fontys.s3.officereservationsystem.domain.Reservation;
import nl.fontys.s3.officereservationsystem.persistence.ReservationRepository;
import nl.fontys.s3.officereservationsystem.persistence.entity.ReservationEntity;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationValidator reservationValidator;
    private final RoomValidator roomValidator;

    @Transactional
    @Override
    public void createReservation(Reservation reservation) {
        reservationValidator.validateReservationForCreation(reservation);
        ReservationEntity entity = ReservationConverter.convert(reservation);
        List<ReservationEntity> existingReservations = reservationRepository.findByTableId(entity.getTable().getId());

        for (ReservationEntity existingReservation : existingReservations) {
            if (existingReservation.getDate().equals(entity.getDate()) &&
                    isTimeOverlap(existingReservation.getStartTime(), existingReservation.getEndTime(),
                            entity.getStartTime(), entity.getEndTime())) {
                throw new IllegalArgumentException("Table is already reserved for the given time slot.");
            }
        }
        reservationRepository.save(entity);
    }

    private boolean isTimeOverlap(LocalTime start1, LocalTime end1, LocalTime start2, LocalTime end2) {
        return (start1.isBefore(end2) && end1.isAfter(start2));
    }

    @Override
    public List<Reservation> getReservationsByRoomId(Long roomId) {
        roomValidator.validateIdExists(roomId);
        return reservationRepository.findByRoomId(roomId).stream()
                .map(ReservationConverter::convert)
                .toList();
    }

    @Transactional
    @Override
    public void cancelReservation(Long id) {
        if (!reservationRepository.existsById(id)) {
            throw new IllegalArgumentException("Reservation with id " + id + " not found");
        }
        reservationRepository.deleteById(id);
    }

    @Override
    public List<Reservation> getReservationsByTableId(Long tableid) {
        return reservationRepository.findByTableId(roomId).stream()
                .map(ReservationConverter::convert)
                .toList();
}
