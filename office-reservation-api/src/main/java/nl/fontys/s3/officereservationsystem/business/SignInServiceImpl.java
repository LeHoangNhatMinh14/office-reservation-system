package nl.fontys.s3.officereservationsystem.business;

import lombok.AllArgsConstructor;
import nl.fontys.s3.officereservationsystem.business.exception.EntityNotFoundException;
import nl.fontys.s3.officereservationsystem.business.interfaces.SignInService;
import nl.fontys.s3.officereservationsystem.configuration.security.token.AccessTokenEncoder;
import nl.fontys.s3.officereservationsystem.configuration.security.token.impl.AccessTokenImpl;
import nl.fontys.s3.officereservationsystem.domain.Credential;
import nl.fontys.s3.officereservationsystem.persistence.UserRepository;
import nl.fontys.s3.officereservationsystem.persistence.entity.UserEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SignInServiceImpl implements SignInService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccessTokenEncoder accessTokenEncoder;

    @Override
    public String signIn(Credential credential) {
        Optional<UserEntity> userOptional = userRepository.findByEmail(credential.getEmail());

        if (userOptional.isEmpty()) {
            throw new EntityNotFoundException("User", credential.getEmail());
        }

        UserEntity userEntity = userOptional.get();

        if (!matchesPassword(credential.getPassword(), userEntity.getPassword())) {
            throw new EntityNotFoundException("User", credential.getPassword());
        }

        return generateAccessToken(userEntity);
    }

    private boolean matchesPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    private String generateAccessToken(UserEntity userEntity) {
        Long userId = userEntity.getId();
        List<String> roles;

        if (userEntity.isAdmin()) {
            roles = List.of("ADMIN");
        }
        else {
            roles = List.of("USER");
        }

        return accessTokenEncoder.encode(
                new AccessTokenImpl(userEntity.getEmail(), userId, roles));
    }
}
