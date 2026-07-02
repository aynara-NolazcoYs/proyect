package com.edufuturo.features.students.service;

import com.edufuturo.features.students.dto.StudentDTO;
import com.edufuturo.features.students.entity.Student;
import com.edufuturo.features.students.repository.StudentRepository;
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
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));
        return convertToDTO(student);
    }

    public StudentDTO createStudent(StudentDTO studentDTO) {
        Student student = new Student();
        student.setNombre(studentDTO.getNombre());
        student.setApellido(studentDTO.getApellido());
        student.setDni(studentDTO.getDni());
        student.setCorreo(studentDTO.getCorreo());
        
        Student savedStudent = studentRepository.save(student);
        return convertToDTO(savedStudent);
    }

    public StudentDTO updateStudent(Long id, StudentDTO studentDTO) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));
        
        student.setNombre(studentDTO.getNombre());
        student.setApellido(studentDTO.getApellido());
        student.setCorreo(studentDTO.getCorreo());
        
        Student updatedStudent = studentRepository.save(student);
        return convertToDTO(updatedStudent);
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
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
}
