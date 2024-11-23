package pe.edu.vallegrande.nutriflow.presentation.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.nutriflow.application.service.InstructionService;
import pe.edu.vallegrande.nutriflow.domain.model.Instruction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/instructions")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class InstructionController {
    private final InstructionService service;

    public InstructionController(InstructionService service) {
        this.service = service;
    }

    @GetMapping("/list/active")
    @ResponseStatus(HttpStatus.OK)
    public Flux<Instruction> listActive() {
        return service.findByStatus("A");
    }

    @GetMapping("/list/inactive")
    @ResponseStatus(HttpStatus.OK)
    public Flux<Instruction> listInactive() {
        return service.findByStatus("I");
    }

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public Flux<Instruction> save(@RequestBody Flux<Instruction> instruction) {
        return service.save(instruction);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Instruction> update(@PathVariable Long id,
                                    @RequestBody Instruction instruction) {
        return service.update(id, instruction);
    }

    @PutMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Instruction> delete(@PathVariable Long id) {
        return service.changeStatus(id, "I");
    }

    @PutMapping("/activate/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Instruction> activate(@PathVariable Long id) {
        return service.changeStatus(id, "A");
    }
}
