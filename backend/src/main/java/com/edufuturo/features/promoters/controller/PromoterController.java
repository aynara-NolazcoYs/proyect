package com.edufuturo.features.promoters.controller;

import com.edufuturo.features.promoters.dto.PromoterDTO;
import com.edufuturo.features.promoters.service.PromoterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/promoters")
public class PromoterController {
    
    @Autowired
    private PromoterService promoterService;

    @GetMapping
    public ResponseEntity<List<PromoterDTO>> getAllPromoters() {
        List<PromoterDTO> promoters = promoterService.getAllPromoters();
        return ResponseEntity.ok(promoters);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PromoterDTO> getPromoterById(@PathVariable Long id) {
        PromoterDTO promoter = promoterService.getPromoterById(id);
        return ResponseEntity.ok(promoter);
    }

    @PostMapping
    public ResponseEntity<PromoterDTO> createPromoter(@RequestBody PromoterDTO promoterDTO) {
        PromoterDTO createdPromoter = promoterService.createPromoter(promoterDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPromoter);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PromoterDTO> updatePromoter(@PathVariable Long id, @RequestBody PromoterDTO promoterDTO) {
        PromoterDTO updatedPromoter = promoterService.updatePromoter(id, promoterDTO);
        return ResponseEntity.ok(updatedPromoter);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePromoter(@PathVariable Long id) {
        promoterService.deletePromoter(id);
        return ResponseEntity.noContent().build();
    }
}
