package client_project.y2s1.team2.graphium.domain;

import client_project.y2s1.team2.graphium.data.jpa.entities.Authorities;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class AuthorityDTO {

    @Getter
    private String username;

    @Getter
    private String authority;

    public Authorities toAuthorityEntity(){
        Authorities authorityEntity = new Authorities(
                this.username,this.authority, null
        );
        return authorityEntity;
    }


}
