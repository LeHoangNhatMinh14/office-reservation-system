package nl.fontys.s3.officereservationsystem.persistence.impl;

import nl.fontys.s3.officereservationsystem.domain.Room;
import nl.fontys.s3.officereservationsystem.persistence.RoomRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class RoomRepositoryImpl implements RoomRepository {
    private final List<Room> rooms = new ArrayList<>();

    public List<Room> findAll() {
        return new ArrayList<>(rooms);
    }

    public Optional<Room> findById(Long id) {
        return rooms.stream()
                .filter(room -> room.getId().equals(id))
                .findFirst();
    }

    public Room save(Room room) {
        // Check if the room already exists (by ID) and update it if found
        deleteById(room.getId());
        rooms.add(room);
        return room;
    }

    public void deleteById(Long id) {
        rooms.removeIf(room -> room.getId().equals(id));
    }
}
