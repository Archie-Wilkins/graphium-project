package client_project.y2s1.team2.graphium.intergrationTests;

import client_project.y2s1.team2.graphium.data.jpa.entities.AccessAuditReports;
import client_project.y2s1.team2.graphium.data.jpa.repositories.AccessAuditReportsRepositoryJPA;
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
public class ActionAuditReportsJPATests {

    @Autowired
    AccessAuditReportsRepositoryJPA reportRepo;

    @Test
    public void canRetrieveAll3AuditReports() throws Exception{
        List<AccessAuditReports> auditLogsList = reportRepo.findAll();
        int listLength = auditLogsList.size();

        assertEquals(3, listLength);
    }
}
