package client_project.y2s1.team2.graphium.data.jpa.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Authorities {
    @Id
    private String username;
    private String authority;
}
