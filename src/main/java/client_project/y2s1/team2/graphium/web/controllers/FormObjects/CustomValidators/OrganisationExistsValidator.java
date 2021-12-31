package client_project.y2s1.team2.graphium.web.controllers.FormObjects.CustomValidators;

import client_project.y2s1.team2.graphium.data.jpa.repositories.OrganisationsRepositoryJPA;
import client_project.y2s1.team2.graphium.web.controllers.FormObjects.NewUserForm;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class OrganisationExistsValidator implements ConstraintValidator<OrganisationExists, String>{

    OrganisationsRepositoryJPA orgsRepo;

    public OrganisationExistsValidator(OrganisationsRepositoryJPA anOrgRepo) {
        this.orgsRepo = anOrgRepo;
    }

    @Override
    public boolean isValid(String orgID,
                           ConstraintValidatorContext constraintValidatorContext){
        try{
            int orgIdConverted = Integer.valueOf(orgID);
            long numberOfOrganisations = orgsRepo.count();
            return (1 <= orgIdConverted) && (orgIdConverted <= numberOfOrganisations);
        }catch (Exception e){
            return false;
        }
    }

}
