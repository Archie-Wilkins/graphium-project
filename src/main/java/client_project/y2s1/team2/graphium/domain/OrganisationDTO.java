package client_project.y2s1.team2.graphium.domain;

import client_project.y2s1.team2.graphium.data.jpa.entities.Organisations;
import client_project.y2s1.team2.graphium.web.controllers.FormObjects.OrganisationForm;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrganisationDTO {

    @Getter
    private Long id;

    @Getter
    private String name;

    @Getter
    private String email;

    public Organisations toOrganisationEntity(){
        Organisations organisationEntity = new Organisations(
                null, this.name, this.email );
        return organisationEntity;
    }

    public OrganisationForm toOrganisationForm(){
        OrganisationForm organisationForm = new OrganisationForm(
               this.name, this.email );
        return organisationForm;
    }
}
