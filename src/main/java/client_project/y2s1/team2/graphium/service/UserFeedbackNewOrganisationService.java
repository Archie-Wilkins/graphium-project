package client_project.y2s1.team2.graphium.service;

import client_project.y2s1.team2.graphium.data.jpa.repositories.OrganisationsRepositoryJPA;
import client_project.y2s1.team2.graphium.domain.OrganisationDTO;
import client_project.y2s1.team2.graphium.domain.OrganisationFeedbackDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserFeedbackNewOrganisationService {

    @Autowired
    private OrganisationsRepositoryJPA orgRepo;

   public OrganisationFeedbackDTO newOrganisationSubmit(OrganisationDTO givenOrganisation){

       OrganisationDTO organisationDTO = new OrganisationDTO();
       String userFeedback = "";

       if(orgRepo.existsByName(givenOrganisation.toOrganisationEntity().getName()) != true) {

           try{
               orgRepo.save(givenOrganisation.toOrganisationEntity());
               userFeedback = givenOrganisation.getName() + " has been saved.";
               //OrganisationDTO is returned empty - set above
            } catch (Exception e) {
                organisationDTO = givenOrganisation;
                userFeedback = "Oh no something went wrong. Please try again.";
            }

       } else{
            userFeedback = givenOrganisation.getName() + " already exists.";
            organisationDTO = givenOrganisation;
        }


       OrganisationFeedbackDTO orgFeedback = new OrganisationFeedbackDTO(userFeedback, organisationDTO);
       return orgFeedback;

           }
       }

