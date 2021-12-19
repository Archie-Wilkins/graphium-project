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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/systemAdmin")
public class SystemAdminNewUserController {

    private final NewUserService newUserService;
    private final OrganisationsService orgService;

    public SystemAdminNewUserController(OrganisationsService anOrgService, NewUserService aNewUserService) {
        this.orgService = anOrgService;
        this.newUserService = aNewUserService;
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
    public String newUser(Model model, @Valid NewUserForm newUserForm, BindingResult bindingResult) {

        List<OrganisationDTO> allOrganisations = orgService.getAllOrganisationsAsDTOs();

        if(bindingResult.hasErrors()){
            String formErrorMessage = "Oh no looks like there's an error. Take another look. ";
            model.addAttribute("feedBackMessage", formErrorMessage);
            model.addAttribute("organisations", allOrganisations );
            model.addAttribute("newUserForm", newUserForm);
            return "systemAdminNewUser.html";
        }

        AuthorityDTO authorityDTO = newUserForm.toAuthorityDTO();
        UserDTO userDTO = newUserForm.toUserDTO();

        NewUserFeedBackDTO feedBack = newUserService.newUserSubmit(authorityDTO, userDTO);
        String feedBackMessage = feedBack.getMessage();
        NewUserForm newUserFormPreFilled = feedBack.getNewUserForm();

        model.addAttribute("feedBackMessage", feedBackMessage);
        model.addAttribute("organisations", allOrganisations );
        model.addAttribute("newUserForm", newUserFormPreFilled);
        return "systemAdminNewUser.html";
    }
}
