package ch.etmles.bidster.Lot;

import ch.etmles.bidster.Categorie.CategorieEntity;
import ch.etmles.bidster.Enchere.EnchereEntity;
import ch.etmles.bidster.Utilisateur.UtilisateurEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.Date;

@Entity
public class LotEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_lot;

    private String nom_article;
    private String details;



    /*Enum Status*/
    public enum Status {
        Enchere,
        Terminer,
        Invendu
    }
    @Enumerated(EnumType.STRING)
    private Status status;

    private Double enchere_depart;
    @Column(name = "date_heure_fin")
    private Date dateHeureFin;

    private String description;

    @ManyToOne
    @JoinColumn(name = "id_categorie")
    @JsonManagedReference
    private CategorieEntity categorie;

    private String image;

    // Relation ManyToOne vers UserEntity
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "utilisateur_nom_utilisateur", referencedColumnName = "nom_utilisateur", nullable = false)
    private UtilisateurEntity utilisateur;

    @OneToOne
    @JoinColumn(name = "enchere_gagnante_id")
    private EnchereEntity enchereGagnante;

    private boolean venteConfirmee = false;


    public LotEntity() {}

    public LotEntity(String nom_article, String details, Status status, Double enchere, Date date_heure_fin, String description, String image, CategorieEntity categorie, UtilisateurEntity utilisateur) {
        setNom_article(nom_article);
        setDetails(details);
        setStatus(Status.Enchere);
        setEnchere(enchere);
        setDateHeureFin(date_heure_fin);
        setDescription(description);
        setImage(image);
        setCategorie(categorie);
        setUtilisateur(utilisateur);
        setVenteConfirmee(false);
        setEnchereGagnante(null);
    }

    // Getters et setters...

    public Long getId_lot() { return id_lot; }

    public String getNom_article() { return nom_article; }
    public void setNom_article(String nom_article) { this.nom_article = nom_article; }

    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }

    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }

    public Double getEnchere() { return enchere_depart; }
    public void setEnchere(Double enchere) { this.enchere_depart = enchere; }

    public Date getDateHeureFin() { return dateHeureFin; }
    public void setDateHeureFin(Date dateHeureFin) { this.dateHeureFin = dateHeureFin; }


    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public CategorieEntity getCategorie() { return categorie; }
    public void setCategorie(CategorieEntity categorie) { this.categorie = categorie; }

    public UtilisateurEntity getUtilisateur() { return utilisateur; }
    public void setUtilisateur(UtilisateurEntity utilisateur) { this.utilisateur = utilisateur; }

    public EnchereEntity getEnchereGagnante() { return enchereGagnante; }
    public void setEnchereGagnante(EnchereEntity enchereGagnante) {
        this.enchereGagnante = enchereGagnante;
    }

    public boolean isVenteConfirmee() { return venteConfirmee; }
    public void setVenteConfirmee(boolean venteConfirmee) {
        this.venteConfirmee = venteConfirmee;
    }

    @Override
    public String toString() {
        return "Lot{" +
                "id_lot=" + getId_lot() +
                ", nom_article='" + getNom_article() + '\'' +
                ", details='" + getDetails() + '\'' +
                ", status=" + getStatus() + '\'' +
                ", enchere=" + getEnchere() +
                ", date_heure_fin=" + getDateHeureFin() +
                ", description='" + getDescription() + '\'' +
                ", image='" + getImage() + '\'' +
                ", categorie=" + (getCategorie() != null ? getCategorie().getNom() : null) +
                ", utilisateur=" + getUtilisateur() +
                '}';
    }
}
