package com.edufuturo.repository;

import com.edufuturo.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    List<Enrollment> findByDni(String dni);
    List<Enrollment> findByPromotor(String promotor);
}
