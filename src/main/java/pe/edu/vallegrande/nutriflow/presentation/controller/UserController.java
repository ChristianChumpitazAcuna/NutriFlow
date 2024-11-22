package pe.edu.vallegrande.nutriflow.presentation.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.nutriflow.application.service.UserService;
import pe.edu.vallegrande.nutriflow.domain.model.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/findByEmail/{email}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ResponseEntity<User>> findByEmail(@PathVariable String email) {
        return service.findByEmail(email)
                .map(ResponseEntity::ok)
                .onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).build()));
    }

    @GetMapping("/list/active")
    @ResponseStatus(HttpStatus.OK)
    public Flux<User> listActiveUsers() {
        return service.findByStatus("A");
    }

    @GetMapping("/list/inactive")
    @ResponseStatus(HttpStatus.OK)
    public Flux<User> listInactiveUsers() {
        return service.findByStatus("I");
    }

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<User> saveUser(@RequestBody User user) {
        return service.save(user);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        return service.update(id, user);
    }

    @PutMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<User> deleteUser(@PathVariable Long id) {
        return service.changeStatus(id, "I");
    }

    @PutMapping("/activate/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<User> activateUser(@PathVariable Long id) {
        return service.changeStatus(id, "A");
    }
}
