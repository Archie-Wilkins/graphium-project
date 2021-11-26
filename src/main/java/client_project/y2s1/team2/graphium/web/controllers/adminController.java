package client_project.y2s1.team2.graphium.web.controllers;

import client_project.y2s1.team2.graphium.data.jpa.entities.Documents;
import client_project.y2s1.team2.graphium.data.jpa.repositories.DocumentsRepositoryJPA;
import client_project.y2s1.team2.graphium.service.StoreFileDatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class adminController {

    @Autowired
    private DocumentsRepositoryJPA docsRepo;

    @GetMapping({"/admin", "admin"})
    public String index(Model model, Principal principal) {
        String userName = principal.getName();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        List<Documents> AllDocuments = docsRepo.findAll();
        System.out.println("Hi");
        return "admin.html";
    }
}
