    package nl.fontys.s3.officereservationsystem.business;

    import jakarta.transaction.Transactional;
    import lombok.AllArgsConstructor;
    import nl.fontys.s3.officereservationsystem.business.converter.UserConverter;
    import nl.fontys.s3.officereservationsystem.business.interfaces.UserService;
    import nl.fontys.s3.officereservationsystem.business.validator.UserValidator;
    import nl.fontys.s3.officereservationsystem.domain.User;
    import nl.fontys.s3.officereservationsystem.persistence.UserRepository;
    import nl.fontys.s3.officereservationsystem.persistence.entity.UserEntity;
    import org.springframework.stereotype.Service;

    import java.util.List;
    import java.util.Optional;

    @Service
    @AllArgsConstructor
    public class UserServiceImpl implements UserService {
        private final UserRepository userRepository;
        private final UserValidator userValidator;

        @Transactional
        @Override
        public User createUser(User user) {
            userValidator.validateUserForCreation(user);
            UserEntity userEntity = UserConverter.convert(user);
            UserEntity savedUserEntity = userRepository.save(userEntity);
            return UserConverter.convert(savedUserEntity);
        }

        @Override
        public List<User> getAllUsers() {
            return userRepository.findAll().stream()
                    .map(UserConverter::convert)
                    .toList();
        }

        @Override
        public Optional<User> getUserById(Long id) {
            userValidator.validateIdExists(id);
            return userRepository.findById(id)
                    .map(UserConverter::convert);
        }

        @Override
        public Optional<User> getUserByEmail(String email) {
            return userRepository.findByEmail(email)
                    .map(UserConverter::convert);
        }

        @Transactional
        @Override
        public void updateUser(Long id, User updatedUser) {
            userValidator.validateUserForUpdate(id, updatedUser);
            updatedUser.setId(id);
            UserEntity userEntity = UserConverter.convert(updatedUser);
            userRepository.save(userEntity);
        }

        @Transactional
        @Override
        public void deleteUser(Long id) {
            userValidator.validateIdExists(id);
            userRepository.deleteById(id);
        }
    }
