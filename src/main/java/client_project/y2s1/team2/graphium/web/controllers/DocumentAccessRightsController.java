package client_project.y2s1.team2.graphium.web.controllers;

import client_project.y2s1.team2.graphium.data.jpa.entities.Documents;
import client_project.y2s1.team2.graphium.data.jpa.entities.Organisations;
import client_project.y2s1.team2.graphium.data.jpa.entities.Users;
import client_project.y2s1.team2.graphium.domain.ReturnError;
import client_project.y2s1.team2.graphium.service.DocumentAccessRightService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/document")
public class DocumentAccessRightsController {
    DocumentAccessRightService accessRightService;

    public DocumentAccessRightsController(DocumentAccessRightService anAccessRightService) {
        accessRightService = anAccessRightService;
    }

    private Boolean canAddAccessRight(Long documentID, String username) {
        return accessRightService.canUserShareDocument(documentID, username);
    }

    /*
        Showing page to share the users document
     */
    @GetMapping({"/share/{documentID}"})
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

    /*
        Share the document to a new organisation
     */
    @PostMapping({"/share/newOrganisation"})
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
                model.addObject("secondaryText", "We failed to get the organisation and file, please go back and try again");
                model.setViewName("error.html");
            } else {
                ReturnError saveResult = accessRightService.addNewSharedOrganisation(sharingDocument.get(), newOrganisation.get());
                if (saveResult.errored()) {
                    if (saveResult.getError().equals("already-exists")) {
                        model.addObject("secondaryText", saveResult);
                        model.setViewName("error.html");
                    } else {
                        model.setViewName("error/500.html");
                    }
                } else {
                    model.setViewName("redirect:/shareDocument/"+documentID);
                }
            }
        }
        return model;
    }

    /*
        Share the document to a new user
     */
    @PostMapping({"/share/newUser"})
    public ModelAndView shareDocumentToUser(
            @RequestParam("documentID") Long documentID,
            @RequestParam("newUsername") String newUserName,
            Principal principal
    ) {
        ModelAndView model = new ModelAndView();
        if (!canAddAccessRight(documentID, principal.getName())) {
            model.addObject("secondaryText", "You do not have the right permissions to share this document");
            model.setViewName("error/403.html");
        } else {
            Optional<Documents> sharingDocument = accessRightService.getDocument(documentID);
            Optional<Users> newUser = accessRightService.getUser(newUserName);
            if (sharingDocument.isEmpty() || newUser.isEmpty()) {
                model.addObject("secondaryText", "We failed to get the username file, please go back and try again");
                model.setViewName("error.html");
            } else {
                ReturnError saveResult = accessRightService.addNewSharedUser(sharingDocument.get(), newUser.get());
                if (saveResult.errored()) {
                    if (saveResult.getError().equals("already-exists")) {
                        model.addObject("secondaryText", saveResult);
                        model.setViewName("error.html");
                    } else {
                        model.setViewName("error/500.html");
                    }
                } else {
                    model.setViewName("redirect:/shareDocument/"+documentID);
                }
            }
        }
        return model;
    }
}
