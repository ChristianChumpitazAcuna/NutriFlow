package pe.edu.vallegrande.nutriflow.presentation.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.nutriflow.application.service.IngredientService;
import pe.edu.vallegrande.nutriflow.domain.model.Ingredient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/ingredients")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class IngredientController {
    private final IngredientService service;

    public IngredientController(IngredientService service) {
        this.service = service;
    }

    @GetMapping("/list/active")
    @ResponseStatus(HttpStatus.OK)
    public Flux<Ingredient> listActiveIngredients() {
        return service.findByStatus("A");
    }

    @GetMapping("/list/inactive")
    @ResponseStatus(HttpStatus.OK)
    public Flux<Ingredient> listInactiveIngredients() {
        return service.findByStatus("I");
    }

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Ingredient> saveIngredient(@RequestBody Ingredient ingredient) {
        return service.save(ingredient);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Ingredient> updateIngredient(@PathVariable Long id,
                                             @RequestBody Ingredient ingredient) {
        return service.update(id, ingredient);
    }

    @PutMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Ingredient> deleteIngredient(@PathVariable Long id) {
        return service.changeStatus(id, "I");
    }

    @PutMapping("/activate/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Ingredient> activateIngredient(@PathVariable Long id) {
        return service.changeStatus(id, "A");
    }
}
