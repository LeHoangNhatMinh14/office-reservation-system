package nl.fontys.s3.officereservationsystem.controller;

import lombok.AllArgsConstructor;
import nl.fontys.s3.officereservationsystem.business.ReservationServiceImpl;
import nl.fontys.s3.officereservationsystem.business.interfaces.ReservationService;
import nl.fontys.s3.officereservationsystem.domain.Reservation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations")
@AllArgsConstructor
public class ReservationsController {

    private final ReservationService reservationService;

    @GetMapping("")
    // ex: http://localhost:8080/reservations?roomId=1
    public ResponseEntity<List<Reservation>> getReservationByRoomId(@RequestParam("roomId") Long roomId) {
        List<Reservation> reservations = reservationService.getReservationsByRoom(roomId);
        return ResponseEntity.ok(reservations);
    }

    @PostMapping("")
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation) {
        reservationService.createReservation(reservation);
        return ResponseEntity.ok(reservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelReservation(@PathVariable Long id) {
        reservationService.cancelReservation(id);
        return ResponseEntity.noContent().build();
    }

    // TODO: set waiting times for reservations
}
