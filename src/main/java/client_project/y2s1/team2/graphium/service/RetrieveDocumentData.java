package client_project.y2s1.team2.graphium.service;

import client_project.y2s1.team2.graphium.data.jpa.entities.Documents;
import client_project.y2s1.team2.graphium.data.jpa.entities.Users;
import client_project.y2s1.team2.graphium.data.jpa.repositories.DocumentAccessRightsRepositoryJPA;
import client_project.y2s1.team2.graphium.data.jpa.repositories.DocumentsRepositoryJPA;
import client_project.y2s1.team2.graphium.data.jpa.repositories.UsersRepositoryJPA;
import client_project.y2s1.team2.graphium.domain.DocumentDTO;
import org.springframework.stereotype.Service;

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
