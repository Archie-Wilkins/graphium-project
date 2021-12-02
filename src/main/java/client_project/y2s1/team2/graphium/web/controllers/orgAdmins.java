package client_project.y2s1.team2.graphium.web.controllers;

import client_project.y2s1.team2.graphium.data.jpa.entities.Documents;
import client_project.y2s1.team2.graphium.data.jpa.repositories.DocumentsRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class orgAdmins {

    @Autowired
    private DocumentsRepositoryJPA docsRepo;

    @GetMapping({"/orgAdmin", "orgAdmin"})
    public String index(Model model, Principal principal) {


        String userName = principal.getName();
        List<Documents> AllDocuments = docsRepo.findAll();
        model.addAttribute("userName",userName);
        model.addAttribute("allDocuments",AllDocuments);
        return "admin.html";

    }
}
