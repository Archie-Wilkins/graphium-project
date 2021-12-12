package client_project.y2s1.team2.graphium.intergrationTests;

import client_project.y2s1.team2.graphium.data.jpa.entities.Access_Audit_Reports;
import client_project.y2s1.team2.graphium.service.AuditService;
import client_project.y2s1.team2.graphium.service.StoreFileDatabaseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts={"/schema-maria.sql", "/data-maria.sql"})
@DirtiesContext
public class AuditServiceTests {

    @Autowired
    AuditService auditService;

    @Autowired
    StoreFileDatabaseService storeFile;

    @Autowired
    private WebApplicationContext context;
    @Autowired
    private MockMvc mockMvc;

    //Audit service intergration tests

    //Document downloaded service intergration test - need to test controller
    @Test
    @WithMockUser("testUser")
    public void canSaveDocumentDownloadedLog_Success() throws Exception {
        mockMvc.perform(get("/document/download/1"));

        List<Access_Audit_Reports> allSuccesfulDownloads = auditService.getAllAuditLogsByActionID(7);
        int allSuccesfulDownloadsSize = allSuccesfulDownloads.size();

        assertEquals(allSuccesfulDownloadsSize, 2);
    }

    //Download failed
    @Test
    @WithMockUser("testUser")
    public void canSaveDocumentDownloadedLog_Failed() throws Exception {
        mockMvc.perform(get("/document/download/7"));

        List<Access_Audit_Reports> allFailedDownloads = auditService.getAllAuditLogsByActionID(14);
        int allFailedDownloadsSize = allFailedDownloads.size();

        assertEquals(allFailedDownloadsSize, 1);
    }

    //Document deleted service intergration test
    @Test
    @WithMockUser("testUser")
    public void canSaveDocumentDeletedAuditIntergration_Success() throws Exception {
        mockMvc.perform(get("/document/download/1"));

        List<Access_Audit_Reports> allSuccesfulDownloads = auditService.getAllAuditLogsByActionID(7);
        int allSuccesfulDownloadsSize = allSuccesfulDownloads.size();

        assertEquals(allSuccesfulDownloadsSize, 2);
    }

    //Document viewed service intergration test - need to test controller
    @Test
    @WithMockUser("testUser")
    public void canSaveDocumentViewedAuditIntergration_Success() throws Exception {
        mockMvc.perform(get("/document/view/1"));

        List<Access_Audit_Reports> allSuccesfulViews = auditService.getAllAuditLogsByActionID(9);
        int allSuccesfulViewsSize = allSuccesfulViews.size();

        assertEquals(allSuccesfulViewsSize, 1);
    }

    //View Failed
    @Test
    @WithMockUser("testUser")
    public void canSaveDocumentViewedAuditIntergration_Failed() throws Exception {
        mockMvc.perform(get("/document/view/7"));

        List<Access_Audit_Reports> allFailedViews = auditService.getAllAuditLogsByActionID(15);
        int allFailedViewsSize = allFailedViews.size();

        assertEquals(allFailedViewsSize, 1);
    }

    @Test
    public void canSaveUploadedFileAuditIntergration_Successful() throws Exception {
        auditService.documentUploaded("testUser", 1, "automated test data");

        List<Access_Audit_Reports> documentUploadedSaved = auditService.getAllAuditLogsByActionID(12);
        int documentUploadedSavedSize = documentUploadedSaved.size();

        assertEquals(documentUploadedSavedSize, 1);
    }


    //Document Uploaded service intergration test
    @Test
    public void canSaveUploadedFileAuditIntergration_Success() throws Exception {
        MockMultipartFile fileToUpload = new MockMultipartFile("data", "test.pdf", "pdf", "The Text".getBytes());

        storeFile.storeFile("data", "testUser", "pdf",fileToUpload);

        List<Access_Audit_Reports> documentUploadedSaved = auditService.getAllAuditLogsByActionID(12);
        int documentUploadedSavedSize = documentUploadedSaved.size();

        assertEquals(documentUploadedSavedSize, 1);
    }

    //Save log on failed upload attempt (invalid file type)
    @Test
    public void canSaveUploadedFileAuditIntergration_Failed() throws Exception {
        MockMultipartFile fileToUpload = new MockMultipartFile("data", "test.pdf", "invalidFileType", "The Text".getBytes());

        storeFile.storeFile("data", "testUser", "invalidFileType",fileToUpload);

        List<Access_Audit_Reports> documentUploadedSaved = auditService.getAllAuditLogsByActionID(13);
        int documentUploadedSavedSize = documentUploadedSaved.size();

        assertEquals(documentUploadedSavedSize, 1);
    }

    //Audit service tests
    @Test
    public void canSaveSuccessfulLogInAuditService() throws Exception {
        auditService.userLoggedIn("testUser", true, "automated test data");

        List<Access_Audit_Reports> allSuccesfulLogIns = auditService.getAllAuditLogsByActionID(1);
        int allSuccesfulLogInsSize = allSuccesfulLogIns.size();

        assertEquals(allSuccesfulLogInsSize, 2);
    }

    @Test
    public void canSaveSuccessfulDocumentUploadedAuditService() throws Exception {
        auditService.organisationDocumentsAccessed("testUser", true, "automated test data");

        List<Access_Audit_Reports> allSuccesfulDocumentsSaved = auditService.getAllAuditLogsByActionID(5);
        int allSuccesfulDocumentsSavedSize = allSuccesfulDocumentsSaved.size();

        assertEquals(allSuccesfulDocumentsSavedSize, 1);
    }

    @Test
    public void canSaveFailedDocumentSavedAuditService() throws Exception {
        auditService.organisationDocumentsAccessed("testUser", false, "automated test data");

        List<Access_Audit_Reports> allFailedDocumentsSaved = auditService.getAllAuditLogsByActionID(6);
        int allFailedDocumentsSavedSize = allFailedDocumentsSaved.size();

        assertEquals(allFailedDocumentsSavedSize, 2);
    }
}
