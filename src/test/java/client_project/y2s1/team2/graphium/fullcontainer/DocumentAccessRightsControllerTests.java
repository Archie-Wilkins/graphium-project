package client_project.y2s1.team2.graphium.fullcontainer;

import client_project.y2s1.team2.graphium.data.jpa.entities.Documents;
import client_project.y2s1.team2.graphium.data.jpa.repositories.DocumentsRepositoryJPA;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts={"/schema-maria.sql", "/data-maria.sql"})
@DirtiesContext
public class DocumentAccessRightsControllerTests {
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
        mockMvc.perform(get("/document/share/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("accessRights.html"));
    }

    @Test
    @WithMockUser("testUser")
    public void NonCreatorCannotAccessShareDocumentPage() throws Exception {
        mockMvc.perform(get("/document/share//4"))
                .andExpect(status().isOk())
                .andExpect(view().name("error/403.html"));
    }

    @Test
    @WithMockUser("testUser")
    public void creatorCanShareNewOrganisation() throws Exception {
        mockMvc.perform(post("/document/share/newOrganisation")
                        .param("documentID", "1")
                        .param("newOrganisationID", "2")
                        .with(csrf())
                )
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser("testUser")
    public void creatorCannotShareNewOrganisation() throws Exception {
        mockMvc.perform(post("/document/share/newOrganisation")
                        .param("documentID", "4")
                        .param("newOrganisationID", "2")
                        .with(csrf())
                )
                .andExpect(status().isOk())
                .andExpect(view().name("error/403.html"));
    }

    @Test
    @WithMockUser("testUser")
    public void creatorCanShareNewUser() throws Exception {
        mockMvc.perform(post("/document/share/newUser")
                        .param("documentID", "2")
                        .param("newUsername", "testUser3")
                        .with(csrf())
                )
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser("testUser")
    public void creatorCannotShareNewUser() throws Exception {
        mockMvc.perform(post("/document/share/newUser")
                        .param("documentID", "4")
                        .param("newUsername", "testUser2")
                        .with(csrf())
                )
                .andExpect(status().isOk())
                .andExpect(view().name("error/403.html"));
    }

    @Test
    @WithMockUser("testUser")
    public void creatorCannotShareDuplicateOrganisation() throws Exception {
        mockMvc.perform(post("/document/share/newOrganisation")
                        .param("documentID", "1")
                        .param("newOrganisationID", "1")
                        .with(csrf())
                )
                .andExpect(status().isOk())
                .andExpect(view().name("error.html"));
    }

    @Test
    @WithMockUser("testUser")
    public void creatorCannotShareDuplicateUser() throws Exception {
        mockMvc.perform(post("/document/share/newUser")
                        .param("documentID", "2")
                        .param("newUsername", "testUser2")
                        .with(csrf())
                )
                .andExpect(status().isOk())
                .andExpect(view().name("error.html"));
    }
}
