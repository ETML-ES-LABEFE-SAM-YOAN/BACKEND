package ch.etmles.bidster.Lot;

import ch.etmles.bidster.Categorie.CategorieEntity;
import ch.etmles.bidster.Categorie.CategorieNotFoundException;
import ch.etmles.bidster.Categorie.CategorieRepository;
import ch.etmles.bidster.Utilisateur.UtilisateurEntity;
import ch.etmles.bidster.Utilisateur.UtilisateurRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

/**
 * Contrôleur REST pour les lots.
 */
@RestController
@RequestMapping("/lots")
public class LotController {
    private final LotService lotService;
    private final CategorieRepository categorieRepository;
    private final UtilisateurRepository utilisateurRepository;

    public LotController(LotService lotService,
                         CategorieRepository categorieRepository,
                         UtilisateurRepository utilisateurRepository) {
        this.lotService = lotService;
        this.categorieRepository = categorieRepository;
        this.utilisateurRepository = utilisateurRepository;
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
     * Affiche tous les lots.
     * Exemple curl :
     * curl http://localhost:8080/lots/all
     */
    @GetMapping("/all")
    public ResponseEntity<List<LotEntity>> getAllLots() {
        return ResponseEntity.ok(lotService.getAllLots());
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

    /**
     * Permet de créer un lot
     * Exemple curl :
     * curl -X POST "http://localhost:8080/lots" -H "Content-Type: application/json" -d "{\"nom_article\": \"Tableau Moderne\", \"details\": \"Peinture acrylique sur toile, 60x80cm\", \"enchere
     * \": 100.0, \"date_heure_fin\": \"2025-06-01T18:00:00\", \"description\": \"Œuvre unique signée par l'artiste.\", \"image\": \"https://exemple.com/images/tableau.jpg\", \"idCategorie\": 3}"
     */
    @PostMapping
    public ResponseEntity<?> createLot(@RequestBody LotDTO lotDTO) {
        if (lotDTO.getIdCategorie() == null) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("error", "La catégorie est obligatoire"));
        }
        if (lotDTO.getIdUtilisateur() == null) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("error", "L'utilisateur est obligatoire"));
        }

        try {
            // Récupération de la catégorie
            CategorieEntity categorie = categorieRepository.findById(lotDTO.getIdCategorie())
                    .orElseThrow(() -> new CategorieNotFoundException(String.valueOf(lotDTO.getIdCategorie())));

            // Récupération de l'utilisateur
            UtilisateurEntity utilisateur = utilisateurRepository.findById(lotDTO.getIdUtilisateur())
                    .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

            // Création de l'entité Lot
            LotEntity lot = new LotEntity(
                    lotDTO.getNom_article(),
                    lotDTO.getDetails(),
                    LotEntity.Status.Enchere,
                    lotDTO.getEnchere(),
                    lotDTO.getDate_heure_fin(),
                    lotDTO.getDescription(),
                    lotDTO.getImage(),
                    categorie,
                    utilisateur
            );

            LotEntity savedLot = lotService.createLot(lot, lotDTO.getIdCategorie());
            return ResponseEntity.ok(savedLot);

        } catch (CategorieNotFoundException e) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("error", "La catégorie spécifiée n'existe pas"));
        } catch (RuntimeException e) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Une erreur interne est survenue"));
        }
    }
}

