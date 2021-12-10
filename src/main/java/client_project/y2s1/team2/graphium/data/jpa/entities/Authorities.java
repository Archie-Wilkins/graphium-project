package client_project.y2s1.team2.graphium.data.jpa.entities;

import lombok.Data;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Authorities {
    @Id
    private String username;
    @Getter
    private String authority;

    @OneToOne
    @JoinColumn(name="username")
    private Users user;
}
