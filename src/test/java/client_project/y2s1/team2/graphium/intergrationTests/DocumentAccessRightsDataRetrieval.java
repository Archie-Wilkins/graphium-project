package client_project.y2s1.team2.graphium.intergrationTests;

import client_project.y2s1.team2.graphium.data.jpa.entities.DocumentAccessRights;
import client_project.y2s1.team2.graphium.data.jpa.entities.Documents;
import client_project.y2s1.team2.graphium.data.jpa.entities.Organisations;
import client_project.y2s1.team2.graphium.data.jpa.entities.Users;
import client_project.y2s1.team2.graphium.data.jpa.repositories.DocumentAccessRightsRepositoryJPA;
import client_project.y2s1.team2.graphium.data.jpa.repositories.DocumentsRepositoryJPA;
import client_project.y2s1.team2.graphium.data.jpa.repositories.OrganisationsRepositoryJPA;
import client_project.y2s1.team2.graphium.data.jpa.repositories.UsersRepositoryJPA;
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
public class DocumentAccessRightsDataRetrieval {
    @Autowired
    DocumentAccessRightsRepositoryJPA accessRightsRepository;
    @Autowired
    DocumentsRepositoryJPA docRepository;
    @Autowired
    OrganisationsRepositoryJPA orgRepository;
    @Autowired
    UsersRepositoryJPA userRepository;

    @Test
    public void canGet4Entries() throws Exception {
        List<DocumentAccessRights> accessRights = accessRightsRepository.findAll();
        assertEquals(4, accessRights.size());
    }

    @Test
    public void canGetId() throws Exception {
        List<DocumentAccessRights> accessRights = accessRightsRepository.findAll();
        long id = accessRights.get(0).getId();
        assertEquals(1, id);
    }

    @Test
    public void canRetrieveDocumentAndSomeDetails() throws Exception {
        Optional<DocumentAccessRights> firstEntry = accessRightsRepository.findById(1L);
        Documents document = firstEntry.get().getDocument();
        assertEquals(true, document != null);
        assertEquals("An Excellent File", document.getTitle());
        assertEquals("pdf", document.getFileType());
    }

    @Test
    public void canRetrieveOrganisationAndSomeDetailsWhenPresent() throws Exception {
        Optional<DocumentAccessRights> firstEntry = accessRightsRepository.findById(1L);
        Optional<Organisations> organisation = firstEntry.get().getOrganisation();
        assertEquals(true, organisation.isPresent());
        assertEquals("Cardiff University", organisation.get().getName());
    }

    @Test
    public void canRetrieveUserAndSomeDetailsWhenPresent() throws Exception {
        Optional<DocumentAccessRights> secondEntry = accessRightsRepository.findById(2L);
        Optional<Users> user = secondEntry.get().getUser();
        assertEquals(true, user.isPresent());
        assertEquals("testUser2", user.get().getUsername());
    }

    @Test
    public void canHandleNullOrganisation() throws Exception {
        Optional<DocumentAccessRights> secondEntry = accessRightsRepository.findById(2L);
        Optional<Organisations> organisation = secondEntry.get().getOrganisation();
        assertEquals(false, organisation.isPresent());
    }

    @Test
    public void canHandleNullUser() throws Exception {
        Optional<DocumentAccessRights> firstEntry = accessRightsRepository.findById(1L);
        Optional<Users> user = firstEntry.get().getUser();
        assertEquals(false, user.isPresent());
    }

    @Test
    public void canFindByDocument() throws Exception {
        Optional<Documents> document = docRepository.findById(1L);
        assertEquals(true, document.isPresent());
        if (document.isPresent()) {
            List<DocumentAccessRights> entry = accessRightsRepository.findByDocument(document.get());
            assertEquals(1, entry.size());
        }
    }

    @Test
    public void canFindByOrganisation() throws Exception {
        Optional<Organisations> organisation = orgRepository.findById(1L);
        assertEquals(true, organisation.isPresent());
        if (organisation.isPresent()) {
            List<DocumentAccessRights> entry = accessRightsRepository.findByOrganisation(organisation.get());
            assertEquals(3, entry.size());
        }
    }

    @Test
    public void canFindByUser() throws Exception {
        Optional<Users> user = userRepository.findByUsername("testUser2");
        assertEquals(true, user.isPresent());
        if (user.isPresent()) {
            List<DocumentAccessRights> entry = accessRightsRepository.findByUser(user.get());
            assertEquals(1, entry.size());
        }
    }

    @Test
    public void canSaveNewOrganisationEntry() throws Exception {
        Optional<Documents> document = docRepository.findById(2L);
        Optional<Organisations> organisation = orgRepository.findById(1L);
        int beforeSaveCount = accessRightsRepository.findAll().size();
        DocumentAccessRights newEntry = new DocumentAccessRights(document.get(), organisation.get());
        accessRightsRepository.save(newEntry);
        assertEquals(beforeSaveCount+1, accessRightsRepository.findAll().size());
    }

    @Test
    public void canSaveNewUserEntry() throws Exception {
        Documents document = docRepository.getById(2L);
        Optional<Users> user = userRepository.findByUsername("testUser2");
        int beforeSaveCount = accessRightsRepository.findAll().size();
        DocumentAccessRights newEntry = new DocumentAccessRights(document, user.get());
        accessRightsRepository.save(newEntry);
        assertEquals(beforeSaveCount+1, accessRightsRepository.findAll().size());
    }
}
