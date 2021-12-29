package client_project.y2s1.team2.graphium.web.controllers.FormObjects.CustomValidators;

import client_project.y2s1.team2.graphium.data.jpa.repositories.OrganisationsRepositoryJPA;
import client_project.y2s1.team2.graphium.web.controllers.FormObjects.NewUserForm;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class OrganisationExistsValidator implements ConstraintValidator<OrganisationExists, Integer>{

    OrganisationsRepositoryJPA orgsRepo;

    public OrganisationExistsValidator(OrganisationsRepositoryJPA anOrgRepo) {
        this.orgsRepo = anOrgRepo;
    }

    @Override
    public boolean isValid(Integer orgID,
                           ConstraintValidatorContext constraintValidatorContext){
        long numberOfOrganisations = orgsRepo.count();
        return (1 <= orgID) && (orgID <= numberOfOrganisations);
    }

}
