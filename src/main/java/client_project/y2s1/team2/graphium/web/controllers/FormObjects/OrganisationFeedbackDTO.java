package client_project.y2s1.team2.graphium.web.controllers.FormObjects;

import client_project.y2s1.team2.graphium.domain.OrganisationDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class OrganisationFeedbackDTO {

    @Getter
    private String userFeedback;

    @Getter
    private OrganisationDTO organisationDTO;


}
