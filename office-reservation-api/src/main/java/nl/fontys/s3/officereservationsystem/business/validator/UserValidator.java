package nl.fontys.s3.officereservationsystem.business.validator;

import lombok.AllArgsConstructor;
import nl.fontys.s3.officereservationsystem.domain.User;
import nl.fontys.s3.officereservationsystem.business.exception.EmailAlreadyExistsException;
import nl.fontys.s3.officereservationsystem.business.exception.EntityNotFoundException;
import nl.fontys.s3.officereservationsystem.business.exception.InvalidFieldException;
import nl.fontys.s3.officereservationsystem.persistence.UserRepository;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserValidator {
    private final UserRepository userRepository;

    public void validateUserForCreation(User user) {
        validateUserFields(user);
        validateUniqueEmail(user);
    }

    public void validateUserForUpdate(Long id, User user) {
        //validateIdExists(id);
        //validateUserFields(user);
        //validateUniqueEmail(user);
    }

    public void validateIdExists(Long id) {
        if (id == null) throw new InvalidFieldException("User ID");
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("User", id);
        }
    }

    private void validateUserFields(User user) {
        if (user == null) throw new InvalidFieldException("User");

        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new InvalidFieldException("User email");
        }
        if (user.getFirstName() == null || user.getFirstName().trim().isEmpty()) {
            throw new InvalidFieldException("First name");
        }
        if (user.getLastName() == null || user.getLastName().trim().isEmpty()) {
            throw new InvalidFieldException("Last name");
        }
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            throw new InvalidFieldException("Password");
        }
    }

    private void validateUniqueEmail(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new EmailAlreadyExistsException();
        }
    }
}
