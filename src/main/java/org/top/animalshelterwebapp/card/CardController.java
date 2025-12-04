package org.top.animalshelterwebapp.card;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.top.animalshelterwebapp.animal.Animal;
import org.top.animalshelterwebapp.animal.AnimalNotFoundException;
import org.top.animalshelterwebapp.animal.AnimalService;
import org.top.animalshelterwebapp.user.UserNotFoundException;

import java.util.List;
import java.util.Set;

@Controller
public class CardController {
    @Autowired
    private final CardService cardService;
    private final AnimalService animalService;

    public CardController(CardService cardService, AnimalService animalService) {
        this.cardService = cardService;
        this.animalService = animalService;
    }

    @GetMapping("/myCard/addAnimal/{id}")
    public String addAnimalToCard(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
        try {
            Card card = cardService.get(1);
            Animal currentAnimal = animalService.get(id);
            currentAnimal.setCard(card);
            animalService.save(currentAnimal);
            List<Animal> listAnimals = animalService.showAllByCardId(1);
            model.addAttribute("listAnimals", listAnimals);
            return "card";
        } catch (CardNotFoundException e) {
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
            Card card = cardService.get(1);
            Animal currentAnimal = animalService.get(id);
            currentAnimal.delCard();
            animalService.save(currentAnimal);
            List<Animal> listAnimals = animalService.showAllByCardId(1);
            model.addAttribute("listAnimals", listAnimals);
            return "card";
        } catch (CardNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/animals";
        } catch (AnimalNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/animals";
        }
    }

    @GetMapping("/myCard/show")
    public String showCard(Model model, RedirectAttributes ra) {
        try {
            List<Animal> listAnimals = animalService.showAllByCardId(1);
            model.addAttribute("listAnimals", listAnimals);
            return "card";
        } catch (AnimalNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/animals";
        }
    }
}
