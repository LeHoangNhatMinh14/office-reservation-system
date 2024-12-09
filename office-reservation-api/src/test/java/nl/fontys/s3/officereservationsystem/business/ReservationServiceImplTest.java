package nl.fontys.s3.officereservationsystem.business;

import nl.fontys.s3.officereservationsystem.business.exception.EntityNotFoundException;
import nl.fontys.s3.officereservationsystem.business.exception.InvalidFieldException;
import nl.fontys.s3.officereservationsystem.business.validator.ReservationValidator;
import nl.fontys.s3.officereservationsystem.business.validator.RoomValidator;
import nl.fontys.s3.officereservationsystem.domain.Reservation;
import nl.fontys.s3.officereservationsystem.domain.ReservationType;
import nl.fontys.s3.officereservationsystem.persistence.ReservationRepository;
import nl.fontys.s3.officereservationsystem.persistence.entity.ReservationEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class ReservationServiceImplTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private ReservationValidator reservationValidator;

    @Mock
    private RoomValidator roomValidator;

    @InjectMocks
    private ReservationServiceImpl reservationService;

    @Test
    void createReservation_shouldCreateReservation() {
        // Arrange
        Reservation reservation = Reservation.builder()
                .date(LocalDate.now())
                .startTime(LocalTime.of(9, 0))
                .endTime(LocalTime.of(10, 0))
                .reservationType(ReservationType.TEAM)
                .tableId(1L)
                .userId(null)
                .teamId(1L)
                .build();

        when(reservationRepository.findByTableId(reservation.getTableId()))
                .thenReturn(List.of());

        // Act
        reservationService.createReservation(reservation);

        // Assert
        verify(reservationValidator, times(1)).validateReservationForCreation(reservation);
        verify(reservationRepository, times(1)).findByTableId(reservation.getTableId());
        verify(reservationRepository, times(1)).save(any(ReservationEntity.class));
    }

    @Test
    void createLeave_shouldThrowException_whenTimeOverlaps() {
        // Arrange
        Reservation reservation = Reservation.builder()
                .date(LocalDate.now())
                .startTime(LocalTime.of(9, 0))
                .endTime(LocalTime.of(10, 0))
                .reservationType(ReservationType.TEAM)
                .tableId(1L)
                .userId(null)
                .teamId(1L)
                .build();

        ReservationEntity reservationOverlap = ReservationEntity.builder()
                .date(LocalDate.now())
                .startTime(LocalTime.of(9, 0))
                .endTime(LocalTime.of(10, 0))
                .reservationType(ReservationType.TEAM)
                .tableId(1L)
                .userId(null)
                .teamId(2L)
                .build();

        when(reservationRepository.findByTableId(reservation.getTableId()))
                .thenReturn(List.of(reservationOverlap));

        // Act
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> reservationService.createReservation(reservation));

        // Assert
        assertEquals("Table is already reserved for the given time slot.", exception.getMessage());
        verify(reservationValidator, times(1)).validateReservationForCreation(reservation);
        verify(reservationRepository, times(1)).findByTableId(reservation.getTableId());
        verify(reservationRepository, times(0)).save(any(ReservationEntity.class));
    }

    @ParameterizedTest
    @MethodSource("provideForCreate_shouldThrowException_whenInvalidDataIsSupplied")
    void createLeave_shouldThrowException_whenInvalidDataIsSupplied(Reservation reservation, String fieldName) {
        // Arrange
        String expectedMessage = String.format(
                "400 BAD_REQUEST \"INVALID_%s: %s cannot be blank or null.\"",
                fieldName.toUpperCase(),
                fieldName
        );

        doThrow(new InvalidFieldException(fieldName))
                .when(reservationValidator).validateReservationForCreation(reservation);

        // Act
        InvalidFieldException exception = assertThrows(InvalidFieldException.class,
                () -> reservationService.createReservation(reservation));

        // Assert
        assertEquals(expectedMessage, exception.getMessage());
        verify(reservationValidator, times(1)).validateReservationForCreation(reservation);
        verify(reservationRepository, times(0)).findByTableId(reservation.getTableId());
        verify(reservationRepository, times(0)).save(any(ReservationEntity.class));
    }

    @Test
    void getReservationsByRoomId_shouldReturnReservations() {
        // Arrange
        Long roomId = 1L;

        ReservationEntity reservation = ReservationEntity.builder()
                .date(LocalDate.now())
                .startTime(LocalTime.of(9, 0))
                .endTime(LocalTime.of(10, 0))
                .reservationType(ReservationType.TEAM)
                .tableId(1L)
                .userId(null)
                .teamId(1L)
                .build();

        Reservation expectedReservation = Reservation.builder()
                .date(LocalDate.now())
                .startTime(LocalTime.of(9, 0))
                .endTime(LocalTime.of(10, 0))
                .reservationType(ReservationType.TEAM)
                .tableId(1L)
                .userId(null)
                .teamId(1L)
                .build();

        when(reservationRepository.findByRoomId(roomId))
                .thenReturn(List.of(reservation));

        // Act
        List<Reservation> result = reservationService.getReservationsByRoomId(roomId);

        // Assert
        assertEquals(List.of(expectedReservation), result);
        verify(roomValidator, times(1)).validateIdExists(roomId);
        verify(reservationRepository, times(1)).findByRoomId(roomId);
    }

    @Test
    void getReservationsByRoomId_shouldThrowException_whenRoomNotFound() {
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
                () -> reservationService.getReservationsByRoomId(roomId));

        // Assert
        assertEquals(expectedMessage, exception.getMessage());
        verify(roomValidator, times(1)).validateIdExists(roomId);
        verify(reservationRepository, times(0)).findByRoomId(roomId);
    }

    @Test
    void deleteReservation_shouldDeleteReservation() {
        // Arrange
        Long reservationId = 1L;

        when(reservationRepository.existsById(reservationId))
                .thenReturn(true);

        // Act
        reservationService.cancelReservation(reservationId);

        // Assert
        verify(reservationRepository, times(1)).existsById(reservationId);
        verify(reservationRepository, times(1)).deleteById(reservationId);
    }

    @Test
    void deleteReservation_shouldThrowException_whenReservationNotFound() {
        // Arrange
        Long reservationId = 1L;

        when(reservationRepository.existsById(reservationId))
                .thenReturn(false);

        // Act
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> reservationService.cancelReservation(reservationId));

        // Assert
        assertEquals("Reservation with id 1 not found", exception.getMessage());
        verify(reservationRepository, times(1)).existsById(reservationId);
        verify(reservationRepository, times(0)).deleteById(reservationId);
    }

    private static Stream<Arguments> provideForCreate_shouldThrowException_whenInvalidDataIsSupplied() {
        return Stream.of(
                Arguments.of(
                        new Reservation(null, LocalDate.now(), LocalTime.of(10, 0), LocalTime.of(11, 0), 1L, ReservationType.INDIVIDUAL, null, null),
                        "User"
                ),
                Arguments.of(
                        new Reservation(null, LocalDate.now(), LocalTime.of(10, 0), LocalTime.of(11, 0), 1L, ReservationType.TEAM, null, null),
                        "Team"
                ),
                Arguments.of(
                        new Reservation(null, null, LocalTime.of(10, 0), LocalTime.of(11, 0), 1L, ReservationType.INDIVIDUAL, null, 1L),
                        "Date"
                ),
                Arguments.of(
                        new Reservation(null, LocalDate.now(), null, LocalTime.of(11, 0), 1L, ReservationType.INDIVIDUAL, null, 1L),
                        "Start Time"
                ),
                Arguments.of(
                        new Reservation(null, LocalDate.now(), LocalTime.of(10, 0), null, 1L, ReservationType.INDIVIDUAL, null, 1L),
                        "End Time"
                ),
                Arguments.of(
                        new Reservation(null, LocalDate.now(), LocalTime.of(10, 0), LocalTime.of(11, 0), null, ReservationType.INDIVIDUAL, null, 1L),
                        "Table"
                )
        );
    }
}