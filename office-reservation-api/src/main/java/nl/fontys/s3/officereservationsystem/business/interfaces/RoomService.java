package nl.fontys.s3.officereservationsystem.business.interfaces;

import nl.fontys.s3.officereservationsystem.domain.Room;

import java.util.List;
import java.util.Optional;

public interface RoomService {
    List<Room> getAllRooms();
    Optional<Room> getRoomById(Long id);
    Room createRoom(Room room);
    Room updateRoom(Long id, Room room);
    void deleteRoom(Long id);
}
