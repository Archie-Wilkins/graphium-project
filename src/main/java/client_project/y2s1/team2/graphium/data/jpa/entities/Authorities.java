package client_project.y2s1.team2.graphium.data.jpa.entities;

import lombok.Data;
import lombok.Getter;

import javax.persistence.*;

@Entity
public class Authorities {
    @Id
    @Column(name = "fk_username")
    private String username;

    @Getter
    private String authority;

    @OneToOne
    @JoinColumn(name="fk_username")
    private Users user;
}
