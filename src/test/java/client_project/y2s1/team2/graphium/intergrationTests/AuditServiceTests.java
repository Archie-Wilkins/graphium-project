package client_project.y2s1.team2.graphium.intergrationTests;

import client_project.y2s1.team2.graphium.data.jpa.repositories.Access_Audit_ReportsRepositoryJPA;
import client_project.y2s1.team2.graphium.service.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts={"/schema-maria.sql", "/data-maria.sql"})
@DirtiesContext
public class AuditServiceTests {

    @Autowired
    AuditService auditService;

// user logged in true
    public void userLoggedInSuccess() throws Exception {

    }

// user logged in false
public void userLoggedInFalse() throws Exception {

    }

    //All systems documents accessed true



    //All systems documents accessed false

    //Document downloaded


    //Document deleted


    //Document viewed


    //Document Uploaded

}
