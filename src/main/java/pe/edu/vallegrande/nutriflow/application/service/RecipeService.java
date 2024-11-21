package pe.edu.vallegrande.nutriflow.application.service;

import org.springframework.stereotype.Service;
import pe.edu.vallegrande.nutriflow.domain.dto.RecipeDto;
import pe.edu.vallegrande.nutriflow.domain.model.Ingredient;
import pe.edu.vallegrande.nutriflow.domain.model.Instruction;
import pe.edu.vallegrande.nutriflow.domain.model.Recipe;
import pe.edu.vallegrande.nutriflow.domain.model.User;
import pe.edu.vallegrande.nutriflow.domain.repository.IngredientRepository;
import pe.edu.vallegrande.nutriflow.domain.repository.InstructionRepository;
import pe.edu.vallegrande.nutriflow.domain.repository.RecipeRepository;
import pe.edu.vallegrande.nutriflow.domain.repository.UserRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {
    private final RecipeRepository repository;
    private final IngredientRepository ingredientRepository;
    private final InstructionRepository instructionRepository;
    private final UserRepository userRepository;

    public RecipeService(
            RecipeRepository repository,
            IngredientRepository ingredientRepository,
            InstructionRepository instructionRepository,
            UserRepository userRepository
    ) {
        this.repository = repository;
        this.ingredientRepository = ingredientRepository;
        this.instructionRepository = instructionRepository;
        this.userRepository = userRepository;
    }

    public Flux<RecipeDto> findByStatus(String status) {
        return repository.findByStatus(status)
                .flatMap(this::convertToDto)
                .collectList()
                .flatMapMany(Flux::fromIterable);
    }

    public Mono<RecipeDto> findByUserAndStatus(Long id, String status) {
        return repository.findByUserIdAndStatus(id, status)
                .flatMap(this::convertToDto);
    }

    public Mono<Recipe> save(Recipe recipe) {
        return repository.save(recipe);
    }

    public Mono<Recipe> update(Long id, Recipe recipe) {
        return repository.findById(id)
                .flatMap(existingRecipe -> updateRecipe(existingRecipe, recipe))
                .switchIfEmpty(Mono.error(new RuntimeException("Recipe not found")));
    }

    public Mono<Recipe> changeStatus(Long id, String status) {
        return repository.findById(id)
                .flatMap(recipe -> {
                    recipe.setStatus(status);
                    return repository.save(recipe);
                })
                .switchIfEmpty(Mono.error(new RuntimeException("Recipe not found")));
    }

    private Mono<Recipe> updateRecipe(Recipe existingRecipe, Recipe newRecipe) {
        return Mono.just(existingRecipe)
                .map(recipe -> {
                    Optional.ofNullable(newRecipe.getName())
                            .ifPresent(recipe::setName);
                    Optional.ofNullable(newRecipe.getImageUrl())
                            .ifPresent(recipe::setImageUrl);
                    Optional.ofNullable(newRecipe.getServings())
                            .ifPresent(recipe::setServings);
                    Optional.ofNullable(newRecipe.getTime())
                            .ifPresent(recipe::setTime);
                    Optional.ofNullable(newRecipe.getDescription())
                            .ifPresent(recipe::setDescription);
                    Optional.ofNullable(newRecipe.getStatus())
                            .ifPresent(recipe::setStatus);
                    return recipe;
                })
                .flatMap(repository::save);
    }

    private Mono<RecipeDto> convertToDto(Recipe recipe) {
        RecipeDto dto = new RecipeDto();
        dto.setId(recipe.getId());
        dto.setName(recipe.getName());
        dto.setImageUrl(recipe.getImageUrl());
        dto.setServings(recipe.getServings());
        dto.setTime(recipe.getTime());
        dto.setDescription(recipe.getDescription());
        dto.setStatus(recipe.getStatus());

        Mono<User> userMono = userRepository.findById(recipe.getUserId())
                .switchIfEmpty(Mono.just(new User()));

        Mono<List<Ingredient>> ingredientsMono = ingredientRepository
                .findByRecipeId(recipe.getId())
                .collectList()
                .switchIfEmpty(Mono.just(List.of()));

        Mono<List<Instruction>> instructionsMono = instructionRepository
                .findByRecipeId(recipe.getId())
                .collectList()
                .switchIfEmpty(Mono.just(List.of()));

        return Mono.zip(userMono, ingredientsMono, instructionsMono)
                .map(tuple -> {
                    dto.setUserId(tuple.getT1());
                    dto.setIngredients(tuple.getT2());
                    dto.setInstructions(tuple.getT3());
                    return dto;
                });

    }
}
