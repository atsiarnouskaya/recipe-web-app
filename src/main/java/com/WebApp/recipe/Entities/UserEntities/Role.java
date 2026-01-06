package com.WebApp.recipe.Entities.UserEntities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "role")
    private String role;

    public Role(String role) {
        this.role = role;
    }

    @ManyToMany(mappedBy = "roles")
    List<User> users;
}
