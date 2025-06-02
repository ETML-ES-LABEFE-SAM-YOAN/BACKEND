package ch.etmles.bidster.Categorie;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Contrôleur REST pour les catégories.
 */
@RestController
@RequestMapping("v1/categories")
public class CategorieController {
    private final CategorieService categorieService;

    public CategorieController(CategorieService categorieService) {
        this.categorieService = categorieService;
    }

    /**
     * Affiche toutes les catégories principales (parent == null), avec leurs sous-catégories.
     * Exemple curl :
     * curl http://localhost:8080/v1/categories/principales
     */
    @GetMapping("/principales")
    public ResponseEntity<List<CategorieDTO>> getCategoriesPrincipales() {
        return ResponseEntity.ok(categorieService.getCategoriesPrincipales());
    }
}
