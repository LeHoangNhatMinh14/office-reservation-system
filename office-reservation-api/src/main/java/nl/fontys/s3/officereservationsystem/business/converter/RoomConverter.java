package nl.fontys.s3.officereservationsystem.business.converter;

import nl.fontys.s3.officereservationsystem.domain.Room;
import nl.fontys.s3.officereservationsystem.persistence.entity.RoomEntity;

public final class RoomConverter {
    private RoomConverter() {}

    public static Room convert(RoomEntity entity) {
        return Room.builder()
                .id(entity.getId())
                .name(entity.getName())
                .tables(entity.getTables().stream()
                        .map(TableConverter::convert)
                        .toList())
                .build();
    }

    public static RoomEntity convert(Room room) {
        RoomEntity roomEntity = RoomEntity.builder()
                .id(room.getId())
                .name(room.getName())
                .build();

        roomEntity.setTables(room.getTables().stream()
                .map(TableConverter::convert)
                .toList());

        return roomEntity;
    }
}
