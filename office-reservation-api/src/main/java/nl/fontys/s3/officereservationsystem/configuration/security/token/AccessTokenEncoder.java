package nl.fontys.s3.officereservationsystem.configuration.security.token;

public interface AccessTokenEncoder {
    String encode(AccessToken accessToken);
}
