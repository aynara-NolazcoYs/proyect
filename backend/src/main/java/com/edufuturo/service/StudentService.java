package com.edufuturo.service;

import com.edufuturo.dto.StudentDTO;
import com.edufuturo.model.Student;
import com.edufuturo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {
    
    @Autowired
    private StudentRepository studentRepository;
    
    public List<StudentDTO> getAllStudents() {
        return studentRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public StudentDTO getStudentById(Long id) {
        return studentRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }
    
    public StudentDTO createStudent(StudentDTO studentDTO) {
        Student student = convertToEntity(studentDTO);
        Student saved = studentRepository.save(student);
        return convertToDTO(saved);
    }
    
    public StudentDTO updateStudent(Long id, StudentDTO studentDTO) {
        return studentRepository.findById(id)
                .map(student -> {
                    student.setNombre(studentDTO.getNombre());
                    student.setApellido(studentDTO.getApellido());
                    student.setDni(studentDTO.getDni());
                    student.setCorreo(studentDTO.getCorreo());
                    Student updated = studentRepository.save(student);
                    return convertToDTO(updated);
                })
                .orElse(null);
    }
    
    public boolean deleteStudent(Long id) {
        if (studentRepository.existsById(id)) {
            studentRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    private StudentDTO convertToDTO(Student student) {
        return new StudentDTO(
                student.getId(),
                student.getNombre(),
                student.getApellido(),
                student.getDni(),
                student.getCorreo()
        );
    }
    
    private Student convertToEntity(StudentDTO studentDTO) {
        Student student = new Student();
        student.setId(studentDTO.getId());
        student.setNombre(studentDTO.getNombre());
        student.setApellido(studentDTO.getApellido());
        student.setDni(studentDTO.getDni());
        student.setCorreo(studentDTO.getCorreo());
        return student;
    }
}
