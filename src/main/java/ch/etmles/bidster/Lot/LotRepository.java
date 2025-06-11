package ch.etmles.bidster.Lot;
import ch.etmles.bidster.Categorie.CategorieEntity;
import ch.etmles.bidster.Utilisateur.UtilisateurEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface LotRepository extends JpaRepository<LotEntity, Long> {
    // Tous les lots d'une liste de catégories
    List<LotEntity> findByCategorieIn(List<CategorieEntity> categories);

    // Tous les lots d'une seule catégorie
    List<LotEntity> findByCategorie(CategorieEntity categorie);

    List<LotEntity> findByDateHeureFinBeforeAndStatus(Date date, LotEntity.Status status);

    List<LotEntity> findByEnchereGagnante_Utilisateur_NomUtilisateur(String nomUtilisateur);
    List<LotEntity> findByStatusAndUtilisateur_NomUtilisateur(LotEntity.Status status, String nomUtilisateur);
    List<LotEntity> findByUtilisateur(UtilisateurEntity utilisateur);


}
