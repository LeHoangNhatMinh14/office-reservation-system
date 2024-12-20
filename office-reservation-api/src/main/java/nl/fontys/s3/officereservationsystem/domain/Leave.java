package nl.fontys.s3.officereservationsystem.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonProperty;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Leave {
    private Long id;

    private Long userId;

    private LocalDate startDate;

    private LocalDate endDate;

    private String reason;
}

