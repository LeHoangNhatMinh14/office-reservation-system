package nl.fontys.s3.officereservationsystem.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class TeamManagerRequirementException extends ResponseStatusException {
    public TeamManagerRequirementException() {
        super(HttpStatus.BAD_REQUEST, "TEAM_MANAGER_REQUIREMENT_NOT_MET: At least one manager is required.");
    }
}
