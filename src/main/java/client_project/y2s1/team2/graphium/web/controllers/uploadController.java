package client_project.y2s1.team2.graphium.web.controllers;

import client_project.y2s1.team2.graphium.domain.SubmissionError;
import client_project.y2s1.team2.graphium.service.StoreFileDatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@Controller
public class uploadController {

    @Autowired
    private StoreFileDatabaseService StoreFile;

    @GetMapping({"/upload", "upload"})
    public String upload(){
        return "upload.html";
    }

    @PostMapping("/uploadDocument")
    public String uploadDocument(
            @RequestParam("documentTitle") String receivedTitle,
            @RequestParam("documentData") MultipartFile receivedDocumentData,
            Principal principal,
            Model model
    ) {
        try {
            // Attempting to store the file, returning boolean and message if it has/has not
            SubmissionError storeResult = StoreFile.storeFile(
                    receivedTitle,
                    principal.getName(),
                    receivedDocumentData.getOriginalFilename().split("[.]")[1],
                    receivedDocumentData
            );
            // Checking if the storing went through, appending returned error if not
            if (storeResult.errored() == true) {
                model.addAttribute("uploaded", false);
                model.addAttribute("errorMessage", storeResult.getNiceError());
            } else {
                model.addAttribute("uploaded", true);
            }
            return "uploaded.html";
        } catch(Exception e) {
            model.addAttribute("uploaded", false);
            model.addAttribute("errorMessage", "Please retry upload.");
            return "uploaded.html";
        }
    }
}
