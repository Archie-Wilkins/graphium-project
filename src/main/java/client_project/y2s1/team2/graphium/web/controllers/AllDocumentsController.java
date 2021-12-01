package client_project.y2s1.team2.graphium.web.controllers;

import client_project.y2s1.team2.graphium.data.jpa.entities.Documents;
import client_project.y2s1.team2.graphium.data.jpa.repositories.DocumentsRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AllDocumentsController {

    @Autowired
    private DocumentsRepositoryJPA docsRepo;

    @GetMapping ({"/documents", "documents"})
    public String index(Model model){
        List<Documents> AllDocs = docsRepo.findAll();
        model.addAttribute("allDocs", AllDocs);
        return "allDocuments.html";
    }
}
