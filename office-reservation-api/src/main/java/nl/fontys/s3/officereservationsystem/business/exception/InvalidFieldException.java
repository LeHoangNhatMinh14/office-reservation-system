package nl.fontys.s3.officereservationsystem.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidFieldException extends ResponseStatusException {
    public InvalidFieldException(String fieldName) {
        super(HttpStatus.BAD_REQUEST, "INVALID_" + fieldName.toUpperCase() + ": " + fieldName + " cannot be blank or null.");
    }
    public InvalidFieldException(String fieldName, String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
