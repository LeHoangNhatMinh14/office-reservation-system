package nl.fontys.s3.officereservationsystem.controller;

import lombok.AllArgsConstructor;
import nl.fontys.s3.officereservationsystem.business.interfaces.TeamService;
import nl.fontys.s3.officereservationsystem.domain.Team;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teams")
@AllArgsConstructor
public class TeamController {
    private final TeamService teamService;

    @PostMapping()
    public ResponseEntity<Team> createTeam(@RequestBody Team team) {
        Team savedTeam = teamService.save(team);
        return ResponseEntity.ok(savedTeam);
    }

    @GetMapping()
    public ResponseEntity<List<Team>> getAllTeams() {
        List<Team> teams = teamService.findAll();
        return ResponseEntity.ok(teams);
    }

    @GetMapping("/filtered/{userId}")
    public ResponseEntity<List<Team>> getTeamsByUserId(@PathVariable Long userId) {
        List<Team> teams = teamService.filterByUserId(userId);
        return ResponseEntity.ok(teams);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Team> getTeamById(@PathVariable("id") Long id) {
        Team team = teamService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Team with id " + id + " does not exist"));
        return ResponseEntity.ok(team);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateTeam(@PathVariable("id") Long id, @RequestBody Team team) {
        team.setId(id);
        teamService.update(team);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable("id") Long id) {
        teamService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
