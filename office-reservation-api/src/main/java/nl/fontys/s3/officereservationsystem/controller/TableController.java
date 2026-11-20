package nl.fontys.s3.officereservationsystem.controller;

import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import nl.fontys.s3.officereservationsystem.business.interfaces.TableService;
import nl.fontys.s3.officereservationsystem.domain.Table;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/table")
public class TableController {
    private final TableService tableService;

    @GetMapping("/{id}")
    @RolesAllowed({"ADMIN", "USER"})
    public ResponseEntity<Table> getTableById(@PathVariable("id") Long id) {
        Table table = tableService.getTableById(id);
        return ResponseEntity.ok().body(table);
    }

    @GetMapping("/all")
    @RolesAllowed({"ADMIN", "USER"})
    public ResponseEntity<List<Table>> getAllTables() {
        List<Table> table = tableService.getAllTables();
        return ResponseEntity.ok().body(table);
    }
}
