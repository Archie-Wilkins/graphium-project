package client_project.y2s1.team2.graphium.data.jpa.entities;

import client_project.y2s1.team2.graphium.domain.DocumentDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Documents {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    @Column(name = "file_type")
    private String fileType;
    @Lob
    private byte[] fileData;

    @ManyToOne
    @JoinColumn(name="fk_creator")
    private Users user;

    public Documents(String aTitle, Users aUser, String aFileType, byte[] someFileData) {
        this(null, aTitle, aFileType, someFileData, aUser);
    }

    public DocumentDTO toDTO(){
        DocumentDTO documentDTO = new DocumentDTO(
                this.id, this.title, this.fileType, this.fileData,
                this.user);
        return documentDTO;
    }


}
