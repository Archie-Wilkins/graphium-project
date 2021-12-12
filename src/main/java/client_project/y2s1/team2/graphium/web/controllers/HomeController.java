package client_project.y2s1.team2.graphium.web.controllers;

import client_project.y2s1.team2.graphium.domain.ListOfDocumentsDTO;
import client_project.y2s1.team2.graphium.service.RetrieveDocumentData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@Controller
public class HomeController {
    private DocumentsListDocumentsController docListDocController;

    public HomeController(DocumentsListDocumentsController aDocListDocController) {
        docListDocController = aDocListDocController;
    }
    /*
        Lists all the documents the user owns from the documents controller
     */
    @GetMapping("/")
    public ModelAndView getMyUploads(Model model, Principal principal) {
        String user = principal.getName();
        model.addAttribute("userName", user);
        model.addAttribute("date", LocalDate.now());
        return docListDocController.getMyUploads(model, principal);
    }
}
