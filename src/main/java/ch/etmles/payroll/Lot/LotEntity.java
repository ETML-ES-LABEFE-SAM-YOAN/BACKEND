package ch.etmles.payroll.Lot;

import ch.etmles.payroll.Categorie.CategorieEntity;
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
    // Remplace "Enum status" par un vrai type enum si besoin
    private Double enchere_depart;
    private Date date_heure_fin;
    private String description;

    @ManyToOne
    @JoinColumn(name = "id_categorie")
    @JsonManagedReference
    private CategorieEntity categorie;

    private String image;

    public LotEntity() {}

    public LotEntity(String nom_article, String details, Double enchere_depart, Date date_heure_fin, String description, String image, CategorieEntity categorie) {
        setNom_article(nom_article);
        setDetails(details);
        setEnchere_depart(enchere_depart);
        setDate_heure_fin(date_heure_fin);
        setDescription(description);
        setImage(image);
        setCategorie(categorie);
    }

    // Getters et setters...

    public Long getId_lot() { return id_lot; }
    public void setId_lot(Long id_lot) { this.id_lot = id_lot; }

    public String getNom_article() { return nom_article; }
    public void setNom_article(String nom_article) { this.nom_article = nom_article; }

    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }

    public Double getEnchere_depart() { return enchere_depart; }
    public void setEnchere_depart(Double enchere_depart) { this.enchere_depart = enchere_depart; }

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
                ", enchere_depart=" + getEnchere_depart() +
                ", date_heure_fin=" + getDate_heure_fin() +
                ", description='" + getDescription() + '\'' +
                ", image='" + getImage() + '\'' +
                ", categorie=" + (getCategorie() != null ? getCategorie().getNom() : null) +
                '}';
    }
}
