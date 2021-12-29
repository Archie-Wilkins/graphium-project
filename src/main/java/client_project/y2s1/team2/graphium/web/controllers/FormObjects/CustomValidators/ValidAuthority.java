package client_project.y2s1.team2.graphium.web.controllers.FormObjects.CustomValidators;

import client_project.y2s1.team2.graphium.data.jpa.repositories.OrganisationsRepositoryJPA;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Constraint(validatedBy = ValidAuthorityValidator.class)
@Target({FIELD})
@Retention(RUNTIME)
public @interface ValidAuthority {

    String message() default "Invalid Authority level please try again";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};



}

