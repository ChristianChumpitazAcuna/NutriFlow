package pe.edu.vallegrande.nutriflow.domain.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table(name = "ingredients")
public class Ingredient {
    @Id
    private Long id;

    @Column(value = "recipe_id")
    private Long recipeId;

    @Column(value = "ingredient")
    private String name;

    @Column(value = "status")
    private String status;
}
