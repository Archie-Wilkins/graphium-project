package client_project.y2s1.team2.graphium.web.controllers;

import client_project.y2s1.team2.graphium.data.jpa.entities.Documents;
import client_project.y2s1.team2.graphium.data.jpa.entities.Users;
import client_project.y2s1.team2.graphium.data.jpa.repositories.DocumentsRepositoryJPA;
import client_project.y2s1.team2.graphium.data.jpa.repositories.OrganisationsRepositoryJPA;
import client_project.y2s1.team2.graphium.data.jpa.repositories.UsersRepositoryJPA;
import client_project.y2s1.team2.graphium.service.PasswordReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.*;
import java.security.Principal;
import java.util.List;

@Controller
public class adminController {

    @Autowired
    private DocumentsRepositoryJPA docsRepo;
    @Autowired
    private OrganisationsRepositoryJPA orgRepo;
    @Autowired
    private UsersRepositoryJPA userRepo;

    @GetMapping({"/admin", "admin"})
    public String index(Model model, Principal principal) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String name = auth.getName();
            String userName = principal.getName();
            List<Documents> AllDocuments = docsRepo.findAll();
            model.addAttribute("userName",userName);
            model.addAttribute("allDocuments",AllDocuments);
            return "admin.html";

    }

    @GetMapping({"/adminreg", "adminreg"})
    public String register(Model model){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String name = auth.getName();
            model.addAttribute("user", new Users());
            return "adminRegister.html";
    }

    @PostMapping("/process_register")
    public String processRegister(Users user) throws IOException {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        String attemptedPassword = user.getPassword();
        PasswordReaderService passwordCheck = new PasswordReaderService();

        if (passwordCheck.fileReader(attemptedPassword) == false){
            String encodedPassword = passwordEncoder.encode(attemptedPassword);
            user.setPassword(encodedPassword);
            userRepo.save(user);
            return "register_success";
        } else {
            return "redirect:/adminreg?error";
        }



    }
}
