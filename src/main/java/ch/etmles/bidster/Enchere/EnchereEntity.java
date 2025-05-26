package ch.etmles.bidster.Enchere;

import ch.etmles.bidster.Lot.LotEntity;
import ch.etmles.bidster.Utilisateur.UtilisateurEntity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class EnchereEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private UtilisateurEntity utilisateur; // Qui a enchéri

    @ManyToOne
    private LotEntity lot; // Sur quel lot

    private Double montant; // Montant de l’enchère

    private Date dateEnchere; // Date et heure de l’enchère

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public UtilisateurEntity getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(UtilisateurEntity utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LotEntity getLot() {
        return lot;
    }

    public void setLot(LotEntity lot) {
        this.lot = lot;
    }

    public Date getDateEnchere() {
        return dateEnchere;
    }

    public void setDateEnchere(Date dateEnchere) {
        this.dateEnchere = dateEnchere;
    }
}
