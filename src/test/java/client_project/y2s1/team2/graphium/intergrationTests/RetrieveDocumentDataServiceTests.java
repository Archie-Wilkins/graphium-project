package client_project.y2s1.team2.graphium.intergrationTests;

import client_project.y2s1.team2.graphium.data.jpa.entities.Documents;
import client_project.y2s1.team2.graphium.data.jpa.repositories.DocumentsRepositoryJPA;
import client_project.y2s1.team2.graphium.domain.DocumentDTO;
import client_project.y2s1.team2.graphium.service.RetrieveDocumentData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts={"/schema-maria.sql", "/data-maria.sql"})
@DirtiesContext
public class RetrieveDocumentDataServiceTests {

    @Autowired
    RetrieveDocumentData retrieveService;

    @Test
    public void getsJust3OrgDocumentsFromCardiffUni() throws Exception {
        List<DocumentDTO> CardiffUniDocs = retrieveService.getAllDocsByAdminOrg("testOrgAdmin");
        int listSize = CardiffUniDocs.size();
        assertEquals(3, listSize);
    }

    @Test
    public void getsJust4OrgDocumentsFromSwanseaUni() throws Exception {
        List<DocumentDTO> SwanseaUniDocs = retrieveService.getAllDocsByAdminOrg("testOrgAdmin2");
        int listSize = SwanseaUniDocs.size();
        assertEquals(4, listSize);
    }

    @Test
    public void canViewDocumentWithOnlyOwner() throws Exception {
        Optional<Documents> document = retrieveService.getDocumentByID(1L);
        assertEquals(true, retrieveService.userCanViewDocument(document.get(), "testUser"));
    }

    @Test
    public void cannotViewDocumentWithOnlyNotOwner() throws Exception {
        Optional<Documents> document = retrieveService.getDocumentByID(1L);
        assertEquals(false, retrieveService.userCanViewDocument(document.get(), "testUser2"));
    }

    @Test
    public void canViewDocumentWithOnlyOrgAccessRight() throws Exception {
        Optional<Documents> document = retrieveService.getDocumentByID(1L);
        assertEquals(true, retrieveService.userCanViewDocument(document.get(), "testUser3"));
    }

    @Test
    public void cannotViewDocumentWithOnlyNotOrgAccessRight() throws Exception {
        Optional<Documents> document = retrieveService.getDocumentByID(1L);
        assertEquals(false, retrieveService.userCanViewDocument(document.get(), "testUser4"));
    }

    @Test
    public void canViewDocumentWithOnlyUserAccessRight() throws Exception {
        Optional<Documents> document = retrieveService.getDocumentByID(2L);
        assertEquals(true, retrieveService.userCanViewDocument(document.get(), "testUser2"));
    }

    @Test
    public void cannotViewDocumentWithOnlyNotUserAccessRight() throws Exception {
        Optional<Documents> document = retrieveService.getDocumentByID(2L);
        assertEquals(false, retrieveService.userCanViewDocument(document.get(), "testUser3"));
    }

    @Test
    public void canViewDocumentWithOnlyOrgAdmin() throws Exception {
        Optional<Documents> document = retrieveService.getDocumentByID(1L);
        assertEquals(true, retrieveService.userCanViewDocument(document.get(), "testOrgAdmin"));
    }

    @Test
    public void canViewDocumentWithOnlySysAdmin() throws Exception {
        Optional<Documents> document = retrieveService.getDocumentByID(1L);
        assertEquals(true, retrieveService.userCanViewDocument(document.get(), "testSystemAdmin"));
    }


}
