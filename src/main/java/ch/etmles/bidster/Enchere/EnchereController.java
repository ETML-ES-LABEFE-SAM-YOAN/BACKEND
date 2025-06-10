package ch.etmles.bidster.Enchere;

import ch.etmles.bidster.Enchere.DTO.EnchereDto;
import ch.etmles.bidster.Enchere.DTO.EnchereResponseDto;
import ch.etmles.bidster.Lot.DTO.LotStatusDto;
import ch.etmles.bidster.Lot.LotEntity;
import ch.etmles.bidster.Lot.LotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("v1/encheres")
public class EnchereController {
    @Autowired
    private EnchereService enchereService;
    @Autowired
    private LotRepository lotRepository;

    @PostMapping("/placer")
    public ResponseEntity<?> placerEnchere(@RequestBody EnchereDto dto, Authentication authentication) {
        String nomUtilisateurToken = authentication.getName();

        // Vérification de cohérence utilisateur
        if (dto.getNomUtilisateur() != null && !dto.getNomUtilisateur().equals(nomUtilisateurToken)) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Vous ne pouvez placer une enchère qu'en votre nom propre."));
        }

        // Vérification du statut du lot
        Optional<LotEntity> optionalLot = lotRepository.findById(dto.getLotId());
        if (optionalLot.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Lot non trouvé."));
        }
        LotEntity lot = optionalLot.get();
        if (lot.getStatus() != LotEntity.Status.Enchere) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Impossible de placer une enchère : le lot n'est pas ouvert aux enchères."));
        }

        // Placement de l'enchère
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

    @PostMapping("/lots/{id}/confirmation")
    public ResponseEntity<?> confirmerVente(@PathVariable Long id) {
        try {
            LotEntity lot = lotRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Lot non trouvé"));

            EnchereEntity enchere = lot.getEnchereGagnante();
            if (enchere == null) {
                throw new IllegalStateException("Tu n'as pas gagner ce lot.");
            }

            enchereService.confirmerVente(id);
            return ResponseEntity.ok(Map.of("message", "Vente confirmée avec succès"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }


    @GetMapping("/utilisateur/{nomUtilisateur}/lots-gagnes")
    public ResponseEntity<?> getLotsGagnes(@PathVariable String nomUtilisateur, Authentication authentication) {
        String nomUtilisateurToken = authentication.getName();
        if (!nomUtilisateur.equals(nomUtilisateurToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Utilisateur non authentifié"));
        }
        List<LotEntity> lots = enchereService.getLotsGagnesParUtilisateur(nomUtilisateur);
        List<LotStatusDto> dtos = lots.stream()
                .map(enchereService::toDto)
                .toList();
        return ResponseEntity.ok(dtos);
    }



    @DeleteMapping("/lots/{id}/retirer")
    public ResponseEntity<?> retirerLot(@PathVariable Long id, Authentication authentication)
    {
        Optional<LotEntity> optionalLot = lotRepository.findById(id);
        if (optionalLot.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Lot non trouvé"));
        }

        LotEntity lot = optionalLot.get();
        String nomUtilisateurToken = authentication.getName();
        String nomUtilisateurLot = lot.getUtilisateur().getNomUtilisateur();

        if (!nomUtilisateurLot.equals(nomUtilisateurToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Vous ne pouvez retirer que vos propres lots."));
        }

        if (lot.getStatus() != LotEntity.Status.Invendu) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Suppression impossible : le lot n'est pas au statut 'Invendu'."));
        }

        lotRepository.deleteById(id);
        return ResponseEntity.ok(Map.of("message", "Lot supprimé de la base de données."));
    }


    @PutMapping("/lots/{id}/remettre-en-vente")
    public ResponseEntity<?> remettreEnVente(@PathVariable Long id, @RequestBody Map<String, String> body, Authentication authentication) {
        Optional<LotEntity> optionalLot = lotRepository.findById(id);
        if (optionalLot.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Lot non trouvé"));
        }

        LotEntity lot = optionalLot.get();

        if (lot.getStatus() != LotEntity.Status.Invendu) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Seuls les lots invendus peuvent être remis en vente."));
        }

        String nomUtilisateurToken = authentication.getName();
        String nomUtilisateurLot = lot.getUtilisateur().getNomUtilisateur();
        if (!nomUtilisateurLot.equals(nomUtilisateurToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Vous ne pouvez remettre en vente que vos propres lots."));
        }

        String nouvelleDateFin = body.get("dateFin");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime ldt = LocalDateTime.parse(nouvelleDateFin, formatter);
        Date date = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
        lot.setDateHeureFin(date);
        lot.setStatus(LotEntity.Status.Enchere);
        lotRepository.save(lot);

        return ResponseEntity.ok(Map.of("message", "Lot remis en vente"));
    }

    @GetMapping("/gagnees/count/{nomUtilisateur}")
    public ResponseEntity<?> getNombreEncheresGagnees(@PathVariable String nomUtilisateur, Authentication authentication) {
        String nomUtilisateurToken = authentication.getName();

        if (!nomUtilisateur.equals(nomUtilisateurToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Vous ne pouvez consulter que vos propres statistiques."));
        }

        int count = enchereService.getNombreEncheresGagnees(nomUtilisateur);
        return ResponseEntity.ok(Map.of("nombreEncheresGagnees", count));
    }

    @GetMapping("/placees/count/{nomUtilisateur}")
    public ResponseEntity<?> getNombreEncheresPlacees(@PathVariable String nomUtilisateur, Authentication authentication) {
        String nomUtilisateurToken = authentication.getName();

        if (!nomUtilisateur.equals(nomUtilisateurToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Vous ne pouvez consulter que vos propres statistiques."));
        }

        int count = enchereService.getNombreEncheresPlaceesEnCours(nomUtilisateur);
        return ResponseEntity.ok(Map.of("nombreEncheresPlacees", count));
    }



    @GetMapping("/lots/invendus/utilisateur/{username}")
    public ResponseEntity<?> getLotsInvendusParUtilisateur(@PathVariable String username) {
        List<LotEntity> invendus = lotRepository.findByStatusAndUtilisateur_NomUtilisateur(LotEntity.Status.Invendu, username);
        if (invendus.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Aucun lot invendu trouvé pour cet utilisateur."));
        }
        List<LotStatusDto> dtos = invendus.stream()
                .map(lot -> new LotStatusDto(
                        lot.getId_lot(),
                        lot.getNom_article(),
                        lot.getDescription(),
                        lot.isVenteConfirmee()
                ))
                .toList();
        return ResponseEntity.ok(dtos);
    }


}

