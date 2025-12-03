package org.top.animalshelterwebapp.animal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

// интерфейс для работы с выборкой
public interface WorkersRepository extends JpaRepository<Animal, UUID>, JpaSpecificationExecutor<Animal> {
    //@Query(value = "select count(20) as recordCount, sum(city) as citySum from animals", nativeQuery = true)
    //Summary sortedAnimals();
    //@Query(value = "select * from animals where age = age")
    //List<Animal> findByAge(Integer age);
    List<Animal> findByCityId(Integer id);
}
