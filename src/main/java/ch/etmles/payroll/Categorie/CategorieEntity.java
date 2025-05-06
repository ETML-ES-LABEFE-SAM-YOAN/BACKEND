package ch.etmles.payroll.Categorie;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Entité représentant une catégorie, pouvant avoir un parent (catégorie parente)
 * et des sous-catégories (hiérarchie arborescente).
 */
@Entity
public class CategorieEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_categorie;

    @Column(nullable = false)
    private String nom;

    // Catégorie parente (null si catégorie principale)
    @ManyToOne
    @JoinColumn(name = "id_parent")
    @JsonBackReference
    private CategorieEntity parent;

    // Sous-catégories (relation inverse)
    @OneToMany(mappedBy = "parent")
    @JsonManagedReference
    private List<CategorieEntity> sousCategories = new ArrayList<>();

    public CategorieEntity() {}

    public CategorieEntity(String nom, CategorieEntity parent) {
        setNom(nom);
        setParent(parent);
    }

    // Getters et setters
    public Long getId_categorie() { return id_categorie; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public CategorieEntity getParent() { return parent; }
    public void setParent(CategorieEntity parent) { this.parent = parent; }

    public List<CategorieEntity> getSousCategories() { return sousCategories; }
    public void setSousCategories(List<CategorieEntity> sousCategories) { this.sousCategories = sousCategories; }
}
