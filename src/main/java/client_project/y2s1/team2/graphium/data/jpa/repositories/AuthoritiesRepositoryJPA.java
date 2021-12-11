package client_project.y2s1.team2.graphium.data.jpa.repositories;

import client_project.y2s1.team2.graphium.data.jpa.entities.Authorities;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthoritiesRepositoryJPA extends JpaRepository<Authorities, Long> {
    List<Authorities> findAll();
    Authorities save(Authorities authority);
}