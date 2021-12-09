package client_project.y2s1.team2.graphium.web.controllers;

import client_project.y2s1.team2.graphium.domain.ReturnError;
import client_project.y2s1.team2.graphium.service.StoreFileDatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

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
    public ModelAndView uploadDocument(
            @RequestParam("documentTitle") String receivedTitle,
            @RequestParam("documentData") MultipartFile receivedDocumentData,
            Principal principal
    ) {
        ModelAndView model = new ModelAndView();
        try {
            // Attempting to store the file, returning boolean and message if it has/has not
            ReturnError storeResult = StoreFile.storeFile(
                    receivedTitle,
                    principal.getName(),
                    receivedDocumentData.getOriginalFilename().split("[.]")[1],
                    receivedDocumentData
            );
            // Checking if the storing went through, appending returned error if not
            if (storeResult.errored() == true) {
                model.addObject("secondaryText", storeResult.getNiceError());
                model.setViewName("error.html");
            } else {
                model.setViewName("redirect:/documents");
            }
        } catch(Exception e) {
            model.addObject("secondaryText", "Please retry upload");
            model.setViewName("error.html");
        }
        return model;
    }
}
