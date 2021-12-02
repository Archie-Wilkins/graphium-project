package client_project.y2s1.team2.graphium.web.controllers.FormObjects;

import client_project.y2s1.team2.graphium.domain.OrganisationDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrganisationForm {

    @Getter
    private String name;

    @Getter
    private String email;

    public OrganisationDTO toOrganisationDTO(){
        OrganisationDTO organisationDTO = new OrganisationDTO(
                this.name, this.email );
        return organisationDTO;
    }
}
