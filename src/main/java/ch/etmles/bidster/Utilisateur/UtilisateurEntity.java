package ch.etmles.bidster.Utilisateur;
import ch.etmles.bidster.Lot.LotEntity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
public class UtilisateurEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom_utilisateur", nullable = false, unique = true)
    private String nomUtilisateur;
    private String nom;

    private String prenom;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "mot_de_passe", nullable = false)
    private String motDePasse;

    private String telephone;

    private String rue;

    private String npa;

    private String localite;

    private Double solde;

    @Column(name = "date_creation")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreation;

    // Relation avec Lot : Un utilisateur possède plusieurs lots
    /*@OneToMany(mappedBy = "utilisateur", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LotEntity> lots;
    */
    // Constructor

    public UtilisateurEntity(Long id, String nomUtilisateur, String nom, String motDePasse, String prenom, String email, String npa, Double solde, String rue, String telephone, String localite /*List<LotEntity> lots*/) {
        setId(id);
        setNomUtilisateur(nomUtilisateur);
        setNom(nom);
        setMotDePasse(motDePasse);
        setPrenom(prenom);
        setEmail(email);
        setNpa(npa);
        setSolde(solde);
        setRue(rue);
        setTelephone(telephone);
        setLocalite(localite);
        setDateCreation(new Date());
        //(lots);
    }

    public UtilisateurEntity() {

    }


    // Getter & Setter
    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }

    public void setNomUtilisateur(String nomUtilisateur) {
        this.nomUtilisateur = nomUtilisateur;
    }
    public String getNomUtilisateur() {
        return nomUtilisateur;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    public String getPrenom() {
        return prenom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getNom() {
        return nom;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return email;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }
    public String getMotDePasse() {
        return motDePasse;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    public String getTelephone() {
        return telephone;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }
    public String getRue() {
        return rue;
    }

    public void setNpa(String npa) {
        this.npa = npa;
    }
    public String getNpa() {
        return npa;
    }

    public void setLocalite(String localite) {
        this.localite = localite;
    }
    public String getLocalite() {
        return localite;
    }

    public void setSolde(Double solde) {
        this.solde = solde;
    }
    public Double getSolde() {
        return solde;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }
    public Date getDateCreation() {
        return dateCreation;
    }

    /*
    public void setLots(List<LotEntity> lots) {
        this.lots = lots;
    }
    public List<LotEntity> getLots() {
        return lots;
    }
    */
}
