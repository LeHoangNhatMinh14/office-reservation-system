package nl.fontys.s3.officereservationsystem.business;

import nl.fontys.s3.officereservationsystem.persistence.TableRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TableServiceImplTest {

    @Mock
    private TableRepository tableRepository;

    @InjectMocks
    private TableServiceImpl tableService;
}