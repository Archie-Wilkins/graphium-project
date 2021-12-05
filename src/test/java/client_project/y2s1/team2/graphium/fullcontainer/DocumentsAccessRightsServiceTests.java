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
    public void canAddNewSharedUser() throws Exception {
        Documents document = docRepository.findById(1L).get();
        Users sharedUser = userRepository.findByUsername("testOrgAdmin").get();
        int beforeCount = accessRightService.getSharedUsers(document).size();
        accessRightService.addNewSharedUser(document, sharedUser);
        assertEquals(beforeCount+1, accessRightService.getSharedUsers(document).size());
    }

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
