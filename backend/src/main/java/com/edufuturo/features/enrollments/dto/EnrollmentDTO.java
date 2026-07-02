package com.edufuturo.features.enrollments.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String dni;
    private String sede;
    private String promotor;
    private String programa;
    private String modalidad;
    private String correo;
}
