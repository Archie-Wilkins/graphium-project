package client_project.y2s1.team2.graphium.web.controllers;

import client_project.y2s1.team2.graphium.data.jpa.entities.Documents;
import client_project.y2s1.team2.graphium.data.jpa.repositories.DocumentsRepositoryJPA;
import client_project.y2s1.team2.graphium.data.jpa.repositories.OrganisationsRepositoryJPA;
import client_project.y2s1.team2.graphium.service.UserFeedbackNewOrganisationService;
import client_project.y2s1.team2.graphium.domain.OrganisationDTO;
import client_project.y2s1.team2.graphium.web.controllers.FormObjects.OrganisationForm;
import client_project.y2s1.team2.graphium.domain.OrganisationFeedbackDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/systemAdmin")
public class SystemAdminController {

    @Autowired
    private DocumentsRepositoryJPA docsRepo;

    @Autowired
    private OrganisationsRepositoryJPA orgRepo;

    @Autowired
    private UserFeedbackNewOrganisationService feedbackService;

    @GetMapping({"/", "/home"})
    public String systemAdminHome(Model model, Principal principal) {

            String userName = principal.getName();
            List<Documents> AllDocuments = docsRepo.findAll();
            model.addAttribute("userName",userName);
            model.addAttribute("allDocuments",AllDocuments);
            return "systemAdminHome.html";

    }

    @GetMapping({"/newOrganisation"})
    public String systemAdminOrganisation(
            Model model){
        OrganisationForm organisationDTO = new OrganisationForm();
        model.addAttribute("organisationDTO",organisationDTO);
        model.addAttribute("userFeedback","");
        return "systemAdminNewOrganisation.html";
    }

    @PostMapping({"/newOrganisation"})
    public String systemAdminOrganisation(OrganisationForm organisationForm, Model model){

        OrganisationDTO organisationDTO = organisationForm.toOrganisationDTO();
        OrganisationFeedbackDTO feedback = feedbackService.newOrganisationSubmit(organisationDTO);
        String userFeedback = feedback.getUserFeedback();
        OrganisationForm returnedOrg = feedback.getOrganisationDTO().toOrganisationForm();

        model.addAttribute("userFeedback",userFeedback);
        model.addAttribute("organisationDTO", returnedOrg);
        return "systemAdminNewOrganisation.html";
    }



}
