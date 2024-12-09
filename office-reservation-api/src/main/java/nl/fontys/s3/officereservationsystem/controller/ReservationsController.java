package nl.fontys.s3.officereservationsystem.controller;

import lombok.AllArgsConstructor;
import nl.fontys.s3.officereservationsystem.business.interfaces.ReservationService;
import nl.fontys.s3.officereservationsystem.domain.Reservation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/reservations")
@AllArgsConstructor
public class ReservationsController {
    private final ReservationService reservationService;

    // TODO: reservationService.createReservation() should return a Reservation object
    @PostMapping
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation) {
        reservationService.createReservation(reservation);
        if (reservation.getTableId() == null) {
            throw new IllegalArgumentException("Table cannot be null");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(reservation);
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> getReservationByRoomId(@RequestParam("roomId") Long roomId) {
        List<Reservation> reservations = reservationService.getReservationsByRoomId(roomId);
        return ResponseEntity.status(HttpStatus.OK).body(reservations);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelReservation(@PathVariable("id") Long id) {
        reservationService.cancelReservation(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/weekly")
    public ResponseEntity<List<Reservation>> getAllReservationsWeekly(@RequestParam("date") LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
        List<Reservation> reservations = reservationService.getAllReservationsWeekly(date);
        return ResponseEntity.status(HttpStatus.OK).body(reservations);
    }
    @GetMapping("/isAvailable")
    // example: http://locahost:8080/reservations/isAvailable?tableId=1&date=2022-01-01&startTime=10:00&endTime=12:00
    public ResponseEntity<Boolean> isTableAvailable(@RequestParam("tableId") Long tableId, @RequestParam("date") LocalDate date, @RequestParam("startTime") LocalTime startTime, @RequestParam("endTime") LocalTime endTime) {
        if (tableId == null || date == null || startTime == null || endTime == null) {
            throw new IllegalArgumentException("TableId, date, startTime and endTime cannot be null");
        }
        return ResponseEntity.status(HttpStatus.OK).body(reservationService.isTableAvailable(tableId, date, startTime, endTime));
    }
}
