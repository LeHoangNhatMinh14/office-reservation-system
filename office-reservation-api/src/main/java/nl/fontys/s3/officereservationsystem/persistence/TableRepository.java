package nl.fontys.s3.officereservationsystem.persistence;

import nl.fontys.s3.officereservationsystem.persistence.entity.TableEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TableRepository extends JpaRepository<TableEntity, Long> {
}
