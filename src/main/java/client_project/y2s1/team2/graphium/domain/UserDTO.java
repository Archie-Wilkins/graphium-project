package client_project.y2s1.team2.graphium.domain;

import client_project.y2s1.team2.graphium.data.jpa.entities.Users;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDTO {

    private String username;

    private String password;

    private Boolean enabled;

    private Integer fk_organisation_id;

    private String first_name;

    private String last_name;

    private String email;

    private String authority_set_date;


    public Users toUserEntity(){
        Users userEntity = new Users(
                this.username, this.password, this.enabled, this.fk_organisation_id, this.first_name, this.last_name, this.email, this.authority_set_date, null, null, null);
        return userEntity;
    }
}
