package ch.etmles.bidster.Lot;

import ch.etmles.bidster.Categorie.CategorieEntity;
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
        Terminer
    }
    @Enumerated(EnumType.STRING)
    private Status status;

    private Double enchere;
    private Date date_heure_fin;
    private String description;

    @ManyToOne
    @JoinColumn(name = "id_categorie")
    @JsonManagedReference
    private CategorieEntity categorie;

    private String image;

    public LotEntity() {}

    public LotEntity(String nom_article, String details, Status status, Double enchere, Date date_heure_fin, String description, String image, CategorieEntity categorie) {
        setNom_article(nom_article);
        setDetails(details);
        setStatus(Status.Enchere);
        setEnchere(enchere);
        setDate_heure_fin(date_heure_fin);
        setDescription(description);
        setImage(image);
        setCategorie(categorie);
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

    public Double getEnchere() { return enchere; }
    public void setEnchere(Double enchere) { this.enchere = enchere; }

    public Date getDate_heure_fin() { return date_heure_fin; }
    public void setDate_heure_fin(Date date_heure_fin) { this.date_heure_fin = date_heure_fin; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public CategorieEntity getCategorie() { return categorie; }
    public void setCategorie(CategorieEntity categorie) { this.categorie = categorie; }

    @Override
    public String toString() {
        return "Lot{" +
                "id_lot=" + getId_lot() +
                ", nom_article='" + getNom_article() + '\'' +
                ", details='" + getDetails() + '\'' +
                ", status=" + getStatus() + '\'' +
                ", enchere=" + getEnchere() +
                ", date_heure_fin=" + getDate_heure_fin() +
                ", description='" + getDescription() + '\'' +
                ", image='" + getImage() + '\'' +
                ", categorie=" + (getCategorie() != null ? getCategorie().getNom() : null) +
                '}';
    }
}
