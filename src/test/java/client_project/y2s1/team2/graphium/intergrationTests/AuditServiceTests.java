package client_project.y2s1.team2.graphium.intergrationTests;

import client_project.y2s1.team2.graphium.data.jpa.entities.Access_Audit_Reports;
import client_project.y2s1.team2.graphium.data.jpa.repositories.Access_Audit_ReportsRepositoryJPA;
import client_project.y2s1.team2.graphium.service.AuditService;
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
public class AuditServiceTests {

    @Autowired
    AuditService auditService;

    //Document downloaded service intergration test


    //Document deleted service intergration test


    //Document viewed service intergration test


    //Document Uploaded service intergration test

    //Audit service tests
    @Test
    public void canSaveSuccessfulLogIn() throws Exception {
        System.out.println("Hi");
        List<Access_Audit_Reports> allSuccesfulLogInsPre = auditService.getAllAuditLogsByActionID(1);

        auditService.userLoggedIn("testUser", true);

        List<Access_Audit_Reports> allSuccesfulLogIns = auditService.getAllAuditLogsByActionID(1);
        int allSuccesfulLogInsSize = allSuccesfulLogIns.size();

        assertEquals(allSuccesfulLogInsSize, 2);
    }
}
