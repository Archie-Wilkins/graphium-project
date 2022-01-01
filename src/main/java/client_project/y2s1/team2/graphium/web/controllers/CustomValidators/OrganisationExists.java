package client_project.y2s1.team2.graphium.web.controllers.CustomValidators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Constraint(validatedBy = OrganisationExistsValidator.class)
@Target({FIELD})
@Retention(RUNTIME)
public @interface OrganisationExists {

    String message() default "Could not find organisation please try again";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};



}


