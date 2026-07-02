package com.edufuturo.service;

import com.edufuturo.dto.PromoterDTO;
import com.edufuturo.model.Promoter;
import com.edufuturo.repository.PromoterRepository;
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
        return promoterRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }
    
    public PromoterDTO createPromoter(PromoterDTO promoterDTO) {
        Promoter promoter = convertToEntity(promoterDTO);
        Promoter saved = promoterRepository.save(promoter);
        return convertToDTO(saved);
    }
    
    public PromoterDTO updatePromoter(Long id, PromoterDTO promoterDTO) {
        return promoterRepository.findById(id)
                .map(promoter -> {
                    promoter.setDni(promoterDTO.getDni());
                    promoter.setNombreCompleto(promoterDTO.getNombreCompleto());
                    promoter.setSede(promoterDTO.getSede());
                    promoter.setEmail(promoterDTO.getEmail());
                    Promoter updated = promoterRepository.save(promoter);
                    return convertToDTO(updated);
                })
                .orElse(null);
    }
    
    public boolean deletePromoter(Long id) {
        if (promoterRepository.existsById(id)) {
            promoterRepository.deleteById(id);
            return true;
        }
        return false;
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
    
    private Promoter convertToEntity(PromoterDTO promoterDTO) {
        Promoter promoter = new Promoter();
        promoter.setId(promoterDTO.getId());
        promoter.setDni(promoterDTO.getDni());
        promoter.setNombreCompleto(promoterDTO.getNombreCompleto());
        promoter.setSede(promoterDTO.getSede());
        promoter.setEmail(promoterDTO.getEmail());
        return promoter;
    }
}
