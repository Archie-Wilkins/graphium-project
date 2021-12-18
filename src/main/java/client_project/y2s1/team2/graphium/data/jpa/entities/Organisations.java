package client_project.y2s1.team2.graphium.data.jpa.entities;

import client_project.y2s1.team2.graphium.domain.OrganisationDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Organisations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    public OrganisationDTO toOrganisationDTO(){
        OrganisationDTO organisationDTO = new OrganisationDTO(
                this.id, this.name, this.email );
        return organisationDTO;
    }
}
