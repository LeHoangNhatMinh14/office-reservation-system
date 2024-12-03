package nl.fontys.s3.officereservationsystem.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Leave {
    private Long id;
    private Long userId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String reason;
}
