package org.top.animalshelterwebapp.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.top.animalshelterwebapp.MainController;
import org.top.animalshelterwebapp.animal.Animal;
import org.top.animalshelterwebapp.animal.AnimalService;

import java.util.List;

@Controller
public class UserController {
    @Autowired
    private final UserService userService;
    private final AnimalService animalService;
    private final MainController mainController;

    public UserController(UserService userService, AnimalService animalService, MainController mainController) {
        this.userService = userService;
        this.animalService = animalService;
        this.mainController = mainController;
    }

    @GetMapping("/users")
    public String showList(Model model, RedirectAttributes ra) {
        try {
            List<User> listUsers = userService.listAll();
            model.addAttribute("listUsers", listUsers);
        } catch (Exception ex) {
            model.addAttribute("message", ex.getCause());
        }
        return mainController.findPaginated(1, "users", "firstName", "asc", model);
    }

    @GetMapping("/users/{id}")
    public String getUserAnimals(@PathVariable("id") Integer id, RedirectAttributes ra, Model model) {
        try {
            List<Animal> animals = animalService.showAllByUserId(id);
            model.addAttribute("animals", animals);
            model.addAttribute("pageTitle",
                    "Pets of User (ID: " + id + ")");
            ra.addFlashAttribute("message", "The pets with User ID " + id +
                    "are here.");
            return "user_animals";
        } catch (UserNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/users";
        }
    }
}