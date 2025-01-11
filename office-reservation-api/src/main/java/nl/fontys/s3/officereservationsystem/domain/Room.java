package nl.fontys.s3.officereservationsystem.domain;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Room {
    private Long id;
    private String name;
    private int capacity;
    private int height;
    private int width;
    private List<Table> tables = new ArrayList<>();
}
