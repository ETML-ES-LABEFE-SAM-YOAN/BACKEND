package ch.etmles.bidster.Utilisateur.Controller;

import ch.etmles.bidster.Utilisateur.UtilisateurDTO;
import ch.etmles.bidster.Utilisateur.UtilisateurEntity;
import ch.etmles.bidster.Utilisateur.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
