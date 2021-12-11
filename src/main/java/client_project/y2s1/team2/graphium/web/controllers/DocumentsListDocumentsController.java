package client_project.y2s1.team2.graphium.web.controllers;

import client_project.y2s1.team2.graphium.domain.ListOfDocumentsDTO;
import client_project.y2s1.team2.graphium.service.RetrieveDocumentData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/document")
public class DocumentsListDocumentsController {
    private RetrieveDocumentData documentData;

    public DocumentsListDocumentsController(RetrieveDocumentData aDocumentData) {
        documentData = aDocumentData;
    }

    /*
        Lists all the documents the user owns
    */
    @GetMapping("/myUploads")
    public ModelAndView getMyUploads(Principal principal) {
        ModelAndView model = new ModelAndView();
        List<ListOfDocumentsDTO> documentList = documentData.getAllUsersUploadedDocuments(principal.getName());
        model.addObject("documentList", documentList);
        model.setViewName("index.html");
        return model;
    }

    /*
        Lists all the documents that have been shared to the users organisation
     */
    @GetMapping("/myOrganisationDocuments")
    public ModelAndView getMyOrgUploads(Principal principal) {
        ModelAndView model = new ModelAndView();
        List<ListOfDocumentsDTO> documentList = documentData.getAllDocumentsSharedToUserByUsersOrganisation(principal.getName());
        model.addObject("documentList", documentList);
        model.setViewName("index.html");
        return model;
    }

    /*
        Lists all the documents that have been shared to the user by another organisation
     */
    @GetMapping("/otherOrganisationDocuments")
    public ModelAndView getOtherOrgUploads(Principal principal) {
        ModelAndView model = new ModelAndView();
        List<ListOfDocumentsDTO> documentList = documentData.getAllDocumentsSharedToUserByOtherOrganisation(principal.getName());
        model.addObject("documentList", documentList);
        model.setViewName("index.html");
        return model;
    }

    /*
        Lists all the documents that have been shared to the user by another user
     */
    @GetMapping("/otherUsersDocuments")
    public ModelAndView getOtherUserUploads(Principal principal) {
        ModelAndView model = new ModelAndView();
        List<ListOfDocumentsDTO> documentList = documentData.getAllSharedToUserDocuments(principal.getName());
        model.addObject("documentList", documentList);
        model.setViewName("index.html");
        return model;
    }
}
