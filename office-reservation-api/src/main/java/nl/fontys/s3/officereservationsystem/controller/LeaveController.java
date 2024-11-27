package nl.fontys.s3.officereservationsystem.controller;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.AllArgsConstructor;
import nl.fontys.s3.officereservationsystem.business.interfaces.LeaveService;
import nl.fontys.s3.officereservationsystem.domain.Leave;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@AllArgsConstructor
@RestController
@RequestMapping("/leave")
public class LeaveController {
    private final LeaveService leaveService;

    // TODO: not working
    @PostMapping("")
    public ResponseEntity<Leave> createLeave(@RequestBody Leave leave) {

        try {
            leaveService.createLeave(leave);
            return ResponseEntity.ok(leave);  // Successfully created
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);  // General error handling
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
