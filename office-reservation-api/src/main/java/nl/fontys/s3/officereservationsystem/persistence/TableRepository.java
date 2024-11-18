package nl.fontys.s3.officereservationsystem.persistence;

import nl.fontys.s3.officereservationsystem.domain.Table;
import nl.fontys.s3.officereservationsystem.persistence.entity.TableEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TableRepository extends JpaRepository<TableEntity, Long> {
    List<TableEntity> findByRoomId(Long roomId);
    List<Table> getTablesByRoomId(Long roomId);
}
