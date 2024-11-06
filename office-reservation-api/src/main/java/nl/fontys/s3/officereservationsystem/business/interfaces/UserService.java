package nl.fontys.s3.officereservationsystem.business.interfaces;

import nl.fontys.s3.officereservationsystem.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    public User createUser(User user);
    public void updateUser(Long id, User updatedUser);
    public void deleteUser(Long id);
    public User getUserByEmail(String email);
    public User getUserById(Long id);
    public List<User> getAllUsers();
}
