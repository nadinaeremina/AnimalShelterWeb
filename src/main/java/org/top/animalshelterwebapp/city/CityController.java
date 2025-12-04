package org.top.animalshelterwebapp.city;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.top.animalshelterwebapp.MainController;

import java.util.List;

@Controller
public class CityController {
    @Autowired
    private final CityService cityService;
    private final MainController mainController;

    public CityController(CityService cityService, MainController mainController) {
        this.cityService = cityService;
        this.mainController = mainController;
    }

    @GetMapping("/cities")
    public String showList(Model model, RedirectAttributes ra) {
        try {
            List<City> listCities = cityService.listAll();
            model.addAttribute("listCities", listCities);
        } catch (Exception ex) {
            model.addAttribute("message", ex.getCause());
        }
        return mainController.findPaginated(1, "cities", "title", "asc", model);
    }
}
