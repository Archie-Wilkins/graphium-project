package client_project.y2s1.team2.graphium.web.controllers.CustomValidators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class ValidAuthorityValidator implements ConstraintValidator<ValidAuthority, String>{

    @Override
    public boolean isValid(String authority,
                           ConstraintValidatorContext constraintValidatorContext) {
        String[] authorities = {"researcher", "orgAdmin", "systemAdmin"};
        return Arrays.stream(authorities).anyMatch(authority::equals);
    }


}
