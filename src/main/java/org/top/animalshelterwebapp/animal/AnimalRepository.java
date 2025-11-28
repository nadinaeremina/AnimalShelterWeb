package org.top.animalshelterwebapp.animal;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AnimalRepository extends CrudRepository<Animal, Integer> {
    Long countById(Integer id);
    List<Animal> findAllByUserId(Integer id);
    List<Animal> findAllByCardId(Integer id);
}