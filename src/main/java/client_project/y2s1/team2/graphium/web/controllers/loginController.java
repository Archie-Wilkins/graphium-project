package client_project.y2s1.team2.graphium.web.controllers;

import client_project.y2s1.team2.graphium.data.jpa.repositories.DocumentsRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class loginController {
        DocumentsRepositoryJPA docsRep;

        public loginController(DocumentsRepositoryJPA aDocsRepo) {
                this.docsRep = aDocsRepo;
        }

        @GetMapping({"/login", "login"})
        public String index(Model model) {
            return "login.html";
        }
}

