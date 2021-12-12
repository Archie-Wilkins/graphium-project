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
    public void userLoggedIn(String username, boolean Success, String actionDescription){
        if (Success == true){
            Access_Audit_Reports auditLog = new Access_Audit_Reports(null, username, null,null, 1, timeService.getTimeStamp(), actionDescription);
            auditRepo.save(auditLog);
        }
        else{
            Access_Audit_Reports auditLog = new Access_Audit_Reports(null, username, null,null, 2, timeService.getTimeStamp(), actionDescription);
            auditRepo.save(auditLog);
        }
     }

    //All systems documents accessed true or false
    public void allSystemDocumentsAccessed(String username, boolean Success, String actionDescription, Integer orgId){
        if (Success == true){
            Access_Audit_Reports auditLog = new Access_Audit_Reports(null, username, null, orgId, 3, timeService.getTimeStamp(), actionDescription);
            auditRepo.save(auditLog);
        }
        else{
            Access_Audit_Reports auditLog = new Access_Audit_Reports(null, username, null, orgId,4, timeService.getTimeStamp(), actionDescription);
            auditRepo.save(auditLog);
        }
    }

    //Organisation documents accessed true or false
    public void organisationDocumentsAccessed(String username, boolean Success, String actionDescription, Integer orgId){
        if (Success == true){
            Access_Audit_Reports auditLog = new Access_Audit_Reports(null, username, null, orgId, 5, timeService.getTimeStamp(), actionDescription);
            auditRepo.save(auditLog);
        }
        else{
            Access_Audit_Reports auditLog = new Access_Audit_Reports(null, username, null, orgId, 6, timeService.getTimeStamp(), actionDescription);
            auditRepo.save(auditLog);
        }

    }

    //Document downloaded
    public void documentDownloaded(String username, int documentID, String actionDescription){
        Access_Audit_Reports auditLog = new Access_Audit_Reports(null, username, documentID, null, 7, timeService.getTimeStamp(), actionDescription);
        auditRepo.save(auditLog);
    }

    //Document deleted
    public void documentDeleted(String username, int documentID, String actionDescription){
        Access_Audit_Reports auditLog = new Access_Audit_Reports(null, username, documentID, null,8, timeService.getTimeStamp(), actionDescription);
        auditRepo.save(auditLog);
    }

    //Document viewed
    public void documentViewed(String username, int documentID, String actionDescription){
        Access_Audit_Reports auditLog = new Access_Audit_Reports(null, username, documentID, null,9, timeService.getTimeStamp(), actionDescription);
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
    public void documentUploaded(String username, int documentID, String actionDescription){
        Access_Audit_Reports auditLog = new Access_Audit_Reports(null, username, documentID, null, 12, timeService.getTimeStamp(), actionDescription);
        auditRepo.save(auditLog);
    }

    public void documentUploadFailed(String username, String actionDescription){
        Access_Audit_Reports auditLog = new Access_Audit_Reports(null, username, null, null,13, timeService.getTimeStamp(), actionDescription);
        auditRepo.save(auditLog);
    }

    public void documentDownloadedFailed(String username, int documentID, String actionDescription) {
        Access_Audit_Reports auditLog = new Access_Audit_Reports(null, username, documentID, null,14, timeService.getTimeStamp(), actionDescription);
        auditRepo.save(auditLog);
    }

    public void documentViewedFailed(String username, int documentID, String actionDescription) {
        Access_Audit_Reports auditLog = new Access_Audit_Reports(null, username, documentID, null,15, timeService.getTimeStamp(), actionDescription);
        auditRepo.save(auditLog);
    }
}
