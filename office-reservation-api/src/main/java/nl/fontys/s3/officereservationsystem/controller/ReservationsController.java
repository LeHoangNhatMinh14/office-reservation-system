package nl.fontys.s3.officereservationsystem.controller;

import lombok.AllArgsConstructor;
import nl.fontys.s3.officereservationsystem.business.interfaces.ReservationService;
import nl.fontys.s3.officereservationsystem.domain.Reservation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            // Handle the error, e.g., log it or throw a custom exception
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

    // TODO: set waiting times for reservations
}
