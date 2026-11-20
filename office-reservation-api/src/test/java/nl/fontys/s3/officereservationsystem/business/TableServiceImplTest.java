package nl.fontys.s3.officereservationsystem.business;

import nl.fontys.s3.officereservationsystem.domain.Table;
import nl.fontys.s3.officereservationsystem.persistence.TableRepository;
import nl.fontys.s3.officereservationsystem.persistence.entity.TableEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TableServiceImplTest {

    @Mock
    private TableRepository tableRepository;

    @InjectMocks
    private TableServiceImpl tableService;

    @Test
    void getAllTables_shouldReturnTables() {
        // Arrange
        TableEntity table = TableEntity.builder()
                .id(1L)
                .build();

        Table expectedTable = Table.builder()
                .id(1L)
                .build();

        when(tableRepository.findAll())
                .thenReturn(List.of(table));

        // Act
        List<Table> result = tableService.getAllTables();

        // Assert
        assertTrue(result.contains(expectedTable));
        verify(tableRepository, times(1)).findAll();
    }

    @Test
    void getTableById_shouldReturnTable() {
        // Arrange
        TableEntity table = TableEntity.builder()
                .id(1L)
                .build();

        Table expectedTable = Table.builder()
                .id(1L)
                .build();

        when(tableRepository.findById(table.getId()))
                .thenReturn(Optional.of(table));

        // Act
        Table result = tableService.getTableById(table.getId());

        // Assert
        assertEquals(expectedTable, result);
        verify(tableRepository, times(1)).findById(table.getId());
    }
}