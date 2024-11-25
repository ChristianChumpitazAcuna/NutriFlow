package pe.edu.vallegrande.nutriflow.domain.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import pe.edu.vallegrande.nutriflow.domain.model.Recipe;
import reactor.core.publisher.Flux;

@Repository
public interface RecipeRepository extends ReactiveCrudRepository<Recipe, Long> {

    Flux<Recipe> findByStatus(String status);

    Flux<Recipe> findByUserIdAndStatus(Long userId, String status);
}
