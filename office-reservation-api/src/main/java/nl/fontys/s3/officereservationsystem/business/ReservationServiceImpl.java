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

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

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

        if (!isTableAvailable(entity.getTableId(), entity.getDate(), entity.getStartTime(), entity.getEndTime())) {
            throw new IllegalArgumentException("Table is already reserved for the given time slot.");
        }

        reservationRepository.save(entity);
    }

    public boolean isTableAvailable(Long tableId, LocalDate date, LocalTime startTime, LocalTime endTime) {
        List<ReservationEntity> existingReservations = reservationRepository.findByTableIdAndDate(tableId, date);

        return existingReservations.stream().noneMatch(existingReservation ->
                isTimeOverlap(existingReservation.getStartTime(), existingReservation.getEndTime(), startTime, endTime)
        );
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
    public List<Reservation> getAllReservationsWeekly(LocalDate date) {
        LocalDate startOfWeek = date.with(DayOfWeek.MONDAY);
        LocalDate endOfWeek = date.with(DayOfWeek.SUNDAY);

        // Log the start and end of the week
        System.out.println("Start of Week: " + startOfWeek);
        System.out.println("End of Week: " + endOfWeek);

        // Fetch and filter reservations
        List<Reservation> reservations = reservationRepository.findAll().stream()
                .map(ReservationConverter::convert)
                .filter(reservation -> {
                    LocalDate reservationDate = reservation.getDate(); // Ensure this is a LocalDate
                    boolean isWithinRange = !reservationDate.isBefore(startOfWeek) &&
                            !reservationDate.isAfter(endOfWeek);
                    System.out.println("Reservation: " + reservation + " | Within Range: " + isWithinRange);
                    return isWithinRange;
                })
                .collect(Collectors.toList());

        // Log the final list of reservations
        System.out.println("Filtered Reservations for Week: " + reservations);

        return reservations;
    }

}
