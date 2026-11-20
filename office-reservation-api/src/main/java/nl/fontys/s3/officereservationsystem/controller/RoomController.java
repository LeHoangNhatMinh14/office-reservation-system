package nl.fontys.s3.officereservationsystem.controller;

import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import nl.fontys.s3.officereservationsystem.business.interfaces.RoomService;
import nl.fontys.s3.officereservationsystem.domain.Room;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rooms")
@AllArgsConstructor
public class RoomController {
    private final RoomService roomService;

    @PostMapping
    @RolesAllowed({"ADMIN", "USER"})
    public ResponseEntity<Room> createRoom(@RequestBody Room room) {
        Room createdRoom = roomService.createRoom(room);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRoom);
    }

    @GetMapping
    @RolesAllowed({"ADMIN", "USER"})
    public ResponseEntity<List<Room>> getAllRooms() {
        List<Room> rooms = roomService.getAllRooms();
        return ResponseEntity.status(HttpStatus.OK).body(rooms);
    }

    @GetMapping("/{id}")
    @RolesAllowed({"ADMIN", "USER"})
    public ResponseEntity<Room> getRoomById(@PathVariable("id") Long id) {
        Optional<Room> roomOptional = roomService.getRoomById(id);
        return roomOptional.map(room -> ResponseEntity.ok().body(room))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    //TODO: ask Minh why it returns Room object
    @PutMapping("/{id}")
    @RolesAllowed({"ADMIN", "USER"})
    public ResponseEntity<Room> updateRoom(@PathVariable("id") Long id, @RequestBody Room room) {
        Room updatedRoom = roomService.updateRoom(id, room);
        return ResponseEntity.ok().body(updatedRoom);
    }

    @DeleteMapping("/{id}")
    @RolesAllowed({"ADMIN", "USER"})
    public ResponseEntity<Void> deleteRoom(@PathVariable("id") Long id) {
        roomService.deleteRoom(id);
        return ResponseEntity.ok().build();
    }
}
