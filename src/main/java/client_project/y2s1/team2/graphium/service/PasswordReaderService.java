package client_project.y2s1.team2.graphium.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

public class PasswordReaderService {
    public static Boolean fileReader(String attemptedPassword) throws IOException
    {
        //used to get path to file
        String documentToRead = Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("static")).getPath();
        String pathWithoutSpaces = documentToRead.replace("%20", " ");
        //https://github.com/danielmiessler/SecLists provides text files with the most common passwords, the one being used is the 10,000 most common passwords
        String file = pathWithoutSpaces.concat("/text/commonPasswords.txt");
        Boolean isCommonPassword = false;
        File commonPasswordsFile=new File(file);
        String[] passwords=null;
        FileReader fileReader = new FileReader(commonPasswordsFile);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String i;

        while((i=bufferedReader.readLine())!=null)
        {
            passwords=i.split("\r\n");
            for (String commonPassword : passwords)
            {
                if (commonPassword.equals(attemptedPassword))
                {
                    isCommonPassword = true;
                }
            }
        }
        fileReader.close();
        System.out.println(isCommonPassword);
        return isCommonPassword;
    }
}
