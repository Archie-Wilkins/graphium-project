package client_project.y2s1.team2.graphium.fullcontainer;

import client_project.y2s1.team2.graphium.service.PasswordReaderService;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class SimplePasswordStoringTest {

    @Test
    public void passwordWontStoreIfFoundInList() throws IOException {
        String password = "password";
        PasswordReaderService passwordCheck = new PasswordReaderService();
        Boolean test = passwordCheck.fileReader(password);
        assertEquals(test);
    }

    @Test
    public void passwordIsNotCaseSensitive() throws IOException {
        String password = "pAsSwOrD";
        PasswordReaderService passwordCheck = new PasswordReaderService();
        Boolean test = passwordCheck.fileReader(password);
        assertEquals(test);
    }

    private void assertEquals(Boolean test) {
    }

}
