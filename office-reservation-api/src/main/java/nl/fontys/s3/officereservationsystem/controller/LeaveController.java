package nl.fontys.s3.officereservationsystem.controller;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.AllArgsConstructor;
import nl.fontys.s3.officereservationsystem.business.interfaces.LeaveService;
import nl.fontys.s3.officereservationsystem.domain.Leave;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/leave_days")
@AllArgsConstructor
public class LeaveController {
    private final LeaveService leaveService;

    @PostMapping("")
    public ResponseEntity<Leave> createLeave(@RequestBody Leave leave) {
        System.out.println("Received raw request body: " + leave);
        if (leave.getUserId() == null || leave.getStartDate() == null || leave.getEndDate() == null || leave.getReason() == null) {
            System.err.println("Deserialization failed. Received null fields.");
            return ResponseEntity.badRequest().body(null);
        }
        try {
            leaveService.createLeave(leave);
            return ResponseEntity.ok(leave); // Successfully created
        } catch (IllegalArgumentException e) {
            System.err.println("Error while processing request: " + e.getMessage());
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

    @GetMapping("/user/{id}")
    public ResponseEntity<List<Leave>> getLeaveByUserId(@PathVariable("id") Long id) {
        try {
            List<Leave> leave = leaveService.getLeaveByUserId(id);
            return ResponseEntity.ok(leave);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
