package nl.fontys.s3.officereservationsystem.business.validator;

import lombok.AllArgsConstructor;
import nl.fontys.s3.officereservationsystem.domain.Reservation;
import nl.fontys.s3.officereservationsystem.business.exception.InvalidFieldException;
import nl.fontys.s3.officereservationsystem.business.exception.EntityNotFoundException;
import nl.fontys.s3.officereservationsystem.domain.ReservationType;
import nl.fontys.s3.officereservationsystem.persistence.TableRepository;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ReservationValidator {
    private final TableRepository tableRepository;

    public void validateReservationForCreation(Reservation reservation) {
        validateReservationFields(reservation);
        validateTableExists(reservation);
    }

    private void validateReservationFields(Reservation reservation) {
        if (reservation == null) throw new InvalidFieldException("Reservation");

        if (reservation.getDate() == null) {
            throw new InvalidFieldException("Date");
        }
        if (reservation.getStartTime() == null) {
            throw new InvalidFieldException("Start Time");
        }
        if (reservation.getEndTime() == null) {
            throw new InvalidFieldException("End Time");
        }
        if (reservation.getTableId() == null) {
            throw new InvalidFieldException("Table");
        }
        if (reservation.getReservationType() == null) {
            throw new InvalidFieldException("Reservation Type");
        }
        if (reservation.getReservationType().equals(ReservationType.INDIVIDUAL)) {
            if (reservation.getUserId() == null) {
                throw new InvalidFieldException("User");
            }
        }
        if (reservation.getReservationType().equals(ReservationType.TEAM)) {
            if (reservation.getTeamId() == null) {
                throw new InvalidFieldException("Team");
            }
        }
    }

    private void validateTableExists(Reservation reservation) {
        if (reservation.getTableId() != null && !tableRepository.existsById(reservation.getTableId())) {
            throw new EntityNotFoundException("Table", reservation.getTableId());
        }
    }
}

