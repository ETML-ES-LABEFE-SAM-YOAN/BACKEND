package ch.etmles.payroll.Lot;

import ch.etmles.payroll.Categorie.CategorieEntity;
import ch.etmles.payroll.Categorie.CategorieRepository;
import ch.etmles.payroll.Categorie.CategorieNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service pour la logique métier liée aux lots.
 */
@Service
public class LotService {
    private final LotRepository lotRepository;
    private final CategorieRepository categorieRepository;

    public LotService(LotRepository lotRepository, CategorieRepository categorieRepository) {
        this.lotRepository = lotRepository;
        this.categorieRepository = categorieRepository;
    }

    /**
     * Récupère tous les lots d'une catégorie principale et de ses sous-catégories, en cherchant par nom.
     */
    public List<LotEntity> getLotsByCategoriePrincipale(String nomCategoriePrincipale) {
        CategorieEntity principale = categorieRepository.findByNom(nomCategoriePrincipale)
                .orElseThrow(() -> new CategorieNotFoundException(nomCategoriePrincipale));
        List<CategorieEntity> sousCategories = principale.getSousCategories();
        List<CategorieEntity> toutesCategories = new ArrayList<>();
        toutesCategories.add(principale);
        toutesCategories.addAll(sousCategories);
        return lotRepository.findByCategorieIn(toutesCategories);
    }

    /**
     * Récupère tous les lots d'une sous-catégorie, en cherchant par nom.
     */
    public List<LotEntity> getLotsBySousCategorie(String nomSousCategorie) {
        CategorieEntity sousCategorie = categorieRepository.findByNom(nomSousCategorie)
                .orElseThrow(() -> new CategorieNotFoundException(nomSousCategorie));
        return lotRepository.findByCategorie(sousCategorie);
    }

    public LotEntity getLot(Long id) {
        return lotRepository.findById(id)
                .orElseThrow(() -> new LotNotFoundException(id));
    }

}
