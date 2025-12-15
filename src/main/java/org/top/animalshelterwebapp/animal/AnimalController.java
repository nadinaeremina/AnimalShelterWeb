package org.top.animalshelterwebapp.animal;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.top.animalshelterwebapp.MainController;
import org.top.animalshelterwebapp.city.City;
import org.top.animalshelterwebapp.city.CityService;
import org.top.animalshelterwebapp.type.Type;
import org.top.animalshelterwebapp.type.TypeService;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class AnimalController {
    @Autowired
    private final AnimalService animalService;
    private final CityService cityService;
    private final TypeService typeService;
    //private final WorkersRepository repository;
    private final MainController mainController;
    private final EntityManager entityManager;

    public AnimalController(AnimalService animalService, CityService cityService, TypeService typeService,
                            WorkersRepository workersRepository, MainController mainController, EntityManager entityManager) {
        this.animalService = animalService;
        this.cityService = cityService;
        this.typeService = typeService;
        // this.repository = workersRepository;
        this.mainController = mainController;
        this.entityManager = entityManager;
    }

    @GetMapping("/")
    public String showHomePage(Model model) {
        return mainController.findPaginated(1, "", "nickname", "asc", model);
    }

    @GetMapping("/animals")
    public String showList(Model model, RedirectAttributes ra) {
        try {
            List<Animal> listAnimals = animalService.listAll();
            model.addAttribute("listAnimals", listAnimals);
        } catch (Exception ex) {
            model.addAttribute("message", "К сожалению,технические проблемы. Скоро починим.");
        }
        return mainController.findPaginated(1, "animals", "nickname", "asc", model);
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
            ra.addFlashAttribute("message", "К сожалению,технические проблемы. Скоро починим.");
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
            myMethod myMethod = new myMethod(entityManager);
            List<Animal> listAnimals = myMethod.findEmployeesByFields(animalSortData.getType().getTitle(),
                    animalSortData.getCity().getTitle(), animalSortData.getAge());

            Set<Animal> set = new HashSet<Animal>(listAnimals);
            List<Animal> uniqueList = new ArrayList<Animal>(set);

            model.addAttribute("listAnimals", uniqueList);
        } catch (Exception ex) {
            model.addAttribute("message", "К сожалению,технические проблемы. Скоро починим.");
        }
        return "animals";
    }
}