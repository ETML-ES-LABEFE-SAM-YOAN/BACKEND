package ch.etmles.bidster.Categorie;

import java.util.List;

/**
 * DTO pour exposer une catégorie et ses sous-catégories sous forme d'arbre.
 */
public class CategorieDTO {
    private Long id_categorie;
    private String nom;
    private List<CategorieDTO> sousCategories;

    public CategorieDTO(Long id_categorie, String nom, List<CategorieDTO> sousCategories) {
        setId_categorie(id_categorie);
        setNom(nom);
        setSousCategories(sousCategories);
    }

    // Getters et setters
    public Long getId_categorie() { return id_categorie; }
    public void setId_categorie(Long id_categorie) { this.id_categorie = id_categorie; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public List<CategorieDTO> getSousCategories() { return sousCategories; }
    public void setSousCategories(List<CategorieDTO> sousCategories) { this.sousCategories = sousCategories; }
}
