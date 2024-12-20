package nl.fontys.s3.officereservationsystem.business.interfaces;

import nl.fontys.s3.officereservationsystem.domain.Leave;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LeaveService {
    void createLeave(Leave leave);
    void deleteLeave(Long id);
    Leave getLeaveById(Long id);
    List<Leave> getLeaveByUserId(Long userId);
}
