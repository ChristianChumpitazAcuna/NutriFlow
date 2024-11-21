package pe.edu.vallegrande.nutriflow.domain.dto;

import lombok.Getter;
import lombok.Setter;
import pe.edu.vallegrande.nutriflow.domain.model.Ingredient;
import pe.edu.vallegrande.nutriflow.domain.model.Instruction;
import pe.edu.vallegrande.nutriflow.domain.model.User;

import java.util.List;

@Getter
@Setter
public class RecipeDto {
    private Long id;
    private User userId;
    private String name;
    private String description;
    private String time;
    private Long servings;
    private String imageUrl;
    private List<Ingredient> ingredients;
    private List<Instruction> instructions;
    private String status;
}
