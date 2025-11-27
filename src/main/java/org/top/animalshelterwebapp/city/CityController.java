package org.top.animalshelterwebapp.city;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class CityController {
    @Autowired
    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping("/cities")
    public String showList(Model model) {
        List<City> listCities = cityService.listAll();
        model.addAttribute("listCities", listCities);
        return "cities";
    }

    @GetMapping("/cities/new")
    public String showNewForm(Model model) {
        model.addAttribute("city", new City());
        model.addAttribute("pageTitle", "Adding a new city");
        return "city_form";
    }

    @PostMapping("/cities/save")
    public String saveCity(City city, RedirectAttributes ra) {
        cityService.save(city);
        ra.addFlashAttribute("message", "The city has been saved successfully.");
        return "redirect:/cities";
    }

    @GetMapping("/cities/delete/{id}")
    public String deleteCity(@PathVariable("id") Integer id, RedirectAttributes ra) {
        try {
            cityService.delete(id);
            ra.addFlashAttribute("message", "The City ID " + id +
                    "has been deleted.");
        } catch (CityNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/cities";
    }
}
