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
        UtilisateurEntity utilisateur = utilisateurService.getUtilisateurParNom(nomUtilisateur);
        if (utilisateur != null) {
            UtilisateurInfoDTO dto = new UtilisateurInfoDTO(utilisateur);
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
