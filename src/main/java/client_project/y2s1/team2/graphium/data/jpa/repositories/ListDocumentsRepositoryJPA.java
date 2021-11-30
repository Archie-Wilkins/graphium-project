package client_project.y2s1.team2.graphium.data.jpa.repositories;

import client_project.y2s1.team2.graphium.data.jpa.entities.Documents;
import client_project.y2s1.team2.graphium.data.jpa.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ListDocumentsRepositoryJPA extends JpaRepository<Documents, Long> {
    List<Documents> findAll();
}
