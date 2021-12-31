package client_project.y2s1.team2.graphium.fullcontainer;

import client_project.y2s1.team2.graphium.service.PasswordReaderService;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class SimplePasswordStoringTest {

    @Test
    public void passwordWontStoreIfFoundInList() throws IOException {
        String password = "password";
        PasswordReaderService passwordCheck = new PasswordReaderService();
        Boolean test = PasswordReaderService.fileReader(password);
        assertEquals(test, true);
    }

    @Test
    public void passwordIsNotCaseSensitive() throws IOException {
        String password = "pAsSwOrD";
        PasswordReaderService passwordCheck = new PasswordReaderService();
        Boolean test = PasswordReaderService.fileReader(password);
        assertEquals(test, false);
    }


}
