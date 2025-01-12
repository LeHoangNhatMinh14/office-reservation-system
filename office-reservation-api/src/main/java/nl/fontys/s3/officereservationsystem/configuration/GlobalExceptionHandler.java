package nl.fontys.s3.officereservationsystem.configuration;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, Object>> handleResponseStatusException(ResponseStatusException ex) {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("status", ex.getStatusCode().value());
        errorDetails.put("error", ex.getStatusCode().toString()); // Use toString() for error code
        errorDetails.put("message", ex.getReason()); // Include the "reason" field from the exception
        errorDetails.put("timestamp", LocalDateTime.now());

        return ResponseEntity.status(ex.getStatusCode()).body(errorDetails);
    }
}


