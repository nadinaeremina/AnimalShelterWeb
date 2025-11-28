package org.top.animalshelterwebapp.city;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CityService {
    @Autowired
    private final CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public List<City> listAll() {
        return (List<City>) cityRepository.findAll();
    }

    public City get(Integer id) throws CityNotFoundException {
        Optional<City> result = cityRepository.findById(id);
        if (result.isPresent()) {
            return result.get();
        }
        throw new CityNotFoundException("Could not find any cities with ID" + id);
    }
}