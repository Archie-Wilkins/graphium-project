package client_project.y2s1.team2.graphium.fullcontainer;

import client_project.y2s1.team2.graphium.data.jpa.entities.Documents;
import client_project.y2s1.team2.graphium.data.jpa.entities.Organisations;
import client_project.y2s1.team2.graphium.data.jpa.entities.Users;
import client_project.y2s1.team2.graphium.data.jpa.repositories.DocumentsRepositoryJPA;
import client_project.y2s1.team2.graphium.data.jpa.repositories.OrganisationsRepositoryJPA;
import client_project.y2s1.team2.graphium.data.jpa.repositories.UsersRepositoryJPA;
import client_project.y2s1.team2.graphium.domain.ReturnError;
import client_project.y2s1.team2.graphium.service.DocumentAccessRightService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts={"/schema-maria.sql", "/data-maria.sql"})
@DirtiesContext
public class DocumentsAccessRightsServiceTests {
    @Autowired
    DocumentAccessRightService accessRightService;
    @Autowired
    DocumentsRepositoryJPA docRepository;
    @Autowired
    OrganisationsRepositoryJPA orgRepository;
    @Autowired
    UsersRepositoryJPA userRepository;

    @Test
    public void canGetDocumentsSharedOrganisations() throws Exception {
        Documents document = docRepository.findById(1L).get();
        List<Organisations> sharedOrganisations = accessRightService.getSharedOrganisations(document);
        assertEquals(1, sharedOrganisations.size());
    }

    @Test
    public void canGetDocumentsSharedUsers() throws Exception {
        Documents document = docRepository.findById(2L).get();
        List<Users> sharedUsers = accessRightService.getSharedUsers(document);
        assertEquals(1, sharedUsers.size());
    }

    @Test
    public void canAddNewSharedOrganisation() throws Exception {
        Documents document = docRepository.findById(1L).get();
        Organisations shareOrganisation = orgRepository.findById(2L).get();
        int beforeCount = accessRightService.getSharedOrganisations(document).size();
        accessRightService.addNewSharedOrganisation(document, shareOrganisation);
        assertEquals(beforeCount+1, accessRightService.getSharedOrganisations(document).size());
    }

    @Test
    public void cannotAddDuplicateSharedOrganisation() throws Exception {
        Documents document = docRepository.findById(1L).get();
        Organisations shareOrganisation = orgRepository.findById(1L).get();
        ReturnError returnError = accessRightService.addNewSharedOrganisation(document, shareOrganisation);
        assertEquals(true, returnError.errored());
        assertEquals("already-exists", returnError.getError());
    }

    @Test
    public void canAddNewSharedUser() throws Exception {
        Documents document = docRepository.findById(1L).get();
        Users sharedUser = userRepository.findByUsername("testOrgAdmin").get();
        int beforeCount = accessRightService.getSharedUsers(document).size();
        accessRightService.addNewSharedUser(document, sharedUser);
        assertEquals(beforeCount+1, accessRightService.getSharedUsers(document).size());
    }

    @Test
    public void cannotAddDuplicateSharedUser() throws Exception {
        Documents document = docRepository.findById(2L).get();
        Users sharedUser = userRepository.findByUsername("testUser2").get();
        ReturnError returnError = accessRightService.addNewSharedUser(document, sharedUser);
        assertEquals(true, returnError.errored());
        assertEquals("already-exists", returnError.getError());
    }

    @Test
    public void canCheckUserCanShareDocument() throws Exception {
        assertEquals(true, accessRightService.canUserShareDocument(1L, "testUser"));
        assertEquals(false, accessRightService.canUserShareDocument(1L, "testUser2"));
    }

    @Test
    public void canGetShareableOrganisations() throws Exception {
        Documents document = docRepository.getById(1L);
        List<Organisations> shareableOrganisations = accessRightService.getShareableOrganisations(document);
        System.out.println(shareableOrganisations);
        assertEquals(1, shareableOrganisations.size());
        assertEquals("Swansea University", shareableOrganisations.get(0).getName());
    }

//    @Test
//    public void canGetShareableUsers() throws Exception {
//        Documents document = docRepository.getById(2L);
//        List<Users> shareableUsers = accessRightService.getShareableUsers(document);
//        assertEquals(2, shareableUsers.size());
//        assertEquals("testOrgAdmin", shareableUsers.get(0).getUsername());
//        assertEquals("testSystemAdmin", shareableUsers.get(1).getUsername());
//    }

    // Issues with JPA delete function
//    @Test
//    public void canRemoveSharedUser() throws Exception {
//        Documents document = docRepository.findById(2L).get();
//        Users sharedUser = userRepository.findByUsername("testUser2").get();
//        int beforeCount = accessRightService.getSharedUsers(document).size();
//        ReturnError error = accessRightService.removeSharedUser(document, sharedUser);
//        assertEquals(beforeCount-1, accessRightService.getSharedUsers(document).size());
//    }
}
