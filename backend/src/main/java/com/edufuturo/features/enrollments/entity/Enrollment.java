package com.edufuturo.features.enrollments.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Enrollments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 100)
    private String apellido;

    @Column(nullable = false, length = 20)
    private String dni;

    @Column(nullable = false, length = 50)
    private String sede;

    @Column(nullable = false, length = 150)
    private String promotor;

    @Column(nullable = false, length = 150)
    private String programa;

    @Column(nullable = false, length = 50)
    private String modalidad;

    @Column(nullable = false, length = 150)
    private String correo;

    @Column(name = "fecha_registro")
    private java.time.LocalDateTime fechaRegistro;

    @PrePersist
    protected void onCreate() {
        fechaRegistro = java.time.LocalDateTime.now();
    }
}
