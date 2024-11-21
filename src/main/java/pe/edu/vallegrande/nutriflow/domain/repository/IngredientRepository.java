package pe.edu.vallegrande.nutriflow.domain.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import pe.edu.vallegrande.nutriflow.domain.model.Ingredient;
import reactor.core.publisher.Flux;

@Repository
public interface IngredientRepository extends ReactiveCrudRepository<Ingredient, Long> {
    Flux<Ingredient> findByRecipeId(Long recipeId);

    Flux<Ingredient> findByStatus(String status);
}
