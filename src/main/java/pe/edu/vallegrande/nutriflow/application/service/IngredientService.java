package pe.edu.vallegrande.nutriflow.application.service;

import org.springframework.stereotype.Service;
import pe.edu.vallegrande.nutriflow.domain.model.Ingredient;
import pe.edu.vallegrande.nutriflow.domain.model.Instruction;
import pe.edu.vallegrande.nutriflow.domain.repository.IngredientRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
public class IngredientService {

    private final IngredientRepository repository;

    public IngredientService(IngredientRepository repository) {
        this.repository = repository;
    }

    public Flux<Ingredient> findByStatus(String status) {
        return repository.findByStatus(status);
    }

    public Flux<Ingredient> save(Flux<Ingredient> ingredient) {
        return repository.saveAll(ingredient);
    }

    public Mono<Ingredient> update(Long id, Ingredient ingredient) {
        return repository.findById(id)
                .flatMap(existingIngredient ->
                        updateIngredient(existingIngredient, ingredient))
                .switchIfEmpty(Mono.error(new RuntimeException("Ingredient not found")));
    }

    public Mono<Ingredient> changeStatus(Long id, String status) {
        return repository.findById(id)
                .flatMap(ingredient -> {
                    ingredient.setStatus(status);
                    return repository.save(ingredient);
                })
                .switchIfEmpty(Mono.error(new RuntimeException("Ingredient not found")));
    }

    private Mono<Ingredient> updateIngredient(Ingredient existingIngredient, Ingredient ingredient) {
        return Mono.just(ingredient)
                .map(i -> {
                    Optional.ofNullable(i.getName())
                            .ifPresent(existingIngredient::setName);
                    Optional.ofNullable(i.getRecipeId())
                            .ifPresent(existingIngredient::setRecipeId);
                    Optional.ofNullable(i.getStatus())
                            .ifPresent(existingIngredient::setStatus);
                    return existingIngredient;
                })
                .flatMap(repository::save);
    }
}
