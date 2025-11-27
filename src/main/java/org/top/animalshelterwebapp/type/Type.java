package org.top.animalshelterwebapp.type;

import jakarta.persistence.*;
import org.top.animalshelterwebapp.animal.Animal;

import java.util.Set;

@Entity
@Table(name = "types")
public class Type {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="title_f", nullable = false, length = 45)
    private String title;

    @Column(name="breed_f", nullable = false, length = 45)
    private String breed;

    // связь с сущностью (таблицей) животных
    @OneToMany(mappedBy = "type")
    private Set<Animal> animals;

    public String getTitle() {
        return title;
    }

    public Integer getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Set<Animal> getAnimals() {
        return animals;
    }

    public void setAnimals(Set<Animal> animals) {
        this.animals = animals;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getBreed() {
        return breed;
    }
}
