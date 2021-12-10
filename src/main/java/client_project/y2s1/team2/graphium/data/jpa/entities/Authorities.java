package client_project.y2s1.team2.graphium.data.jpa.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Authorities {

    @Id
    private String fk_username;
    private String authority;

    public String getFk_Username() {
        return fk_username;
    }

    public String getAuthority() {
        return authority;
    }

    public void setFk_username(String fk_username) {
        this.fk_username = fk_username;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
