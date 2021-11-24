package client_project.y2s1.team2.graphium.data.jpa.entities;

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
    private String fileType;
    private String visibility;
    @Lob
    private byte[] fileData;

    @ManyToOne
    @JoinColumn(name="fk_username")
    private Users user;

    public Documents(String aTitle, Users aUser, String aFileType, String aVisibility, byte[] someFileData) {
        this(null, aTitle, aFileType, aVisibility, someFileData, aUser);
    }
}
