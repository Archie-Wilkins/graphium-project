package client_project.y2s1.team2.graphium.fullcontainer;

import client_project.y2s1.team2.graphium.data.jpa.entities.Documents;
import client_project.y2s1.team2.graphium.data.jpa.repositories.DocumentsRepositoryJPA;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts={"/schema-maria.sql", "/data-maria.sql"})
@DirtiesContext
public class ControllerAuthorisedAccessTests {
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser("testUser")
    public void creatorCanAccessShareDocumentPage() throws Exception {
        // Checking if page status is ok and that it's the page view name
        mockMvc.perform(get("/shareDocument/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("accessRights.html"));
    }

    @Test
    @WithMockUser("testUser")
    public void NonCreatorCannotAccessShareDocumentPage() throws Exception {
        mockMvc.perform(get("/shareDocument/4"))
                .andExpect(status().isOk())
                .andExpect(view().name("error/403.html"));
    }

    @Test
    @WithMockUser("testUser")
    public void creatorCanShareNewOrganisation() throws Exception {
        mockMvc.perform(post("/shareNewOrganisation")
                        .param("documentID", "1")
                        .param("newOrganisationID", "2")
                )
                .andExpect(status().isOk())
                .andExpect(view().name("accessRights.html"));
    }

    @Test
    @WithMockUser("testUser")
    public void NonCreatorCannotShareNewOrganisation() throws Exception {
        mockMvc.perform(post("/shareNewOrganisation")
                        .param("documentID", "4")
                        .param("newOrganisationID", "1")
                )
                .andExpect(status().isOk())
                .andExpect(view().name("accessRights.html"));
    }
}
