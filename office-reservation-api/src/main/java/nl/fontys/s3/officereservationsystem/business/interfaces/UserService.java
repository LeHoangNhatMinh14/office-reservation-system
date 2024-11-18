package nl.fontys.s3.officereservationsystem.business.interfaces;

import nl.fontys.s3.officereservationsystem.domain.User;
import nl.fontys.s3.officereservationsystem.persistence.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {

    void createUser(User user);
    List<User> getAllUsers();
    Optional<User> getUserById(Long id);
    Optional<User> getUserByEmail(String email);
    void updateUser(Long id, User updatedUser);
    void deleteUser(Long id);

    User assignRole(Long id, String role);

}
