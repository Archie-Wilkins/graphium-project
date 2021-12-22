package client_project.y2s1.team2.graphium.data.jpa.repositories;

import client_project.y2s1.team2.graphium.data.jpa.entities.AccessAuditReports;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccessAuditReportsRepositoryJPA extends JpaRepository<AccessAuditReports, Long> {

    List<AccessAuditReports> findAll();
    List<AccessAuditReports> findAllByFkActionId(int actionID);
    AccessAuditReports save(AccessAuditReports access_audit_reports);
}
