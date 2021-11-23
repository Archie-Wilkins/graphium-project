package client_project.y2s1.team2.graphium.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class uploadController {

    @GetMapping({"/upload", "upload"})
    public String upload(){
        return "upload.html";
    }
}
