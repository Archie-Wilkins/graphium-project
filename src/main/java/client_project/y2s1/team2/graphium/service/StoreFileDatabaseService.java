package client_project.y2s1.team2.graphium.service;

import client_project.y2s1.team2.graphium.data.jpa.entities.Documents;
import client_project.y2s1.team2.graphium.data.jpa.entities.Users;
import client_project.y2s1.team2.graphium.data.jpa.repositories.DocumentsRepositoryJPA;
import client_project.y2s1.team2.graphium.data.jpa.repositories.UsersRepositoryJPA;
import client_project.y2s1.team2.graphium.domain.ReturnError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    public ReturnError storeFile(String docTitle, String username, String fileType, MultipartFile file) throws IOException {
        Optional<Users> currentUser = userRepository.findByUsername(username);
        if (currentUser.isEmpty()) {
            return new ReturnError(true, "invalid_username", "Error finding user from given username.");
        }
        if (docRepository.findByTitleAndUser(docTitle, currentUser.get()).isPresent()) {
            return new ReturnError(true, "duplicate_title_and_user", "You already have a document with that title.");
        }
        if (!Arrays.stream(allowedFileExstensions).anyMatch(fileType::equals)) {
            return new ReturnError(true, "file_type_invalid", "Document file is the wrong format.");
        }
        if (!Arrays.stream(allowedFileExstensions).anyMatch(file.getOriginalFilename().split("[.]")[1]::equals)) {
            return new ReturnError(true, "file_extension_invalid", "Document file is the wrong format.");
        }
        try {

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
            return new ReturnError(false);
        } catch(Exception e) {
            return new ReturnError(true, "issue-saving", "Issue during submission, please try again.");
        }
    }
}


