package org.top.animalshelterwebapp.animal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.top.animalshelterwebapp.city.City;
import org.top.animalshelterwebapp.city.CityService;
import org.top.animalshelterwebapp.type.Type;
import org.top.animalshelterwebapp.type.TypeService;
import org.top.animalshelterwebapp.user.User;
import org.top.animalshelterwebapp.user.UserNotFoundException;
import org.top.animalshelterwebapp.user.UserService;

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

    public AnimalController(AnimalService animalService, CityService cityService, UserService userService,
                            TypeService typeService) {
        this.animalService = animalService;
        this.cityService = cityService;
        this.userService = userService;
        this.typeService = typeService;
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
    public String showEditForm(@PathVariable("id") Integer id, Model model,
                               RedirectAttributes ra) {
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
}