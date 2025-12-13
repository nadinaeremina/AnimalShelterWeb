package org.top.animalshelterwebapp.guardian;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.top.animalshelterwebapp.MainController;
import org.top.animalshelterwebapp.animal.Animal;
import org.top.animalshelterwebapp.animal.AnimalService;

import java.util.List;

@Controller
public class GuardianController {
    @Autowired
    private final GuardianService guardianService;
    private final AnimalService animalService;
    private final MainController mainController;

    public GuardianController(GuardianService guardianService, AnimalService animalService, MainController mainController) {
        this.guardianService = guardianService;
        this.animalService = animalService;
        this.mainController = mainController;
    }

    @GetMapping("/guardians")
    public String showList(Model model, RedirectAttributes ra) {
        try {
            List<Guardian> listGuardians = guardianService.listAll();
            model.addAttribute("listGuardians", listGuardians);
        } catch (Exception ex) {
            model.addAttribute("message", ex.getCause());
        }
        return mainController.findPaginated(1, "guardians", "firstName", "asc", model);
    }

    @GetMapping("/guardians/{id}")
    public String getGuardianAnimals(@PathVariable("id") Integer id, RedirectAttributes ra, Model model) {
        try {
            List<Animal> animals = animalService.showAllByGuardianId(id);
            model.addAttribute("animals", animals);
            model.addAttribute("pageTitle",
                    "Pets of User (ID: " + id + ")");
            ra.addFlashAttribute("message", "The pets with Guardian ID " + id +
                    "are here.");
            return "guardian_animals";
        } catch (GuardianNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/guardians";
        }
    }
}