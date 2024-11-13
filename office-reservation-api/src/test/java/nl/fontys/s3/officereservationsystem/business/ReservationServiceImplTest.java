package nl.fontys.s3.officereservationsystem.business;

import nl.fontys.s3.officereservationsystem.domain.ReservationType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import nl.fontys.s3.officereservationsystem.business.interfaces.ReservationService;
import nl.fontys.s3.officereservationsystem.domain.Reservation;
import nl.fontys.s3.officereservationsystem.persistence.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceImplTest {

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationServiceImpl reservationService;

    private Reservation sampleReservation;
    private Reservation newReservation;

    @BeforeEach
    public void setup() {
        sampleReservation = Reservation.builder()
                .id(1L)
                .date(LocalDate.of(2024, 11, 13))
                .startTime(LocalTime.of(9, 0))
                .endTime(LocalTime.of(10, 0))
                .tableId(1L)
                .build();
        newReservation = Reservation.builder()
                .id(2L)
                .date(LocalDate.of(2024, 11, 13))
                .startTime(LocalTime.of(9, 0))
                .endTime(LocalTime.of(10, 0))
                .tableId(1L)
                .reservationType(ReservationType.INDIVIDUAL) // Add this for completeness
                .seatedUserId(103L)
                .reservationUserId(204L)
                .build();
    }

    @Test
    public void createReservation_shouldAddReservation() {
        reservationService.createReservation(sampleReservation);
        verify(reservationRepository, times(1)).create(sampleReservation);
    }

    @Test
    public void cancelReservation_existingReservation_shouldDeleteReservation() {
        doNothing().when(reservationRepository).deleteById(sampleReservation.getId());

        reservationService.cancelReservation(sampleReservation.getId());

        verify(reservationRepository, times(1)).deleteById(sampleReservation.getId());
    }

    @Test
    public void cancelReservation_nonExistingReservation_shouldThrowException() {
        doThrow(new NoSuchElementException("Reservation with id 99 not found"))
                .when(reservationRepository).deleteById(99L);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> reservationService.cancelReservation(99L));
        assertEquals("Reservation with id 99 not found", exception.getMessage());
    }

    @Test
    public void getReservationsByRoom_existingRoom_shouldReturnReservations() {
        when(reservationRepository.getReservationsByRoom(1L)).thenReturn(List.of(sampleReservation));

        List<Reservation> reservations = reservationService.getReservationsByRoom(1L);

        assertNotNull(reservations);
        assertEquals(1, reservations.size());
        assertEquals(sampleReservation, reservations.get(0));
    }

    @Test
    public void getReservationsByRoom_nonExistingRoom_shouldReturnEmptyList() {
        when(reservationRepository.getReservationsByRoom(2L)).thenReturn(Collections.emptyList());

        List<Reservation> reservations = reservationService.getReservationsByRoom(2L);

        assertNotNull(reservations);
        assertTrue(reservations.isEmpty());
    }





}
