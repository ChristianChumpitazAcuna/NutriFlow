package pe.edu.vallegrande.nutriflow.domain.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import pe.edu.vallegrande.nutriflow.domain.model.Instruction;
import reactor.core.publisher.Flux;

@Repository
public interface InstructionRepository extends ReactiveCrudRepository<Instruction, Long> {
    Flux<Instruction> findByRecipeId(Long recipeId);

    Flux<Instruction> findByStatus(String status);
}
