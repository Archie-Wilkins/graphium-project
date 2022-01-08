package client_project.y2s1.team2.graphium.web.controllers.CustomValidators;

import client_project.y2s1.team2.graphium.data.jpa.repositories.OrganisationsRepositoryJPA;

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
            long orgIdConverted = Integer.valueOf(orgID);
            boolean orgExists = orgsRepo.existsById(orgIdConverted);
            return orgExists;
        }catch (Exception e){
            return false;
        }
    }

}
