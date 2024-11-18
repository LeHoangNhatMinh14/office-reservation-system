package nl.fontys.s3.officereservationsystem.business.converter;

import nl.fontys.s3.officereservationsystem.domain.User;
import nl.fontys.s3.officereservationsystem.persistence.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public final class UserConverter {
    private UserConverter() {}

    public static User convert(UserEntity entity) {
        return User.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .isAdmin(entity.isAdmin())
                .build();
    }

    public static UserEntity convert(User user) {
        return UserEntity.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .password(user.getPassword())
                .isAdmin(user.isAdmin())
                .build();
    }



}
