package client_project.y2s1.team2.graphium.fullcontainer;

import client_project.y2s1.team2.graphium.data.jpa.repositories.DocumentsRepositoryJPA;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts={"/schema-maria.sql", "/data-maria.sql"})
@DirtiesContext
public class DocumentDeletingTests {
    @Autowired
    DocumentsRepositoryJPA docRepository;

//    @Test
//    public void whenDeleteAllDocumentsFromRepository_thenThereShouldBeNoDocumentsInRepository() {
//        docRepository.deleteAll();
//        assertThat(docRepository.count()).isEqualTo(0);
//    }
//
//    @Test
//    public void whenOneDocumentDeleted_thenThereShouldBeOneLess() {
//        long numberOfDocuments = docRepository.count();
//        docRepository.deleteById(numberOfDocuments);
//        assertThat(docRepository.count()).isEqualTo(numberOfDocuments-1);
//    }

}
