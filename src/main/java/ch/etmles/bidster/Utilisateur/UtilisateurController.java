package ch.etmles.bidster.Utilisateur;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/utilisateurs")
public class UtilisateurController {

    @Autowired
    private UtilisateurService utilisateurService;

    @PostMapping("/creer")
    public ResponseEntity<UtilisateurEntity> creerUtilisateur(@RequestBody UtilisateurDTO dto) {
        UtilisateurEntity utilisateurCree = utilisateurService.creerUtilisateur(dto);
        return ResponseEntity.ok(utilisateurCree);
    }
}
