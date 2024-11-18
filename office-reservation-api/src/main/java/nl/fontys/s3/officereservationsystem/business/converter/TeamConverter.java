package nl.fontys.s3.officereservationsystem.business.converter;

import nl.fontys.s3.officereservationsystem.domain.Team;
import nl.fontys.s3.officereservationsystem.persistence.entity.TeamEntity;

public final class TeamConverter {
    private TeamConverter() {}

    public static Team convert(TeamEntity entity) {
        return Team.builder()
                .id(entity.getId())
                .name(entity.getName())
                .users(entity.getUsers().stream()
                        .map(UserConverter::convert)
                        .toList())
                .teamManagers(entity.getTeamManagers().stream()
                        .map(UserConverter::convert)
                        .toList())
                .build();
    }

    public static TeamEntity convert(Team team) {
        return TeamEntity.builder()
                .id(team.getId())
                .name(team.getName())
                .users(team.getUsers().stream()
                        .map(UserConverter::convert)
                        .toList())
                .teamManagers(team.getTeamManagers().stream()
                        .map(UserConverter::convert)
                        .toList())
                .build();
    }
}
