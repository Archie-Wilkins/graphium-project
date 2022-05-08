package client_project.y2s1.team2.graphium.data.jpa.entities;

import client_project.y2s1.team2.graphium.domain.DocumentDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

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

    private String date;

    @Nullable
    @Column(name = "file_type")
    private String fileType;

    @Nullable
    @Lob
    private byte[] fileData;

    @ManyToOne
    @JoinColumn(name="fk_creator")
    private Users user;

    public Documents(String aTitle, String aDate, Users aUser, String aFileType, byte[] someFileData) {
        this(null, aTitle, aDate, aFileType, someFileData, aUser);
    }

    public boolean isOwner(Users possibleUser) {
        return this.user.getUsername().equals(possibleUser.getUsername());
    }

    public DocumentDTO toDTO(){
        DocumentDTO documentDTO = new DocumentDTO(
                this.id, this.title, this.date,  this.fileType, this.fileData,
                this.user);
        return documentDTO;
    }


}
