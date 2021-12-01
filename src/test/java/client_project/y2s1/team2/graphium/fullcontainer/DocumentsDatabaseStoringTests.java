package client_project.y2s1.team2.graphium.fullcontainer;

import client_project.y2s1.team2.graphium.data.jpa.entities.Documents;
import client_project.y2s1.team2.graphium.data.jpa.entities.Users;
import client_project.y2s1.team2.graphium.data.jpa.repositories.DocumentsRepositoryJPA;
import client_project.y2s1.team2.graphium.data.jpa.repositories.UsersRepositoryJPA;
import client_project.y2s1.team2.graphium.domain.SubmissionError;
import client_project.y2s1.team2.graphium.service.StoreFileDatabaseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.stylesheets.DocumentStyle;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts={"/schema-maria.sql", "/data-maria.sql"})
@DirtiesContext
public class DocumentsDatabaseStoringTests {
    @Autowired
    DocumentsRepositoryJPA docRepository;
    @Autowired
    UsersRepositoryJPA userRepository;
    @Autowired
    StoreFileDatabaseService storeFileService;

    @Test
    public void canStorePDFDocumentAndReadMetadata() throws Exception {
        // Creating file for storing
        MockMultipartFile fileToUpload = new MockMultipartFile("data", "testName.pdf", "application/pdf", "The Text".getBytes());
        // Attempting to store the document
        SubmissionError storeResult = storeFileService.storeFile("Document Title", "testUser", fileToUpload.getOriginalFilename().split("[.]")[1], fileToUpload);
        // Did the store have an issue and did it store correctly
        assertEquals(false, storeResult.errored());
        if (storeResult.succeed()) {
            List<Documents> storedDoc = docRepository.findAll();
            Documents lastDoc = storedDoc.get(storedDoc.size()-1);
            assertEquals("Document Title", lastDoc.getTitle());
            assertEquals("testUser", lastDoc.getUser().getUsername());
            assertEquals("pdf", lastDoc.getFileType());
        }
    }

    @Test
    public void canStoreWordDocumentAndReadMetadata() throws Exception {
        // Creating file for storing
        MockMultipartFile fileToUpload = new MockMultipartFile("data", "testName.docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document", "The Text".getBytes());
        // Attempting to store the document
        SubmissionError storeResult = storeFileService.storeFile("Document Title", "testUser", fileToUpload.getOriginalFilename().split("[.]")[1], fileToUpload);
        // Did the store have an issue and did it store correct
        assertEquals(false, storeResult.errored());
        if (storeResult.succeed()) {
            List<Documents> storedDoc = docRepository.findAll();
            Documents lastDoc = storedDoc.get(storedDoc.size()-1);
            assertEquals("Document Title", lastDoc.getTitle());
            assertEquals("testUser", lastDoc.getUser().getUsername());
            assertEquals("docx", lastDoc.getFileType());
        }
    }

    @Test
    public void canNotStoreDuplicateTitlesWithSameUser() throws Exception {
        // Creating file for storing
        MockMultipartFile fileToUpload = new MockMultipartFile("data", "testName.docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document", "The Text".getBytes());
        // Storing first document
        SubmissionError firstStoreResult = storeFileService.storeFile("Document Title","testUser", fileToUpload.getOriginalFilename().split("[.]")[1], fileToUpload);
        assertEquals(false, firstStoreResult.errored());
        // Storing second document
        SubmissionError secondStoreResult = storeFileService.storeFile("Document Title","testUser", fileToUpload.getOriginalFilename().split("[.]")[1], fileToUpload);
        assertEquals(true, secondStoreResult.errored());
        assertEquals("duplicate_title_and_user", secondStoreResult.getError());
    }

    @Test
    public void canStoreDuplicateTitlesWithDifferentUser() throws Exception {
        // Creating file for storing
        MockMultipartFile fileToUpload = new MockMultipartFile("data", "testName.docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document", "The Text".getBytes());
        // Storing first document
        SubmissionError firstStoreResult = storeFileService.storeFile("Document Title","testUser", fileToUpload.getOriginalFilename().split("[.]")[1], fileToUpload);
        assertEquals(false, firstStoreResult.errored());
        // Storing first document
        SubmissionError secondStoreResult = storeFileService.storeFile("Document Title","testUser2", fileToUpload.getOriginalFilename().split("[.]")[1], fileToUpload);
        assertEquals(false, secondStoreResult.errored());
    }

    @Test
    public void canNotStoreInvalidFileType() throws Exception {
        // Storing correct pdf file with Incorrect file type
        MockMultipartFile fileOne = new MockMultipartFile("data", "testName.pdf", "application/pdf", "The Text".getBytes());
        SubmissionError StoreResultOne = storeFileService.storeFile("Document Title","testUser", "jpg", fileOne);
        assertEquals(true, StoreResultOne.errored());
        assertEquals("file_type_invalid", StoreResultOne.getError());
        // Storing correct pdf file with Incorrect file type
        MockMultipartFile fileTwo = new MockMultipartFile("data", "testName.jpg", "application/pdf", "The Text".getBytes());
        SubmissionError StoreResultTwo = storeFileService.storeFile("Document Title","testUser", "pdf", fileTwo);
        assertEquals(true, StoreResultTwo.errored());
        assertEquals("file_extension_invalid", StoreResultTwo.getError());
    }

    @Test
    public void canNotStoreInvalidUser() throws Exception {
        // Creating file for storing
        MockMultipartFile fileToUpload = new MockMultipartFile("data", "testName.pdf", "application/pdf", "The Text".getBytes());
        // Storing first document
        SubmissionError StoreResult = storeFileService.storeFile("Document Title","invalidUsername", fileToUpload.getOriginalFilename().split("[.]")[1], fileToUpload);
        assertEquals(true, StoreResult.errored());
        assertEquals("invalid_username", StoreResult.getError());
    }
}
