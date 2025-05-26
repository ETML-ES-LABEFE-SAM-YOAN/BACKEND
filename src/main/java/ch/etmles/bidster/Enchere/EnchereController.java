package ch.etmles.bidster.Enchere;

import ch.etmles.bidster.Enchere.DTO.EnchereDto;
import ch.etmles.bidster.Enchere.DTO.EnchereResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/encheres")
public class EnchereController {
    @Autowired
    private EnchereService enchereService;

    @PostMapping("/placer")
    public ResponseEntity<EnchereResponseDto> placerEnchere(@RequestBody EnchereDto dto) {
        EnchereEntity enchere = enchereService.placerEnchere(dto.getLotId(), dto.getNomUtilisateur(), dto.getMontant());
        return ResponseEntity.ok(enchereService.toDto(enchere));
    }

    @GetMapping("/lot/{lotId}")
    public ResponseEntity<List<EnchereResponseDto>> getEncheresPourLot(@PathVariable Long lotId) {
        List<EnchereEntity> encheres = enchereService.getEncheresPourLot(lotId);
        List<EnchereResponseDto> dtos = encheres.stream()
                .map(enchereService::toDto)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/lot/{lotId}/meilleure")
    public ResponseEntity<EnchereResponseDto> getMeilleureEncherePourLot(@PathVariable Long lotId) {
        EnchereEntity meilleure = enchereService.getMeilleureEncherePourLot(lotId);
        if (meilleure == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(enchereService.toDto(meilleure));
    }

    @GetMapping("/utilisateur/{nomUtilisateur}")
    public ResponseEntity<List<EnchereResponseDto>> getEncheresPourUtilisateur(@PathVariable String nomUtilisateur) {
        List<EnchereEntity> encheres = enchereService.getEncheresPourUtilisateur(nomUtilisateur);
        List<EnchereResponseDto> dtos = encheres.stream()
                .map(enchereService::toDto)
                .toList();
        return ResponseEntity.ok(dtos);
    }


}

