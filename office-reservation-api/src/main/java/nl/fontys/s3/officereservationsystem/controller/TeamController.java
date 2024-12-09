package nl.fontys.s3.officereservationsystem.controller;

import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import nl.fontys.s3.officereservationsystem.business.interfaces.TeamService;
import nl.fontys.s3.officereservationsystem.domain.Team;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/teams")
@AllArgsConstructor
public class TeamController {
    private final TeamService teamService;

    @PostMapping
    @RolesAllowed("ADMIN")
    public ResponseEntity<Team> createTeam(@RequestBody Team team) {
        Team createdTeam = teamService.createTeam(team);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTeam);
    }

    @GetMapping
    @RolesAllowed({"ADMIN", "USER"})
    public ResponseEntity<List<Team>> getAllTeams() {
        List<Team> teams = teamService.getAllTeams();
        return ResponseEntity.status(HttpStatus.OK).body(teams);
    }

    @GetMapping("/{id}")
    @RolesAllowed({"ADMIN", "USER"})
    public ResponseEntity<Team> getTeamById(@PathVariable("id") Long id) {
        Optional<Team> teamOptional = teamService.getTeamById(id);
        return teamOptional.map(team -> ResponseEntity.ok().body(team))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    @RolesAllowed({"ADMIN", "USER"})
    public ResponseEntity<List<Team>> getTeamsByUserId(@PathVariable("userId") Long userId) {
        List<Team> teams = teamService.getTeamsByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(teams);
    }

    //TODO: teamService.update(team) should take (id, team) as parameter
    @PutMapping("/{id}")
    @RolesAllowed({"ADMIN", "USER"})
    public ResponseEntity<Void> updateTeam(@PathVariable("id") Long id, @RequestBody Team team) {
        team.setId(id);
        teamService.updateTeam(team);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @RolesAllowed("ADMIN")
    public ResponseEntity<Void> deleteTeam(@PathVariable("id") Long id) {
        teamService.deleteTeam(id);
        return ResponseEntity.ok().build();
    }
}
