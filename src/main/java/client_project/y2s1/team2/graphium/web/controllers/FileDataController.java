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

import java.security.Principal;
import java.util.Optional;

@Controller
public class FileDataController {
    private RetrieveDocumentData docData;
    public FileDataController(RetrieveDocumentData aDocData) {
        docData = aDocData;
    }

    @GetMapping("/viewPDF/{documentID}")
    public ResponseEntity<byte[]> returnInlinePDFData(@PathVariable("documentID") Long documentID, Principal principal) {
        Optional<Documents> document = docData.getDocumentByID(documentID);
        // Creating ResponseEntity with correct headers for displaying in-browser
        if (document.isPresent()) {
            if (docData.userCanViewDocument(document.get(), principal.getName())) {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.parseMediaType("application/pdf"));
                headers.add("content-disposition", "inline; filename=" + document.get().getTitle());
                return new ResponseEntity<>(document.get().getFileData(), headers, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/downloadPDF/{documentID}")
    public ResponseEntity<byte[]> returnDownloadPDFData(@PathVariable("documentID") Long documentID, Principal principal) {
        Optional<Documents> document = docData.getDocumentByID(documentID);
        // Creating ResponseEntity with correct headers for displaying in-browser
        if (document.isPresent()) {
            if (docData.userCanViewDocument(document.get(), principal.getName())) {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.parseMediaType("application/pdf"));
                headers.add("content-disposition", "attachment; filename=" + document.get().getTitle()+".pdf");
                return new ResponseEntity<>(document.get().getFileData(), headers, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

