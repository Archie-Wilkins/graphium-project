package client_project.y2s1.team2.graphium.data.jpa.repositories;

import client_project.y2s1.team2.graphium.data.jpa.entities.Documents;
import client_project.y2s1.team2.graphium.data.jpa.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DocumentsRepositoryJPA extends JpaRepository<Documents, Long> {
    List<Documents> findAll();
    Optional<Documents> findByTitleAndUser(String title, Users user);
    Documents save(Documents document);

    @Query(value="select * from Documents d where d.title like %:keyword%", nativeQuery = true)
    List<Documents> findByTitle(@Param("keyword") String keyword);

    List<Documents> findAllByUser_Organisation_Name(String orgName);


}
