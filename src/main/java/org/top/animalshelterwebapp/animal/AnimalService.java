package org.top.animalshelterwebapp.animal;

import org.springframework.beans.factory.annotation.Autowired;
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

    public void save(Animal animal) {
        animalRepository.save(animal);
    }

    public Animal get(Integer id) throws AnimalNotFoundException {
        Optional<Animal> result = animalRepository.findById(id);
        if (result.isPresent()) {
            return result.get();
        }
        throw new AnimalNotFoundException("Could not find any pets with ID" + id);
    }

    public void delete(Integer id) throws AnimalNotFoundException {
        Long count = animalRepository.countById(id);
        if (count == null || count == 0) {
            throw new AnimalNotFoundException("Could not find any pets with ID" + id);
        }
        animalRepository.deleteById(id);
    }

    public List<Animal> showAllByUserId(Integer id) throws UserNotFoundException {
        List<Animal> animals = animalRepository.findAllByUserId(id);
        if (animals.isEmpty()) {
            throw new UserNotFoundException("Could not find any pets with User ID" + id);
        }
        return animals;
    }

    public List<Animal> showAllByCardId(Integer id) throws CardNotFoundException {
        List<Animal> animals = animalRepository.findAllByCardId(id);
        if (animals.isEmpty()) {
            throw new CardNotFoundException("Could not find any pets with Card ID" + id);
        }
        return animals;
    }
}