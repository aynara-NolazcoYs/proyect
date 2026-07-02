package com.edufuturo.repository;

import com.edufuturo.model.Promoter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PromoterRepository extends JpaRepository<Promoter, Long> {
    Optional<Promoter> findByDni(String dni);
    Optional<Promoter> findByEmail(String email);
}
