package client_project.y2s1.team2.graphium.data.jpa.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Users {
    @Id
    private String username;

    private String password;

    private Boolean enabled;

    private Integer fk_organisation_id;

    private String first_name;

    private String last_name;

    private String email;

    private String authority_set_date;

//   Need to set foreign key to organisations
    @ToString.Exclude
    @OneToMany(mappedBy="user")
    private List<Documents> ownedDocuments = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "fk_organisation_id",insertable = false,updatable = false)
    private Organisations organisation;

}
