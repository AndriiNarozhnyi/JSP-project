package org.itstep.model.entity;

import lombok.*;

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
    private Set<Role> roles = new HashSet<>();
    Set<Course> takenCourses = new HashSet<>();

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", usernameukr='" + usernameukr + '\'' +
                ", email='" + email + '\'' +
                ", active=" + active +
                ", roles=" + roles +
                '}';
    }
}
