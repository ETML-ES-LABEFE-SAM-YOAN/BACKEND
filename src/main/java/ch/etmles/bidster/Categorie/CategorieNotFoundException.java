package ch.etmles.bidster.Categorie;

/**
 * Exception personnalisée pour la gestion des erreurs sur les catégories non trouvées.
 */
public class CategorieNotFoundException extends RuntimeException {
    public CategorieNotFoundException(String nom) {
        super("Catégorie non trouvée avec le nom : " + nom);
    }
}
