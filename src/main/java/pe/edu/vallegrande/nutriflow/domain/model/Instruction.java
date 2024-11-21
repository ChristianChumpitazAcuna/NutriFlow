package pe.edu.vallegrande.nutriflow.domain.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table(name = "instructions")
public class Instruction {
    @Id
    @Column(value = "id")
    private Long id;

    @Column(value = "recipe_id")
    private Long recipeId;

    @Column(value = "step_number")
    private Long stepNumber;

    @Column(value = "instruction")
    private String name;

    @Column(value = "status")
    private String status;
}
