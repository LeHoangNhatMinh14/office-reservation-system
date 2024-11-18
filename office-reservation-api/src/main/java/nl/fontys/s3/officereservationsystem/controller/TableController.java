package nl.fontys.s3.officereservationsystem.controller;

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

    @PostMapping("")
    public ResponseEntity<Table> createTable(@RequestBody Table table) {
       try {
           tableService.createTable(table);
           return ResponseEntity.ok().body(table);
         } catch (Exception e) {
           return ResponseEntity.badRequest().body(null);
         }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Table> getTableById(@PathVariable("id") Long id) {
        Table table = tableService.getTableById(id);
        return ResponseEntity.ok().body(table);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Table>> getAllTables() {
        List<Table> table = tableService.getAllTables();
        return ResponseEntity.ok().body(table);
    }
}
