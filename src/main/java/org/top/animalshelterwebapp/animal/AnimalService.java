package org.top.animalshelterwebapp.animal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.top.animalshelterwebapp.card.CardNotFoundException;
import org.top.animalshelterwebapp.user.UserNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class AnimalService {
    @Autowired
    private final AnimalRepository animalRepository;

    public AnimalService(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
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

    public List<Animal> showAllByUserId(Integer id) throws UserNotFoundException {
        List<Animal> animals = animalRepository.findAllByUserId(id);
        if (animals.isEmpty()) {
            throw new UserNotFoundException("Could not find any pets with User ID" + id);
        }
        return animals;
    }

    public List<Animal> showAllByCardId(Integer id) throws AnimalNotFoundException {
        List<Animal> animals = animalRepository.findAllByCardId(id);
        if (animals.isEmpty()) {
            throw new AnimalNotFoundException("Could not find any pets with Card ID" + id);
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