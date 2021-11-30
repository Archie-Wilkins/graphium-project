package client_project.y2s1.team2.graphium.web.controllers;

import client_project.y2s1.team2.graphium.data.jpa.entities.Documents;
import client_project.y2s1.team2.graphium.service.RetrieveDocumentData;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class FileDataController {
    private RetrieveDocumentData docData;
    public FileDataController(RetrieveDocumentData aDocData) {
        docData = aDocData;
    }

    @GetMapping("/viewPDF/{documentID}")
    public ResponseEntity<byte[]> returnInlinePDFData(@PathVariable("documentID") Long receivedID) {
        // Finding document using service
        Optional<Documents> doc = docData.getDocumentByID(receivedID);
        // Creating ResponseEntity with correct headers for displaying in-browser
        if (doc.isPresent()) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/pdf"));
            headers.add("content-disposition", "inline; filename=" + doc.get().getTitle());
            return new ResponseEntity<>(doc.get().getFileData(), headers, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
