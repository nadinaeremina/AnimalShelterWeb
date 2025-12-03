package org.top.animalshelterwebapp.animal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.top.animalshelterwebapp.city.City;
import org.top.animalshelterwebapp.city.CityService;
import org.top.animalshelterwebapp.type.Type;
import org.top.animalshelterwebapp.type.TypeService;
import org.top.animalshelterwebapp.user.User;
import org.top.animalshelterwebapp.user.UserNotFoundException;
import org.top.animalshelterwebapp.user.UserService;

import java.awt.print.Pageable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Controller
public class AnimalController {
    @Autowired
    private final AnimalService animalService;
    private final CityService cityService;
    private final UserService userService;
    private final TypeService typeService;
    private final WorkersRepository repository;

    public AnimalController(AnimalService animalService, CityService cityService, UserService userService,
                            TypeService typeService, WorkersRepository workersRepository) {
        this.animalService = animalService;
        this.cityService = cityService;
        this.userService = userService;
        this.typeService = typeService;
        this.repository = workersRepository;
    }

    @GetMapping("/animals")
    public String showList(Model model, RedirectAttributes ra) {
        try {
            List<Animal> listAnimals = animalService.listAll();
            model.addAttribute("listAnimals", listAnimals);
        } catch (Exception ex) {
            model.addAttribute("message", ex.getCause());
        }
        return "animals";
    }

    @GetMapping("/animals/current/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
        try {
            Animal currentAnimal = animalService.get(id);
            model.addAttribute("currentAnimal", currentAnimal);
            model.addAttribute("pageTitle",
                    "Editing a pet with ID: " + id + ":");
            return "current_animal";
        } catch (AnimalNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/animals";
        }
    }

    @GetMapping("/sorting_form")
    public String sortingForm(Model model) {
        model.addAttribute("pageTitle", "Sorted Animals");
        List<City> listCities = cityService.listAll();
        List<Type> listTypes = typeService.listAll();
        model.addAttribute("animalSortData", new AnimalSortData());
        model.addAttribute("listCities", listCities);
        model.addAttribute("listTypes", listTypes);
        return "sorting_form";
    }

    @PostMapping("/sorted_animals")
    public String sortedAnimals(AnimalSortData animalSortData, RedirectAttributes ra, Model model) {
        model.addAttribute("pageTitle", "Sorted Animals");
        try {

            // работа с выборкой
            int PageSize = 10; // почему так ???????????????????????????????????
            Integer age = animalSortData.getAge();
            CriteriaData criteriaData = new CriteriaData(String.valueOf(age), Operation.LT, "age");
            PageRequest pageRequest = PageRequest.of(0, PageSize);
            Page<Animal> entities = repository.findAll(new WorkerSpecification(criteriaData), pageRequest);
            List<Animal> listAnimals = entities.getContent();

            model.addAttribute("listAnimals", listAnimals);
        } catch (Exception ex) {
            model.addAttribute("message", ex.getCause());
        }
        return "animals";
    }
}