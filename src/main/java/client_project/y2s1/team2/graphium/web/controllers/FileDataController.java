package client_project.y2s1.team2.graphium.web.controllers;

import client_project.y2s1.team2.graphium.data.jpa.entities.Documents;
import client_project.y2s1.team2.graphium.data.jpa.repositories.DocumentsRepositoryJPA;
import client_project.y2s1.team2.graphium.service.AuditService;
import client_project.y2s1.team2.graphium.service.RetrieveDocumentData;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private DocumentsRepositoryJPA docsRepo;

    @Autowired
    private AuditService auditService;

    @GetMapping("/viewPDF/{documentID}")
    public ResponseEntity<byte[]> returnInlinePDFData(@PathVariable("documentID") Long documentID, Principal principal) {
        Optional<Documents> document = docData.getDocumentByID(documentID);
        // Creating ResponseEntity with correct headers for displaying in-browser
        if (document.isPresent()) {
            if (docData.userCanViewDocument(document.get(), principal.getName())) {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.parseMediaType("application/pdf"));
                headers.add("content-disposition", "inline; filename=" + document.get().getTitle());
                auditService.documentViewed(principal.getName(), document.get().getId().intValue(), "user successfully viewed a file - returnInlinePDFData method within FileDataController");
                return new ResponseEntity<>(document.get().getFileData(), headers, HttpStatus.OK);
            } else {
                auditService.documentViewedFailed(principal.getName(), document.get().getId().intValue(), "a user attempted to view a file they don't have permission to - returnInlinePDFDatamethod within FileDataController");
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }
        auditService.documentViewedFailed(principal.getName(), document.get().getId().intValue(), "a user attempted to view a file that didn't exist - returnInlinePDFData method within FileDataController");
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/delete/{documentID}")
    public String deleteDocument(@PathVariable("documentID") Long receivedID) {
        docsRepo.deleteById(receivedID);
        return "redirect:/myuploads";
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
                auditService.documentDownloaded(principal.getName(), document.get().getId().intValue(), "a user downloaded a file - returnDownloadPDFData method within FileDataController");
                return new ResponseEntity<>(document.get().getFileData(), headers, HttpStatus.OK);
            } else {
                auditService.documentDownloadedFailed(principal.getName(), document.get().getId().intValue(), "a user attempted to download a file they don't have permission to download - returnDownloadPDFData method within FileDataController");
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }
        auditService.documentDownloadedFailed(principal.getName(), document.get().getId().intValue(), "a user attempted to download a file they didn't exist - returnDownloadPDFData method within FileDataController");
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
