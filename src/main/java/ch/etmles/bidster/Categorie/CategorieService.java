package ch.etmles.bidster.Categorie;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service pour la logique métier liée aux catégories.
 */
@Service
public class CategorieService {
    private final CategorieRepository categorieRepository;

    public CategorieService(CategorieRepository categorieRepository) {
        this.categorieRepository = categorieRepository;
    }

    /**
     * Récupère toutes les catégories principales (parent == null) sous forme de DTO.
     */
    public List<CategorieDTO> getCategoriesPrincipales() {
        return categorieRepository.findByParentIsNull()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Récupère une catégorie par son nom et la retourne sous forme d'arbre (DTO).
     */
    public CategorieDTO getCategorieTreeByNom(String nom) {
        CategorieEntity entity = categorieRepository.findByNom(nom)
                .orElseThrow(() -> new CategorieNotFoundException(nom));
        return toDto(entity);
    }

    /**
     * Mapping récursif d'une entité catégorie vers un DTO.
     */
    public CategorieDTO toDto(CategorieEntity entity) {
        List<CategorieDTO> sousDtos = entity.getSousCategories().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return new CategorieDTO(entity.getId_categorie(), entity.getNom(), sousDtos);
    }
}
