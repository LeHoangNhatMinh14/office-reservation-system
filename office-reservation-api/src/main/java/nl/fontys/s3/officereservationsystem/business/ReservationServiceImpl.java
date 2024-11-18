package nl.fontys.s3.officereservationsystem.business;


import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import nl.fontys.s3.officereservationsystem.business.converter.ReservationConverter;
import nl.fontys.s3.officereservationsystem.business.interfaces.ReservationService;
import nl.fontys.s3.officereservationsystem.domain.Reservation;
import nl.fontys.s3.officereservationsystem.domain.Room;
import nl.fontys.s3.officereservationsystem.domain.Table;
import nl.fontys.s3.officereservationsystem.persistence.ReservationRepository;
import nl.fontys.s3.officereservationsystem.persistence.RoomRepository;
import nl.fontys.s3.officereservationsystem.persistence.TableRepository;
import nl.fontys.s3.officereservationsystem.persistence.entity.ReservationEntity;
import nl.fontys.s3.officereservationsystem.persistence.entity.RoomEntity;
import nl.fontys.s3.officereservationsystem.persistence.entity.TableEntity;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;
    private final TableRepository tableRepository;

    @Transactional
    @Override
    public void createReservation(Reservation reservation) {
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


    public List<Reservation> getReservationsByRoomId(Long roomId) {
        List<Reservation> roomReservations = new ArrayList<>();

        // Step 1: Find room by roomId
        RoomEntity room = roomRepository.findById(roomId).orElse(null);
        if (room == null) {
            return roomReservations; // Return an empty list if room is not found
        }

        // Step 2: Get the tables associated with the room
        List<TableEntity> tables = room.getTables();  // Assuming room has a getTables method

        // Step 3: For each table, get reservations and add them to roomReservations
        for (TableEntity table : tables) {
            Long tableId = table.getId();

            // Find reservations by tableId and convert them to Reservation domain objects
            List<ReservationEntity> tableReservations = reservationRepository.findByTableId(tableId);

            // Convert each ReservationEntity to Reservation and add to the list
            for (ReservationEntity entity : tableReservations) {
                roomReservations.add(ReservationConverter.convert(entity));
            }
        }
        return roomReservations;

    }

    @Transactional
    @Override
    public void cancelReservation(Long id) {
        if (!reservationRepository.existsById(id)) {
            throw new IllegalArgumentException("Reservation with id " + id + " not found");
        }

        reservationRepository.deleteById(id);
    }
}
