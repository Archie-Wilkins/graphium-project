package client_project.y2s1.team2.graphium.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class loginController {

        @GetMapping({"/loginForm", "loginForm"})
        public String index(Model model) {
            String thing = "asd";
            model.addAttribute("test", thing);
            return "loginForm.html";
        }
}

