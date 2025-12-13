package org.top.animalshelterwebapp.animal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AnimalRepository extends JpaRepository<Animal, Integer> {
    // Long countById(Integer id);
    List<Animal> findAllByGuardianId(Integer id);
    List<Animal> findAllByUserId(Integer id);
}