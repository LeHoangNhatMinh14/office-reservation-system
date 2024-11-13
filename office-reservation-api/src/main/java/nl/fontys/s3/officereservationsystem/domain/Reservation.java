package nl.fontys.s3.officereservationsystem.domain;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {
    private Long id;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private Table table;
    private ReservationType reservationType;
    private User seatedUser;
    private User reservationUser;
}
