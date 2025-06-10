package ch.etmles.bidster.Lot.DTO;

public class LotStatusDto {
    private Long id;
    private String nomArticle;
    private String description;
    private boolean venteConfirmee;


    // Constructeurs, getters, setters.
    public LotStatusDto() {}

    public LotStatusDto(Long id, String nomArticle, String description, boolean venteConfirmee) {
        this.id = id;
        this.nomArticle = nomArticle;
        this.description = description;
        this.venteConfirmee = venteConfirmee;
    }

    public boolean isVenteConfirmee() {
        return venteConfirmee;
    }

    public void setVenteConfirmee(boolean venteConfirmee) {
        this.venteConfirmee = venteConfirmee;
    }

    public String getNomArticle() {
        return nomArticle;
    }


    public void setNomArticle(String nomArticle) {
        this.nomArticle = nomArticle;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

