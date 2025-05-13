package ch.etmles.bidster.Lot;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Contrôleur REST pour les lots.
 */
@RestController
@RequestMapping("/lots")
public class LotController {
    private final LotService lotService;

    public LotController(LotService lotService) {
        this.lotService = lotService;
    }

    /**
     * Affiche un lot
     * Exemple curl :
     * curl http://localhost:8080/lots/{id}
     */
    @GetMapping("{id}")
    public LotEntity getLot(@PathVariable Long id) {
        return lotService.getLot(id);
    }

    /**
     * Affiche tous les lots d'une catégorie principale (et de ses sous-catégories), en cherchant par nom.
     * Exemple curl :
     * curl http://localhost:8080/lots/categorie-principale/Bijoux
     */
    @GetMapping("/categorie-principale/{nom}")
    public ResponseEntity<List<LotEntity>> getLotsByCategoriePrincipale(@PathVariable String nom) {
        return ResponseEntity.ok(lotService.getLotsByCategoriePrincipale(nom));
    }

    /**
     * Affiche tous les lots d'une sous-catégorie, en cherchant par nom.
     * Exemple curl :
     * curl http://localhost:8080/lots/sous-categorie/Bagues
     */
    @GetMapping("/sous-categorie/{nom}")
    public ResponseEntity<List<LotEntity>> getLotsBySousCategorie(@PathVariable String nom) {
        return ResponseEntity.ok(lotService.getLotsBySousCategorie(nom));
    }
}
