package nl.fontys.s3.officereservationsystem.business.interfaces;

import nl.fontys.s3.officereservationsystem.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User createUser(User user);
    void updateUser(Long id, User updatedUser);
    void deleteUser(Long id);
    Optional<User> getUserByEmail(String email);
    Optional<User> getUserById(Long id);
    List<User> getAllUsers();
}
