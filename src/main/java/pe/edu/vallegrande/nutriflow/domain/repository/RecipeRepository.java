package pe.edu.vallegrande.nutriflow.domain.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import pe.edu.vallegrande.nutriflow.domain.model.Recipe;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface RecipeRepository extends ReactiveCrudRepository<Recipe, Long> {

    Flux<Recipe> findByStatus(String status);

    Mono<Recipe> findByUserIdAndStatus(Long userId, String status);
}
