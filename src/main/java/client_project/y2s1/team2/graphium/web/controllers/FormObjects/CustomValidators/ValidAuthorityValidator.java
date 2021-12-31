package client_project.y2s1.team2.graphium.web.controllers.FormObjects.CustomValidators;

import client_project.y2s1.team2.graphium.data.jpa.repositories.OrganisationsRepositoryJPA;
import client_project.y2s1.team2.graphium.web.controllers.FormObjects.NewUserForm;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ValidAuthorityValidator implements ConstraintValidator<ValidAuthority, String>{

    @Override
    public boolean isValid(String authority,
                           ConstraintValidatorContext constraintValidatorContext) {
        String[] authorities = {"researcher", "orgAdmin", "systemAdmin"};
        return Arrays.stream(authorities).anyMatch(authority::equals);
    }


}
