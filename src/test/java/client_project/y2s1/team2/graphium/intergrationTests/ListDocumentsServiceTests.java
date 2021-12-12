package client_project.y2s1.team2.graphium.intergrationTests;

import client_project.y2s1.team2.graphium.domain.ListOfDocumentsDTO;
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
public class ListDocumentsServiceTests {
    @Autowired
    RetrieveDocumentData documentData;

    @Test
    public void canListUsersUploads() throws Exception {
        List<ListOfDocumentsDTO> documentList = documentData.getAllUsersUploadedDocuments("testUser");
        assertEquals(3, documentList.size());
    }

    @Test
    public void canListDocumentsSharedToUsersByUsersOrganisation() throws Exception {
        List<ListOfDocumentsDTO> documentsList = documentData.getAllDocumentsSharedToUserByUsersOrganisation("testUser");
        assertEquals(1, documentsList.size());
    }

    @Test
    public void canListDocumentsSharedToUser() throws Exception {
        List<ListOfDocumentsDTO> documentsList = documentData.getAllSharedToUserDocuments("testUser2");
        assertEquals(1, documentsList.size());
    }

    @Test
    public void canListDocumentsSharedToUserByOtherOrganisation() throws Exception {
        List<ListOfDocumentsDTO> documentsList = documentData.getAllDocumentsSharedToUserByOtherOrganisation("testUser");
        assertEquals(2, documentsList.size());
    }
}
