package org.top.animalshelterwebapp.type;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface TypeRepository extends JpaRepository<Type, Integer> {
    Long countById(Integer id);
}
