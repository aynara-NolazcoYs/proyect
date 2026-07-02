package com.edufuturo.service;

import com.edufuturo.dto.EnrollmentDTO;
import com.edufuturo.model.Enrollment;
import com.edufuturo.repository.EnrollmentRepository;
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
        return enrollmentRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }
    
    public List<EnrollmentDTO> getEnrollmentsByDni(String dni) {
        return enrollmentRepository.findByDni(dni)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public EnrollmentDTO createEnrollment(EnrollmentDTO enrollmentDTO) {
        Enrollment enrollment = convertToEntity(enrollmentDTO);
        Enrollment saved = enrollmentRepository.save(enrollment);
        return convertToDTO(saved);
    }
    
    public EnrollmentDTO updateEnrollment(Long id, EnrollmentDTO enrollmentDTO) {
        return enrollmentRepository.findById(id)
                .map(enrollment -> {
                    enrollment.setNombre(enrollmentDTO.getNombre());
                    enrollment.setApellido(enrollmentDTO.getApellido());
                    enrollment.setDni(enrollmentDTO.getDni());
                    enrollment.setSede(enrollmentDTO.getSede());
                    enrollment.setPromotor(enrollmentDTO.getPromotor());
                    enrollment.setPrograma(enrollmentDTO.getPrograma());
                    enrollment.setModalidad(enrollmentDTO.getModalidad());
                    enrollment.setCorreo(enrollmentDTO.getCorreo());
                    Enrollment updated = enrollmentRepository.save(enrollment);
                    return convertToDTO(updated);
                })
                .orElse(null);
    }
    
    public boolean deleteEnrollment(Long id) {
        if (enrollmentRepository.existsById(id)) {
            enrollmentRepository.deleteById(id);
            return true;
        }
        return false;
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
    
    private Enrollment convertToEntity(EnrollmentDTO enrollmentDTO) {
        Enrollment enrollment = new Enrollment();
        enrollment.setId(enrollmentDTO.getId());
        enrollment.setNombre(enrollmentDTO.getNombre());
        enrollment.setApellido(enrollmentDTO.getApellido());
        enrollment.setDni(enrollmentDTO.getDni());
        enrollment.setSede(enrollmentDTO.getSede());
        enrollment.setPromotor(enrollmentDTO.getPromotor());
        enrollment.setPrograma(enrollmentDTO.getPrograma());
        enrollment.setModalidad(enrollmentDTO.getModalidad());
        enrollment.setCorreo(enrollmentDTO.getCorreo());
        return enrollment;
    }
}
