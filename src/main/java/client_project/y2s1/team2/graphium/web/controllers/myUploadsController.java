package client_project.y2s1.team2.graphium.web.controllers;

import client_project.y2s1.team2.graphium.data.jpa.entities.Documents;
import client_project.y2s1.team2.graphium.data.jpa.repositories.DocumentsRepositoryJPA;
import client_project.y2s1.team2.graphium.service.RetrieveDocumentData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class myUploadsController {

    @Autowired
    private DocumentsRepositoryJPA docsRepo;

    @GetMapping({"/myuploads", "myuploads"})
    public String myuploads(Model model, String keyword){
        List<Documents> AllDocuments = docsRepo.findAll();
        model.addAttribute("allDocuments", AllDocuments);

        if(keyword !=null){
            model.addAttribute("allDocuments", docsRepo.findByTitle(keyword));
        }else{
            model.addAttribute("allDocuments", docsRepo.findAll());
        }
        return "myuploads.html";
    }
}
