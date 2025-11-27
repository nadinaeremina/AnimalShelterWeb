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

    @GetMapping("/animals/new")
    public String showNewForm(Model model) {
        List<City> listCities = cityService.listAll();
        List<User> listUsers = userService.listAll();
        List<Type> listTypes = typeService.listAll();
        List<Integer> listBreeds = new ArrayList<Integer>();
        listBreeds.add(1);
        listBreeds.add(2);
        model.addAttribute("animalCreateData", new AnimalCreateData());
        model.addAttribute("listCities", listCities);
        model.addAttribute("listUsers", listUsers);
        model.addAttribute("listTypes", listTypes);
        model.addAttribute("listBreeds", listBreeds);
        model.addAttribute("pageTitle", "Adding a new pet:");
        return "animal_form";
    }

    @PostMapping("/animals/save")
    public String saveAnimal(AnimalCreateData animalCreateData, RedirectAttributes ra, Model model,
                             @RequestParam("photo") MultipartFile imageData) throws IOException {

        // преобразование полученных данных в формат БД
        String imageDataAsString= Base64
                .getEncoder()
                .encodeToString(
                        imageData.getBytes()
                );

        try {
            Animal animal = new Animal();

            if (animalCreateData.getId() != null) {
                animal = animalService.get(animalCreateData.getId());
            }

            // заполнили данные животного
            animal.setAge(animalCreateData.getAge());
            animal.setNickname(animalCreateData.getNickname());
            animal.setDescription(animalCreateData.getDescription());
            animal.setPhoto(imageDataAsString);

            // для пользователя заполнили только id и установим данные пользователя в заказе
            User animalUser = userService.get(animalCreateData.getUserId());
            animal.setUser(animalUser);

            City animalCity = cityService.get(Integer.parseInt(animalCreateData.getCityId()));
            animal.setCity(animalCity);

            Type animalType = typeService.get(Integer.parseInt(animalCreateData.getTypeId()));
            animal.setType(animalType);

            // сохранить в БД
            animalService.save(animal);
            ra.addFlashAttribute("message", "The pet was successfully added!");
            return "redirect:/animals";
        } catch (AnimalNotFoundException ex) {
            ra.addFlashAttribute("message", "The Pet with this ID not found!");
            return "redirect:/animals/new";
        } catch (DataAccessException e) {
            ra.addFlashAttribute("message", "A guardian with this ID was not found.!");
            return "redirect:/animals/new";
        } catch (UserNotFoundException e) {
            ra.addFlashAttribute("message", "A guardian with this ID was not found.!");
            return "redirect:/animals/new";
        }
    }

    @GetMapping("/animals/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model,
                               RedirectAttributes ra) {
        try {
            Animal animal = animalService.get(id);

            AnimalCreateData animalCreateData = new AnimalCreateData();
            animalCreateData.setDescription(animal.getDescription());
            animalCreateData.setId(animal.getId());
            animalCreateData.setAge(animal.getAge());
            animalCreateData.setNickname(animal.getNickname());
            animalCreateData.setUserId(animal.getUser().getId());
            animalCreateData.setCityId(Integer.toString(animal.getCity().getId()));
            animalCreateData.setTypeId(Integer.toString(animal.getType().getId()));

            model.addAttribute("animalCreateData", animalCreateData);
            model.addAttribute("pageTitle",
                    "Editing a pet with ID: " + id + ":");
            return "animal_form";
        } catch (AnimalNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/animals";
        }
    }

    @GetMapping("/animals/delete/{id}")
    public String deleteAnimal(@PathVariable("id") Integer id, RedirectAttributes ra) {
        try {
            animalService.delete(id);
            ra.addFlashAttribute("message", "The Pet with ID " + id +
                    "has been deleted.");
        } catch (AnimalNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/animals";
    }
}