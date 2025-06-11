package ch.etmles.bidster.Utilisateur.DTO;


import ch.etmles.bidster.Utilisateur.UtilisateurEntity;

public class UtilisateurDTO {

    private String nomUtilisateur;
    private String nom;
    private String prenom;
    private String email;
    private String motDePasse;
    private String telephone;
    private String rue;
    private String npa;
    private String localite;

    // Constructeur sans argument
    public UtilisateurDTO() {}

    public UtilisateurDTO(UtilisateurEntity utilisateur) {
        setNomUtilisateur(utilisateur.getNomUtilisateur());
        setNom(utilisateur.getNom());
        setEmail(utilisateur.getEmail());
        setMotDePasse(utilisateur.getMotDePasse());
        setTelephone(utilisateur.getTelephone());
        setRue(utilisateur.getRue());
        setNpa(utilisateur.getNpa());
        setLocalite(utilisateur.getLocalite());
    }

    // Getters et Setters
    public String getNomUtilisateur() { return nomUtilisateur; }
    public void setNomUtilisateur(String nomUtilisateur) { this.nomUtilisateur = nomUtilisateur; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getMotDePasse() { return motDePasse; }
    public void setMotDePasse(String motDePasse) { this.motDePasse = motDePasse; }

    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }

    public String getRue() { return rue; }
    public void setRue(String rue) { this.rue = rue; }

    public String getNpa() { return npa; }
    public void setNpa(String npa) { this.npa = npa; }

    public String getLocalite() { return localite; }
    public void setLocalite(String localite) { this.localite = localite; }
}
