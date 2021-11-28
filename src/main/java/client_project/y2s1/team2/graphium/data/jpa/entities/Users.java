package client_project.y2s1.team2.graphium.data.jpa.entities;

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
public class Users {
    @Id
    private String username;
    private String password;
    private Boolean enabled;
    @OneToMany(mappedBy="user")
    private List<Documents> ownedDocuments = new ArrayList<>();

}
