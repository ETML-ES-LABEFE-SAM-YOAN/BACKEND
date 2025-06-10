package ch.etmles.bidster.Enchere;

import ch.etmles.bidster.Enchere.DTO.EnchereResponseDto;
import ch.etmles.bidster.Lot.DTO.LotStatusDto;
import ch.etmles.bidster.Lot.LotEntity;
import ch.etmles.bidster.Lot.LotNotFoundException;
import ch.etmles.bidster.Lot.LotRepository;
import ch.etmles.bidster.Utilisateur.UtilisateurEntity;
import ch.etmles.bidster.Utilisateur.UtilisateurNotFoundException;
import ch.etmles.bidster.Utilisateur.UtilisateurRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class EnchereService {
    @Autowired
    private EnchereRepository enchereRepository;
    @Autowired
    private LotRepository lotRepository;
    @Autowired
    private UtilisateurRepository utilisateurRepository;

    public EnchereEntity placerEnchere(Long lotId, String nomUtilisateur, Double montant) {
        LotEntity lot = lotRepository.findById(lotId)
                .orElseThrow(() -> new LotNotFoundException(lotId));
        UtilisateurEntity utilisateur = utilisateurRepository.findByNomUtilisateur(nomUtilisateur)
                .orElseThrow(() -> new UtilisateurNotFoundException("Utilisateur non trouvé"));

        if (utilisateur.getSolde() == null || utilisateur.getSolde() < montant) {
            throw new SoldeInsuffisantException("Solde insuffisant pour placer cette enchère !");
        }

        // Vérifier que l’enchère est supérieure à la meilleure précédente
        Optional<EnchereEntity> meilleure = enchereRepository.findTopByLotOrderByMontantDesc(lot);
        if (meilleure.isPresent() && montant <= meilleure.get().getMontant()) {
            throw new EnchereMontantInvalideException("L’enchère doit être supérieure à l’offre actuelle !");
        }

        EnchereEntity enchere = new EnchereEntity();
        enchere.setLot(lot);
        enchere.setUtilisateur(utilisateur);
        enchere.setMontant(montant);
        enchere.setDateEnchere(new Date());

        return enchereRepository.save(enchere);
    }

    public List<EnchereEntity> getEncheresPourLot(Long lotId) {
        LotEntity lot = lotRepository.findById(lotId)
                .orElseThrow(() -> new LotNotFoundException(lotId));
        return enchereRepository.findByLotOrderByMontantDesc(lot);
    }

    public EnchereResponseDto toDto(EnchereEntity enchere) {
        EnchereResponseDto dto = new EnchereResponseDto();
        dto.setId(enchere.getId());
        dto.setMontant(enchere.getMontant());
        dto.setDateEnchere(enchere.getDateEnchere());
        dto.setNomUtilisateur(enchere.getUtilisateur().getNomUtilisateur());
        dto.setLotId(enchere.getLot().getId_lot());
        return dto;
    }

    public EnchereEntity getMeilleureEncherePourLot(Long lotId) {
        LotEntity lot = lotRepository.findById(lotId)
                .orElseThrow(() -> new LotNotFoundException(lotId));
        return enchereRepository.findTopByLotOrderByMontantDesc(lot).orElse(null);
    }

    public List<EnchereEntity> getEncheresPourUtilisateur(String nomUtilisateur) {
        UtilisateurEntity utilisateur = utilisateurRepository.findByNomUtilisateur(nomUtilisateur)
                .orElseThrow(() -> new UtilisateurNotFoundException("Utilisateur non trouvé"));
        return enchereRepository.findByUtilisateurOrderByDateEnchereDesc(utilisateur);
    }

    @Scheduled(fixedRate = 60000) // Vérification chaque minute
    public void cloturerEncheresTerminees() {
        System.out.println("Exécution du scheduler à " + new Date());

        List<LotEntity> lots = lotRepository.findByDateHeureFinBeforeAndStatus(
                new Date(),
                LotEntity.Status.Enchere
        );

        lots.forEach(lot -> {
            EnchereEntity meilleure = getMeilleureEncherePourLot(lot.getId_lot());
            if(meilleure != null) {
                lot.setEnchereGagnante(meilleure);
                lot.setStatus(LotEntity.Status.Terminer);
                lotRepository.save(lot);
            } else {
                lot.setStatus(LotEntity.Status.Invendu);
                lotRepository.save(lot);
            }
        });
    }

    @Transactional
    public void confirmerVente(Long lotId) {
        LotEntity lot = lotRepository.findById(lotId)
                .orElseThrow(() -> new LotNotFoundException(lotId));

        if(lot.isVenteConfirmee()) {
            throw new IllegalStateException("Vente déjà confirmée");
        }

        EnchereEntity enchere = lot.getEnchereGagnante();
        UtilisateurEntity acheteur = enchere.getUtilisateur();
        UtilisateurEntity vendeur = lot.getUtilisateur();

        if(acheteur.getSolde() < enchere.getMontant()) {
            throw new SoldeInsuffisantException("Solde insuffisant pour confirmation");
        }

        // Transfert des fonds
        acheteur.setSolde(acheteur.getSolde() - enchere.getMontant());
        vendeur.setSolde(vendeur.getSolde() + enchere.getMontant());

        // Mise à jour du statut
        lot.setVenteConfirmee(true);

        utilisateurRepository.saveAll(List.of(acheteur, vendeur));
        lotRepository.save(lot);
    }



    public List<LotEntity> getLotsGagnesParUtilisateur(String nomUtilisateur) {
        return lotRepository.findByEnchereGagnante_Utilisateur_NomUtilisateur(nomUtilisateur);
    }

    public int getNombreEncheresGagnees(String nomUtilisateur) {
        List<LotEntity> lotsGagnes = getLotsGagnesParUtilisateur(nomUtilisateur);
        return lotsGagnes.size();
    }

    public int getNombreEncheresPlaceesEnCours(String nomUtilisateur) {
        UtilisateurEntity utilisateur = utilisateurRepository.findByNomUtilisateur(nomUtilisateur)
                .orElseThrow(() -> new UtilisateurNotFoundException("Utilisateur non trouvé"));

        // On ne compte que les enchères sur des lots dont le statut est "Enchere"
        return enchereRepository.countByUtilisateurAndLot_Status(utilisateur, LotEntity.Status.Enchere);
    }



    public LotStatusDto toDto(LotEntity lot) {
        LotStatusDto dto = new LotStatusDto();
        dto.setId(lot.getId_lot());
        dto.setNomArticle(lot.getNom_article());
        dto.setDescription(lot.getDescription());
        dto.setVenteConfirmee(lot.isVenteConfirmee());
        return dto;
    }




}
