package nl.fontys.s3.officereservationsystem.business.interfaces;

import nl.fontys.s3.officereservationsystem.domain.Credential;

public interface SignInService {
    String signIn(Credential credential);
}
