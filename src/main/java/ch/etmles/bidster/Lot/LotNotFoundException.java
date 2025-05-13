package ch.etmles.bidster.Lot;
/**
 * Exception personnalisée pour la gestion des erreurs sur les lots non trouvées.
 */
public class LotNotFoundException extends RuntimeException {
  public LotNotFoundException(Long id) {
    super("Le lot avec cette id n'existe pas : " + id);
  }
}