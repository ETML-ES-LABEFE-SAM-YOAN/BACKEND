package ch.etmles.payroll.Lot;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import org.w3c.dom.Text;

import java.util.Date;
import java.util.Objects;

@Entity
public class LotEntity {
    private @Id
    @GeneratedValue Long id_lot;
    private String nom_article;
    private String details;
    private Enum status;
    private Double enchere_depart;
    private Date date_heure_fin;
    private String description;
    //private Categorie id_categorie
    //private Categorie id_sous-categorie
    private String image;

    public LotEntity() {}

    public LotEntity(String nom_article, String details,Double enchere_depart, Date date_heure_fin, String description, String image) {
        this.setNom_article(nom_article);
        this.setDetails(details);
        this.setEnchere_depart(enchere_depart);
        this.setDate_heure_fin(date_heure_fin);
        this.setDescription(description);
        this.setImage(image);
    }

    public Long getId_lot() {
        return id_lot;
    }

    public void setId_lot(Long id_lot) {
        this.id_lot = id_lot;
    }

    public String getNom_article() {
        return nom_article;
    }

    public void setNom_article(String nom_article) {
        this.nom_article = nom_article;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Double getEnchere_depart() {
        return enchere_depart;
    }

    public void setEnchere_depart(Double enchere_depart) {
        this.enchere_depart = enchere_depart;
    }

    public Date getDate_heure_fin() {
        return date_heure_fin;
    }

    public void setDate_heure_fin(Date date_heure_fin) {
        this.date_heure_fin = date_heure_fin;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    @Override
    public String toString() {
        return "Lot{"+"id lot="+ this.getId_lot()+", nom d'article=" + this.getNom_article() + ", details ="+ getDetails()+", enchere départ="+ this.getEnchere_depart()+"Date et heure fin : "+ this.getDate_heure_fin()+" description : "+ this.getDescription()+", image : "+ this.getImage()+ "}";
    }
}
