package client_project.y2s1.team2.graphium.service;

import client_project.y2s1.team2.graphium.data.jpa.entities.Documents;
import client_project.y2s1.team2.graphium.data.jpa.entities.Users;
import client_project.y2s1.team2.graphium.data.jpa.repositories.DocumentsRepositoryJPA;
import client_project.y2s1.team2.graphium.data.jpa.repositories.UsersRepositoryJPA;
import client_project.y2s1.team2.graphium.domain.SubmissionError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.Document;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Optional;

@Service
public class StoreFileDatabaseService {
    @Autowired
    private DocumentsRepositoryJPA docRepository;
    @Autowired
    private UsersRepositoryJPA userRepository;
    private String[] allowedFileExstensions = {"pdf", "docx"};

    public SubmissionError storeFile(String docTitle, String username, String fileType, MultipartFile file) throws IOException {
        Optional<Users> currentUser = userRepository.findByUsername(username);
        if (currentUser.isEmpty()) {
            return new SubmissionError(true, "invalid_username", "Could not find your account, please try signing in again");
        }
        if (docRepository.findByTitleAndUser(docTitle, currentUser.get()).isPresent()) {
            return new SubmissionError(true, "duplicate_title_and_user", "You already have a document with that title.");
        }
        if (!Arrays.stream(allowedFileExstensions).anyMatch(fileType::equals)) {
            return new SubmissionError(true, "file_type_invalid", "Document is in an unsupported format");
        }
        if (!Arrays.stream(allowedFileExstensions).anyMatch(file.getOriginalFilename().split("[.]")[1]::equals)) {
            return new SubmissionError(true, "file_extension_invalid", "Document is in an unsupported format");
        }

        DateTimeFormatter dateTime = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String date = String.valueOf(dateTime.format(now));

        Documents newDoc = new Documents(
                docTitle,
                date,
                currentUser.get(),
                fileType,
                file.getBytes()
        );
        docRepository.save(newDoc);
        return new SubmissionError(false);
    }
}


