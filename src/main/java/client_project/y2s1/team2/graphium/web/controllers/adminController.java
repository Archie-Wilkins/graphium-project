package client_project.y2s1.team2.graphium.web.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

public class adminController {

    @GetMapping({"/admin", "admin"})
    public String index(Model model) {
        return "admin.html";
    }
}
