package ch.etmles.bidster.Enchere;

import ch.etmles.bidster.Lot.LotEntity;
import ch.etmles.bidster.Utilisateur.UtilisateurEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EnchereRepository extends JpaRepository<EnchereEntity, Long> {
    List<EnchereEntity> findByLotOrderByMontantDesc(LotEntity lot);
    Optional<EnchereEntity> findTopByLotOrderByMontantDesc(LotEntity lot);
    List<EnchereEntity> findByUtilisateurOrderByDateEnchereDesc(UtilisateurEntity utilisateur);
    int countByUtilisateurAndLot_Status(UtilisateurEntity utilisateur, LotEntity.Status status);


}
