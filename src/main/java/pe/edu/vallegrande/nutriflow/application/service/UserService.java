package pe.edu.vallegrande.nutriflow.application.service;

import org.springframework.stereotype.Service;
import pe.edu.vallegrande.nutriflow.domain.dto.UserDto;
import pe.edu.vallegrande.nutriflow.domain.model.Recipe;
import pe.edu.vallegrande.nutriflow.domain.model.User;
import pe.edu.vallegrande.nutriflow.domain.repository.UserRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public Flux<User> findByStatus(String status) {
        return repository.findByStatus(status);
    }

    public Mono<User> findByEmail(String email) {
        return repository.findByEmail(email)
                .switchIfEmpty(Mono.error(new RuntimeException("User not found")));
    }

    public Mono<User> save(User user) {
        return repository.save(user);
    }

    public Mono<User> update(Long id, User user) {
        return repository.findById(id)
                .flatMap(existingUser -> updateUser(existingUser, user))
                .switchIfEmpty(Mono.error(new RuntimeException("User not found")));
    }

    public Mono<User> changeStatus(Long id, String status) {
        return repository.findById(id)
                .map(user -> {
                    user.setStatus(status);
                    return user;
                })
                .flatMap(repository::save)
                .switchIfEmpty(Mono.error(new RuntimeException("User not found")));
    }

    private Mono<User> updateUser(User existingUser, User newUser) {
        return Mono.just(existingUser)
                .map(user -> {
                    Optional.ofNullable(newUser.getDisplayName())
                            .ifPresent(user::setDisplayName);
                    Optional.ofNullable(newUser.getAvatarUrl())
                            .ifPresent(user::setAvatarUrl);
                    Optional.ofNullable(newUser.getEmail())
                            .ifPresent(user::setEmail);
                    Optional.ofNullable(newUser.getStatus())
                            .ifPresent(user::setStatus);
                    return user;
                })
                .flatMap(repository::save);
    }

}
