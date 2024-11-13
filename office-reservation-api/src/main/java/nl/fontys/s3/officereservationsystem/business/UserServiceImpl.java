package nl.fontys.s3.officereservationsystem.business;

import nl.fontys.s3.officereservationsystem.business.interfaces.UserService;
import nl.fontys.s3.officereservationsystem.domain.User;
import nl.fontys.s3.officereservationsystem.persistence.entity.UserRepositoryImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepositoryImpl userRepository;

    public UserServiceImpl(UserRepositoryImpl userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User with id " + id + " does not exist"));
    }

    public User getUserByEmail(String email) {
        if (email == null) {
            throw new IllegalArgumentException("Email cannot be null");
        }
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("User with email " + email + " does not exist"));

    }

    public User createUser(User user) {
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