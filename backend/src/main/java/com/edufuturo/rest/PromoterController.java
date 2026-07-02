package com.edufuturo.rest;

import com.edufuturo.dto.PromoterDTO;
import com.edufuturo.service.PromoterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/promoters")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000"}, maxAge = 3600)
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
        if (promoter != null) {
            return ResponseEntity.ok(promoter);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping
    public ResponseEntity<PromoterDTO> createPromoter(@RequestBody PromoterDTO promoterDTO) {
        PromoterDTO created = promoterService.createPromoter(promoterDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<PromoterDTO> updatePromoter(@PathVariable Long id, @RequestBody PromoterDTO promoterDTO) {
        PromoterDTO updated = promoterService.updatePromoter(id, promoterDTO);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePromoter(@PathVariable Long id) {
        if (promoterService.deletePromoter(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
