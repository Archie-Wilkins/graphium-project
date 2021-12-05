package client_project.y2s1.team2.graphium.service;

import client_project.y2s1.team2.graphium.data.jpa.entities.DocumentAccessRights;
import client_project.y2s1.team2.graphium.data.jpa.entities.Documents;
import client_project.y2s1.team2.graphium.data.jpa.entities.Organisations;
import client_project.y2s1.team2.graphium.data.jpa.entities.Users;
import client_project.y2s1.team2.graphium.data.jpa.repositories.DocumentAccessRightsRepositoryJPA;
import client_project.y2s1.team2.graphium.domain.ReturnError;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DocumentAccessRightService {
    private DocumentAccessRightsRepositoryJPA accessRightsRepository;

    public DocumentAccessRightService(DocumentAccessRightsRepositoryJPA aAccessRightRepo) {
        accessRightsRepository = aAccessRightRepo;
    }

    public List<Organisations> getSharedOrganisations(Documents document) {
        List<DocumentAccessRights> accessRights = accessRightsRepository.findByDocument(document);
        List<Organisations> organisations = new ArrayList<>();

        for (int i=0; i<accessRights.size(); i++) {
            if (accessRights.get(i).getOrganisation().isPresent()) {
                organisations.add(accessRights.get(i).getOrganisation().get());
            }
        }
        return organisations;
    }

    public List<Users> getSharedUsers(Documents document) {
        List<DocumentAccessRights> accessRights = accessRightsRepository.findByDocument(document);
        List<Users> users = new ArrayList<>();

        for (int i=0; i<accessRights.size(); i++) {
            if (accessRights.get(i).getUser().isPresent()) {
                users.add(accessRights.get(i).getUser().get());
            }
        }
        return users;
    }

    public ReturnError addNewSharedOrganisation(Documents document, Organisations organisation) {
        try {
            DocumentAccessRights newAccessRight = new DocumentAccessRights(document, organisation);
            accessRightsRepository.save(newAccessRight);
            return new ReturnError(false);
        } catch(Exception e) {
            return new ReturnError(true, "issue-saving", "Issue during submission, please try again.");
        }
    }

    public ReturnError addNewSharedUser(Documents document, Users user) {
        Optional<DocumentAccessRights> existingAccessRight = accessRightsRepository.findByDocumentAndUser(document, user);
        if (existingAccessRight.isPresent()) {
            return new ReturnError(true, "already-exists", "The document has already been shared with this user.");
        }
        try {
            DocumentAccessRights newAccessRight = new DocumentAccessRights(document, user);
            accessRightsRepository.save(newAccessRight);
            return new ReturnError(false);
        } catch(Exception e) {
            return new ReturnError(true, "issue-saving", "Issue during submission, please try again.");
        }
    }

    // Issue with JPA delete function
//    public ReturnError removeSharedUser(Documents document, Users user) {
//        Optional<DocumentAccessRights> existingAccessRight = accessRightsRepository.findByDocumentAndUser(document, user);
//        if (existingAccessRight.isEmpty()) {
//            return new ReturnError(true, "does-not-exists", "The document has not been shared with this user.");
//        }
//        try {
//            accessRightsRepository.deleteDocumentAccessRightsByDocumentAndUser(document, user);
//            return new ReturnError(false);
//        } catch(Exception e) {
//            return new ReturnError(true, "issue-removing", "There was a problem removing this user, please try again.");
//        }
//    }
}
