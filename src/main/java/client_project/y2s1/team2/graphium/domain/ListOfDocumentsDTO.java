package client_project.y2s1.team2.graphium.domain;

import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@Value
public class ListOfDocumentsDTO {
    private Long id;
    private String title;
    private String ownerUsername;
    private String dateCreated;

    public ListOfDocumentsDTO(DocumentDTO docDTO) {
        this.id = docDTO.getId();
        this.title = docDTO.getTitle();
        this.ownerUsername = docDTO.getUser().getUsername();
        this.dateCreated = docDTO.getDate();
    }
}
