package ch.etmles.payroll.Categorie;

public class CategorieNotFoundException extends RuntimeException {
  public CategorieNotFoundException(String message) {
    super(message);
  }
}
