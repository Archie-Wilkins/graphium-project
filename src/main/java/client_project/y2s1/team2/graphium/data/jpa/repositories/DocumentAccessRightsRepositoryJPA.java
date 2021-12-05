package client_project.y2s1.team2.graphium.data.jpa.repositories;

import client_project.y2s1.team2.graphium.data.jpa.entities.DocumentAccessRights;
import client_project.y2s1.team2.graphium.data.jpa.entities.Documents;
import client_project.y2s1.team2.graphium.data.jpa.entities.Organisations;
import client_project.y2s1.team2.graphium.data.jpa.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface DocumentAccessRightsRepositoryJPA extends JpaRepository<DocumentAccessRights, Long> {
    List<DocumentAccessRights> findAll();
    Optional<DocumentAccessRights> findById(long Id);
    Optional<DocumentAccessRights> findByDocumentAndUser(Documents document, Users user);
    List<DocumentAccessRights> findByDocument(Documents document);
    List<DocumentAccessRights> findByOrganisation(Organisations organisation);
    List<DocumentAccessRights> findByUser(Users user);
    DocumentAccessRights save(DocumentAccessRights accessRights);
    // Causes issues when running tests
//    DocumentAccessRights deleteDocumentAccessRightsByDocumentAndUser(Documents document, Users user);
}
