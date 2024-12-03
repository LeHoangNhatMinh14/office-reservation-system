package nl.fontys.s3.officereservationsystem.business.converter;

import nl.fontys.s3.officereservationsystem.domain.Reservation;
import nl.fontys.s3.officereservationsystem.persistence.entity.ReservationEntity;

public final class ReservationConverter {
    private ReservationConverter() {}

    public static Reservation convert(ReservationEntity entity) {
        return Reservation.builder()
                .id(entity.getId())
                .date(entity.getDate())
                .startTime(entity.getStartTime())
                .endTime(entity.getEndTime())
                .reservationType(entity.getReservationType())
                .tableId(entity.getTableId())
                .teamId(entity.getTeamId())
                .userId(entity.getUserId())
                .build();
    }

    public static ReservationEntity convert(Reservation reservation) {
        return ReservationEntity.builder()
                .id(reservation.getId())
                .date(reservation.getDate())
                .startTime(reservation.getStartTime())
                .endTime(reservation.getEndTime())
                .reservationType(reservation.getReservationType())
                .tableId(reservation.getTableId())
                .teamId(reservation.getTeamId())
                .userId(reservation.getUserId())
                .build();
    }
}
