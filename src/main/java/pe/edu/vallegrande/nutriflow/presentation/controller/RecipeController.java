package pe.edu.vallegrande.nutriflow.presentation.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.nutriflow.application.service.RecipeService;
import pe.edu.vallegrande.nutriflow.domain.dto.RecipeDto;
import pe.edu.vallegrande.nutriflow.domain.model.Recipe;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/recipes")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class RecipeController {
    private final RecipeService service;

    public RecipeController(RecipeService service) {
        this.service = service;
    }

    @GetMapping("/list/active")
    @ResponseStatus(HttpStatus.OK)
    public Flux<RecipeDto> listActive() {
        return service.findByStatus("A");
    }

    @GetMapping("/list/inactive")
    @ResponseStatus(HttpStatus.OK)
    public Flux<RecipeDto> listInactive() {
        return service.findByStatus("I");
    }

    @GetMapping("/list/active/user/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Flux<RecipeDto> listActiveByUser(@PathVariable Long id) {
        return service.findByUserAndStatus(id, "A");
    }

    @GetMapping("/list/inactive/user/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Flux<RecipeDto> listInactiveByUser(@PathVariable Long id) {
        return service.findByUserAndStatus(id, "I");
    }

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Recipe> create(@RequestBody Recipe recipe) {
        return service.save(recipe);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Recipe> update(@PathVariable Long id, @RequestBody Recipe recipe) {
        return service.update(id, recipe);
    }

    @PutMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Recipe> delete(@PathVariable Long id) {
        return service.changeStatus(id, "I");
    }

    @PutMapping("/activate/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Recipe> activate(@PathVariable Long id) {
        return service.changeStatus(id, "A");
    }
}
