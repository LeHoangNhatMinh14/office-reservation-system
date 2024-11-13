package nl.fontys.s3.officereservationsystem.persistence.impl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.officereservationsystem.domain.Reservation;
import nl.fontys.s3.officereservationsystem.domain.Room;
import nl.fontys.s3.officereservationsystem.domain.Table;
import nl.fontys.s3.officereservationsystem.persistence.ReservationRepository;
import nl.fontys.s3.officereservationsystem.persistence.RoomRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Repository
@AllArgsConstructor
public class ReservationRepositoryImpl implements ReservationRepository {
    private final List<Reservation> reservations = new ArrayList<>();
    private final RoomRepository roomRepository;

    public void create(Reservation reservation) {
        for (Reservation existingReservation : reservations) {
            if (existingReservation.getTableId().equals(reservation.getTableId()) &&
                    existingReservation.getDate().equals(reservation.getDate()) &&
                    isTimeOverlap(existingReservation.getStartTime(), existingReservation.getEndTime(),
                            reservation.getStartTime(), reservation.getEndTime())) {
                throw new IllegalArgumentException("Table is already reserved for the given time slot.");
            }
        }
        reservations.add(reservation);
    }

    private boolean isTimeOverlap(LocalTime start1, LocalTime end1, LocalTime start2, LocalTime end2) {
        return (start1.isBefore(end2) && end1.isAfter(start2));
    }

    public List<Reservation> findByTableId(Long tableId) {
        List<Reservation> tableReservations = new ArrayList<>();

        for (Reservation reservation : reservations) {
            if (reservation.getTableId().equals(tableId)) {
                tableReservations.add(reservation);
            }
        }

        return tableReservations;
    }

    public List<Reservation> getReservationsByRoom(Long roomId) {
        List<Reservation> roomReservations = new ArrayList<>();

        // Find room by roomId
        Room room = roomRepository.findById(roomId).orElse(null);
        if (room == null) {
            return roomReservations; // Return an empty list if room is not found
        }

        List<Table> tables = room.getTables();

        for (Table table : tables) {
            Long tableId = table.getId();

            // Find reservations by tableId and add them to roomReservations
            List<Reservation> tableReservations = findByTableId(tableId);
            roomReservations.addAll(tableReservations);
        }

        return roomReservations;
    }

    public void deleteById(Long id) {
        boolean removed = reservations.removeIf(reservation -> reservation.getId().equals(id));
        if (!removed) {
            throw new NoSuchElementException("Reservation with id " + id + " not found");
        }    }

}
