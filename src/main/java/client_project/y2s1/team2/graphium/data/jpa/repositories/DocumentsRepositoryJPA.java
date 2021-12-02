package client_project.y2s1.team2.graphium.data.jpa.repositories;

import client_project.y2s1.team2.graphium.data.jpa.entities.Documents;
import client_project.y2s1.team2.graphium.data.jpa.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DocumentsRepositoryJPA extends JpaRepository<Documents, Long> {
    List<Documents> findAll();
    Optional<Documents> findByTitleAndUser(String title, Users user);
    Documents save(Documents document);
