package client_project.y2s1.team2.graphium.data.jpa.repositories;

import client_project.y2s1.team2.graphium.data.jpa.entities.Access_Audit_Reports;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Access_Audit_ReportsRepositoryJPA extends JpaRepository<Access_Audit_Reports, Long> {

    List<Access_Audit_Reports> findAll();
    List<Access_Audit_Reports> findAllByFkActionId(int actionID);
    Access_Audit_Reports save(Access_Audit_Reports access_audit_reports);
}
