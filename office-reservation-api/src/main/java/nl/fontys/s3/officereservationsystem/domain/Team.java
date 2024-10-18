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
public class Team {
    private Long id;
    private String name;
    private List<User> users;
    private List<User> teamManagers;
}
