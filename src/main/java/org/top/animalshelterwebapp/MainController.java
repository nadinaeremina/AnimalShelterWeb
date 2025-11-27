package org.top.animalshelterwebapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.top.animalshelterwebapp.animal.Animal;
import org.top.animalshelterwebapp.animal.AnimalService;
import org.top.animalshelterwebapp.city.CityService;
import org.top.animalshelterwebapp.type.TypeService;
import org.top.animalshelterwebapp.user.UserService;

import java.util.List;

@Controller
public class MainController {
    @Autowired
    private final AnimalService animalService;

    public MainController(AnimalService animalService) {
        this.animalService = animalService;
    }

    @GetMapping("")
    public String showHomePage(Model model, RedirectAttributes ra) {
        try {
            List<Animal> listAnimals = animalService.listAll();
            model.addAttribute("listAnimals", listAnimals);
        } catch (Exception ex) {
            model.addAttribute("message", ex.getCause());
        }
        return "index";
    }
}