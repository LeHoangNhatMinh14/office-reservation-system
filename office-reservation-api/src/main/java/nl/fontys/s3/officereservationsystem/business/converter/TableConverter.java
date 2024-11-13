package nl.fontys.s3.officereservationsystem.business.converter;

import nl.fontys.s3.officereservationsystem.domain.Table;
import nl.fontys.s3.officereservationsystem.persistence.entity.TableEntity;
import nl.fontys.s3.officereservationsystem.persistence.entity.RoomEntity;

public final class TableConverter {
    private TableConverter() {}

    public static Table convert(TableEntity entity) {
        return Table.builder()
                .id(entity.getId())
                .islandNumber(entity.getIslandNumber())
                .build();
    }

    public static TableEntity convert(Table table, RoomEntity room) {
        return TableEntity.builder()
                .id(table.getId())
                .islandNumber(table.getIslandNumber())
                .room(room)
                .build();
    }
}
