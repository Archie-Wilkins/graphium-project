package client_project.y2s1.team2.graphium.data.jpa.repositories;

import client_project.y2s1.team2.graphium.data.jpa.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsersRepositoryJPA extends JpaRepository<Users, Long> {
    List<Users> findAll();
    Optional<Users> findByUsername(String username);
    Optional<Users> findFirstByEmail(String email);
    Users save(Users user);
}