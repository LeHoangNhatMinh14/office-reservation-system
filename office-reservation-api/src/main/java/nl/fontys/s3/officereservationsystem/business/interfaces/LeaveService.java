package nl.fontys.s3.officereservationsystem.business.interfaces;

import nl.fontys.s3.officereservationsystem.domain.Leave;
import org.springframework.stereotype.Service;

@Service
public interface LeaveService {
    void createLeave(Leave leave);
    void deleteLeave(Long id);
    Leave getLeaveById(Long id);
}
