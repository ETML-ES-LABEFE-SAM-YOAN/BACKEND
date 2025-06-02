package ch.etmles.bidster.Enchere;

import ch.etmles.bidster.Enchere.DTO.EnchereDto;
import ch.etmles.bidster.Enchere.DTO.EnchereResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("v1/encheres")
public class EnchereController {
    @Autowired
    private EnchereService enchereService;

    @PostMapping("/placer")
    public ResponseEntity<?> placerEnchere(@RequestBody EnchereDto dto, Authentication authentication) {
        String nomUtilisateurToken = authentication.getName(); // Nom d'utilisateur du token

        // Vérification de cohérence
        if (dto.getNomUtilisateur() != null && !dto.getNomUtilisateur().equals(nomUtilisateurToken)) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Vous ne pouvez placer une enchère qu'en votre nom propre."));
        }

        // Utilise le nom d'utilisateur du token pour la suite
        EnchereEntity enchere = enchereService.placerEnchere(dto.getLotId(), nomUtilisateurToken, dto.getMontant());
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
    public ResponseEntity<?> getEncheresPourUtilisateur(
            @PathVariable String nomUtilisateur,
            Authentication authentication) {
        String nomUtilisateurToken = authentication.getName();

        // Vérifie que l'utilisateur du token correspond à l'utilisateur demandé
        if (!nomUtilisateur.equals(nomUtilisateurToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Vous ne pouvez consulter que vos propres enchères."));
        }


            List<EnchereEntity> encheres = enchereService.getEncheresPourUtilisateur(nomUtilisateur);
        List<EnchereResponseDto> dtos = encheres.stream()
                .map(enchereService::toDto)
                .toList();
        return ResponseEntity.ok(dtos);
    }



}

