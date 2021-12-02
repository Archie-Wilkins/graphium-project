package client_project.y2s1.team2.graphium.intergrationTests;

import client_project.y2s1.team2.graphium.data.jpa.entities.Organisations;
import client_project.y2s1.team2.graphium.data.jpa.repositories.OrganisationsRepositoryJPA;
import client_project.y2s1.team2.graphium.domain.OrganisationDTO;
import client_project.y2s1.team2.graphium.service.UserFeedbackNewOrganisationService;
import client_project.y2s1.team2.graphium.web.controllers.FormObjects.OrganisationFeedbackDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext
@Sql(scripts={"/schema-maria.sql", "/data-maria.sql"})
public class UserFeedbackNewOrganisationServiceTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserFeedbackNewOrganisationService feedbackService;

    @Autowired
    private OrganisationsRepositoryJPA orgRepo;

    @Test
    public void UserFeedbackNewOrganisationServiceCanSaveOrganisations(){
        List<Organisations> allOrgs = orgRepo.findAll();
        int totalOrganisations = allOrgs.size();
        OrganisationDTO orgDTO = new OrganisationDTO("A Unique Test Org", "TestEmail@Test.co.uk");

        feedbackService.newOrganisationSubmit(orgDTO);

        List<Organisations> allOrgsPostInsert = orgRepo.findAll();

        int totalOrganisationsPostInsert = allOrgsPostInsert.size();
        int expectedNumberOfOrgs = totalOrganisations + 1;

        assertEquals(totalOrganisationsPostInsert, expectedNumberOfOrgs);
    }

    @Test
    public void UserFeedbackNewOrganisationServiceReturnsNullFormDTOOnSuccess(){
        OrganisationDTO orgDTO = new OrganisationDTO("Returns Null Form Values", "TestEmail@Test.co.uk");
        OrganisationFeedbackDTO feedback = feedbackService.newOrganisationSubmit(orgDTO);

        String orgName = feedback.getOrganisationDTO().getName();
        String orgEmail = feedback.getOrganisationDTO().getEmail();

        assertEquals(null, orgName);
        assertEquals(null, orgEmail);
    }

    @Test
    public void UserFeedbackNewOrganisationServiceReturnsPreviousDTOObjectOnFailure(){
        //Needs to be repeated to force duplication
        OrganisationDTO orgDTO = new OrganisationDTO("Returns Previous Form", "TestEmail@Test.co.uk");
        OrganisationFeedbackDTO feedback = feedbackService.newOrganisationSubmit(orgDTO);

        OrganisationDTO orgDTO2 = new OrganisationDTO("Returns Previous Form", "TestEmail@Test.co.uk");
        OrganisationFeedbackDTO feedback2 = feedbackService.newOrganisationSubmit(orgDTO);

        String orgName = feedback2.getOrganisationDTO().getName();
        String orgEmail = feedback2.getOrganisationDTO().getEmail();

        assertEquals("Returns Previous Form", orgName);
        assertEquals("TestEmail@Test.co.uk", orgEmail);
    }

    @Test
    public void UserFeedbackNewOrganisationServiceReturnsSuccessFeedback(){
        OrganisationDTO orgDTO = new OrganisationDTO("A Successful Org", "TestEmail@Test.co.uk");
        OrganisationFeedbackDTO feedback = feedbackService.newOrganisationSubmit(orgDTO);

        String submitFeedback = feedback.getUserFeedback();

        assertEquals("A Successful Org has been saved.", submitFeedback);
    }

    @Test
    public void UserFeedbackNewOrganisationServiceReturnsDuplicationErrorFeedback(){
        //Needs to be repeated to force duplication
        OrganisationDTO orgDTO = new OrganisationDTO("A Duplicate Org", "TestEmail@Test.co.uk");
        OrganisationFeedbackDTO feedback = feedbackService.newOrganisationSubmit(orgDTO);

        OrganisationDTO orgDTO2 = new OrganisationDTO("A Duplicate Org", "TestEmail@Test.co.uk");
        OrganisationFeedbackDTO feedback2 = feedbackService.newOrganisationSubmit(orgDTO);

        String submitFeedback = feedback2.getUserFeedback();

        assertEquals("A Duplicate Org already exists.", submitFeedback);
    }
}
