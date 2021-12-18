package client_project.y2s1.team2.graphium.web.controllers;

import client_project.y2s1.team2.graphium.domain.AuthorityDTO;
import client_project.y2s1.team2.graphium.domain.NewUserFeedBackDTO;
import client_project.y2s1.team2.graphium.domain.OrganisationDTO;
import client_project.y2s1.team2.graphium.domain.UserDTO;
import client_project.y2s1.team2.graphium.service.NewUserService;
import client_project.y2s1.team2.graphium.service.OrganisationsService;
import client_project.y2s1.team2.graphium.web.controllers.FormObjects.NewUserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/systemAdmin")
public class SystemAdminNewUserController {
    @Autowired
    NewUserService newUserService;

    private final OrganisationsService orgService;

    public SystemAdminNewUserController(OrganisationsService anOrgService) {
        this.orgService = anOrgService;
    }


    @GetMapping("/newUser")
    public String newUser(Model model) {

        List<OrganisationDTO> allOrganisations = orgService.getAllOrganisationsAsDTOs();

        model.addAttribute("feedbackMessage", null);
        model.addAttribute("organisations", allOrganisations );
        model.addAttribute("newUserForm", new NewUserForm());
        return "systemAdminNewUser.html";
    }

    @PostMapping("/newUserSubmit")
    public String newUser(Model model, NewUserForm newUserForm) {

        AuthorityDTO authorityDTO = newUserForm.toAuthorityDTO();
        UserDTO userDTO = newUserForm.toUserDTO();


        NewUserFeedBackDTO feedBack = newUserService.newUserSubmit(authorityDTO, userDTO);
        String feedBackMessage = feedBack.getMessage();
        NewUserForm newUserFormPreFilled = feedBack.getNewUserForm();

        List<OrganisationDTO> allOrganisations = orgService.getAllOrganisationsAsDTOs();

        model.addAttribute("feedBackMessage", feedBackMessage);
        model.addAttribute("organisations", allOrganisations );
        model.addAttribute("newUserForm", newUserFormPreFilled);
        return "systemAdminNewUser.html";
    }
}
