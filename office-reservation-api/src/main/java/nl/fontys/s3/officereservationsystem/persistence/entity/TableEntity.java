package nl.fontys.s3.officereservationsystem.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "room_table")
public class TableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "vertical_position", nullable = false)
    private int verticalPosition;

    @Column(name = "horizontal_position", nullable = false)
    private int horizontalPosition;

    @Enumerated(EnumType.STRING)
    @Column(name = "table_type")
    private TableEntityType tableType;
}
