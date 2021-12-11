package client_project.y2s1.team2.graphium.service;

import client_project.y2s1.team2.graphium.data.jpa.entities.Access_Audit_Reports;
import client_project.y2s1.team2.graphium.data.jpa.entities.Documents;
import client_project.y2s1.team2.graphium.data.jpa.entities.Organisations;
import client_project.y2s1.team2.graphium.data.jpa.repositories.Access_Audit_ReportsRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class AuditService {

    private Access_Audit_ReportsRepositoryJPA auditRepo;
    private TimeService timeService;

    public AuditService(Access_Audit_ReportsRepositoryJPA anAuditRepo, TimeService aTimeService){
        auditRepo = anAuditRepo;
        timeService = aTimeService;
    }

    public List<Access_Audit_Reports> getAllAuditLogs(){
        return auditRepo.findAll();
    }

    public List<Access_Audit_Reports> getAllAuditLogsByActionID(int actionID) {
        return auditRepo.findAllByFkActionId(actionID);
    }


    //User logged in true or false
    public void userLoggedIn(String username, boolean Success){
        if (Success == true){
            Access_Audit_Reports auditLog = new Access_Audit_Reports(null, username, null, 1, timeService.getTimeStamp());
            auditRepo.save(auditLog);
        }
        else{
            Access_Audit_Reports auditLog = new Access_Audit_Reports(null, username, null, 2, timeService.getTimeStamp());
            auditRepo.save(auditLog);
        }
     }

    //All systems documents accessed true or false
    public void allSystemDocumentsAccessed(String username, boolean Success){
        if (Success == true){
            Access_Audit_Reports auditLog = new Access_Audit_Reports(null, username, null, 3, timeService.getTimeStamp());
            auditRepo.save(auditLog);
        }
        else{
            Access_Audit_Reports auditLog = new Access_Audit_Reports(null, username, null, 4, timeService.getTimeStamp());
            auditRepo.save(auditLog);
        }
    }

    //Organisation documents accessed true or false
    public void organisationDocumentsAccessed(String username, boolean Success){
        if (Success == true){
            Access_Audit_Reports auditLog = new Access_Audit_Reports(null, username, null, 5, timeService.getTimeStamp());
            auditRepo.save(auditLog);
        }
        else{
            Access_Audit_Reports auditLog = new Access_Audit_Reports(null, username, null, 6, timeService.getTimeStamp());
            auditRepo.save(auditLog);
        }

    }

    //Document downloaded
    public void documentDownloaded(String username, int documentID){
        Access_Audit_Reports auditLog = new Access_Audit_Reports(null, username, documentID, 7, timeService.getTimeStamp());
        auditRepo.save(auditLog);
    }

    //Document deleted
    public void documentDeleted(String username, int documentID){
        Access_Audit_Reports auditLog = new Access_Audit_Reports(null, username, documentID, 8, timeService.getTimeStamp());
        auditRepo.save(auditLog);
    }

    //Document viewed
    public void documentViewed(String username, int documentID){
        Access_Audit_Reports auditLog = new Access_Audit_Reports(null, username, documentID, 9, timeService.getTimeStamp());
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
    public void documentUploaded(String username, int documentID){
        Access_Audit_Reports auditLog = new Access_Audit_Reports(null, username, documentID, 12, timeService.getTimeStamp());
        auditRepo.save(auditLog);
    }

    public void documentUploadFailed(String username){
        Access_Audit_Reports auditLog = new Access_Audit_Reports(null, username, null, 13, timeService.getTimeStamp());
        auditRepo.save(auditLog);
    }

    public void documentDownloadedFailed(String username, int documentID) {
        Access_Audit_Reports auditLog = new Access_Audit_Reports(null, username, documentID, 14, timeService.getTimeStamp());
        auditRepo.save(auditLog);
    }

    public void documentViewedFailed(String username, int documentID) {
        Access_Audit_Reports auditLog = new Access_Audit_Reports(null, username, documentID, 15, timeService.getTimeStamp());
        auditRepo.save(auditLog);
    }
}
