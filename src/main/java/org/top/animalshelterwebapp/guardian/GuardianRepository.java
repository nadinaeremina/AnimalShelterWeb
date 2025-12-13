package org.top.animalshelterwebapp.guardian;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GuardianRepository extends JpaRepository<Guardian, Integer> {
    Long countById(Integer id);
}
