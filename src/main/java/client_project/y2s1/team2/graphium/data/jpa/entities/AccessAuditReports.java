package client_project.y2s1.team2.graphium.data.jpa.entities;

import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AccessAuditReports {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Getter
    @Column(name = "fk_username")
    private String fkUsername;

    @Getter
    @Column(name = "fk_document_id")
    private Integer DocumentId;

    @Getter
    @Column(name = "fk_organisation_id")
    private Integer orgId;

    @Getter
    @Column(name = "fk_action_id")
    private Integer fkActionId;

    @Getter
    @Column(name = "action_date")
    private String actionDate;

    @Getter
    @Column(name = "action_description")
    private String actionDescription;

//    @ToString.Exclude
//    @ManyToOne
//    @JoinColumn(name = "fk_username")
//    private Users users;
//
//    @ToString.Exclude
//    @ManyToOne
//    @JoinColumn(name = "fk_document_id")
//    private Documents documents;


}
