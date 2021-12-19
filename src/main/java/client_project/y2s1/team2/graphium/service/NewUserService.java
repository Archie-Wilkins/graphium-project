package client_project.y2s1.team2.graphium.service;

import client_project.y2s1.team2.graphium.data.jpa.entities.Users;
import client_project.y2s1.team2.graphium.data.jpa.repositories.AuthoritiesRepositoryJPA;
import client_project.y2s1.team2.graphium.data.jpa.repositories.UsersRepositoryJPA;
import client_project.y2s1.team2.graphium.domain.AuthorityDTO;
import client_project.y2s1.team2.graphium.domain.NewUserFeedBackDTO;
import client_project.y2s1.team2.graphium.domain.UserDTO;
import client_project.y2s1.team2.graphium.web.controllers.FormObjects.NewUserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class NewUserService {

    private final TimeService timeService;
    private final AuthoritiesRepositoryJPA authRepo;
    private final UsersRepositoryJPA userRepo;

    public NewUserService(TimeService aTimeService, AuthoritiesRepositoryJPA anAuthRepo, UsersRepositoryJPA aUserRepo){
        timeService = aTimeService;
        authRepo = anAuthRepo;
        userRepo = aUserRepo;
    }


    private void saveAuthority(AuthorityDTO authorityDTO) {
        authRepo.save(authorityDTO.toAuthorityEntity());
    }

    private void saveUser(UserDTO userDTO) {
        userRepo.save(userDTO.toUserEntity());
    }

    private boolean userNameExists(String username) {
        Optional<Users> usernameExists = userRepo.findByUsername(username);
        return !usernameExists.isEmpty();
    }

    public NewUserFeedBackDTO newUserSubmit(AuthorityDTO authorityDTO, UserDTO userDTO) {
//        Check for current username
        String username = userDTO.getUsername();
        if (userNameExists(username)) {
            //error
            String errorMessage = "Username taken, please try again";
            NewUserForm newUserForm = new NewUserForm(
                    userDTO.getUsername(),
                    userDTO.getPassword(),
                    userDTO.getEnabled(),
                    userDTO.getFk_organisation_id(),
                    userDTO.getFirst_name(),
                    userDTO.getLast_name(),
                    userDTO.getEmail(),
                    userDTO.getAuthority_set_date(),
                    authorityDTO.getAuthority()
            );
            NewUserFeedBackDTO newUserFeedBackDTO = new NewUserFeedBackDTO(errorMessage, newUserForm);
            return newUserFeedBackDTO;
        } else {

            String password = userDTO.getPassword();
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = passwordEncoder.encode(password);
            userDTO.setPassword(encodedPassword);
            userDTO.setAuthority_set_date(timeService.getTimeStamp());
            this.saveUser(userDTO);

            this.saveAuthority(authorityDTO);
            String successMessage = "Success. " + username + " has been saved";
            NewUserForm newUserForm2 = new NewUserForm();
            NewUserFeedBackDTO newUserFeedBackDTO = new NewUserFeedBackDTO(successMessage, newUserForm2);
            return newUserFeedBackDTO;
        }

    }
}
