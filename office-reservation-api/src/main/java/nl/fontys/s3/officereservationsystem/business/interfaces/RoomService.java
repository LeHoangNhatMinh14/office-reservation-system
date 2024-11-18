package nl.fontys.s3.officereservationsystem.business.interfaces;

import nl.fontys.s3.officereservationsystem.domain.Room;

import java.util.List;
import java.util.Optional;

public interface RoomService {
    Room createRoom(Room room);
    List<Room> getAllRooms();
    Optional<Room> getRoomById(Long id);
    Room updateRoom(Long id, Room room);
    void deleteRoom(Long id);
}
