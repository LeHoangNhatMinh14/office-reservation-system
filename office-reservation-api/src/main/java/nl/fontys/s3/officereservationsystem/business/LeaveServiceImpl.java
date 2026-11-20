package nl.fontys.s3.officereservationsystem.business;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import nl.fontys.s3.officereservationsystem.business.converter.LeaveConverter;
import nl.fontys.s3.officereservationsystem.business.interfaces.LeaveService;
import nl.fontys.s3.officereservationsystem.domain.Leave;
import nl.fontys.s3.officereservationsystem.domain.User;
import nl.fontys.s3.officereservationsystem.persistence.LeaveRepository;
import nl.fontys.s3.officereservationsystem.persistence.UserRepository;
import nl.fontys.s3.officereservationsystem.persistence.entity.LeaveEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LeaveServiceImpl implements LeaveService {
    private final LeaveRepository leaveRepository;
    private final UserRepository userRepository;

    public void createLeave(Leave leave) {
        // Null checks for required fields
        if (leave.getStartDate() == null || leave.getEndDate() == null) {
            throw new IllegalArgumentException("Start date and end date must not be null.");
        }

        if (leave.getStartDate().isAfter(leave.getEndDate())) {
            throw new IllegalArgumentException("Start date is after end date.");
        }

        if (leave.getStartDate().isEqual(leave.getEndDate())) {
            throw new IllegalArgumentException("Start date is equal to end date.");
        }

        // Validate if user exists
        Long userId = leave.getUserId();
        userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with id " + userId + " not found"));

        // Convert to entity and save
        LeaveEntity leaveEntity = LeaveEntity.builder()
                .userId(leave.getUserId())
                .startDate(leave.getStartDate())
                .endDate(leave.getEndDate())
                .reason(leave.getReason())
                .build();

        leaveRepository.save(leaveEntity);
    }



    public void deleteLeave(Long id) {
        if (!leaveRepository.existsById(id)){
            throw new IllegalArgumentException("Leave with id " + id + " not found");
        }
        leaveRepository.deleteById(id);
    }

    public Leave getLeaveById(Long id) {
        return leaveRepository.findById(id).map(LeaveConverter::convert).orElseThrow(() -> new IllegalArgumentException("Leave with id " + id + " not found"));
    }

    public List<Leave> getLeaveByUserId(Long userId) {
        return leaveRepository.findByUserId(userId).stream()
                .map(LeaveConverter::convert)
                .toList();
    }




}
