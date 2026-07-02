package com.edufuturo.rest;

import com.edufuturo.dto.DashboardDTO;
import com.edufuturo.repository.EnrollmentRepository;
import com.edufuturo.repository.PromoterRepository;
import com.edufuturo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000"}, maxAge = 3600)
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
