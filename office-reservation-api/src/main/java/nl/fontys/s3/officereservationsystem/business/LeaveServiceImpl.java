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

@Service
@AllArgsConstructor
public class LeaveServiceImpl implements LeaveService {
    private final LeaveRepository leaveRepository;
    private final UserRepository userRepository;

    public void createLeave(@Valid Leave leave) {
//        if (!leaveRepository.existsById(leave.getId())){
//            throw new IllegalArgumentException("Leave with id " + leave.getId() + " already exists");
//        }
//        if (leave.getStartDate().isAfter(leave.getEndDate())){
//            throw new IllegalArgumentException("Start date is after end date");
//        }
//        if (leave.getStartDate().isEqual(leave.getEndDate())){
//            throw new IllegalArgumentException("Start date is equal to end date");
//        }
        Long userId = leave.getUserId();
        userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User with id " + userId + " not found"));
        leaveRepository.save(LeaveConverter.convert(leave));
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




}
