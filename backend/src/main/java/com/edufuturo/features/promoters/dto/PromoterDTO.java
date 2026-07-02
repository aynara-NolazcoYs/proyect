package com.edufuturo.features.promoters.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PromoterDTO {
    private Long id;
    private String dni;
    private String nombreCompleto;
    private String sede;
    private String email;
}
