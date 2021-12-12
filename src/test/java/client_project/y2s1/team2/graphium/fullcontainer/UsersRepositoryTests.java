package client_project.y2s1.team2.graphium.fullcontainer;

import static org.assertj.core.api.Assertions.assertThat;

import client_project.y2s1.team2.graphium.data.jpa.entities.Users;
import client_project.y2s1.team2.graphium.data.jpa.repositories.UsersRepositoryJPA;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UsersRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UsersRepositoryJPA usersRepo;

    @Test
    public void testCreateAdmin(){
        Users user = new Users();
        user.setUsername("martin");
        user.setEmail("martin@test.com");
        user.setPassword("password");
        user.setEnabled(Boolean.TRUE);
        user.setOrgId(1);
        user.setFirstName("Martin");
        user.setLastName("Manasiev");
//        user.setAuthorityDate();

        Users savedUser = usersRepo.save(user);

        Users existUser = entityManager.find(Users.class, savedUser.getUsername());

        assertThat(user.getEmail()).isEqualTo(existUser.getEmail());
    }
}
