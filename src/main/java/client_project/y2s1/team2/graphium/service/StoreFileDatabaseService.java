package client_project.y2s1.team2.graphium.service;

import client_project.y2s1.team2.graphium.data.jpa.entities.Documents;
import client_project.y2s1.team2.graphium.data.jpa.entities.Users;
import client_project.y2s1.team2.graphium.data.jpa.repositories.DocumentsRepositoryJPA;
import client_project.y2s1.team2.graphium.data.jpa.repositories.UsersRepositoryJPA;
import client_project.y2s1.team2.graphium.domain.ReturnError;
import client_project.y2s1.team2.graphium.service.auditing.AuditService;
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
    @Autowired
    AuditService auditService;

    private final String[] allowedFileExstensions = {"pdf", "docx"};

    public ReturnError storeFile(String docTitle, String username, String fileType, MultipartFile file) throws IOException {
        Optional<Users> currentUser = userRepository.findByUsername(username);
        if (currentUser.isEmpty()) {
            auditService.documentUploadFailed("invalid_username", "an invalid username (" + username + ") was passed in to the storeFile method under within the StoreFileDatabaseService");
            return new ReturnError(true, "invalid_username", "Could not find your account, please try signing in again");
        }
        if (docRepository.findByTitleAndUser(docTitle, currentUser.get()).isPresent()) {
            auditService.documentUploadFailed(username, "a duplicate title was submitted by a user within the storeFile method under the storeFileDatabaseService");
            return new ReturnError(true, "duplicate_title_and_user", "You already have a document with that title");
        }
        if (!Arrays.stream(allowedFileExstensions).anyMatch(fileType::equals)) {
            auditService.documentUploadFailed(username, "an invalid file type was submitted by a user within the storeFile method under the storeFileDatabaseService");
            return new ReturnError(true, "file_type_invalid", "Document is in an unsupported format");
        }
        if (!Arrays.stream(allowedFileExstensions).anyMatch(file.getOriginalFilename().split("[.]")[1]::equals)) {
            auditService.documentUploadFailed(username, "a file with an invalid file extension was submitted by a user within the storeFile method under the storeFileDatabaseService");
            return new ReturnError(true, "file_extension_invalid", "Document is in an unsupported format");
        }
        try {
            DateTimeFormatter dateTime = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            String date = dateTime.format(now);

        Documents newDoc = new Documents(
                docTitle,
                date,
                currentUser.get(),
                fileType,
                file.getBytes()
        );
        docRepository.save(newDoc);
        auditService.documentUploaded(username, newDoc.getId().intValue(), "user uploaded file to the database successfully");
        return new ReturnError(false);
        } catch(Exception e) {
            return new ReturnError(true, "issue-saving", "Issue during submission, please try again.");
        }
    }
}


