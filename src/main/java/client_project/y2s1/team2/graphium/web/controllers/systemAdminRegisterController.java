package client_project.y2s1.team2.graphium.web.controllers;

import client_project.y2s1.team2.graphium.data.jpa.entities.*;
import client_project.y2s1.team2.graphium.data.jpa.repositories.AuthoritiesRepositoryJPA;
import client_project.y2s1.team2.graphium.data.jpa.repositories.DocumentsRepositoryJPA;
import client_project.y2s1.team2.graphium.data.jpa.repositories.UsersRepositoryJPA;
import client_project.y2s1.team2.graphium.service.OrganisationAdminRegisterService;
import client_project.y2s1.team2.graphium.service.PasswordReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping(path = "/systemAdmin")
public class systemAdminRegisterController {

    @Autowired
    private DocumentsRepositoryJPA docsRepo;
    @Autowired
    private UsersRepositoryJPA userRepo;
    @Autowired
    private AuthoritiesRepositoryJPA authorityRepo;

    private final OrganisationAdminRegisterService orgService;

    @Autowired
    public systemAdminRegisterController(OrganisationAdminRegisterService orgService) {
        this.orgService = orgService;
    }



    @GetMapping({"/adminreg", "adminreg"})
    public String register(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String name = auth.getName();
        model.addAttribute("user", new Users());
        return "adminRegister.html";
    }

    @PostMapping("/process_register")
    public String processRegister(@Validated Users user, Authorities authority, BindingResult bindingResult) throws IOException {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String attemptedPassword = user.getPassword();
        PasswordReaderService passwordCheck = new PasswordReaderService();
        if (orgService.usernameExists(user.getUsername())) {
            bindingResult.addError(new FieldError("user", "username", "Username already exists!"));
        } else if (orgService.emailExists(user.getEmail())){
            bindingResult.addError(new FieldError("user2", "email", "Email already exists"));
        }

        if(bindingResult.hasErrors()){
            return "redirect:/adminreg?user";
        }

        if (passwordCheck.fileReader(attemptedPassword) == false) {
            String encodedPassword = passwordEncoder.encode(attemptedPassword);
            user.setPassword(encodedPassword);
            user.setEnabled(Boolean.TRUE);
            authority.setFk_username(user.getUsername());
            authority.setAuthority("orgAdmin");
            userRepo.save(user);
            authorityRepo.save(authority);
            return "register_success";
        } else {
            return "redirect:/adminreg?error";
        }
    }
}

