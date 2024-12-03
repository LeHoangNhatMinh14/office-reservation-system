package nl.fontys.s3.officereservationsystem.business;

import lombok.AllArgsConstructor;
import nl.fontys.s3.officereservationsystem.business.converter.TableConverter;
import nl.fontys.s3.officereservationsystem.business.interfaces.TableService;
import nl.fontys.s3.officereservationsystem.domain.Table;
import nl.fontys.s3.officereservationsystem.persistence.TableRepository;
import nl.fontys.s3.officereservationsystem.persistence.entity.TableEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class TableServiceImpl implements TableService {
    private final TableRepository tableRepository;

    public Table getTableById(Long id) {
        return TableConverter.convert(Objects.requireNonNull(tableRepository.findById(id).orElse(null)));
    }

    public List<Table> getAllTables() {
        List<TableEntity> tables = tableRepository.findAll();
        List<Table> tableList = new ArrayList<>();
        for (TableEntity table : tables) {
            tableList.add(TableConverter.convert(table));
        }
        return tableList;
    }
}
