package client_project.y2s1.team2.graphium.web.controllers;

import client_project.y2s1.team2.graphium.data.jpa.entities.Documents;
import client_project.y2s1.team2.graphium.data.jpa.repositories.DocumentsRepositoryJPA;
import client_project.y2s1.team2.graphium.domain.SubmissionError;
import client_project.y2s1.team2.graphium.service.StoreFileDatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@Controller
public class SystemAdminController {

    @Autowired
    private DocumentsRepositoryJPA docsRepo;

    @GetMapping({"/systemAdmin", "systemAdmin"})
    public String systemAdminHome(Model model, Principal principal) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String name = auth.getName();
            String userName = principal.getName();
            List<Documents> AllDocuments = docsRepo.findAll();
            model.addAttribute("userName",userName);
            model.addAttribute("allDocuments",AllDocuments);
            return "systemAdminHome.html";

    }

    @GetMapping({"/systemAdmin/organisation"})
    public String systemAdminOrganisation(
            Model model,
            Principal principal){
        return "systemAdminNewOrganisation.html";
    }

    @PostMapping({"/systemAdmin/organisation"})
    public String systemAdminOrganisation(
    @RequestParam("organisationNameInput") String receivedName,
    @RequestParam("emailInput") String recievedEmail,
    Model model,
    Principal principal){

        try{

        }
        return "systemAdminNewOrganisation.html";

    }



}
