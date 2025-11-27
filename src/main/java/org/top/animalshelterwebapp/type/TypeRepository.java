package org.top.animalshelterwebapp.type;

import org.springframework.data.repository.CrudRepository;

public interface TypeRepository extends CrudRepository<Type, Integer> {
    Long countById(Integer id);
}
