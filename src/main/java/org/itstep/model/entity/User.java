package org.itstep.model.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long id;
    private String username;
    private String usernameukr;
    private String email;
    private String password;
    private boolean active;
    private Set<Role> roles;
    Set<Course> takenCourses = new HashSet<>();
}
