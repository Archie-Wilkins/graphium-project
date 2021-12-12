package client_project.y2s1.team2.graphium.web.controllers;

import client_project.y2s1.team2.graphium.data.jpa.entities.*;
import client_project.y2s1.team2.graphium.data.jpa.repositories.AuthoritiesRepositoryJPA;
import client_project.y2s1.team2.graphium.data.jpa.repositories.UsersRepositoryJPA;
import client_project.y2s1.team2.graphium.domain.OrganisationDTO;
import client_project.y2s1.team2.graphium.domain.OrganisationFeedbackDTO;
import client_project.y2s1.team2.graphium.service.UserRegisterService;
import client_project.y2s1.team2.graphium.service.PasswordReaderService;
import client_project.y2s1.team2.graphium.service.UserFeedbackNewOrganisationService;
import client_project.y2s1.team2.graphium.web.controllers.FormObjects.OrganisationForm;
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

@Controller
@RequestMapping(path = "/systemAdmin")
public class SystemAdminOrganisationController {
    private UsersRepositoryJPA userRepo;
    private AuthoritiesRepositoryJPA authorityRepo;
    private final UserRegisterService orgService;
    private UserFeedbackNewOrganisationService feedbackService;

    public SystemAdminOrganisationController(UsersRepositoryJPA aUserRepo, AuthoritiesRepositoryJPA aAuthorityRepo, UserRegisterService aOrgService, UserFeedbackNewOrganisationService aFeedbackService) {
        this.userRepo = aUserRepo;
        this.authorityRepo = aAuthorityRepo;
        this.orgService = aOrgService;
        this.feedbackService = aFeedbackService;
    }

    /*
        Create New Organisation
     */
    @GetMapping({"/newOrganisation"})
    public String systemAdminOrganisation(
            Model model){
        OrganisationForm organisationDTO = new OrganisationForm();
        model.addAttribute("organisationDTO",organisationDTO);
        model.addAttribute("userFeedback","");
        return "systemAdminNewOrganisation.html";
    }

    @PostMapping({"/newOrganisation"})
    public String systemAdminOrganisation(OrganisationForm organisationForm, Model model){

        OrganisationDTO organisationDTO = organisationForm.toOrganisationDTO();
        OrganisationFeedbackDTO feedback = feedbackService.newOrganisationSubmit(organisationDTO);
        String userFeedback = feedback.getUserFeedback();
        OrganisationForm returnedOrg = feedback.getOrganisationDTO().toOrganisationForm();

        model.addAttribute("userFeedback",userFeedback);
        model.addAttribute("organisationDTO", returnedOrg);
        return "systemAdminNewOrganisation.html";
    }


    /*
        Create New Organisation Admin
     */
    @GetMapping("/newOrgAdmin")
    public String register(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String name = auth.getName();
        model.addAttribute("user", new Users());
        return "adminRegister.html";
    }

    @PostMapping("/newOrgAdmin")
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
            return "redirect:/newOrgAdmin?user";
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

