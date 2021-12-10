package client_project.y2s1.team2.graphium.intergrationTests;

import client_project.y2s1.team2.graphium.data.jpa.entities.Access_Audit_Reports;
import client_project.y2s1.team2.graphium.data.jpa.repositories.Access_Audit_ReportsRepositoryJPA;
import client_project.y2s1.team2.graphium.domain.SubmissionError;
import client_project.y2s1.team2.graphium.service.AuditService;
import client_project.y2s1.team2.graphium.service.StoreFileDatabaseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts={"/schema-maria.sql", "/data-maria.sql"})
@DirtiesContext
public class AuditServiceTests {

    @Autowired
    AuditService auditService;

    @Autowired
    StoreFileDatabaseService storeFile;

    //Audit service intergration tests

    //Document downloaded service intergration test - need to test controller


    //Document deleted service intergration test
    @Test
    public void storeFileCanSubmitsAuditOnSuccesfulUpload() throws Exception {
        MockMultipartFile testFile = new MockMultipartFile("data", "testName.pdf", "application/pdf", "The Text".getBytes());
        storeFile.storeFile("Document Title","testUser", "pdf", testFile);

        List<Access_Audit_Reports> allSuccesfulUploads = auditService.getAllAuditLogsByActionID(12);
        int allSuccesfulUploadsSize = allSuccesfulUploads.size();

        assertEquals(allSuccesfulUploadsSize, 1);
    }

    //Document viewed service intergration test - need to test controller
//    @Test
//    public void storeFileCanSubmitsAuditOnSuccessfulView() throws Exception {
//        MockMultipartFile testFile = new MockMultipartFile("data", "testName.pdf", "application/pdf", "The Text".getBytes());
//        storeFile.storeFile("Document Title","testUser", "pdf", testFile);
//
//        List<Access_Audit_Reports> allSuccesfulUploads = auditService.getAllAuditLogsByActionID(12);
//        int allSuccesfulUploadsSize = allSuccesfulUploads.size();
//
//        assertEquals(allSuccesfulUploadsSize, 1);
//    }


    //Document Uploaded service intergration test
//    @Test
//    public void storeFileCanSubmitsAuditOnSuccesfulUpload() throws Exception {
//        MockMultipartFile testFile = new MockMultipartFile("data", "testName.pdf", "application/pdf", "The Text".getBytes());
//        storeFile.storeFile("Document Title","testUser", "pdf", testFile);
//
//        List<Access_Audit_Reports> allSuccesfulUploads = auditService.getAllAuditLogsByActionID(12);
//        int allSuccesfulUploadsSize = allSuccesfulUploads.size();
//
//        assertEquals(allSuccesfulUploadsSize, 1);
//    }

    //Save on failed upload attempt

    //Audit service tests
    @Test
    public void canSaveSuccessfulLogInAudit() throws Exception {
        auditService.userLoggedIn("testUser", true);

        List<Access_Audit_Reports> allSuccesfulLogIns = auditService.getAllAuditLogsByActionID(1);
        int allSuccesfulLogInsSize = allSuccesfulLogIns.size();

        assertEquals(allSuccesfulLogInsSize, 2);
    }

    @Test
    public void canSaveSuccessfulDocumentSavedAudit() throws Exception {
        auditService.organisationDocumentsAccessed("testUser", true);

        List<Access_Audit_Reports> allSuccesfulDocumentsSaved = auditService.getAllAuditLogsByActionID(5);
        int allSuccesfulDocumentsSavedSize = allSuccesfulDocumentsSaved.size();

        assertEquals(allSuccesfulDocumentsSavedSize, 1);
    }

    @Test
    public void canSaveFailedDocumentSavedAudit() throws Exception {
        auditService.organisationDocumentsAccessed("testUser", false);

        List<Access_Audit_Reports> allFailedDocumentsSaved = auditService.getAllAuditLogsByActionID(6);
        int allFailedDocumentsSavedSize = allFailedDocumentsSaved.size();

        assertEquals(allFailedDocumentsSavedSize, 2);
    }

    @Test
    public void canSaveUploadedFileAudit() throws Exception {
        auditService.documentUploaded("testUser", 1);

        List<Access_Audit_Reports> documentUploadedSaved = auditService.getAllAuditLogsByActionID(12);
        int documentUploadedSavedSize = documentUploadedSaved.size();

        assertEquals(documentUploadedSavedSize, 1);
    }


}
