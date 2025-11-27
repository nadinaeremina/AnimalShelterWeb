package org.top.animalshelterwebapp.city;

import org.springframework.data.repository.CrudRepository;

public interface CityRepository extends CrudRepository<City, Integer> {
    Long countById(Integer id);
}
