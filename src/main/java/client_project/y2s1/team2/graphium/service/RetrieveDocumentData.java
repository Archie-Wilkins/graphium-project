package client_project.y2s1.team2.graphium.service;

import client_project.y2s1.team2.graphium.data.jpa.entities.DocumentAccessRights;
import client_project.y2s1.team2.graphium.data.jpa.entities.Documents;
import client_project.y2s1.team2.graphium.data.jpa.entities.Users;
import client_project.y2s1.team2.graphium.data.jpa.repositories.DocumentAccessRightsRepositoryJPA;
import client_project.y2s1.team2.graphium.data.jpa.repositories.DocumentsRepositoryJPA;
import client_project.y2s1.team2.graphium.data.jpa.repositories.UsersRepositoryJPA;
import client_project.y2s1.team2.graphium.domain.DocumentDTO;
import client_project.y2s1.team2.graphium.domain.ListOfDocumentsDTO;
import client_project.y2s1.team2.graphium.web.controllers.AccessRightsController;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.util.List;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RetrieveDocumentData {
    private DocumentsRepositoryJPA docRepository;
    private UsersRepositoryJPA userRepository;
    private DocumentAccessRightsRepositoryJPA accessRightsRepository;

    public RetrieveDocumentData(DocumentsRepositoryJPA aDocRepo, UsersRepositoryJPA aUserRepo, DocumentAccessRightsRepositoryJPA aAccessRightsRepository) {
        docRepository = aDocRepo;
        userRepository = aUserRepo;
        accessRightsRepository = aAccessRightsRepository;
    }

    public Optional<Documents> getDocumentByID(Long docID) {
        return docRepository.findById(docID);
    }

    public boolean userHasDocumentAccessRight(Documents document, Users user) {
        return (
                accessRightsRepository.findByDocumentAndOrganisation(document, user.getOrganisation()).isPresent()
                || accessRightsRepository.findByDocumentAndUser(document, user).isPresent()
        );
    }

    public boolean userIsAdminOfDocument(Documents document, Users user) {
        String authority = user.getAuthority().getAuthority();
        return (authority.equals("systemAdmin")) || (authority.equals("orgAdmin") && (document.getUser().getOrganisation().equals(user.getOrganisation())));
    }

    public boolean userCanViewDocument(Documents document, String userName) {
        boolean canView = false;
        Optional<Users> user = userRepository.findByUsername(userName);
        if (document.isOwner(user.get())) {
            canView = true;
        } else if (userHasDocumentAccessRight(document, user.get())) {
            canView = true;
        } else if (userIsAdminOfDocument(document, user.get())) {
            canView = true;
        }
        return canView;
    }

    public List<ListOfDocumentsDTO> getAllUsersUploadedDocuments(String userName) {
        List<ListOfDocumentsDTO> documentDTOList = new ArrayList<>();
        Optional<Users> user = userRepository.findByUsername(userName);
        if (user.isPresent()) {
            List<Documents> allDocuments = docRepository.findAllByUser(user.get());
            for (Documents document : allDocuments) {
                documentDTOList.add(new ListOfDocumentsDTO(
                        document.getId(),
                        document.getTitle(),
                        document.getUser().getUsername(),
                        document.getDate()
                ));
            }
        }
        return documentDTOList;
    }

    public List<ListOfDocumentsDTO> getAllDocumentsSharedToUserByUsersOrganisation(String userName) {
        List<ListOfDocumentsDTO> documentDTOList = new ArrayList<>();
        Optional<Users> user = userRepository.findByUsername(userName);
        if (user.isPresent()) {
            List<DocumentAccessRights> allDocumentAccessRights = accessRightsRepository.findAllByOrganisation_Name(user.get().getOrganisation().getName());
            for (DocumentAccessRights documentAccessRight : allDocumentAccessRights) {
                if (documentAccessRight.getDocument().getUser().getOrganisation().equals(user.get().getOrganisation())) {
                    documentDTOList.add(new ListOfDocumentsDTO(
                            documentAccessRight.getDocument().getId(),
                            documentAccessRight.getDocument().getTitle(),
                            documentAccessRight.getDocument().getUser().getUsername(),
                            documentAccessRight.getDocument().getDate()
                    ));
                }
            }
        }
        return documentDTOList;
    }

    public List<ListOfDocumentsDTO> getAllDocumentsSharedToUserByOtherOrganisation(String userName) {
        List<ListOfDocumentsDTO> documentDTOList = new ArrayList<>();
        Optional<Users> user = userRepository.findByUsername(userName);
        if (user.isPresent()) {
            List<DocumentAccessRights> allDocumentAccessRights = accessRightsRepository.findByOrganisation(user.get().getOrganisation());
            for (DocumentAccessRights documentAccessRight : allDocumentAccessRights) {
                if (!documentAccessRight.getDocument().getUser().getOrganisation().equals(user.get().getOrganisation())) {
                    documentDTOList.add(new ListOfDocumentsDTO(
                            documentAccessRight.getDocument().getId(),
                            documentAccessRight.getDocument().getTitle(),
                            documentAccessRight.getDocument().getUser().getUsername(),
                            documentAccessRight.getDocument().getDate()
                    ));
                }
            }
        }
        return documentDTOList;
    }

    public List<ListOfDocumentsDTO> getAllSharedToUserDocuments(String username) {
        List<ListOfDocumentsDTO> documentDTOList = new ArrayList<>();
        Optional<Users> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            List<DocumentAccessRights> allDocumentAccessRights = accessRightsRepository.findByUser(user.get());
            for (DocumentAccessRights documentAccessRight : allDocumentAccessRights) {
                documentDTOList.add(new ListOfDocumentsDTO(
                        documentAccessRight.getDocument().getId(),
                        documentAccessRight.getDocument().getTitle(),
                        documentAccessRight.getDocument().getUser().getUsername(),
                        documentAccessRight.getDocument().getDate()
                ));
            }
        }
        return documentDTOList;
    }

    public List<DocumentDTO> getAllDocsByAdminOrg(String userName){
        Optional<Users> userDetailsOptional =  userRepository.findByUsername(userName);
        Users userDetails = userDetailsOptional.get();
        String orgName = userDetails.getOrganisation().getName();
        List<Documents> allDocsByOrg = docRepository.findAllByUser_Organisation_Name(orgName);

        ArrayList<DocumentDTO> allDocsByOrgDTOs = new ArrayList<>();
        for (Documents docs : allDocsByOrg){
            allDocsByOrgDTOs.add(docs.toDTO());
        }

        return allDocsByOrgDTOs;
    }

}
