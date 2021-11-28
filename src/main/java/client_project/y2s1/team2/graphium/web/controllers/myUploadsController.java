package client_project.y2s1.team2.graphium.web.controllers;



import client_project.y2s1.team2.graphium.data.jpa.entities.Documents;
import client_project.y2s1.team2.graphium.data.jpa.repositories.DocumentsRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;


@Controller
public class myUploadsController {



    @GetMapping({"/My_Uploads", "myUploads"})
    public String getUploads(Model model) {
        return "myUploads";
    }



}