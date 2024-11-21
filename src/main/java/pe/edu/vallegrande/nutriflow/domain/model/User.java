package pe.edu.vallegrande.nutriflow.domain.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table(name = "users")
public class User {
    @Id
    @Column(value = "id")
    private Long id;

    @Column(value = "display_name")
    private String displayName;

    @Column(value = "avatar_url")
    private String avatarUrl;

    @Column(value = "email")
    private String email;

    @Column(value = "status")
    private String status;
}
