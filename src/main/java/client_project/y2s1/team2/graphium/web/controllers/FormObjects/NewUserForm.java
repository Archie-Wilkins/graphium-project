package client_project.y2s1.team2.graphium.web.controllers.FormObjects;

import client_project.y2s1.team2.graphium.domain.AuthorityDTO;
import client_project.y2s1.team2.graphium.domain.UserDTO;
import client_project.y2s1.team2.graphium.web.controllers.CustomValidators.OrganisationExists;
import client_project.y2s1.team2.graphium.web.controllers.CustomValidators.ValidAuthority;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewUserForm {

    @NotNull(message = "Username is required")
    @NotBlank(message = "Username is required")
    @Size(min = 4, max = 20,
    message = "Username must be between 4 and 20 characters long")
    private String username;

    @NotNull(message = "Password is required")
    @NotBlank(message = "Password is required")
    @Size(max = 20,
    message = "Password must be between 6 and 20 characters long - We recommend using 3 words, for example 'BottleDoorPanda'")
    private String password;

    private Boolean enabled = true;

    @NotNull(message = "Organisation is required")
    @OrganisationExists
    // Need to use string incase user manipulates HTML to enter letter character
    ///Validation checks that value can be parse into an int
    //DTO still takes fk_organistion_id int
    //This allows for the error to be handled without needing to redirect the user to the error page
    private String fk_organisation_id;

    @NotNull(message = "First name is required")
    @NotBlank(message = "First name is required")
    @Size(max = 20,
    message = "First name must be between 1 and 20 characters long")
    private String first_name;

    @NotNull(message = "Last name is required")
    @NotBlank(message = "Last name is required")
    @Size(max = 20,
    message = "Last name must be between 1 and 20 characters long")
    private String last_name;

    @NotNull(message = "Email is required")
    @NotBlank(message = "Email is required")
    @Email(message = "Email must contain '@' sign")
    private String email;

    private String authority_set_date;

    @NotNull(message = "Authority is required")
    @NotBlank(message = "Authority is required")
    @ValidAuthority
    private String authority;

    public UserDTO toUserDTO(){
        UserDTO userDTO = new UserDTO(
                this.username, this.password, true, Integer.valueOf(this.fk_organisation_id), this.first_name, this.last_name,this.email, this.authority_set_date);
        return userDTO;
    }

    public AuthorityDTO toAuthorityDTO(){
        AuthorityDTO authorityDTO = new AuthorityDTO(
                this.username,this.authority
        );
        return authorityDTO;
    }

}
