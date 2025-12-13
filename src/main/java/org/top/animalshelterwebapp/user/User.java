package org.top.animalshelterwebapp.user;

import jakarta.persistence.*;
import org.top.animalshelterwebapp.animal.Animal;

import java.time.LocalDateTime;
import java.util.Set;

// UserDbEntity - БД-сущность пользователя
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

    @Column(name="failed_attempts_t", nullable = false)
    private Integer failedAttempts = 0;

    @Column(name="locked_until_t")
    private java.time.LocalDateTime lockedUntil;

    // связь с сущность (таблицей) животных
    @OneToMany(mappedBy = "user")
    private Set<Animal> animals;

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

    public LocalDateTime getLockedUntil() {
        return lockedUntil;
    }

    public Integer getFailedAttempts() {
        return failedAttempts;
    }

    public Set<Animal> getAnimals() {
        return animals;
    }

    public void setAnimals(Set<Animal> animals) {
        this.animals = animals;
    }

    public void setLockedUntil(LocalDateTime lockedUntil) {
        this.lockedUntil = lockedUntil;
    }

    public void setFailedAttempts(Integer failedAttempts) {
        this.failedAttempts = failedAttempts;
    }
}
