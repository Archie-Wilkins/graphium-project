package client_project.y2s1.team2.graphium.data.jpa.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class DocumentAccessRights {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="fk_document_id")
    private Documents document;

    @ManyToOne
    @JoinColumn(name="fk_organisation_id")
    private Organisations organisation;
    public Optional<Organisations> getOrganisation() {
        return Optional.ofNullable(organisation);
    }

    @ManyToOne
    @JoinColumn(name="fk_user_id")
    private Users user;
    public Optional<Users> getUser() {
        return Optional.ofNullable(user);
    }

    public DocumentAccessRights(Documents aDocument, Organisations aOrganisation) {
        this(null, aDocument, aOrganisation, null);
    }
    public DocumentAccessRights(Documents aDocument, Users aUser) {
        this(null, aDocument, null, aUser);
    }
}
