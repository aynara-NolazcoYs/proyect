package com.edufuturo.features.dashboard.controller;

import com.edufuturo.features.dashboard.dto.DashboardDTO;
import com.edufuturo.features.students.repository.StudentRepository;
import com.edufuturo.features.promoters.repository.PromoterRepository;
import com.edufuturo.features.enrollments.repository.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {
    
    @Autowired
    private StudentRepository studentRepository;
    
    @Autowired
    private PromoterRepository promoterRepository;
    
    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @GetMapping
    public ResponseEntity<DashboardDTO> getDashboard() {
        DashboardDTO dashboard = new DashboardDTO(
            studentRepository.count(),
            enrollmentRepository.count(),
            promoterRepository.count()
        );
        return ResponseEntity.ok(dashboard);
    }
}
