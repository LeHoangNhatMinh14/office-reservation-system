package nl.fontys.s3.officereservationsystem.business.converter;

import nl.fontys.s3.officereservationsystem.domain.Table;
import nl.fontys.s3.officereservationsystem.domain.TableType;
import nl.fontys.s3.officereservationsystem.persistence.entity.TableEntity;
import nl.fontys.s3.officereservationsystem.persistence.entity.TableEntityType;

public final class TableConverter {
    private TableConverter() {
    }

    public static Table convert(TableEntity entity) {
        TableEntityType tableType = entity.getTableType();
        return Table.builder()
                .id(entity.getId())
                .tableType(tableType == null ? null : TableType.valueOf(tableType.name()))
                .horizontalPosition(entity.getHorizontalPosition())
                .verticalPosition(entity.getVerticalPosition())
                .build();
    }

    public static TableEntity convert(Table table) {
        TableType tableType = table.getTableType();
        return TableEntity.builder()
                .id(table.getId())
                .tableType(tableType == null ? null : TableEntityType.valueOf(tableType.name()))
                .horizontalPosition(table.getHorizontalPosition())
                .verticalPosition(table.getVerticalPosition())
                .build();
    }
}
