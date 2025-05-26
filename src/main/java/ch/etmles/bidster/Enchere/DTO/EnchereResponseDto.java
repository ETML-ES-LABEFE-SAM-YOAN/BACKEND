package ch.etmles.bidster.Enchere.DTO;

import java.util.Date;

public class EnchereResponseDto {
    private Long id;
    private Double montant;
    private Date dateEnchere;
    private String nomUtilisateur;
    private Long lotId;

    // Getters et setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Double getMontant() { return montant; }
    public void setMontant(Double montant) { this.montant = montant; }
    public Date getDateEnchere() { return dateEnchere; }
    public void setDateEnchere(Date dateEnchere) { this.dateEnchere = dateEnchere; }
    public String getNomUtilisateur() { return nomUtilisateur; }
    public void setNomUtilisateur(String nomUtilisateur) { this.nomUtilisateur = nomUtilisateur; }
    public Long getLotId() { return lotId; }
    public void setLotId(Long lotId) { this.lotId = lotId; }
}
