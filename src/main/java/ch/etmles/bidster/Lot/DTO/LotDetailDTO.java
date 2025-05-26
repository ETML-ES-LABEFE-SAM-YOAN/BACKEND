package ch.etmles.bidster.Lot.DTO;

import ch.etmles.bidster.Lot.LotEntity;

import java.util.Date;

public class LotDetailDTO {
    private Long id;
    private String nom_article;
    private String details;
    private Double enchere;
    private Date date_heure_fin;
    private String description;
    private String image;
    private String utilisateur; // nom d'utilisateur seulement

    public LotDetailDTO(LotEntity lot) {
        setId(lot.getId_lot());
        setNom_article(lot.getNom_article());
        setDetails(lot.getDetails());
        setEnchere(lot.getEnchere());
        setDate_heure_fin(lot.getDate_heure_fin());
        setDescription(lot.getDescription());
        setImage(lot.getImage());
        setUtilisateur(lot.getUtilisateur().getNomUtilisateur());
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Double getEnchere() {
        return enchere;
    }

    public void setEnchere(Double enchere) {
        this.enchere = enchere;
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

    public String getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(String utilisateur) {
        this.utilisateur = utilisateur;
    }
}
