package org.top.animalshelterwebapp.guardian;

import jakarta.persistence.*;
import org.top.animalshelterwebapp.animal.Animal;

import java.util.Set;

@Entity
@Table(name = "guardians")
public class Guardian {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="firstName_f", nullable = false, length = 45)
    private String firstName;

    @Column(name="lastName_f", nullable = false, length = 45)
    private String lastName;

    @Column(name="number_f", nullable = false, length = 15)
    private String number;

    // связь с сущность (таблицей) животных
    @OneToMany(mappedBy = "guardian")
    private Set<Animal> animals;

    public Integer getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getNumber() {
        return number;
    }

    public Set<Animal> getAnimals() {
        return animals;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setAnimals(Set<Animal> animals) {
        this.animals = animals;
    }

    @Override
    public String toString() {
        return lastName + " " + firstName + ", \n" + number;
    }
}
