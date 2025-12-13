package org.top.animalshelterwebapp.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.top.animalshelterwebapp.animal.Animal;
import org.top.animalshelterwebapp.animal.AnimalNotFoundException;

import java.util.List;

@Controller
public class UserController {
    @Autowired
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    public UserController(PasswordEncoder passwordEncoder, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @GetMapping("/user_form")
    public String userForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("pageTitle", "Регистрация нового пользователя:");
        return "user_form";
    }

    @GetMapping("/my_card")
    public String myCard(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("pageTitle", "Избранное");
        return "card";
    }

    // регистрация пользователя в БД
    @PostMapping("/register-user")
    public String registerUser(User user, RedirectAttributes ra) {
        if (!userService.isExistByLogin(user)) {
            User newUser = new User();
            newUser.setLogin(user.getLogin());
            newUser.setRole("user");
            String passwordHash = passwordEncoder.encode(user.getPasswordHash());
            newUser.setPasswordHash(passwordHash);
            userService.save(newUser);
            ra.addFlashAttribute("message", "The user has been saved successfully.");
            return "redirect:/index";
        }

        ra.addFlashAttribute("message", "User is already exists.");
        return "redirect:/user_form";
    }

//    @GetMapping("/myCard/addAnimal/{id}")
//    public String addAnimalToCard(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
//        try {
//            Card card = cardService.get(1);
//            Animal currentAnimal = animalService.get(id);
//            currentAnimal.setCard(card);
//            animalService.save(currentAnimal);
//            List<Animal> listAnimals = animalService.showAllByCardId(1);
//            model.addAttribute("listAnimals", listAnimals);
//            return "card";
//        } catch (CardNotFoundException e) {
//            ra.addFlashAttribute("message", e.getMessage());
//            return "redirect:/animals";
//        } catch (AnimalNotFoundException e) {
//            ra.addFlashAttribute("message", e.getMessage());
//            return "redirect:/animals";
//        }
//    }
//
//    @GetMapping("/myCard/delAnimal/{id}")
//    public String delAnimalFromCard(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
//        try {
//            Card card = cardService.get(1);
//            Animal currentAnimal = animalService.get(id);
//            currentAnimal.delCard();
//            animalService.save(currentAnimal);
//            List<Animal> listAnimals = animalService.showAllByCardId(1);
//            model.addAttribute("listAnimals", listAnimals);
//            return "card";
//        } catch (CardNotFoundException e) {
//            ra.addFlashAttribute("message", e.getMessage());
//            return "redirect:/animals";
//        } catch (AnimalNotFoundException e) {
//            ra.addFlashAttribute("message", e.getMessage());
//            return "redirect:/animals";
//        }
//    }
//
//    @GetMapping("/myCard/show")
//    public String showCard(Model model, RedirectAttributes ra) {
//        try {
//            List<Animal> listAnimals = animalService.showAllByCardId(1);
//            model.addAttribute("listAnimals", listAnimals);
//            return "card";
//        } catch (AnimalNotFoundException e) {
//            ra.addFlashAttribute("message", e.getMessage());
//            return "redirect:/animals";
//        }
//    }
}