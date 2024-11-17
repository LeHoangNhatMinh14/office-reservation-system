package nl.fontys.s3.officereservationsystem.business;

import lombok.AllArgsConstructor;
import nl.fontys.s3.officereservationsystem.business.interfaces.LeaveService;
import nl.fontys.s3.officereservationsystem.domain.Leave;
import nl.fontys.s3.officereservationsystem.persistence.LeaveRepository;
import nl.fontys.s3.officereservationsystem.persistence.UserRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LeaveServiceImpl implements LeaveService {
    private final LeaveRepository leaveRepository;
    private final UserRepository userRepository;

    public void setLeaveDays(Leave leave) {
//        if (leave.getUserId() == null || userRepository.findById(leave.getUserId()).isEmpty()) {
//            throw new IllegalArgumentException("User id is null or user does not exist");
//        }
        leaveRepository.save(leave);
    }

    public Leave getLeaveDaysByUserId(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User id cannot be null");
        }
        return leaveRepository.findByUserId(userId);
    }

}
