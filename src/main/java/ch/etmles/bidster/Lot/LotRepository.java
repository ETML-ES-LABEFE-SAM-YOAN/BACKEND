package ch.etmles.bidster.Lot;
import ch.etmles.bidster.Categorie.CategorieEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LotRepository extends JpaRepository<LotEntity, Long> {
    // Tous les lots d'une liste de catégories
    List<LotEntity> findByCategorieIn(List<CategorieEntity> categories);

    // Tous les lots d'une seule catégorie
    List<LotEntity> findByCategorie(CategorieEntity categorie);
}
