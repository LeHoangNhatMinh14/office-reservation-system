package nl.fontys.s3.officereservationsystem.business;

import lombok.AllArgsConstructor;
import nl.fontys.s3.officereservationsystem.business.converter.RoomConverter;
import nl.fontys.s3.officereservationsystem.business.interfaces.RoomService;
import nl.fontys.s3.officereservationsystem.domain.Room;
import nl.fontys.s3.officereservationsystem.persistence.RoomRepository;
import nl.fontys.s3.officereservationsystem.persistence.entity.RoomEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;

    @Override
    public List<Room> getAllRooms() {
        return roomRepository.findAll().stream()
                .map(RoomConverter::convert)
                .toList();
    }

    @Override
    public Optional<Room> getRoomById(Long id) {
        return roomRepository.findById(id).map(RoomConverter::convert);
    }

    @Override
    public Room createRoom(Room room) {
        RoomEntity roomEntity = RoomConverter.convert(room);
        RoomEntity savedRoomEntity = roomRepository.save(roomEntity);

        return RoomConverter.convert(savedRoomEntity);
    }

    @Override
    public Room updateRoom(Long id, Room room) {
        if (!roomRepository.existsById(id)) {
            throw new IllegalArgumentException("Room with ID " + id + " does not exist.");
        }

        room.setId(id);
        RoomEntity roomEntity = RoomConverter.convert(room);
        RoomEntity savedRoomEntity = roomRepository.save(roomEntity);

        return RoomConverter.convert(savedRoomEntity);
    }

    @Override
    public void deleteRoom(Long id) {
        if (!roomRepository.existsById(id)) {
            throw new IllegalArgumentException("Room with id " + id + " does not exist.");
        }

        roomRepository.deleteById(id);
    }
}
