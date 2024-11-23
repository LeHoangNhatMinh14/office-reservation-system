package nl.fontys.s3.officereservationsystem.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class EntityNotFoundException extends ResponseStatusException {
    public EntityNotFoundException(String entityName, Long id) {
        super(HttpStatus.NOT_FOUND, entityName.toUpperCase() + "_NOT_FOUND: ID " + id + " does not exist.");
    }
    public EntityNotFoundException(String entityName, String identifier) {
        super(HttpStatus.NOT_FOUND, entityName.toUpperCase() + "_NOT_FOUND: " + identifier + " does not exist.");
    }
}
