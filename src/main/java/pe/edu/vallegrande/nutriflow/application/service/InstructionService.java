package pe.edu.vallegrande.nutriflow.application.service;

import org.springframework.stereotype.Service;
import pe.edu.vallegrande.nutriflow.domain.model.Instruction;
import pe.edu.vallegrande.nutriflow.domain.repository.InstructionRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
public class InstructionService {
    private final InstructionRepository repository;

    public InstructionService(InstructionRepository repository) {
        this.repository = repository;
    }

    public Flux<Instruction> findByStatus(String status) {
        return repository.findByStatus(status);
    }

    public Mono<Instruction> save(Instruction instruction) {
        return repository.save(instruction);
    }

    public Mono<Instruction> update(Long id, Instruction instruction) {
        return repository.findById(id)
                .flatMap(existingInstruction -> updateInstruction(existingInstruction, instruction))
                .switchIfEmpty(Mono.error(new RuntimeException("Instruction not found")));
    }

    public Mono<Instruction> changeStatus(Long id, String status) {
        return repository.findById(id)
                .flatMap(instruction -> {
                    instruction.setStatus(status);
                    return repository.save(instruction);
                })
                .switchIfEmpty(Mono.error(new RuntimeException("Instruction not found")));
    }

    private Mono<Instruction> updateInstruction(Instruction existingInstruction, Instruction instruction) {
        return Mono.just(instruction)
                .map(i -> {
                    Optional.ofNullable(i.getName())
                            .ifPresent(existingInstruction::setName);
                    Optional.ofNullable(i.getRecipeId())
                            .ifPresent(existingInstruction::setRecipeId);
                    Optional.ofNullable(i.getStepNumber())
                            .ifPresent(existingInstruction::setStepNumber);
                    Optional.ofNullable(i.getStatus())
                            .ifPresent(existingInstruction::setStatus);
                    return existingInstruction;
                })
                .flatMap(repository::save);
    }
}
