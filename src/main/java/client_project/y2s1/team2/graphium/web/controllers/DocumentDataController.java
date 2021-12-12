package client_project.y2s1.team2.graphium.web.controllers;

import client_project.y2s1.team2.graphium.data.jpa.entities.Documents;
import client_project.y2s1.team2.graphium.data.jpa.repositories.DocumentsRepositoryJPA;
import client_project.y2s1.team2.graphium.domain.ReturnError;
import client_project.y2s1.team2.graphium.service.AuditService;
import client_project.y2s1.team2.graphium.service.RetrieveDocumentData;
import client_project.y2s1.team2.graphium.service.StoreFileDatabaseService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/document")
public class DocumentDataController {
    private RetrieveDocumentData docData;
    private DocumentsRepositoryJPA docsRepo;
    private AuditService auditService;
    private StoreFileDatabaseService storeFileService;

    public DocumentDataController(RetrieveDocumentData aDocData, DocumentsRepositoryJPA aDocsRepo, AuditService aAuditService, StoreFileDatabaseService aStoreFileService) {
        docData = aDocData;
        docsRepo = aDocsRepo;
        auditService = aAuditService;
        storeFileService = aStoreFileService;
    }

    /*
        Viewing a PDFs data
     */
    @GetMapping("/view/{documentID}")
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

    /*
        Downloading a PDFs data
     */
    @GetMapping("/download/{documentID}")
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


    /*
        Page to allow users to upload a file
     */
    @GetMapping("/upload")
    public String upload(){
        return "upload.html";
    }

    /*
        Trys to take uploaded file and save it to database
     */
    @PostMapping("/upload")
    public ModelAndView uploadDocument(
            @RequestParam("documentTitle") String receivedTitle,
            @RequestParam("documentData") MultipartFile receivedDocumentData,
            Principal principal
    ) {
        ModelAndView model = new ModelAndView();
        try {
            // Attempting to store the file, returning boolean and message if it has/has not
            ReturnError storeResult = storeFileService.storeFile(
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
                model.setViewName("redirect:/");
            }
        } catch(Exception e) {
            model.addObject("secondaryText", "Please retry upload");
            model.setViewName("error.html");
        }
        return model;
    }

    /*
        Deleting a PDFs data
     */
    @GetMapping("/delete/{documentID}")
    public String deleteDocument(@PathVariable("documentID") Long receivedID) {
        docsRepo.deleteById(receivedID);
        return "redirect:/";
    }
}

