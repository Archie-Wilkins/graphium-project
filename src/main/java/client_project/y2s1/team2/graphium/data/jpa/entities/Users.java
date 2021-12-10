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

    public String getUsername(){
        return username;
    }
    public String getPassword(){
        return password;
    }
    public String getEmail(){
        return email;
    }
    public Boolean getEnabled(){
        return enabled;
    }
    public Integer getOrgId(){
        return fk_organisation_id;
    }
    public String getFirstName(){
        return first_name;
    }
    public String getLastName(){
        return last_name;
    }
//    public String getAuthorityDate(){
//        return authority_set_date;
//    }

    public void setUsername(String username){
        this.username = username;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public void setEnabled(Boolean enabled){
        this.enabled = enabled;
    }
    public void setOrgId(Integer fk_organisation_id){
        this.fk_organisation_id = fk_organisation_id;
    }
    public void setFirstName(String first_name){
        this.first_name = first_name;
    }
    public void setLastName(String last_name){
        this.last_name = last_name;
    }
//    public void setAuthorityDate(){
//        this.authority_set_date = authority_set_date;
//    }

//   Need to set foreign key to organisations

    @ToString.Exclude
    @OneToMany(mappedBy="user", fetch = FetchType.EAGER)
    private List<Documents> ownedDocuments = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "fk_organisation_id",insertable = false,updatable = false)
    private Organisations organisation;


}
