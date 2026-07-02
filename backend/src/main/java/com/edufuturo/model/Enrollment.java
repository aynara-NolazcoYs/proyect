package com.edufuturo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "Enrollments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Enrollment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;
    
    @Column(name = "apellido", nullable = false, length = 100)
    private String apellido;
    
    @Column(name = "dni", nullable = false, length = 20)
    private String dni;
    
    @Column(name = "sede", nullable = false, length = 50)
    private String sede;
    
    @Column(name = "promotor", nullable = false, length = 150)
    private String promotor;
    
    @Column(name = "programa", nullable = false, length = 150)
    private String programa;
    
    @Column(name = "modalidad", nullable = false, length = 50)
    private String modalidad;
    
    @Column(name = "correo", nullable = false, length = 150)
    private String correo;
    
    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;
    
    @PrePersist
    protected void onCreate() {
        fechaRegistro = LocalDateTime.now();
    }
}
