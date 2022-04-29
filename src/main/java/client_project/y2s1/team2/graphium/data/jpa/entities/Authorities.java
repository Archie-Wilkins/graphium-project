package client_project.y2s1.team2.graphium.data.jpa.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
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


    public String getFk_Username() {
        return username;
    }

    public void setFk_username(String fk_username) {
        this.username = fk_username;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
