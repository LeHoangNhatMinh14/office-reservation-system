package nl.fontys.s3.officereservationsystem.business;

import nl.fontys.s3.officereservationsystem.domain.Leave;
import nl.fontys.s3.officereservationsystem.persistence.LeaveRepository;
import nl.fontys.s3.officereservationsystem.persistence.UserRepository;
import nl.fontys.s3.officereservationsystem.persistence.entity.LeaveEntity;
import nl.fontys.s3.officereservationsystem.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LeaveServiceImplTest {

    @Mock
    private LeaveRepository leaveRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private LeaveServiceImpl leaveService;

    @Test
    void createLeave_shouldCreateLeave() {
        // Arrange
        Leave leave = Leave.builder()
                .userId(1L)
                .startDate(LocalDate.of(2024, 12, 13))
                .endDate(LocalDate.of(2024, 12, 29))
                .reason("Test Reason")
                .build();

        UserEntity user = UserEntity.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("johndoe@example.com")
                .password("password")
                .isAdmin(true)
                .build();

        when(userRepository.findById(leave.getUserId())).thenReturn(Optional.of(user));

        // Act
        leaveService.createLeave(leave);

        // Assert
        verify(userRepository, times(1)).findById(leave.getUserId());
        verify(leaveRepository, times(1)).save(any(LeaveEntity.class));
    }

    @Test
    void createLeave_shouldThrowException_whenUserNotFound() {
        // Arrange
        Leave leave = Leave.builder()
                .userId(1L)
                .startDate(LocalDate.of(2024, 12, 13))
                .endDate(LocalDate.of(2024, 12, 29))
                .reason("Test Reason")
                .build();

        when(userRepository.findById(leave.getUserId()))
                .thenReturn(Optional.empty());

        // Act
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> leaveService.createLeave(leave));

        // Assert
        assertEquals("User with id 1 not found", exception.getMessage());
        verify(userRepository, times(1)).findById(leave.getUserId());
        verify(leaveRepository, times(0)).save(any(LeaveEntity.class));
    }
}