package nl.fontys.s3.officereservationsystem.business.converter;

import nl.fontys.s3.officereservationsystem.domain.Reservation;
import nl.fontys.s3.officereservationsystem.persistence.entity.ReservationEntity;
import nl.fontys.s3.officereservationsystem.business.converter.UserConverter;
import nl.fontys.s3.officereservationsystem.business.converter.TableConverter;

public final class ReservationConverter {
    private ReservationConverter() {}

    public static Reservation convert(ReservationEntity entity) {
        return Reservation.builder()
                .id(entity.getId())
                .date(entity.getDate())
                .startTime(entity.getStartTime())
                .endTime(entity.getEndTime())
                .reservationType(entity.getReservationType())
                .table(TableConverter.convert(entity.getTable()))
                .seatedUser(UserConverter.convert(entity.getSeatedUser()))
                .reservationUser(UserConverter.convert(entity.getReservationUser()))
                .build();
    }

    public static ReservationEntity convert(Reservation reservation) {
        return ReservationEntity.builder()
                .id(reservation.getId())
                .date(reservation.getDate())
                .startTime(reservation.getStartTime())
                .endTime(reservation.getEndTime())
                .reservationType(reservation.getReservationType())
                .table(TableConverter.convert(reservation.getTable()))
                .seatedUser(UserConverter.convert(reservation.getSeatedUser()))
                .reservationUser(UserConverter.convert(reservation.getReservationUser()))
                .build();
    }
}
