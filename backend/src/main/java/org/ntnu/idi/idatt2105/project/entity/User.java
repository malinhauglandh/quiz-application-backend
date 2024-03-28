package org.ntnu.idi.idatt2105.project.entity;

import jakarta.persistence.*;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "users")
public class User {
    @Setter
    @Id
    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password")
    private String password;

    public User(){}

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return getUsername().equals(user.getUsername());
    }
    @Override
    public int hashCode() {
        return Objects.hash(getUsername());
    }
}