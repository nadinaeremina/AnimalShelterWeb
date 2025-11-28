package org.top.animalshelterwebapp.card;

import jakarta.persistence.*;
import org.top.animalshelterwebapp.animal.Animal;

import java.util.Set;

@Entity
@Table(name = "cards")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="name_f", nullable = false, length = 45)
    private String name;

    // связь с сущность (таблицей) животных
    @OneToMany(mappedBy = "card")
    private Set<Animal> animals;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setAnimals(Set<Animal> animals) {
        this.animals = animals;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Animal> getAnimals() {
        return animals;
    }

    public String getName() {
        return name;
    }
}
