package client_project.y2s1.team2.graphium.web.controllers;

import client_project.y2s1.team2.graphium.data.jpa.entities.Authorities;
import client_project.y2s1.team2.graphium.data.jpa.entities.Users;
import client_project.y2s1.team2.graphium.data.jpa.repositories.AuthoritiesRepositoryJPA;
import client_project.y2s1.team2.graphium.data.jpa.repositories.DocumentsRepositoryJPA;
import client_project.y2s1.team2.graphium.data.jpa.repositories.UsersRepositoryJPA;
import client_project.y2s1.team2.graphium.domain.DocumentDTO;
import client_project.y2s1.team2.graphium.domain.ListOfDocumentsDTO;
import client_project.y2s1.team2.graphium.service.PasswordReaderService;
import client_project.y2s1.team2.graphium.service.RetrieveDocumentData;
import client_project.y2s1.team2.graphium.service.UserRegisterService;
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
@RequestMapping("/organisation")
public class OrganisationAdminController {
    private DocumentsRepositoryJPA docsRepo;
    private UsersRepositoryJPA userRepo;
    private final UserRegisterService resService;
    private RetrieveDocumentData retrieveDocumentData;
    private AuthoritiesRepositoryJPA authorityRepo;

    public OrganisationAdminController(DocumentsRepositoryJPA aDocsRepo, UsersRepositoryJPA aUserRepo, UserRegisterService aResService, RetrieveDocumentData aRetrieveDocumentData, AuthoritiesRepositoryJPA aAuthorityRepo) {
        this.docsRepo = aDocsRepo;
        this.userRepo = aUserRepo;
        this.resService = aResService;
        this.retrieveDocumentData = aRetrieveDocumentData;
        this.authorityRepo = aAuthorityRepo;
    }

    /*
        Lists all documents owned by the organisations researchers
     */
    @GetMapping("/admin")
    public String index(Model model, Principal principal) {
        List<ListOfDocumentsDTO> allDocsByOrg = retrieveDocumentData.getAllDocsByAdminOrg(principal.getName());

        model.addAttribute("userName", principal.getName());
        model.addAttribute("documentList", allDocsByOrg);
        return "orgAdminHome.html";
    }


    @GetMapping("/newResearcher")
    public String register(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String name = auth.getName();
        model.addAttribute("user", new Users());
        return "researcherRegister.html";
    }

    @PostMapping("/newResearcher")
    public String processRegister(@Validated Users user, Authorities authority, BindingResult bindingResult) throws IOException {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String attemptedPassword = user.getPassword();
        PasswordReaderService passwordCheck = new PasswordReaderService();
        if (resService.usernameExists(user.getUsername())) {
            bindingResult.addError(new FieldError("user", "username", "Username already exists!"));
        } else if (resService.emailExists(user.getEmail())){
            bindingResult.addError(new FieldError("user2", "email", "Email already exists"));
        }

        if(bindingResult.hasErrors()){
            return "redirect:/organisation/newResearcher?user";
        }
        if (passwordCheck.fileReader(attemptedPassword) == false) {
            String encodedPassword = passwordEncoder.encode(attemptedPassword);
            user.setPassword(encodedPassword);
            user.setEnabled(Boolean.TRUE);
            authority.setFk_username(user.getUsername());
            authority.setAuthority("researcher");
            userRepo.save(user);
            authorityRepo.save(authority);
            return "register_success";
        } else {
            return "redirect:/organisation/newResearcher?error";
        }
    }
}
