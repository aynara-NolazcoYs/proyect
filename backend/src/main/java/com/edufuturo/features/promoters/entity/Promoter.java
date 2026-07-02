package com.edufuturo.features.promoters.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Promoters")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Promoter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String dni;

    @Column(nullable = false, length = 150)
    private String nombreCompleto;

    @Column(nullable = false, length = 50)
    private String sede;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(name = "fecha_registro")
    private java.time.LocalDateTime fechaRegistro;

    @PrePersist
    protected void onCreate() {
        fechaRegistro = java.time.LocalDateTime.now();
    }
}
