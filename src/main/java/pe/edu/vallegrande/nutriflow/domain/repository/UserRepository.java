package pe.edu.vallegrande.nutriflow.domain.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import pe.edu.vallegrande.nutriflow.domain.model.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveCrudRepository<User, Long> {
    Flux<User> findByStatus(String status);
    Mono<User> findByEmail(String email);
}
