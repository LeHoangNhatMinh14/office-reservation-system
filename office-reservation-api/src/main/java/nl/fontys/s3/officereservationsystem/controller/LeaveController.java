package nl.fontys.s3.officereservationsystem.controller;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import nl.fontys.s3.officereservationsystem.business.interfaces.LeaveService;
import nl.fontys.s3.officereservationsystem.domain.Leave;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/leave_days")
@AllArgsConstructor
public class LeaveController {
    private final LeaveService leaveService;

    @PostMapping("")
    public ResponseEntity<Leave> createLeave(@RequestBody Leave leave) {
        System.out.println("Received request body: {}"+ leave);
        try {
            leaveService.createLeave(leave);
            return ResponseEntity.ok(leave); // Successfully created
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build(); // Bad request
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
