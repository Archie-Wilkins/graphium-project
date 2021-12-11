package client_project.y2s1.team2.graphium.web.controllers;

import client_project.y2s1.team2.graphium.domain.ListOfDocumentsDTO;
import client_project.y2s1.team2.graphium.service.RetrieveDocumentData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

@Controller
public class HomeController {
    private RetrieveDocumentData documentData;

    public HomeController(RetrieveDocumentData aDocumentData) {
        documentData = aDocumentData;
    }

    @GetMapping({"/", "/document/myUploads"})
    public ModelAndView getMyUploads(Principal principal) {
        ModelAndView model = new ModelAndView();
        List<ListOfDocumentsDTO> documentList = documentData.getAllUsersUploadedDocuments(principal.getName());
        model.addObject("documentList", documentList);
        model.setViewName("index.html");
        return model;
    }

    @GetMapping("/document/myOrganisationDocuments")
    public ModelAndView getMyOrgUploads(Principal principal) {
        ModelAndView model = new ModelAndView();
        List<ListOfDocumentsDTO> documentList = documentData.getAllDocumentsSharedToUserByUsersOrganisation(principal.getName());
        model.addObject("documentList", documentList);
        model.setViewName("index.html");
        return model;
    }

    @GetMapping("/document/otherOrganisationDocuments")
    public ModelAndView getOtherOrgUploads(Principal principal) {
        ModelAndView model = new ModelAndView();
        List<ListOfDocumentsDTO> documentList = documentData.getAllDocumentsSharedToUserByOtherOrganisation(principal.getName());
        model.addObject("documentList", documentList);
        model.setViewName("index.html");
        return model;
    }

    @GetMapping("/document/otherUsersDocuments")
    public ModelAndView getOtherUserUploads(Principal principal) {
        ModelAndView model = new ModelAndView();
        List<ListOfDocumentsDTO> documentList = documentData.getAllSharedToUserDocuments(principal.getName());
        model.addObject("documentList", documentList);
        model.setViewName("index.html");
        return model;
    }
}
