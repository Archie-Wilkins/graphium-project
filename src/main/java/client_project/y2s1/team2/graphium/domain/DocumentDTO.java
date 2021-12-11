package client_project.y2s1.team2.graphium.domain;

import client_project.y2s1.team2.graphium.data.jpa.entities.Documents;
import client_project.y2s1.team2.graphium.data.jpa.entities.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
public class DocumentDTO {

    @Getter
    private Long id;

    @Getter
    private String title;

    @Getter
    private String date;

    @Getter
    private String fileType;

    @Getter
    private byte[] fileData;

    @Getter
    private Users user;

    public Documents toEntity(){
        Documents documentEntity = new Documents(
                this.id, this.title, this.date, this.fileType, this.fileData,
                this.user);
        return documentEntity;
    }
}
