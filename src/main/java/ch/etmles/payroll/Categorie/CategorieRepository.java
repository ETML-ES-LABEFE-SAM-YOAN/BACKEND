package ch.etmles.payroll.Categorie;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

/**
 * Repository Spring Data pour accéder aux catégories.
 */
public interface CategorieRepository extends JpaRepository<CategorieEntity, Long> {
    // Trouver une catégorie par son nom
    Optional<CategorieEntity> findByNom(String nom);

    // Trouver toutes les catégories principales (parent == null)
    List<CategorieEntity> findByParentIsNull();
}
