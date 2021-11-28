package client_project.y2s1.team2.graphium.intergrationTests;


import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts={"/schema-maria.sql", "/data-maria.sql"})
public class SpringSecurityTests {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @WithMockUser("testSystemAdmin")
    @Test
    public void systemAdminAccessPublicPage_shouldSucceedWith200() throws Exception {
        mvc.perform(get("/login").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @WithMockUser("testSystemAdmin")
    @Test
    public void systemAdminAccessSystemAdminPage_shouldSucceedWith200() throws Exception {
        mvc.perform(get("/admin").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @WithMockUser("testUser")
    @Test
    public void researcherCanAccessPublicPage_shouldSucceedWith200() throws Exception {
        mvc.perform(get("/login").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @WithMockUser("testUser")
    @Test
    public void researcherCantAccessSystemAdminPage_shouldBeForbidden() throws Exception {
        mvc.perform(get("/admin").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @WithMockUser("testOrgAdmin")
    @Test
    public void orgAdminAccessPublicPage_shouldSucceedWith200() throws Exception {
        mvc.perform(get("/login").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @WithMockUser("testOrgAdmin")
    @Test
    public void orgAdminCantAccessSysAdminPage_shouldFailWithForbidden() throws Exception {
        mvc.perform(get("/admin").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }



}


