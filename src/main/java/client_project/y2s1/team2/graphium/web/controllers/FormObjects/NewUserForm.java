package client_project.y2s1.team2.graphium.web.controllers.FormObjects;

import client_project.y2s1.team2.graphium.data.jpa.repositories.AccessAuditReportsRepositoryJPA;
import client_project.y2s1.team2.graphium.domain.AuthorityDTO;
import client_project.y2s1.team2.graphium.domain.UserDTO;
import client_project.y2s1.team2.graphium.service.TimeService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewUserForm {

    @Size(min = 4, max = 20,
    message = "Username must be between 4 and 20 characters long")
    private String username;

    @Size(min = 6, max = 20,
    message = "Password must be between 6 and 20 characters long")
    private String password;

    private Boolean enabled = true;

    private Integer fk_organisation_id;

    @Size(min = 1, max = 20)
    private String first_name;

    @Size(min = 1, max = 20)
    private String last_name;

    @Pattern(regexp = "([A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{1,}$)",
    message = "Email must contain @ sign e.g example@example.com")
    private String email;

    private String authority_set_date;

    private String authority;

    public UserDTO toUserDTO(){
        UserDTO userDTO = new UserDTO(
                this.username, this.password, true, this.fk_organisation_id, this.first_name, this.last_name,this.email, this.authority_set_date);
        return userDTO;
    }

    public AuthorityDTO toAuthorityDTO(){
        AuthorityDTO authorityDTO = new AuthorityDTO(
                this.username,this.authority
        );
        return authorityDTO;
    }

}
