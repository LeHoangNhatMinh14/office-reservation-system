package nl.fontys.s3.officereservationsystem.business.validator;

import lombok.AllArgsConstructor;
import nl.fontys.s3.officereservationsystem.domain.Reservation;
import nl.fontys.s3.officereservationsystem.business.exception.InvalidFieldException;
import nl.fontys.s3.officereservationsystem.business.exception.EntityNotFoundException;
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
        if (reservation.getTable() == null) {
            throw new InvalidFieldException("Table");
        }
        if (reservation.getReservationUser() == null) {
            throw new InvalidFieldException("Reservation User");
        }
        if (reservation.getSeatedUser() == null) {
            throw new InvalidFieldException("Seated User");
        }
    }

    private void validateTableExists(Reservation reservation) {
        if (reservation.getTable() != null && !tableRepository.existsById(reservation.getTable().getId())) {
            throw new EntityNotFoundException("Table", reservation.getTable().getId());
        }
    }
}

