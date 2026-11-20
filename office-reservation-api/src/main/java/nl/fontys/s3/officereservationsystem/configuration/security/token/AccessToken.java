package nl.fontys.s3.officereservationsystem.configuration.security.token;

import java.util.Set;

public interface AccessToken {
    String getSubject();
    Set<String> getRoles();
    Long getUserId();
}
