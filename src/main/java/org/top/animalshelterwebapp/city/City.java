package org.top.animalshelterwebapp.city;

import jakarta.persistence.*;
import org.top.animalshelterwebapp.animal.Animal;

import java.util.Set;

@Entity
@Table(name = "cities")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="title_f", nullable = false, length = 45)
    private String title;

    // связь с сущность (таблицей) животных
    @OneToMany(mappedBy = "city")
    private Set<Animal> animals;

    public String getTitle() {
        return title;
    }

    public Set<Animal> getAnimals() {
        return animals;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAnimals(Set<Animal> animals) {
        this.animals = animals;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
