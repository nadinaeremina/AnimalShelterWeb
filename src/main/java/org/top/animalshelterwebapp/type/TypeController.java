package org.top.animalshelterwebapp.type;

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
public class TypeController {
    @Autowired
    private final TypeService typeService;
    private final MainController mainController;

    public TypeController(TypeService typeService, MainController mainController) {
        this.typeService = typeService;
        this.mainController = mainController;
    }

    @GetMapping("/types")
    public String showList(Model model, RedirectAttributes ra) {
        try {
            List<Type> listTypes = typeService.listAll();
            model.addAttribute("listTypes", listTypes);
        } catch (Exception ex) {
            model.addAttribute("message", ex.getCause());
        }
        return mainController.findPaginated(1, "types", "title", "asc",model);
    }
}
