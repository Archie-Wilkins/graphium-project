package client_project.y2s1.team2.graphium.data.jpa.entities;

import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Access_Audit_Reports {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Getter
    @Column(name = "fk_username")
    private String fk_username;

    @Getter
    @Column(name = "fk_document_id")
    private Integer fk_document_id;

    @Getter
    @Column(name = "fk_action_id")
    private Integer fk_action_id;

    @Getter
    @Column(name = "action_date")
    private String action_date;

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
