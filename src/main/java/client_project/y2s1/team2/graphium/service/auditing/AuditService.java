package client_project.y2s1.team2.graphium.service.auditing;

import client_project.y2s1.team2.graphium.data.jpa.entities.Access_Audit_Reports;

import java.util.List;

public interface AuditService {

    List<Access_Audit_Reports> getAllAuditLogs();

    List<Access_Audit_Reports> getAllAuditLogsByActionID(int actionID);

    void userLoggedIn(String username, boolean Success, String actionDescription);

    void allSystemDocumentsAccessed(String username, boolean Success, String actionDescription, Integer orgId);

    void organisationDocumentsAccessed(String username, boolean Success, String actionDescription, Integer orgId);

    void documentDownloaded(String username, int documentID, String actionDescription);

    void documentDeleted(String username, int documentID, String actionDescription);

    void documentViewed(String username, int documentID, String actionDescription);

    void documentUploaded(String username, int documentID, String actionDescription);

    void documentUploadFailed(String username, String actionDescription);

    void documentDownloadedFailed(String username, int documentID, String actionDescription);

    void documentViewedFailed(String username, int documentID, String actionDescription);
}
