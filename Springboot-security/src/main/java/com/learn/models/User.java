package com.learn.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
//    @NotNull(message = "id can not be null")
    private int id;

    @NotNull(message = "Name can not be null")
    @NotBlank(message = "Name can not be blank")
    private String name;

    @NotNull(message = "Password can not be null")
    @NotBlank(message = "Password can not be blank")
    private String password;

    @NotNull(message = "email can not be null")
    @NotBlank(message = "email can not be blank")
    @Column(unique = true)
    private String email;

    @NotNull(message = "role can not be null")
    @NotBlank(message = "role can not be blank")
    private String role;

}
