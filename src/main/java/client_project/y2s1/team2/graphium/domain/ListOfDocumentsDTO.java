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
}
