package nl.fontys.s3.officereservationsystem.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Room {
    private Long id;
    private String name;
    private List<Table> tables;
}
