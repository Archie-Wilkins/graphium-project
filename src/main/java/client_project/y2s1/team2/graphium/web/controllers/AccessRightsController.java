package client_project.y2s1.team2.graphium.web.controllers;

import client_project.y2s1.team2.graphium.data.jpa.entities.DocumentAccessRights;
import client_project.y2s1.team2.graphium.data.jpa.entities.Documents;
import client_project.y2s1.team2.graphium.data.jpa.entities.Organisations;
import client_project.y2s1.team2.graphium.data.jpa.entities.Users;
import client_project.y2s1.team2.graphium.service.DocumentAccessRightService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class AccessRightsController {
    DocumentAccessRightService accessRightService;

    public AccessRightsController(DocumentAccessRightService anAccessRightService) {
        accessRightService = anAccessRightService;
    }

    @GetMapping({"/shareDocument/{documentID}"})
    public String returnDocumentSharePage(@PathVariable("documentID") Long documentID, Model model, Principal principal) {
        if (accessRightService.canUserShareDocument(documentID, principal.getName()) == false) { return "forbidden-error.html"; }
        Optional<Documents> sharingDocument = accessRightService.getDocument(documentID);
        if (sharingDocument.isEmpty()) { return "error.html"; }

        List<Organisations> shareableOrganisations = accessRightService.getShareableOrganisations(sharingDocument.get());
        List<Organisations> sharedOrganisations = accessRightService.getSharedOrganisations(sharingDocument.get());
        List<Users> shareableUsers = accessRightService.getShareableUsers(sharingDocument.get());
        List<Users> sharedUsers = accessRightService.getSharedUsers(sharingDocument.get());

        model.addAttribute("documentTitle", sharingDocument.get().getTitle());
        model.addAttribute("shareableOrganisations", shareableOrganisations);
        model.addAttribute("shareableUsers", shareableUsers);
        model.addAttribute("currentSharedOrganisations", sharedOrganisations);
        model.addAttribute("currentSharedUsers", sharedUsers);
        return "accessRights.html";
    }

    @PostMapping({"/shareNewOrganisation"})
    public String shareDocumentToOrg(
            @RequestParam("organisationID") String orgID
    ) {
        System.out.println(orgID);
        return "accessRights.html";
    }
}
