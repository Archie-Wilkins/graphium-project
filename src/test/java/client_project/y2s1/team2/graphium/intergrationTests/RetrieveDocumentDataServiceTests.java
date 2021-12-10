package client_project.y2s1.team2.graphium.intergrationTests;

import client_project.y2s1.team2.graphium.domain.DocumentDTO;
import client_project.y2s1.team2.graphium.service.RetrieveDocumentData;
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
}
