package nl.fontys.s3.officereservationsystem.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import nl.fontys.s3.officereservationsystem.business.interfaces.SignInService;
import nl.fontys.s3.officereservationsystem.domain.Credential;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tokens")
@AllArgsConstructor
public class SignInController {
    private final SignInService signInService;

    @PostMapping
    public ResponseEntity<String> signIn(@RequestBody Credential credential) {
        String response = signInService.signIn(credential);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
