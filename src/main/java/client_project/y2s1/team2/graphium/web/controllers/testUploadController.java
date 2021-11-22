package client_project.y2s1.team2.graphium.web.controllers;

import client_project.y2s1.team2.graphium.service.FileDatabaseStoreService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class testUploadController {

    @Autowired
    private FileDatabaseStoreService FileStoreService;

    @GetMapping("/uploadPage")
    public String testUpload(Model model) {
        return "tempUploadTest.html";
    }

    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam("file") MultipartFile uploadedFile) {
        try {
            FileStoreService.store(uploadedFile);
            System.out.print("Uploaded");
            return "index.html";
        } catch(Exception e) {
            System.out.print("Error uploading");
            return "index.html";
        }
    }
}
