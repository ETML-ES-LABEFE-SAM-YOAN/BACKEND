package ch.etmles.bidster.Utilisateur.DTO;

import ch.etmles.bidster.Utilisateur.UtilisateurEntity;

import java.util.Date;

public class UtilisateurInfoDTO {
    public String nomUtilisateur;
    public String nom;
    public String prenom;
    public String email;
    public String telephone;
    public String rue;
    public String npa;
    public String localite;
    public Double solde;
    public Date dateCreation;

    // Constructeur à partir de l'entité
    public UtilisateurInfoDTO() {}

    public UtilisateurInfoDTO(UtilisateurEntity utilisateur) {
        setNomUtilisateur(utilisateur.getNomUtilisateur());
        setNom(utilisateur.getNom());
        setPrenom(utilisateur.getPrenom());
        setEmail(utilisateur.getEmail());
        setTelephone(utilisateur.getTelephone());
        setRue(utilisateur.getRue());
        setNpa(utilisateur.getNpa());
        setLocalite(utilisateur.getLocalite());
        setSolde(utilisateur.getSolde());
        setDateCreation(utilisateur.getDateCreation());
    }

    public String getNomUtilisateur() {
        return nomUtilisateur;
    }

    public void setNomUtilisateur(String nomUtilisateur) {
        this.nomUtilisateur = nomUtilisateur;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Double getSolde() {
        return solde;
    }

    public void setSolde(Double solde) {
        this.solde = solde;
    }

    public String getNpa() {
        return npa;
    }

    public void setNpa(String npa) {
        this.npa = npa;
    }

    public String getLocalite() {
        return localite;
    }

    public void setLocalite(String localite) {
        this.localite = localite;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getRue() {
        return rue;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
