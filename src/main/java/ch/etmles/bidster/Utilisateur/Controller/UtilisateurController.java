package ch.etmles.bidster.Utilisateur.Controller;

import ch.etmles.bidster.Utilisateur.DTO.UtilisateurDTO;
import ch.etmles.bidster.Utilisateur.DTO.UtilisateurInfoDTO;
import ch.etmles.bidster.Utilisateur.UtilisateurEntity;
import ch.etmles.bidster.Utilisateur.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


import java.util.Map;

@RestController
@RequestMapping("v1/utilisateurs")
public class UtilisateurController {

    @Autowired
    private UtilisateurService utilisateurService;

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

}
