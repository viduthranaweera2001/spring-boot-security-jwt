package lk.ranaweera.api.service.impl;

import lk.ranaweera.api.model.Attendence;
import lk.ranaweera.api.model.User;
import lk.ranaweera.api.repository.AttendenceRepository;
import lk.ranaweera.api.repository.UserRepository;
import lk.ranaweera.api.security.JwtService;
import lk.ranaweera.api.service.AttendenceService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class AttendenceServiceImpl implements AttendenceService {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private AttendenceRepository attendenceRepository;

    @Override
    public String markAttendance(String token) {

        String username = jwtService.extractUsername(token);
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        Attendence attendance = Attendence.builder()
                .user(user)
                .timestamp(LocalDateTime.now())
                .status("Present")
                .build();

        attendenceRepository.save(attendance);
        return "Attendance marked successfully for " + user.getUsername();
    }
}
