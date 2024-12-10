package nl.fontys.s3.officereservationsystem.business;

import nl.fontys.s3.officereservationsystem.business.exception.EntityNotFoundException;
import nl.fontys.s3.officereservationsystem.business.exception.InvalidFieldException;
import nl.fontys.s3.officereservationsystem.business.exception.NameAlreadyExistsException;
import nl.fontys.s3.officereservationsystem.business.validator.RoomValidator;
import nl.fontys.s3.officereservationsystem.domain.Room;
import nl.fontys.s3.officereservationsystem.domain.Table;
import nl.fontys.s3.officereservationsystem.persistence.RoomRepository;
import nl.fontys.s3.officereservationsystem.persistence.entity.RoomEntity;
import nl.fontys.s3.officereservationsystem.persistence.entity.TableEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class RoomServiceImplTest {

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private RoomValidator roomValidator;

    @InjectMocks
    private RoomServiceImpl roomService;

    @Test
    void createRoom_shouldCreateRoom() {
        // Arrange
        Table table = Table.builder()
                .islandNumber(1)
                .build();

        Room room = Room.builder()
                .name("Test Name")
                .tables(List.of(table))
                .build();

        TableEntity tableEntity = TableEntity.builder()
                .id(1L)
                .islandNumber(1)
                .build();

        RoomEntity roomEntity = RoomEntity.builder()
                .id(1L)
                .name("Test Name")
                .tables(List.of(tableEntity))
                .build();

        when(roomRepository.save(any(RoomEntity.class)))
                .thenReturn(roomEntity);

        // Act
        roomService.createRoom(room);

        // Assert
        verify(roomValidator, times(1)).validateRoomForCreation(room);
        verify(roomRepository, times(1)).save(any(RoomEntity.class));
    }

    @ParameterizedTest
    @MethodSource("provideForCreate_shouldThrowException_whenInvalidDataIsSupplied")
    void createRoom_shouldThrowException_whenInvalidDataIsSupplied(Room room, Class<? extends RuntimeException> expectedException, String fieldName) {
        // Arrange
        String expectedMessage;

        if (expectedException.equals(NameAlreadyExistsException.class)) {
            expectedMessage = String.format(
                    "400 BAD_REQUEST \"%s_NAME_ALREADY_EXISTS\"",
                    fieldName.toUpperCase()
            );

            doThrow(new NameAlreadyExistsException(fieldName))
                    .when(roomValidator).validateRoomForCreation(room);
        }
        else {
            expectedMessage = String.format(
                    "400 BAD_REQUEST \"INVALID_%s: %s cannot be blank or null.\"",
                    fieldName.toUpperCase(),
                    fieldName
            );

            doThrow(new InvalidFieldException(fieldName))
                    .when(roomValidator).validateRoomForCreation(room);
        }

        // Act
        Exception exception = assertThrows(expectedException,
                () -> roomService.createRoom(room));

        // Assert
        assertEquals(expectedMessage, exception.getMessage());
        verify(roomValidator, times(1)).validateRoomForCreation(room);
        verify(roomRepository, times(0)).save(any(RoomEntity.class));
    }

    @Test
    void getAllRooms_shouldReturnRooms() {
        // Arrange
        TableEntity table = TableEntity.builder()
                .id(1L)
                .islandNumber(1)
                .build();

        RoomEntity room = RoomEntity.builder()
                .id(1L)
                .name("Test Name")
                .tables(List.of(table))
                .build();

        Table expectedTable = Table.builder()
                .id(1L)
                .islandNumber(1)
                .build();

        Room expectedRoom = Room.builder()
                .id(1L)
                .name("Test Name")
                .tables(List.of(expectedTable))
                .build();

        when(roomRepository.findAll())
                .thenReturn(List.of(room));

        // Act
        List<Room> result = roomService.getAllRooms();

        // Assert
        assertTrue(result.contains(expectedRoom));
        verify(roomRepository, times(1)).findAll();
    }

    @Test
    void getRoomById_shouldReturnRoom() {
        // Arrange
        TableEntity table = TableEntity.builder()
                .id(1L)
                .islandNumber(1)
                .build();

        RoomEntity room = RoomEntity.builder()
                .id(1L)
                .name("Test Name")
                .tables(List.of(table))
                .build();

        Table expectedTable = Table.builder()
                .id(1L)
                .islandNumber(1)
                .build();

        Room expectedRoom = Room.builder()
                .id(1L)
                .name("Test Name")
                .tables(List.of(expectedTable))
                .build();

        when(roomRepository.findById(room.getId()))
                .thenReturn(Optional.of(room));

        // Act
        Optional<Room> result = roomService.getRoomById(room.getId());

        // Assert
        assertTrue(result.isPresent());
        assertEquals(expectedRoom, result.get());
        verify(roomValidator, times(1)).validateIdExists(room.getId());
        verify(roomRepository, times(1)).findById(room.getId());
    }

    @Test
    void getRoomById_shouldThrowException_whenRoomNotFound() {
        // Arrange
        Long roomId = 1L;

        String expectedMessage = String.format(
                "404 NOT_FOUND \"ROOM_NOT_FOUND: ID %s does not exist.\"",
                roomId
        );

        doThrow(new EntityNotFoundException("Room", roomId))
                .when(roomValidator).validateIdExists(roomId);

        // Act
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> roomService.getRoomById(roomId));

        // Assert
        assertEquals(expectedMessage, exception.getMessage());
        verify(roomValidator, times(1)).validateIdExists(roomId);
        verify(roomRepository, times(0)).findById(roomId);
    }

    @Test
    void deleteRoom_shouldDeleteRoom() {
        // Arrange
        Long roomId = 1L;

        // Act
        roomService.deleteRoom(roomId);

        // Assert
        verify(roomRepository, times(1)).deleteById(roomId);
    }

    @Test
    void deleteRoom_shouldThrowException_whenRoomNotFound() {
        // Arrange
        Long roomId = 1L;

        String expectedMessage = String.format(
                "404 NOT_FOUND \"ROOM_NOT_FOUND: ID %s does not exist.\"",
                roomId
        );

        doThrow(new EntityNotFoundException("Room", roomId))
                .when(roomValidator).validateIdExists(roomId);

        // Act
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> roomService.deleteRoom(roomId));

        // Assert
        assertEquals(expectedMessage, exception.getMessage());
        verify(roomValidator, times(1)).validateIdExists(roomId);
        verify(roomRepository, times(0)).deleteById(roomId);
    }

    private static Stream<Arguments> provideForCreate_shouldThrowException_whenInvalidDataIsSupplied() {
        return Stream.of(
                Arguments.of(null, InvalidFieldException.class, "Room"),
                Arguments.of(new Room(null, " ", List.of(new Table(null, 5))), InvalidFieldException.class, "Room name"),
                Arguments.of(new Room(null, "Test Name", List.of(new Table(null, 5))), NameAlreadyExistsException.class, "Room"),
                Arguments.of(new Room(null, "Test Name", null), InvalidFieldException.class, "Room tables")
        );
    }
}