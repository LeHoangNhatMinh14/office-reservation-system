package nl.fontys.s3.officereservationsystem.controller;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.AllArgsConstructor;
import nl.fontys.s3.officereservationsystem.business.interfaces.LeaveService;
import nl.fontys.s3.officereservationsystem.domain.Leave;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@AllArgsConstructor
@RestController
@RequestMapping("/leave")
public class LeaveController {
    private final LeaveService leaveService;

    // TODO: not working
    @PostMapping("")
    public ResponseEntity<Object> createLeave(@RequestBody Leave leave) {
        System.out.println("Received request body: {}"+ leave);
        try {
            leaveService.createLeave(leave);
            return ResponseEntity.ok(leave); // Successfully created
        } catch (IllegalArgumentException e) {
            // Return the specific error message for bad requests
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            // General error handling with detailed message
            return ResponseEntity.status(500).body(Map.of("error", "An unexpected error occurred: " + e.getMessage()));
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLeave(@PathVariable("id") Long id) {
        try {
            leaveService.deleteLeave(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Leave> getLeaveById(@PathVariable("id") Long id) {
        try {
            Leave leave = leaveService.getLeaveById(id);
            return ResponseEntity.ok(leave);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
