package nl.fontys.s3.officereservationsystem.business;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import nl.fontys.s3.officereservationsystem.business.converter.RoomConverter;
import nl.fontys.s3.officereservationsystem.business.interfaces.RoomService;
import nl.fontys.s3.officereservationsystem.business.validator.RoomValidator;
import nl.fontys.s3.officereservationsystem.domain.Room;
import nl.fontys.s3.officereservationsystem.persistence.RoomRepository;
import nl.fontys.s3.officereservationsystem.persistence.entity.RoomEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    private final RoomValidator roomValidator;

    @Transactional
    @Override
    public Room createRoom(Room room) {
        roomValidator.validateRoomForCreation(room);
        RoomEntity roomEntity = RoomConverter.convert(room);
        RoomEntity savedRoomEntity = roomRepository.save(roomEntity);
        return RoomConverter.convert(savedRoomEntity);
    }

    @Override
    public List<Room> getAllRooms() {
        return roomRepository.findAll().stream()
                .map(RoomConverter::convert)
                .toList();
    }

    @Override
    public Optional<Room> getRoomById(Long id) {
        roomValidator.validateIdExists(id);
        return roomRepository.findById(id).map(RoomConverter::convert);
    }

    @Transactional
    @Override
    public Room updateRoom(Long id, Room room) {
        roomValidator.validateRoomForUpdate(id, room);
        room.setId(id);
        RoomEntity roomEntity = RoomConverter.convert(room);
        RoomEntity savedRoomEntity = roomRepository.save(roomEntity);
        return RoomConverter.convert(savedRoomEntity);
    }

    @Transactional
    @Override
    public void deleteRoom(Long id) {
        roomValidator.validateIdExists(id);
        roomRepository.deleteById(id);
    }
}
