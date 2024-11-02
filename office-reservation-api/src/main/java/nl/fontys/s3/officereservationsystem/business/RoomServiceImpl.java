package nl.fontys.s3.officereservationsystem.business;

import lombok.AllArgsConstructor;
import nl.fontys.s3.officereservationsystem.business.interfaces.RoomService;
import nl.fontys.s3.officereservationsystem.domain.Room;
import nl.fontys.s3.officereservationsystem.persistence.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;

    @Override
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    @Override
    public Optional<Room> getRoomById(Long id) {
        return roomRepository.findById(id);
    }

    @Override
    public Room createRoom(Room room) {
        return roomRepository.save(room);
    }

    @Override
    public Room updateRoom(Long id, Room room) {
        if (roomRepository.findById(id).isPresent()) {
            room.setId(id);
            return roomRepository.save(room);
        } else {
            throw new IllegalArgumentException("Room with ID " + id + " does not exist.");
        }
    }

    @Override
    public void deleteRoom(Long id) {
        roomRepository.deleteById(id);
    }
}
