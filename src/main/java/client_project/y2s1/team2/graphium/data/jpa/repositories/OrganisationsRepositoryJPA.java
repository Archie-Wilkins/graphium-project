package client_project.y2s1.team2.graphium.data.jpa.repositories;

import client_project.y2s1.team2.graphium.data.jpa.entities.Organisations;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrganisationsRepositoryJPA extends JpaRepository<Organisations, Long> {
    List<Organisations> findAll();
    Optional<Organisations> findByName(String name);
    Organisations save(Organisations organisation);
}
