package nl.fontys.s3.officereservationsystem.configuration.db;

import lombok.AllArgsConstructor;
import nl.fontys.s3.officereservationsystem.business.interfaces.UserService;
import nl.fontys.s3.officereservationsystem.domain.ReservationType;
import nl.fontys.s3.officereservationsystem.domain.User;
import nl.fontys.s3.officereservationsystem.persistence.ReservationRepository;
import nl.fontys.s3.officereservationsystem.persistence.RoomRepository;
import nl.fontys.s3.officereservationsystem.persistence.TeamRepository;
import nl.fontys.s3.officereservationsystem.persistence.UserRepository;
import nl.fontys.s3.officereservationsystem.persistence.entity.*;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Component
@AllArgsConstructor
public class DatabaseDataInitializer {

    private final RoomRepository roomRepository;
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final ReservationRepository reservationRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void populateDatabaseInitialDummyData() {

        if (userRepository.count() == 0) {
            userService.createUser(User.builder()
                    .firstName("Alice")
                    .lastName("Johnson")
                    .email("alice.johnson@example.com")
                    .password("password123")
                    .isAdmin(false)
                    .build());

            userService.createUser(User.builder()
                    .firstName("Bob")
                    .lastName("Smith")
                    .email("bob.smith@example.com")
                    .password("password456")
                    .isAdmin(true)
                    .build());
        }

        if (roomRepository.count() == 0) {
            TableEntity table1 = TableEntity.builder()
                    .islandNumber(1)
                    .build();
            TableEntity table2 = TableEntity.builder()
                    .islandNumber(2)
                    .build();

            RoomEntity room = RoomEntity.builder()
                    .name("Conference Room A")
                    .tables(List.of(table1, table2))
                    .build();
            roomRepository.save(room);
        }

        if (teamRepository.count() == 0) {
            UserEntity user1 = userRepository.findByEmail("alice.johnson@example.com").orElseThrow();
            UserEntity user2 = userRepository.findByEmail("bob.smith@example.com").orElseThrow();

            TeamEntity team = TeamEntity.builder()
                    .name("Development Team")
                    .users(List.of(user1))
                    .teamManagers(List.of(user2))
                    .build();
            teamRepository.save(team);
        }

        if (reservationRepository.count() == 0) {
            RoomEntity room = roomRepository.findByName("Conference Room A").orElseThrow();
            UserEntity user = userRepository.findByEmail("alice.johnson@example.com").orElseThrow();

            ReservationEntity reservation = ReservationEntity.builder()
                    .date(LocalDate.now())
                    .startTime(LocalTime.of(9, 0))
                    .endTime(LocalTime.of(10, 0))
                    .reservationType(ReservationType.INDIVIDUAL)
                    .tableId(room.getTables().get(0).getId())
                    .teamId(null)
                    .userId(user.getId())
                    .build();
            reservationRepository.save(reservation);
        }
    }
}
