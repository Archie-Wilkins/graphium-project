package client_project.y2s1.team2.graphium.web.controllers;

import client_project.y2s1.team2.graphium.data.jpa.entities.Documents;
import client_project.y2s1.team2.graphium.data.jpa.entities.Organisations;
import client_project.y2s1.team2.graphium.data.jpa.repositories.DocumentsRepositoryJPA;
import client_project.y2s1.team2.graphium.data.jpa.repositories.OrganisationsRepositoryJPA;
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

    @Autowired
    private OrganisationsRepositoryJPA orgRepo;

    @GetMapping({"/systemAdmin", "systemAdmin"})
    public String systemAdminHome(Model model, Principal principal) {

            String userName = principal.getName();
            List<Documents> AllDocuments = docsRepo.findAll();
            model.addAttribute("userName",userName);
            model.addAttribute("allDocuments",AllDocuments);
            return "systemAdminHome.html";

    }

    @GetMapping({"/systemAdmin/organisation"})
    public String systemAdminOrganisation(
            Model model){
        Organisations organisation = new Organisations();
        model.addAttribute("organisation",organisation);
        model.addAttribute("userFeedback","");
        return "systemAdminNewOrganisation.html";
    }

    @PostMapping({"/systemAdmin/organisation"})
    public String systemAdminOrganisation(Organisations organisation, Model model){

//        Need to push into interface
        Organisations newOrganisation = new Organisations();

        String userFeedback = "";

        if(orgRepo.existsByName(organisation.getName()) != true) {

            try {
                orgRepo.save(organisation);
                userFeedback = organisation.getName() + " has been saved.";
                model.addAttribute("organisation", newOrganisation);

            } catch (Exception e) {
                model.addAttribute("organisation", organisation);

                userFeedback = "Oh no something went wrong. Please try again.";
            }
        } else{
            userFeedback = organisation.getName() + " already exists";
            model.addAttribute("organisation", organisation);

        }

        model.addAttribute("userFeedback",userFeedback);
        return "systemAdminNewOrganisation.html";
    }



}
