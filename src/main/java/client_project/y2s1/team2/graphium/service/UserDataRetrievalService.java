package client_project.y2s1.team2.graphium.service;

import client_project.y2s1.team2.graphium.data.jpa.entities.Users;
import client_project.y2s1.team2.graphium.data.jpa.repositories.UsersRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserDataRetrievalService {

    @Autowired
    private UsersRepositoryJPA usersRepo;

    @Transactional
    public Optional<Users> findUserByUsername(String username){
        return usersRepo.findByUsername(username);
    }

    @Transactional
    public Optional<Users> findByEmail(String email){
        return usersRepo.findFirstByEmail(email);
    }

    public boolean usernameExists(String username){
        return findUserByUsername(username).isPresent();
    }

    public boolean emailExists(String email){
        return findByEmail(email).isPresent();
    }
}
