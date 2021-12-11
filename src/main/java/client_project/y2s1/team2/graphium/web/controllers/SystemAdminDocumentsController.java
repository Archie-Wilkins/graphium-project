package client_project.y2s1.team2.graphium.web.controllers;

import client_project.y2s1.team2.graphium.data.jpa.entities.Documents;
import client_project.y2s1.team2.graphium.data.jpa.repositories.DocumentsRepositoryJPA;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/systemAdmin")
public class SystemAdminDocumentsController {
    private DocumentsRepositoryJPA docsRepo;

    public SystemAdminDocumentsController(DocumentsRepositoryJPA aDocRepo) {
        this.docsRepo = aDocRepo;
    }

    /*
        List all documents on system by search term
     */
    @GetMapping({"/", "/home"})
    public String systemAdminHome(Model model, String keyword, Principal principal){
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
        return "systemAdminListDocuments.html";
    }
}
