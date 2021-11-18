package client_project.y2s1.team2.graphium.data.jpa;

import client_project.y2s1.team2.graphium.data.jpa.entities.Documents;
import client_project.y2s1.team2.graphium.data.jpa.repositories.DocumentsRepositoryJPA;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@Sql(scripts={"/schema-h2.sql", "/data-h2.sql"})
@DirtiesContext
public class DocumentsDataJPATests {
    @Autowired
    DocumentsRepositoryJPA docRepository;

//    @Test
//    public void shouldGet3Documents() throws Exception {
//        List<Documents> docs = docRepository.findAll();
//        System.out.println("Amt Docs ="+docs.size());
//        assertEquals(3, docs.size());
//    }
}
