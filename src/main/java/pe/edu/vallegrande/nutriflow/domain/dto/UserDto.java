package pe.edu.vallegrande.nutriflow.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDto {
    private Long id;
    private String displayName;
    private String avatarUrl;
    private String email;
    private String status;
    private List<RecipeDto> recipes;

}
