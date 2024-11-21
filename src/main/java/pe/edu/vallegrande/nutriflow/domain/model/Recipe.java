package pe.edu.vallegrande.nutriflow.domain.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table("recipes")
public class Recipe {
    @Id
    @Column(value = "id")
    private Long id;

    @Column(value = "user_id")
    private Long userId;

    @Column(value = "name")
    private String name;

    @Column(value = "description")
    private String description;

    @Column(value = "time")
    private String time;

    @Column(value = "servings")
    private Long servings;

    @Column(value = "image_url")
    private String imageUrl;

    @Column(value = "status")
    private String status;
}
