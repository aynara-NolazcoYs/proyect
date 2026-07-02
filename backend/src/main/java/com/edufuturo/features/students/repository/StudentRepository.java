package com.edufuturo.features.students.repository;

import com.edufuturo.features.students.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByDni(String dni);
    Optional<Student> findByCorreo(String correo);
}
