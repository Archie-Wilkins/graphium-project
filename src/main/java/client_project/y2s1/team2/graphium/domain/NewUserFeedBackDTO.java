package client_project.y2s1.team2.graphium.domain;

import client_project.y2s1.team2.graphium.web.controllers.FormObjects.NewUserForm;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class NewUserFeedBackDTO {

    @Getter
    String message;

    @Getter
    NewUserForm newUserForm;
}
