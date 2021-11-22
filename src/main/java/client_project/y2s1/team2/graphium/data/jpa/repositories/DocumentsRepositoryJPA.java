package client_project.y2s1.team2.graphium.data.jpa.repositories;

import client_project.y2s1.team2.graphium.data.jpa.entities.Documents;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DocumentsRepositoryJPA extends JpaRepository<Documents, Long> {
    List<Documents> findAll();
    Optional<Documents> findByTitle(String title);
    Optional<Documents> findByFileType(String type);
    Documents save(Documents document);
}
