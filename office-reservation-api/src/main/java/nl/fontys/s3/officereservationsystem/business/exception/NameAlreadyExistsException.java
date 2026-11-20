package nl.fontys.s3.officereservationsystem.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NameAlreadyExistsException extends ResponseStatusException {
    public NameAlreadyExistsException(String entityName) {
        super(HttpStatus.BAD_REQUEST, entityName.toUpperCase() + "_NAME_ALREADY_EXISTS");
    }
    public NameAlreadyExistsException(String entityName, String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
