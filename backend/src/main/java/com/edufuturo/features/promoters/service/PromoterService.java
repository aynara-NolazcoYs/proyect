package com.edufuturo.features.promoters.service;

import com.edufuturo.features.promoters.dto.PromoterDTO;
import com.edufuturo.features.promoters.entity.Promoter;
import com.edufuturo.features.promoters.repository.PromoterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PromoterService {
    
    @Autowired
    private PromoterRepository promoterRepository;

    public List<PromoterDTO> getAllPromoters() {
        return promoterRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public PromoterDTO getPromoterById(Long id) {
        Promoter promoter = promoterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Promotor no encontrado"));
        return convertToDTO(promoter);
    }

    public PromoterDTO createPromoter(PromoterDTO promoterDTO) {
        Promoter promoter = new Promoter();
        promoter.setDni(promoterDTO.getDni());
        promoter.setNombreCompleto(promoterDTO.getNombreCompleto());
        promoter.setSede(promoterDTO.getSede());
        promoter.setEmail(promoterDTO.getEmail());
        
        Promoter savedPromoter = promoterRepository.save(promoter);
        return convertToDTO(savedPromoter);
    }

    public PromoterDTO updatePromoter(Long id, PromoterDTO promoterDTO) {
        Promoter promoter = promoterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Promotor no encontrado"));
        
        promoter.setNombreCompleto(promoterDTO.getNombreCompleto());
        promoter.setSede(promoterDTO.getSede());
        promoter.setEmail(promoterDTO.getEmail());
        
        Promoter updatedPromoter = promoterRepository.save(promoter);
        return convertToDTO(updatedPromoter);
    }

    public void deletePromoter(Long id) {
        promoterRepository.deleteById(id);
    }

    private PromoterDTO convertToDTO(Promoter promoter) {
        return new PromoterDTO(
            promoter.getId(),
            promoter.getDni(),
            promoter.getNombreCompleto(),
            promoter.getSede(),
            promoter.getEmail()
        );
    }
}
