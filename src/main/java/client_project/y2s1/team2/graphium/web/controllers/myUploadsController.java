package client_project.y2s1.team2.graphium.web.controllers;

import client_project.y2s1.team2.graphium.data.jpa.entities.Documents;
import client_project.y2s1.team2.graphium.data.jpa.entities.Users;
import client_project.y2s1.team2.graphium.data.jpa.repositories.DocumentsRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;
import java.util.Optional;


@Controller
public class myUploadsController {

    @Autowired
    private DocumentsRepositoryJPA docsRepo;

    @GetMapping({"/myuploads", "myuploads"})
    public String myuploads(Model model, String keyword, Principal principal){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();

            String user = principal.getName();
            List<Documents> AllDocuments = docsRepo.findAll();
//            Optional<Documents> userDocs = docsRepo.findByUser();
            model.addAttribute("allDocuments", AllDocuments);
            model.addAttribute("userName", user);

            if(keyword !=null){
                model.addAttribute("allDocuments", docsRepo.findByTitle(keyword));
            }else{
                model.addAttribute("allDocuments", docsRepo.findAll());
            }
            return "myuploads.html";
    }
}
