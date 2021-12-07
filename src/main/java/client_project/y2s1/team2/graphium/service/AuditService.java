package client_project.y2s1.team2.graphium.service;

import client_project.y2s1.team2.graphium.data.jpa.entities.Access_Audit_Reports;
import client_project.y2s1.team2.graphium.data.jpa.entities.Documents;
import client_project.y2s1.team2.graphium.data.jpa.entities.Organisations;
import client_project.y2s1.team2.graphium.data.jpa.repositories.Access_Audit_ReportsRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.time.LocalTime;

public class AuditService {

    @Autowired
    Access_Audit_ReportsRepositoryJPA auditRepo;

    //User logged in true or false
    public void userLoggedIn(String username, boolean Success){
        if (Success == true){
            Access_Audit_Reports auditLog = new Access_Audit_Reports(null, username, null, 1, LocalTime.now().toString());
            auditRepo.save(auditLog);
        }
        else{
            Access_Audit_Reports auditLog = new Access_Audit_Reports(null, username, null, 2, LocalTime.now().toString());
            auditRepo.save(auditLog);
        }
     }

    //All systems documents accessed true or false
    public void allSystemDocumentsAccessed(String username, boolean Success){
        if (Success == true){
            Access_Audit_Reports auditLog = new Access_Audit_Reports(null, username, null, 3, LocalTime.now().toString());
            auditRepo.save(auditLog);
        }
        else{
            Access_Audit_Reports auditLog = new Access_Audit_Reports(null, username, null, 4, LocalTime.now().toString());
            auditRepo.save(auditLog);
        }
    }

    //Organisation documents accessed true or false
    public void organisationDocumentsAccessed(String username, boolean Success){
        if (Success == true){
            Access_Audit_Reports auditLog = new Access_Audit_Reports(null, username, null, 5, LocalTime.now().toString());
            auditRepo.save(auditLog);
        }
        else{
            Access_Audit_Reports auditLog = new Access_Audit_Reports(null, username, null, 6, LocalTime.now().toString());
            auditRepo.save(auditLog);
        }

    }

    //Document downloaded
    public void documentDownloaded(String username, int DocumentID){
        Access_Audit_Reports auditLog = new Access_Audit_Reports(null, username, DocumentID, 7, LocalTime.now().toString());
        auditRepo.save(auditLog);
    }

    //Document deleted
    public void documentDeleted(String username, int DocumentID){
        Access_Audit_Reports auditLog = new Access_Audit_Reports(null, username, DocumentID, 8, LocalTime.now().toString());
        auditRepo.save(auditLog);
    }

    //Document viewed
    public void documentViewed(String username, int DocumentID){
        Access_Audit_Reports auditLog = new Access_Audit_Reports(null, username, DocumentID, 9, LocalTime.now().toString());
        auditRepo.save(auditLog);
    }

//    //New org created
//    public void userLoggedLog(String username, Organisations organisations){
//        //jpa save
//    }
//
//    //New user created
//    public void userLoggedLog(String username, String usernameCreated){
//        //jpa save
//    }

//    Document Uploaded
    public void documentUploaded(String username, int DocumentID){
        Access_Audit_Reports auditLog = new Access_Audit_Reports(null, username, DocumentID, 12, LocalTime.now().toString());
        auditRepo.save(auditLog);
    }
}
