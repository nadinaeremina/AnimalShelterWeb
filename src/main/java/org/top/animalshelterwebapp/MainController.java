package org.top.animalshelterwebapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.top.animalshelterwebapp.animal.Animal;
import org.top.animalshelterwebapp.animal.AnimalService;
import org.top.animalshelterwebapp.city.City;
import org.top.animalshelterwebapp.city.CityService;
import org.top.animalshelterwebapp.guardian.Guardian;
import org.top.animalshelterwebapp.type.Type;
import org.top.animalshelterwebapp.type.TypeService;
import org.top.animalshelterwebapp.guardian.GuardianService;

import java.util.List;

@Controller
public class MainController {
    @Autowired
    private final AnimalService animalService;
    private final CityService cityService;
    private final GuardianService guardianService;
    private final TypeService typeService;

    public MainController(AnimalService animalService, CityService cityService, GuardianService guardianService,
                          TypeService typeService) {
        this.animalService = animalService;
        this.cityService = cityService;
        this.guardianService = guardianService;
        this.typeService = typeService;
    }

    @GetMapping("/index")
    public String showHomePage() {
        return "index";
    }

    // /page/1?sortField=name&sortDir=asc
    @GetMapping("/page/{number}/{object}")
    public String findPaginated(@PathVariable("number") Integer pageNumber,
                                @PathVariable("object") String object,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model model) {
        int pageSize = 6;
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        if (object.equals("animals")) {
            Page<Animal> page = animalService.findPaginated(pageNumber, pageSize, sortField, sortDir);
            List<Animal> listAnimals = page.getContent();
            model.addAttribute("listAnimals", listAnimals);
            model.addAttribute("totalPages", page.getTotalPages());
            model.addAttribute("totalItems", page.getTotalElements());
            return "animals";
        } else if (object.equals("guardians")) {
            Page<Guardian> page = guardianService.findPaginated(pageNumber, pageSize, sortField, sortDir);
            List<Guardian> listGuardians= page.getContent();
            model.addAttribute("listGuardians", listGuardians);
            model.addAttribute("totalPages", page.getTotalPages());
            model.addAttribute("totalItems", page.getTotalElements());
            return "guardians";
        } else if (object.equals("cities")) {
            Page<City> page = cityService.findPaginated(pageNumber, pageSize, sortField, sortDir);
            List<City> listCities = page.getContent();
            model.addAttribute("listCities", listCities);
            model.addAttribute("totalPages", page.getTotalPages());
            model.addAttribute("totalItems", page.getTotalElements());
            return "cities";
        } else if (object.equals("types")) {
            Page<Type> page = typeService.findPaginated(pageNumber, pageSize, sortField, sortDir);
            List<Type> listTypes = page.getContent();
            model.addAttribute("listTypes", listTypes);
            model.addAttribute("totalPages", page.getTotalPages());
            model.addAttribute("totalItems", page.getTotalElements());
            return "types";
        }
        return "index";
    }
}