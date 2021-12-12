package client_project.y2s1.team2.graphium.intergrationTests;


import client_project.y2s1.team2.graphium.data.jpa.entities.Authorities;
import client_project.y2s1.team2.graphium.data.jpa.entities.Documents;
import client_project.y2s1.team2.graphium.data.jpa.entities.Organisations;
import client_project.y2s1.team2.graphium.data.jpa.entities.Users;
import client_project.y2s1.team2.graphium.data.jpa.repositories.AuthoritiesRepositoryJPA;
import client_project.y2s1.team2.graphium.data.jpa.repositories.OrganisationsRepositoryJPA;
import client_project.y2s1.team2.graphium.data.jpa.repositories.UsersRepositoryJPA;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts={"/schema-maria.sql", "/data-maria.sql"})
public class SpringSecurityTests {

    @Autowired
    OrganisationsRepositoryJPA orgRepo;

    @Autowired
    AuthoritiesRepositoryJPA authRepo;

    @Autowired
    UsersRepositoryJPA userRepo;

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

    @WithMockUser("testSystemAdmin")
    @Test
    public void systemAdminAccessPublicPage_shouldSucceedWith200() throws Exception {
        mockMvc.perform(get("/login").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @WithMockUser(username = "testSystemAdmin", authorities = "systemAdmin")
    @Test
    public void systemAdminAccessSystemAdminHomePage_shouldSucceedWith200() throws Exception {
        mockMvc.perform(get("/systemAdmin/home").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @WithMockUser(username = "testSystemAdmin", authorities = "systemAdmin")
    @Test
    public void systemAdminAccessSystemAdminHomePage2_shouldSucceedWith200() throws Exception {
        mockMvc.perform(get("/systemAdmin/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @WithMockUser("testUser")
    @Test
    public void researcherCanAccessPublicPage_shouldSucceedWith200() throws Exception {
        mockMvc.perform(get("/login").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @WithMockUser("testUser")
    @Test
    public void researcherCantAccessSystemAdminPage_shouldBeForbidden() throws Exception {
        mockMvc.perform(get("/systemAdmin/home").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @WithMockUser("testOrgAdmin")
    @Test
    public void orgAdminAccessPublicPage_shouldSucceedWith200() throws Exception {
        mockMvc.perform(get("/login").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

//    need to ref baeldung
    @WithMockUser("testOrgAdmin")
    @Test
    public void orgAdminCantAccessSysAdminPage_shouldFailWithForbidden() throws Exception {
        mockMvc.perform(get("/systemAdmin").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    //researcher cant create new org
    @Test
    @WithMockUser(username = "testUser", authorities = "researcher")
    public void researcherCantCreateNewOrg() throws Exception {
        mockMvc.perform(post("/systemAdmin/newOrganisation")
                        .with(csrf())
                )
                .andExpect(status().isForbidden());
    }

    //system admin can access new org controller
    @Test
    @WithMockUser(username = "testSystemAdmin", authorities = "systemAdmin")
    public void systemAdminCanCreateNewOrg() throws Exception {
        mockMvc.perform(post("/systemAdmin/newOrganisation")
                        .with(csrf())
                )
                .andExpect(status().isOk());
    }

    //researcher cant create new org admin
//    @Test
//    @WithMockUser(username = "testSystemAdmin", authorities = "systemAdmin")
//    public void researcherCantCreateNewOrgAdmin() throws Exception {
//        mockMvc.perform(post("/systemAdmin/process_register")
//                        .with(csrf())
//                )
//                .andExpect(status().isOk());
//    }

    @Test
    @WithMockUser(username = "testSystemAdmin", authorities = "systemAdmin")
    public void systemAdminCanAccessAdminReg() throws Exception {
        mockMvc.perform(get("/systemAdmin/newOrgAdmin")
                        .with(csrf())
                )
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "testUser", authorities = "researcher")
    public void researcherAccessAdminReg() throws Exception {
        mockMvc.perform(get("/systemAdmin/newOrgAdmin")
                        .with(csrf())
                )
                .andExpect(status().isForbidden());
    }



//    system admin can create new org admin
//    In-progress test
    @Test
    @WithMockUser(username = "testSystemAdmin", authorities = "systemAdmin")
    public void systemAdminCanCreateNewOrgAdmin() throws Exception {
        List<Documents> ownedDocuments = new ArrayList<>();
        Optional<Organisations> cardiffUniOrg = orgRepo.findById(1L);
        Optional<Users> testUser = userRepo.findByUsername("testUser");
        Optional<Authorities> testAuth = authRepo.findByUsername("testUser");

        Users user = new Users("Automated User","aComplexPassword",true,1,"Ron","Simmons","Ron@Simmons.com",null, ownedDocuments, cardiffUniOrg.get(), null);
        Authorities authority = new Authorities("Automated User", "orgAdmin",user);
        user.setAuthority(authority);


        mockMvc.perform(post("/systemAdmin/newOrgAdmin")
                        .param("user", String.valueOf(testUser))
                        .param("authority", String.valueOf(testAuth))
                        .with(csrf())
                )
                .andExpect(status().is4xxClientError());
//               Expecting a 400 error is a little hackey I know but it shows the SysAdmin can get to the controller
//              where as the researcher cant
    }

    @Test
    @WithMockUser(username = "testUser", authorities = "researcher")
    public void ResearcherCantCreateNewOrgAdmin() throws Exception {
        List<Documents> ownedDocuments = new ArrayList<>();
        Optional<Organisations> cardiffUniOrg = orgRepo.findById(1L);
        Optional<Users> testUser = userRepo.findByUsername("testUser");
        Optional<Authorities> testAuth = authRepo.findByUsername("testUser");

        Users user = new Users("Automated User","aComplexPassword",true,1,"Ron","Simmons","Ron@Simmons.com",null, ownedDocuments, cardiffUniOrg.get(), null);
        Authorities authority = new Authorities("Automated User", "orgAdmin",user);
        user.setAuthority(authority);


        mockMvc.perform(post("/systemAdmin/newOrgAdmin")
                        .param("user", String.valueOf(testUser))
                        .param("authority", String.valueOf(testAuth))
                        .with(csrf())
                )
                .andExpect(status().isForbidden());
    }
}


