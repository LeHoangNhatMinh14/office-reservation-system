package nl.fontys.s3.officereservationsystem.business;

import lombok.AllArgsConstructor;
import nl.fontys.s3.officereservationsystem.business.converter.UserConverter;
import nl.fontys.s3.officereservationsystem.business.interfaces.UserService;
import nl.fontys.s3.officereservationsystem.domain.User;
import nl.fontys.s3.officereservationsystem.persistence.UserRepository;
import nl.fontys.s3.officereservationsystem.persistence.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;



    public List<User> getAllUsers() {
        // Retrieve the list of users (currently generic `List<?>`)
        List<?> users = userRepository.findAll();

        // Convert each user and collect the results into a new list
        return users.stream()
                .map(user -> UserConverter.convert((UserEntity) user)) // Convert each user
                .filter(Objects::nonNull)          // Ensure only valid Users are included
                .map(User.class::cast)                   // Cast to User after filtering
                .toList();                               // Collect into a list
    }

    public Optional<User> getUserById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }

        return Optional.ofNullable(userRepository.findById(id).map(UserConverter::convert)
                .orElseThrow(() -> new NoSuchElementException("User with id " + id + " does not exist")));
    }

    public Optional<User> getUserByEmail(String email) {
        if (email == null) {
            throw new IllegalArgumentException("Email cannot be null");
        }
        return Optional.ofNullable(userRepository.findByEmail(email).map(UserConverter::convert)
                .orElseThrow(() -> new NoSuchElementException("User with email " + email + " does not exist")));

    }

    public void createUser(User user) {
        UserEntity newUser = UserConverter.convert(user);
        userRepository.save(newUser);
    }

    public void updateUser(Long id, User updatedUser) {
        Optional<UserEntity> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            deleteUser(id);
            userRepository.save(UserConverter.convert(updatedUser));
        } else {
            throw new IllegalArgumentException("User with id " + id + " does not exist");
        }
    }


        public void deleteUser(Long id) {
            Optional<UserEntity> existingUser = userRepository.findById(id);
            if (existingUser.isPresent()) {
                userRepository.deleteById(id);
            }
            else{
                throw new IllegalArgumentException("User with id " + id + " does not exist");
            }
        }
        public User assignRole(Long id, String role) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User with id " + id + " does not exist"));
        if (user.getRoles() == null) {
            user.setRoles(new ArrayList<>());
        }
        user.getRoles().add(role); // Assuming `roles` is a List<String> or similar
        return userRepository.save(user);
    }
    }




    
