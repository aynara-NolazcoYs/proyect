package com.edufuturo.features.enrollments.service;

import com.edufuturo.features.enrollments.dto.EnrollmentDTO;
import com.edufuturo.features.enrollments.entity.Enrollment;
import com.edufuturo.features.enrollments.repository.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnrollmentService {
    
    @Autowired
    private EnrollmentRepository enrollmentRepository;

    public List<EnrollmentDTO> getAllEnrollments() {
        return enrollmentRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public EnrollmentDTO getEnrollmentById(Long id) {
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Matrícula no encontrada"));
        return convertToDTO(enrollment);
    }

    public List<EnrollmentDTO> getEnrollmentsByDni(String dni) {
        return enrollmentRepository.findByDni(dni)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public EnrollmentDTO createEnrollment(EnrollmentDTO enrollmentDTO) {
        Enrollment enrollment = new Enrollment();
        enrollment.setNombre(enrollmentDTO.getNombre());
        enrollment.setApellido(enrollmentDTO.getApellido());
        enrollment.setDni(enrollmentDTO.getDni());
        enrollment.setSede(enrollmentDTO.getSede());
        enrollment.setPromotor(enrollmentDTO.getPromotor());
        enrollment.setPrograma(enrollmentDTO.getPrograma());
        enrollment.setModalidad(enrollmentDTO.getModalidad());
        enrollment.setCorreo(enrollmentDTO.getCorreo());
        
        Enrollment savedEnrollment = enrollmentRepository.save(enrollment);
        return convertToDTO(savedEnrollment);
    }

    public EnrollmentDTO updateEnrollment(Long id, EnrollmentDTO enrollmentDTO) {
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Matrícula no encontrada"));
        
        enrollment.setNombre(enrollmentDTO.getNombre());
        enrollment.setApellido(enrollmentDTO.getApellido());
        enrollment.setSede(enrollmentDTO.getSede());
        enrollment.setPromotor(enrollmentDTO.getPromotor());
        enrollment.setPrograma(enrollmentDTO.getPrograma());
        enrollment.setModalidad(enrollmentDTO.getModalidad());
        enrollment.setCorreo(enrollmentDTO.getCorreo());
        
        Enrollment updatedEnrollment = enrollmentRepository.save(enrollment);
        return convertToDTO(updatedEnrollment);
    }

    public void deleteEnrollment(Long id) {
        enrollmentRepository.deleteById(id);
    }

    private EnrollmentDTO convertToDTO(Enrollment enrollment) {
        return new EnrollmentDTO(
            enrollment.getId(),
            enrollment.getNombre(),
            enrollment.getApellido(),
            enrollment.getDni(),
            enrollment.getSede(),
            enrollment.getPromotor(),
            enrollment.getPrograma(),
            enrollment.getModalidad(),
            enrollment.getCorreo()
        );
    }
}
