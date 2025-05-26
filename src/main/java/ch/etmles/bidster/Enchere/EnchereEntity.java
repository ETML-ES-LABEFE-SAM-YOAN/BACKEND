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
}
