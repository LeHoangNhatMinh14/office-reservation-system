package nl.fontys.s3.officereservationsystem.business.converter;

import nl.fontys.s3.officereservationsystem.domain.Leave;
import nl.fontys.s3.officereservationsystem.persistence.entity.LeaveEntity;

public final class LeaveConverter {
    private LeaveConverter() {}

    public static LeaveEntity convert(Leave leave) {
        return LeaveEntity.builder()
                .id(leave.getId())
                .userId(leave.getUserId())
                .startDate(leave.getStartDate())
                .endDate(leave.getEndDate())
                .build();
    }

    public static Leave convert(LeaveEntity entity) {
        return Leave.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .build();
    }
}
