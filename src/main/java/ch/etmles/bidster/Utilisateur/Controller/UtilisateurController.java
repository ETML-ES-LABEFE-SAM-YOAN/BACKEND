package ch.etmles.bidster.Utilisateur.Controller;

import ch.etmles.bidster.Enchere.EnchereService;
import ch.etmles.bidster.Lot.DTO.LotStatusDto;
import ch.etmles.bidster.Lot.LotEntity;
import ch.etmles.bidster.Utilisateur.DTO.UtilisateurDTO;
import ch.etmles.bidster.Utilisateur.DTO.UtilisateurInfoDTO;
import ch.etmles.bidster.Utilisateur.UtilisateurEntity;
import ch.etmles.bidster.Utilisateur.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("v1/utilisateurs")
public class UtilisateurController {

    @Autowired
    private UtilisateurService utilisateurService;
    @Autowired
    private EnchereService enchereService;

    @PostMapping
    public ResponseEntity<UtilisateurEntity> creerUtilisateur(@RequestBody UtilisateurDTO dto) {
        UtilisateurEntity utilisateurCree = utilisateurService.creerUtilisateur(dto);
        return ResponseEntity.ok(utilisateurCree);
    }


    @GetMapping("/{nomUtilisateur}")
    public ResponseEntity<?> getUtilisateur(
            @PathVariable String nomUtilisateur,
            Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Utilisateur non authentifié"));
        }

        String usernameAuth = authentication.getName();
        if (!usernameAuth.equals(nomUtilisateur)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Accès interdit à ce compte"));
        }

        UtilisateurInfoDTO dto = utilisateurService.getUtilisateurParNom(nomUtilisateur);
        if (dto != null) {
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }



    @PatchMapping("/{nomUtilisateur}/ajouter-solde")
    public ResponseEntity<?> ajouterSolde(
            @PathVariable String nomUtilisateur,
            @RequestParam Double montant,
            Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Utilisateur non authentifié"));
        }

        String usernameAuth = authentication.getName();
        if (!usernameAuth.equals(nomUtilisateur)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Vous ne pouvez modifier que votre propre solde"));
        }

        if (montant == null || montant <= 0) {
            return ResponseEntity.badRequest().body(Map.of("error", "Montant invalide"));
        }
        UtilisateurInfoDTO dto = utilisateurService.ajouterSolde(nomUtilisateur, montant);
        if (dto != null) {
            return ResponseEntity.ok(Map.of("solde", dto.getSolde()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PatchMapping("/{nomUtilisateur}/reduire-solde")
    public ResponseEntity<?> reduireSolde(
            @PathVariable String nomUtilisateur,
            @RequestParam Double montant,
            Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Utilisateur non authentifié"));
        }

        String usernameAuth = authentication.getName();
        if (!usernameAuth.equals(nomUtilisateur)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Vous ne pouvez modifier que votre propre solde"));
        }

        if (montant == null || montant <= 0) {
            return ResponseEntity.badRequest().body(Map.of("error", "Montant invalide"));
        }

        UtilisateurInfoDTO dto = utilisateurService.reduireSolde(nomUtilisateur, montant);
        if (dto != null) {
            return ResponseEntity.ok(Map.of("solde", dto.getSolde()));
        } else {
            return ResponseEntity.badRequest().body(Map.of("error", "Solde insuffisant ou utilisateur introuvable"));
        }
    }

    @GetMapping("/{nomUtilisateur}/lots-vendus")
    public ResponseEntity<?> getLotsVendus(@PathVariable String nomUtilisateur, Authentication authentication) {
        String nomUtilisateurToken = authentication.getName();
        if (!nomUtilisateur.equals(nomUtilisateurToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Utilisateur non authentifié"));
        }
        List<LotEntity> lots = enchereService.getLotsParVendeur(nomUtilisateur);
        List<LotStatusDto> dtos = lots.stream()
                .map(enchereService::toDto)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @PutMapping("/{nomUtilisateur}")
    public ResponseEntity<?> updateUtilisateur(
            @PathVariable String nomUtilisateur,
            @RequestBody UtilisateurInfoDTO dto,
            Authentication authentication) {
        String nomUtilisateurToken = authentication.getName();
        if (!nomUtilisateur.equals(nomUtilisateurToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Utilisateur non authentifié"));
        }
        UtilisateurEntity updated = utilisateurService.updateUtilisateur(nomUtilisateur, dto);
        return ResponseEntity.ok(updated);
    }


}
