package org.top.animalshelterwebapp.type;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TypeService {
    @Autowired
    private final TypeRepository typeRepository;

    public TypeService(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    public List<Type> listAll() {
        return (List<Type>) typeRepository.findAll();
    }

    public Type get(Integer id) throws TypeNotFoundException {
        Optional<Type> result = typeRepository.findById(id);
        if (result.isPresent()) {
            return result.get();
        }
        throw new TypeNotFoundException("Could not find any types with ID" + id);
    }
}
