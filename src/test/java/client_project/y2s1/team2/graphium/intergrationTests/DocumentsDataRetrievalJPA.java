package client_project.y2s1.team2.graphium.intergrationTests;

import client_project.y2s1.team2.graphium.data.jpa.entities.Documents;
import client_project.y2s1.team2.graphium.data.jpa.entities.Organisations;
import client_project.y2s1.team2.graphium.data.jpa.repositories.DocumentsRepositoryJPA;
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
public class DocumentsDataRetrievalJPA {

    @Autowired
    DocumentsRepositoryJPA docRepository;

    @Test
    public void canRetrieveDocumentTitle() throws Exception{
        List<Documents> retrievedDocuments = docRepository.findAll();
        Documents firstDoc = retrievedDocuments.get(0);
        assertEquals("An Excellent File", firstDoc.getTitle());
    }

    @Test
    public void canRetrieveDocumentAuthor() throws Exception{
        List<Documents> retrievedDocuments = docRepository.findAll();
        Documents firstDoc = retrievedDocuments.get(0);
        assertEquals("testUser", firstDoc.getUser().getUsername());
    }

    @Test
    public void canRetrieveDocumentAuthorEmail() throws Exception{
        List<Documents> retrievedDocuments = docRepository.findAll();
        Documents firstDoc = retrievedDocuments.get(0);
        assertEquals("John@Cardiff.ac.uk", firstDoc.getUser().getEmail());
    }

    @Test
    public void canRetrieveDocumentAuthorOrganisation() throws Exception{
        List<Documents> retrievedDocuments = docRepository.findAll();
        Documents firstDoc = retrievedDocuments.get(0);
        String authorOrganisation = firstDoc.getUser().getOrganisation().getName();
        assertEquals("Cardiff University", authorOrganisation);
    }

    @Test
    public void canRetrieveAll7TestDocuments() throws Exception{
        List<Documents> retrievedDocuments = docRepository.findAll();
        int DocumentsListLength = retrievedDocuments.size();
        assertEquals(7, DocumentsListLength);
    }

}
