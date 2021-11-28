package client_project.y2s1.team2.graphium.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class myUploadsController {

    @GetMapping({"/myuploads", "myuploads"})
    public String myuploads(){
        return "myuploads.html";
    }
}
