package nl.fontys.s3.officereservationsystem.controller;

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
    public ResponseEntity<Team> createTeam(@RequestBody Team team) {
        Team createdTeam = teamService.save(team);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTeam);
    }

    @GetMapping
    public ResponseEntity<List<Team>> getAllTeams() {
        List<Team> teams = teamService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(teams);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Team> getTeamById(@PathVariable("id") Long id) {
        Optional<Team> teamOptional = teamService.findById(id);
        return teamOptional.map(team -> ResponseEntity.ok().body(team))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Team>> getTeamsByUserId(@RequestParam("userId") Long userId) {
        List<Team> teams = teamService.filterByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(teams);
    }

    //TODO: teamService.update(team) should take (id, team) as parameter
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
