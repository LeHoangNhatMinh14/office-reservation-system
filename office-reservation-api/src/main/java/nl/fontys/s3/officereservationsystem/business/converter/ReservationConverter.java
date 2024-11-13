package nl.fontys.s3.officereservationsystem.business.converter;

import nl.fontys.s3.officereservationsystem.domain.Reservation;
import nl.fontys.s3.officereservationsystem.persistence.entity.ReservationEntity;
import nl.fontys.s3.officereservationsystem.persistence.entity.TableEntity;
import nl.fontys.s3.officereservationsystem.persistence.entity.UserEntity;

public final class ReservationConverter {
    private ReservationConverter() {}

    public static Reservation convert(ReservationEntity entity) {
        return Reservation.builder()
                .id(entity.getId())
                .date(entity.getDate())
                .startTime(entity.getStartTime())
                .endTime(entity.getEndTime())
                .reservationType(entity.getReservationType())
                .tableId(entity.getTable().getId())
                .seatedUserId(entity.getSeatedUser().getId())
                .reservationUserId(entity.getReservationUser().getId())
                .build();
    }

    public static ReservationEntity convert(Reservation reservation, TableEntity table, UserEntity seatedUser, UserEntity reservationUser) {
        return ReservationEntity.builder()
                .id(reservation.getId())
                .date(reservation.getDate())
                .startTime(reservation.getStartTime())
                .endTime(reservation.getEndTime())
                .reservationType(reservation.getReservationType())
                .table(table)
                .seatedUser(seatedUser)
                .reservationUser(reservationUser)
                .build();
    }
}
