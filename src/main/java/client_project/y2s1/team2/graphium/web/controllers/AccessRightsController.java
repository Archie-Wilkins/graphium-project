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
    public ModelAndView returnDocumentSharePage(@PathVariable("documentID") Long documentID, Principal principal) {
        ModelAndView model = new ModelAndView();
        if (!canAddAccessRight(documentID, principal.getName())) {
            model.addObject("secondaryText", "You do not have the right permissions to share this document");
            model.setViewName("error/403.html");
        } else {
            Optional<Documents> sharingDocument = accessRightService.getDocument(documentID);
            if (sharingDocument.isEmpty()) {
                model.addObject("secondaryText", "Couldn't find your document, please go back and try again");
                model.setViewName("error.html");
            } else {
                List<Organisations> shareableOrganisations = accessRightService.getShareableOrganisations(sharingDocument.get());
                List<Organisations> sharedOrganisations = accessRightService.getSharedOrganisations(sharingDocument.get());
                List<Users> shareableUsers = accessRightService.getShareableUsers(sharingDocument.get());
                List<Users> sharedUsers = accessRightService.getSharedUsers(sharingDocument.get());

                model.addObject("document", sharingDocument.get());
                model.addObject("shareableOrganisations", shareableOrganisations);
                model.addObject("shareableUsers", shareableUsers);
                model.addObject("currentSharedOrganisations", sharedOrganisations);
                model.addObject("currentSharedUsers", sharedUsers);
                model.setViewName("accessRights.html");
            }
        }
        return model;
    }

    @PostMapping({"/shareNewOrganisation"})
    public ModelAndView shareDocumentToOrg(
            @RequestParam("documentID") Long documentID,
            @RequestParam("newOrganisationID") Long organisationID,
            Principal principal
    ) {
        ModelAndView model = new ModelAndView();
        if (!canAddAccessRight(documentID, principal.getName())) {
            model.addObject("secondaryText", "You do not have the right permissions to share this document");
            model.setViewName("error/403.html");
        } else {
            Optional<Documents> sharingDocument = accessRightService.getDocument(documentID);
            Optional<Organisations> newOrganisation = accessRightService.getOrganisation(organisationID);
            if (sharingDocument.isEmpty() || newOrganisation.isEmpty()) {
                model.addObject("secondaryText", "We failed to get your file, please go back and try again");
                model.setViewName("error.html");
            } else {
                ReturnError saveResult = accessRightService.addNewSharedOrganisation(sharingDocument.get(), newOrganisation.get());
                if (saveResult.errored()) {
                    model.setViewName("error/500.html");
                }
            }
            model.setViewName("redirect:/shareDocument/"+documentID);
        }
        return model;
    }
}
