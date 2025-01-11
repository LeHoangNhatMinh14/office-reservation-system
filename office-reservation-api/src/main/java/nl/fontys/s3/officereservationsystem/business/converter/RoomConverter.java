package nl.fontys.s3.officereservationsystem.business.converter;

import nl.fontys.s3.officereservationsystem.domain.Room;
import nl.fontys.s3.officereservationsystem.persistence.entity.RoomEntity;
import nl.fontys.s3.officereservationsystem.persistence.entity.TableEntityType;

public final class RoomConverter {
    private RoomConverter() {
    }

    public static Room convert(RoomEntity entity) {
        int capacity = entity.getTables().stream()
                .mapToInt(tableEntity -> tableEntity.getTableType() == TableEntityType.SMALL_TABLE ? 4 : 8)
                .sum();

        return Room.builder()
                .id(entity.getId())
                .name(entity.getName())
                .capacity(capacity)
                .height(entity.getHeight())
                .width(entity.getWidth())
                .tables(entity.getTables().stream()
                        .map(TableConverter::convert)
                        .toList())
                .build();
    }

    public static RoomEntity convert(Room room) {
        RoomEntity roomEntity = RoomEntity.builder()
                .id(room.getId())
                .name(room.getName())
                .height(room.getHeight())
                .width(room.getWidth())
                .build();

        roomEntity.setTables(room.getTables().stream()
                .map(TableConverter::convert)
                .toList());

        return roomEntity;
    }
}
