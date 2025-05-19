package ch.etmles.payroll.Lot;

import java.util.Date;

/**
 * DTO pour exposer les informations d'un lot.
 */
public class LotDTO {
    private String nom_article;
    private String details;
    private Double enchere_depart;
    private Date date_heure_fin;
    private String description;
    private String image;
    private Long idCategorie;

    public LotDTO(String nom_article, String details, Double enchere_depart, Date date_heure_fin,
                  String description, String image, Long idCategorie) {
        setNom_article(nom_article);
        setDetails(details);
        setEnchere_depart(enchere_depart);
        setDate_heure_fin(date_heure_fin);
        setDescription(description);
        setImage(image);
        setIdCategorie(idCategorie);
    }

    // Getters et setters
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

    public Long getIdCategorie() { return idCategorie; }
    public void setIdCategorie(Long idCategorie) { this.idCategorie = idCategorie; }
}
