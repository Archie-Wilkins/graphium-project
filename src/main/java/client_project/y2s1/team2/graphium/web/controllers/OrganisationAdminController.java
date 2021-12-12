package client_project.y2s1.team2.graphium.web.controllers;

import client_project.y2s1.team2.graphium.data.jpa.entities.Documents;
import client_project.y2s1.team2.graphium.data.jpa.entities.Users;
import client_project.y2s1.team2.graphium.data.jpa.repositories.DocumentsRepositoryJPA;
import client_project.y2s1.team2.graphium.data.jpa.repositories.UsersRepositoryJPA;
import client_project.y2s1.team2.graphium.domain.DocumentDTO;
import client_project.y2s1.team2.graphium.service.RetrieveDocumentData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/organisation")
public class OrganisationAdminController {
    private DocumentsRepositoryJPA docsRepo;
    private UsersRepositoryJPA userRepo;
    private RetrieveDocumentData retrieveDocumentData;

    public OrganisationAdminController(DocumentsRepositoryJPA aDocsRepo, UsersRepositoryJPA aUserRepo, RetrieveDocumentData aRetrieveDocumentData) {
        this.docsRepo = aDocsRepo;
        this.userRepo = aUserRepo;
        this.retrieveDocumentData = aRetrieveDocumentData;
    }

    /*
        Lists all documents owned by the organisations researchers
     */
    @GetMapping("/admin")
    public String index(Model model, Principal principal) {

        String userName = principal.getName();
        List<DocumentDTO> allDocsByOrg = retrieveDocumentData.getAllDocsByAdminOrg(userName);

        model.addAttribute("userName",userName);
        model.addAttribute("orgDocuments",allDocsByOrg);

        return "orgAdminHome.html";
    }
}
