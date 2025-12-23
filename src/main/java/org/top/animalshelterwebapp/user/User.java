package org.top.animalshelterwebapp.user;

import jakarta.persistence.*;
import org.springframework.lang.Nullable;
import org.top.animalshelterwebapp.animal.Animal;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

// БД-сущность пользователя
@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="login_f", nullable = false, length = 45)
    private String login;

    // пароль в захэшированном виде
    @Column(name="password_hash_f", nullable = false, length = 100)
    private String passwordHash;

    @Column(name="role_f", nullable = false, length = 15)
    private String role;

//    @ManyToMany(mappedBy = "users") // "books" refers to the field name in the Author entity
//    private Set<Animal> animals = new HashSet<>();

    // связь с сущность (таблицей) животных
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "animal_user",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName="id"),
            inverseJoinColumns = @JoinColumn(name = "animal_id", referencedColumnName="id")
    )
    private @Nullable Set<Animal> animals = new HashSet<>();

    public User() {}

    public void setRole(String role) {
        this.role = role;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getLogin() {
        return login;
    }

    public Integer getId() {
        return id;
    }

    public Set<Animal> getAnimals() {
        return animals;
    }

    public void setAnimals (@Nullable Animal animal) {
        this.animals.add(animal);
    }
}
