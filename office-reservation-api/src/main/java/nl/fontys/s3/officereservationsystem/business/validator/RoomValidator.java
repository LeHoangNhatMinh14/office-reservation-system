package nl.fontys.s3.officereservationsystem.business.validator;

import lombok.AllArgsConstructor;
import nl.fontys.s3.officereservationsystem.domain.Room;
import nl.fontys.s3.officereservationsystem.business.exception.InvalidFieldException;
import nl.fontys.s3.officereservationsystem.business.exception.NameAlreadyExistsException;
import nl.fontys.s3.officereservationsystem.business.exception.EntityNotFoundException;
import nl.fontys.s3.officereservationsystem.persistence.RoomRepository;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RoomValidator {
    private final RoomRepository roomRepository;

    public void validateRoomForCreation(Room room) {
        validateRoomFields(room);
        validateUniqueName(room);
    }

    public void validateRoomForUpdate(Long id, Room room) {
        validateIdExists(id);
        validateRoomFields(room);
    }

    private void validateRoomFields(Room room) {
        if (room == null) throw new InvalidFieldException("Room");

        if (room.getName() == null || room.getName().trim().isEmpty()) {
            throw new InvalidFieldException("Room name");
        }
    }

    private void validateUniqueName(Room room) {
        if (roomRepository.existsByName(room.getName())) {
            throw new NameAlreadyExistsException("Room");
        }
    }

    public void validateIdExists(Long id) {
        if (id == null) throw new InvalidFieldException("Room ID");
        if (!roomRepository.existsById(id)) {
            throw new EntityNotFoundException("Room", id);
        }
    }
}
