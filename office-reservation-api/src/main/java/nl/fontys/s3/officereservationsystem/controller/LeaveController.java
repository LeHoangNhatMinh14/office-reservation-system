package nl.fontys.s3.officereservationsystem.controller;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.AllArgsConstructor;
import nl.fontys.s3.officereservationsystem.business.interfaces.LeaveService;
import nl.fontys.s3.officereservationsystem.domain.Leave;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/leave")
public class LeaveController {

    private final LeaveService leaveService;

    @PostMapping("")
    public ResponseEntity<Leave> setLeaveDays(@RequestBody Leave leave) {
        leaveService.setLeaveDays(leave);
        return ResponseEntity.ok(leave);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Leave> getLeaveDaysByUserId(@PathVariable Long userId) {
        try {
            return ResponseEntity.ok(leaveService.getLeaveDaysByUserId(userId));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }

    }
}
