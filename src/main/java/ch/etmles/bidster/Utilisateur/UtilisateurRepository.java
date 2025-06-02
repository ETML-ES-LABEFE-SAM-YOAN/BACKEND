package ch.etmles.bidster.Utilisateur;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


public interface UtilisateurRepository extends JpaRepository<UtilisateurEntity, String>  {
        Optional<UtilisateurEntity> findByNomUtilisateur(String nomUtilisateur);
        Optional<UtilisateurEntity> findByEmail(String email);

}