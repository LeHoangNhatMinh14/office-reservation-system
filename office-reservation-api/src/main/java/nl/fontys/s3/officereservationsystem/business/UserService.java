package nl.fontys.s3.officereservationsystem.business;

import nl.fontys.s3.officereservationsystem.domain.User;
import nl.fontys.s3.officereservationsystem.persistence.UserRepository;
import nl.fontys.s3.officereservationsystem.persistence.impl.UserRepositoryImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepositoryImpl userRepository;

    public UserService(UserRepositoryImpl userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User createUser(User user) {
        if (user.getId() != null && userRepository.findById(user.getId()).isPresent()) {
            throw new IllegalArgumentException("User with id " + user.getId() + " already exists");
        }
        return userRepository.save(user);
    }

    public void updateUser(Long id, User updatedUser) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            deleteUser(id);
            userRepository.save(updatedUser);
        }
        else {
            throw new IllegalArgumentException("User with id " + id + " does not exist");
        }

    }

    public void deleteUser(Long id) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            userRepository.deleteById(id);
        }

        else{
            throw new IllegalArgumentException("User with id " + id + " does not exist");
        }
    }
}