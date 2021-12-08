package client_project.y2s1.team2.graphium.web.controllers;

import client_project.y2s1.team2.graphium.data.jpa.entities.DocumentAccessRights;
import client_project.y2s1.team2.graphium.data.jpa.entities.Documents;
import client_project.y2s1.team2.graphium.data.jpa.entities.Organisations;
import client_project.y2s1.team2.graphium.data.jpa.entities.Users;
import client_project.y2s1.team2.graphium.domain.ReturnError;
import client_project.y2s1.team2.graphium.service.DocumentAccessRightService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class AccessRightsController {
    DocumentAccessRightService accessRightService;

    public AccessRightsController(DocumentAccessRightService anAccessRightService) {
        accessRightService = anAccessRightService;
    }

    private Boolean canAddAccessRight(Long documentID, String username) {
        return accessRightService.canUserShareDocument(documentID, username);
    }

    @GetMapping({"/shareDocument/{documentID}"})
    public String returnDocumentSharePage(@PathVariable("documentID") Long documentID, Model model, Principal principal) {
        if (!canAddAccessRight(documentID, principal.getName())) { return "forbidden-error.html"; }
        Optional<Documents> sharingDocument = accessRightService.getDocument(documentID);
        if (sharingDocument.isEmpty()) { return "error.html"; }

        List<Organisations> shareableOrganisations = accessRightService.getShareableOrganisations(sharingDocument.get());
        List<Organisations> sharedOrganisations = accessRightService.getSharedOrganisations(sharingDocument.get());
        List<Users> shareableUsers = accessRightService.getShareableUsers(sharingDocument.get());
        List<Users> sharedUsers = accessRightService.getSharedUsers(sharingDocument.get());

        model.addAttribute("document", sharingDocument.get());
        model.addAttribute("shareableOrganisations", shareableOrganisations);
        model.addAttribute("shareableUsers", shareableUsers);
        model.addAttribute("currentSharedOrganisations", sharedOrganisations);
        model.addAttribute("currentSharedUsers", sharedUsers);
        return "accessRights.html";
    }

    @PostMapping({"/shareNewOrganisation"})
    public ModelAndView shareDocumentToOrg(
            @RequestParam("documentID") Long documentID,
            @RequestParam("newOrganisationID") Long organisationID,
            Principal principal
    ) {
        if (!canAddAccessRight(documentID, principal.getName())) { new ModelAndView("error.html"); }
        Optional<Documents> sharingDocument = accessRightService.getDocument(documentID);
        Optional<Organisations> newOrganisation = accessRightService.getOrganisation(organisationID);
        if (sharingDocument.isEmpty() || newOrganisation.isEmpty()) { new ModelAndView("error.html"); }

        ReturnError saveResult = accessRightService.addNewSharedOrganisation(sharingDocument.get(), newOrganisation.get());
        if (saveResult.errored()) {
            return new ModelAndView("error.html");
        }
        return new ModelAndView("redirect:/shareDocument/"+documentID);
    }
}
