package client_project.y2s1.team2.graphium.intergrationTests;

import client_project.y2s1.team2.graphium.data.jpa.entities.Users;
import client_project.y2s1.team2.graphium.data.jpa.repositories.UsersRepositoryJPA;
import client_project.y2s1.team2.graphium.domain.AuthorityDTO;
import client_project.y2s1.team2.graphium.domain.UserDTO;
import client_project.y2s1.team2.graphium.service.NewUserService;
import client_project.y2s1.team2.graphium.service.TimeService;
import client_project.y2s1.team2.graphium.service.UserDataRetrievalService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.thymeleaf.util.StringUtils;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts={"/schema-maria.sql", "/data-maria.sql"})
@DirtiesContext
public class newUserServiceTests {

    @Autowired
    NewUserService newUserService;

    @Autowired
    UserDataRetrievalService userDataRetrievalService;

    @Autowired
    TimeService timeService;

    @Autowired
    UsersRepositoryJPA userRepo;

    @Test
    public void encryptsUserPasswordOnUpload() throws Exception {
        AuthorityDTO authorityDTO = new AuthorityDTO("SeleniumTestUser", "researcher");
        UserDTO userDTO = new UserDTO("SeleniumTestUser","aTestPassword",true,1,"Selenium","Testing", "Selenium@Testing.com",null);

        newUserService.newUserSubmit(authorityDTO,userDTO);

        Optional<Users> userOptional = userDataRetrievalService.findUserByUsername("SeleniumTestUser");
        Users user = userOptional.get();

        String userPassword = user.getPassword();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //Will return false if the password is not encrpyted or if the passwords don't match
        boolean passwordCheck = passwordEncoder.matches("aTestPassword",userPassword);

        assertEquals(true, passwordCheck);
    }

    @Test
    public void canSetAuthorityDate() throws Exception {
        AuthorityDTO authorityDTO = new AuthorityDTO("SeleniumTestUser", "researcher");
        UserDTO userDTO = new UserDTO("SeleniumTestUser","aTestPassword",true,1,"Selenium","Testing", "Selenium@Testing.com",null);

        newUserService.newUserSubmit(authorityDTO,userDTO);

        Optional<Users> userOptional = userDataRetrievalService.findUserByUsername("SeleniumTestUser");
        Users user = userOptional.get();

        String userAuthorityDate = user.getAuthority_set_date();

        //Checking if not null as the purpose of the test is to confirm an authority_set_date
        //has been added a better approach would be to set the authority set date from the database
        boolean userAuthorityDateNotNull = userAuthorityDate.equals(null);

        assertEquals(userAuthorityDateNotNull,false);
    }


    @Test
    public void canUploadsUser() throws Exception {

        List<Users> allUsers = userRepo.findAll();

        AuthorityDTO authorityDTO = new AuthorityDTO("SeleniumTestUser", "researcher");
        UserDTO userDTO = new UserDTO("SeleniumTestUser","aTestPassword",true,1,"Selenium","Testing", "Selenium@Testing.com",null);
        newUserService.newUserSubmit(authorityDTO,userDTO);

        List<Users> allUsersPostSubmit = userRepo.findAll();

        int allUsersSize = allUsers.size();
        int allUsersPostSubmitSize = allUsersPostSubmit.size();

        assertEquals(allUsersPostSubmitSize, allUsersSize + 1);
    }

    @Test
    public void doesNotUploadUserWithDuplicateUserName() throws Exception{
        AuthorityDTO authorityDTO = new AuthorityDTO("SeleniumTestUser", "researcher");
        UserDTO userDTO = new UserDTO("SeleniumTestUser","aTestPassword",true,1,"Selenium","Testing", "Selenium@Testing.com",null);
        newUserService.newUserSubmit(authorityDTO,userDTO);

        //Gets all users after first submission
        List<Users> allUsers = userRepo.findAll();
        //Submitting again to force duplicate
        newUserService.newUserSubmit(authorityDTO,userDTO);

        List<Users> allUsersPostSecondSubmit = userRepo.findAll();

        int allUsersSize = allUsers.size();
        int allUsersPostSecondSubmitSize = allUsersPostSecondSubmit.size();

        assertEquals(allUsersPostSecondSubmitSize, allUsersSize);
    }
}
