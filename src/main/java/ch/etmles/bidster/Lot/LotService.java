package ch.etmles.bidster.Lot;

import ch.etmles.bidster.Categorie.CategorieEntity;
import ch.etmles.bidster.Categorie.CategorieRepository;
import ch.etmles.bidster.Categorie.CategorieNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

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

    /**
     * Récupère tous les lots
     *
     */
    public List<LotEntity> getAllLots() {
        return lotRepository.findAll();
    }

}
