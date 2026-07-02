package com.edufuturo.features.enrollments.repository;

import com.edufuturo.features.enrollments.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    List<Enrollment> findByDni(String dni);
    List<Enrollment> findByPromotor(String promotor);
}
