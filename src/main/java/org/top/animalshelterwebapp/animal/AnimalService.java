package org.top.animalshelterwebapp.animal;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.top.animalshelterwebapp.guardian.GuardianNotFoundException;
import org.top.animalshelterwebapp.user.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AnimalService {
    @Autowired
    private final AnimalRepository animalRepository;
    private final EntityManager entityManager;

    public AnimalService(AnimalRepository animalRepository, EntityManager entityManager) {
        this.animalRepository = animalRepository;
        this.entityManager = entityManager;
    }

    public List<Animal> listAll() {
        return (List<Animal>) animalRepository.findAll();
    }

    public Animal get(Integer id) throws AnimalNotFoundException {
        Optional<Animal> result = animalRepository.findById(id);
        if (result.isPresent()) {
            return result.get();
        }
        throw new AnimalNotFoundException("Could not find any pets with ID" + id);
    }

    public void save(Animal animal) {
        animalRepository.save(animal);
    }

    public List<Animal> showAllByGuardianId(Integer id) throws GuardianNotFoundException {
        List<Animal> animals = animalRepository.findAllByGuardianId(id);
        if (animals.isEmpty()) {
            throw new GuardianNotFoundException("Could not find any pets with User ID" + id);
        }
        return animals;
    }

    public Set<Animal> showAllByUserId(Integer id) throws AnimalNotFoundException {
        User user = entityManager.find(User.class, id);
        Set<Animal> animals = user.getAnimals();
        if (animals == null) {
            throw new AnimalNotFoundException("Could not find any pets with User ID" + id);
        }
        return animals;
    }

    public Page<Animal> findPaginated(Integer pageNumber, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        PageRequest pageable= PageRequest.of(pageNumber - 1, pageSize, sort);
        return this.animalRepository.findAll(pageable);
    }
}