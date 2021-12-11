//package client_project.y2s1.team2.graphium.data.jpa.entities;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.*;
//
//@AllArgsConstructor
//@NoArgsConstructor
//@Entity
//public class Action_Audit_Reports {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Getter
//    private Long id;
//
//    @Getter
//    private String fk_username;
//
//    @Getter
//    private Integer fk_document_id;
//
//    @Getter
//    private Integer fk_action_id;
//
//    @Getter
//    private String action_date;
//
//    @ManyToOne
//    @JoinColumn(name = "fk_username")
//    private Users users;
//
//    @ManyToOne
//    @JoinColumn(name = "fk_document_id")
//    private Documents documents;
//
//
//}
