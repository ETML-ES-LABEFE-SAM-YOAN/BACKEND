package ch.etmles.bidster.Lot;

import ch.etmles.bidster.Categorie.CategorieEntity;
import ch.etmles.bidster.Categorie.CategorieNotFoundException;
import ch.etmles.bidster.Categorie.CategorieRepository;
import ch.etmles.bidster.Lot.DTO.LotCreateDTO;
import ch.etmles.bidster.Lot.DTO.LotDetailDTO;
import ch.etmles.bidster.Utilisateur.UtilisateurEntity;
import ch.etmles.bidster.Utilisateur.UtilisateurRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;

/**
 * Contrôleur REST pour les lots.
 */
@RestController
@RequestMapping("v1/lots")
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
     * curl http://localhost:8080/v1/lots/{id}
     */

    @GetMapping("{id}")
    public LotDetailDTO getLot(@PathVariable Long id) {
        return lotService.getLot(id);
    }

    /**
     * Affiche tous les lots.
     * Exemple curl :
     * curl http://localhost:8080/v1/lots/all
     */
    @GetMapping("/all")
    public ResponseEntity<List<LotDetailDTO>> getAllLots() {
        return ResponseEntity.ok(
                lotService.getAllLots().stream().map(LotDetailDTO::new).toList()
        );
    }

    /**
     * Affiche tous les lots d'une catégorie principale (et de ses sous-catégories), en cherchant par nom.
     * Exemple curl :
     * curl http://localhost:8080/v1/lots/categorie-principale/Bijoux
     */
    @GetMapping("/categorie-principale/{nom}")
    public ResponseEntity<List<LotDetailDTO>> getLotsByCategoriePrincipale(@PathVariable String nom) {
        return ResponseEntity.ok(lotService.getLotsByCategoriePrincipale(nom));
    }

    /**
     * Affiche tous les lots d'une sous-catégorie, en cherchant par nom.
     * Exemple curl :
     * curl http://localhost:8080/v1/lots/sous-categorie/Bagues
     */
    @GetMapping("/sous-categorie/{nom}")
    public ResponseEntity<List<LotDetailDTO>> getLotsBySousCategorie(@PathVariable String nom) {
        return ResponseEntity.ok(lotService.getLotsBySousCategorie(nom));
    }

    /**
     * Permet de créer un lot
     * Exemple curl :
     * curl -X POST "http://localhost:8080/v1/lots" -H "Content-Type: application/json" -d "{\"nom_article\": \"Tableau Moderne\", \"details\": \"Peinture acrylique sur toile, 60x80cm\", \"enchere
     * \": 100.0, \"date_heure_fin\": \"2025-06-01T18:00:00\", \"description\": \"Œuvre unique signée par l'artiste.\", \"image\": \"https://exemple.com/images/tableau.jpg\", \"idCategorie\": 3}"
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createLot(
            @RequestPart("lot") LotCreateDTO lotcreateDTO,
            @RequestPart(value = "image", required = false) MultipartFile image,
            Authentication authentication) {
        if (lotcreateDTO.getIdCategorie() == null) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("error", "La catégorie est obligatoire"));
        }

        try {
            // Gestion de l'image si présente
            String fileName = null;
            if (image != null && !image.isEmpty()) {
                String uploadDir = "images/";
                fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
                Path filePath = Paths.get(uploadDir, fileName);
                Files.createDirectories(filePath.getParent());
                Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                // Concatène le baseUrl ici
                String baseUrl = "http://localhost:8080/images/"; // Idéalement à mettre en propriété de config
                lotcreateDTO.setImage(baseUrl + fileName); // Stocke l'URL complète dans le DTO
            }


            // Récupération de la catégorie
            CategorieEntity categorie = categorieRepository.findById(lotcreateDTO.getIdCategorie())
                    .orElseThrow(() -> new CategorieNotFoundException(String.valueOf(lotcreateDTO.getIdCategorie())));

            // Vérification de l'authentification
            if (authentication == null || !authentication.isAuthenticated()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("error", "Utilisateur non authentifié"));
            }

            // Récupération de l'utilisateur connecté
            String nomUtilisateur = authentication.getName();
            UtilisateurEntity utilisateur = utilisateurRepository.findByNomUtilisateur(nomUtilisateur)
                    .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

            // Création de l'entité Lot
            LotEntity lot = new LotEntity(
                    lotcreateDTO.getNom_article(),
                    lotcreateDTO.getDetails(),
                    LotEntity.Status.Enchere,
                    lotcreateDTO.getEnchere(),
                    lotcreateDTO.getDate_heure_fin(),
                    lotcreateDTO.getDescription(),
                    lotcreateDTO.getImage(), // nom du fichier image ou null
                    categorie,
                    utilisateur
            );

            LotEntity savedLot = lotService.createLot(lot, lotcreateDTO.getIdCategorie());

            // Retourne un DTO propre, pas l'entité JPA complète
            return ResponseEntity.ok(new LotDetailDTO(savedLot));

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

