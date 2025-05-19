package ch.etmles.bidster.Utilisateur;

import ch.etmles.bidster.Categorie.CategorieEntity;
import ch.etmles.bidster.Lot.LotEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface UtilisateurRepository extends JpaRepository<UtilisateurEntity, Long>  {
        Optional<UtilisateurEntity> findByNomUtilisateur(String nomUtilisateur);
}