package org.top.animalshelterwebapp.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.top.animalshelterwebapp.animal.Animal;
import org.top.animalshelterwebapp.animal.AnimalNotFoundException;
import org.top.animalshelterwebapp.animal.AnimalService;

import java.security.Principal;
import java.util.List;
import java.util.Set;

@Controller
public class UserController {
    @Autowired
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final AnimalService animalService;

    public UserController(PasswordEncoder passwordEncoder, UserService userService, AnimalService animalService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.animalService = animalService;
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("pageTitle", "Регистрация:");
        return "register_form";
    }

    @GetMapping("/auth")
    public String auth(Model model, RedirectAttributes ra) {
        model.addAttribute("user", new User());
        model.addAttribute("pageTitle", "Авторизация:");
        ra.addFlashAttribute("success", "Вы успешно авторизовались!");
        return "redirect:/delayed-redirect";
    }

    // регистрация пользователя в БД
    @PostMapping("/register-user")
    public String registerUser(User user, Model model, RedirectAttributes ra, Principal principal) {

        User newUser = new User();
        newUser.setLogin(user.getLogin());
        String passwordHash = passwordEncoder.encode(user.getPasswordHash());
        newUser.setPasswordHash(passwordHash);
        newUser.setRole("user");

        if (!userService.isExistByLogin(user)) {
            userService.save(newUser);
            ra.addFlashAttribute("success", "Вы успешно зарегистрированы!");
            model.addAttribute("showButton", false); // Скрыть кнопку
            return "redirect:/delayed-redirect";
        } else {
            model.addAttribute("message", "Такой логин уже существует! " +
                    "Войдите или придумайте новый логин.");
            return "register_form";
        }
    }

    @GetMapping("/delayed-redirect")
    public String showDelayedPage() {
        return "delayed-page"; // Имя вашего HTML-шаблона
    }

    // авторизация пользователя
    @PostMapping("/auth-user")
    public String authUser(User user, RedirectAttributes ra, Model model) {

        if (userService.isExistByLogin(user)) {
            if (userService.isRightPassword(user)) {
            ra.addFlashAttribute("message", "Вы успешно авторизованы!");

            model.addAttribute("user", new User());

            return "redirect:/index";
            }
            ra.addFlashAttribute("message", "Неверный пароль!");
            return "redirect:/auth_form";
        }
        ra.addFlashAttribute("message", "Такого логина не существует!");
        return "auth_form";
    }

    @GetMapping("/my_card")
    public String myCard(Model model, RedirectAttributes ra) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.get(username);
        model.addAttribute("pageTitle", "Избранное");
        try {
            Set<Animal> listAnimals = animalService.showAllByUserId(user.getId());
            Integer sizeAnimals = listAnimals.size();
            model.addAttribute("listAnimals", listAnimals);
            model.addAttribute("sizeAnimals", sizeAnimals);
            return "card";
        } catch (AnimalNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/animals";
        }
    }

    @GetMapping("/myCard/addAnimal/{id}")
    public String addAnimalToCard(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
        try {
            Animal currentAnimal = animalService.get(id);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentUsername = authentication.getName();
            User currentUser = userService.get(currentUsername);
            if ((currentUser == null) || !currentAnimal.isUser(currentUser)) {
                currentAnimal.setUsers(currentUser);
                animalService.save(currentAnimal);
            }
            Set<Animal> listAnimals = animalService.showAllByUserId(currentUser.getId());
            model.addAttribute("listAnimals", listAnimals);
            return "card";
        } catch (UserNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/animals";
        } catch (AnimalNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/animals";
        }
    }

    @GetMapping("/myCard/delAnimal/{id}")
    public String delAnimalFromCard(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
        try {
            Animal currentAnimal = animalService.get(id);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User currentUser = userService.get(username);
            currentAnimal.unSetUsers(currentUser);
            animalService.save(currentAnimal);
            Set<Animal> listAnimals = animalService.showAllByUserId(currentUser.getId());
            model.addAttribute("listAnimals", listAnimals);
            return "card";
        } catch (UserNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/animals";
        } catch (AnimalNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/animals";
        }
    }

    @GetMapping("/template")
    public String delAnimalFromCard() {
        return "template";
    }

    // регистрация пользователя в БД
//    @PostMapping("/register-db-user")
//    public User registerDbUser(
//            @RequestParam String login,
//            @RequestParam String password,
//            @RequestParam String role) {
//
//        User user = new User();
//        user.setLogin(login);
//        user.setRole(role);
//        String passwordHash = passwordEncoder.encode(password);
//        user.setPasswordHash(passwordHash);
//
//        userService.save(user);
//        return user;
//    }
}