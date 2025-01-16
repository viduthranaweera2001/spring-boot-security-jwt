package lk.ranaweera.api.controller;

import jakarta.servlet.http.HttpServletRequest;
import lk.ranaweera.api.service.AttendenceService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AttendenceController {

    private AttendenceService attendenceService;

    @PostMapping("/mark")
    public ResponseEntity<String> markAttendance(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        String response = attendenceService.markAttendance(token);
        return ResponseEntity.ok(response);
    }
}
