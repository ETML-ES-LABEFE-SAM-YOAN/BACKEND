package ch.etmles.bidster.Categorie;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Contrôleur REST pour les catégories.
 */
@RestController
@RequestMapping("/categories")
public class CategorieController {
    private final CategorieService categorieService;

    public CategorieController(CategorieService categorieService) {
        this.categorieService = categorieService;
    }

    /**
     * Affiche toutes les catégories principales (parent == null), avec leurs sous-catégories.
     * Exemple curl :
     * curl http://localhost:8080/categories/principales
     */
    @GetMapping("/principales")
    public ResponseEntity<List<CategorieDTO>> getCategoriesPrincipales() {
        return ResponseEntity.ok(categorieService.getCategoriesPrincipales());
    }

    /**
     * Affiche une catégorie et toutes ses sous-catégories sous forme d'arbre, en cherchant par nom.
     * Exemple curl :
     * curl http://localhost:8080/categories/nom/Bijoux
     */
    @GetMapping("/nom/{nom}")
    public ResponseEntity<CategorieDTO> getCategorieTreeByNom(@PathVariable String nom) {
        return ResponseEntity.ok(categorieService.getCategorieTreeByNom(nom));
    }
}
