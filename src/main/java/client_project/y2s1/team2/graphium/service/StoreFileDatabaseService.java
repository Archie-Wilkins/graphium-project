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
import java.util.Optional;

@Service
public class StoreFileDatabaseService {
    @Autowired
    private DocumentsRepositoryJPA docRepository;
    @Autowired
    private UsersRepositoryJPA userRepository;

    public SubmissionError storeFile(String docTitle, String username, String fileType, MultipartFile file, String docVisibility) throws IOException {
        Optional<Users> currentUser = userRepository.findByUsername(username);
        if (currentUser.isEmpty()) {
            return new SubmissionError(true, "User_not_found", "Error finding user from given username.");
        }
        Documents newDoc = new Documents(
                docTitle,
                currentUser.get(),
                fileType,
                docVisibility,
                file.getBytes()
        );
        if (docRepository.findByTitleAndUser(newDoc.getTitle(), currentUser.get()).isPresent()) {
            return new SubmissionError(true, "titles", "You already have a document with that title.");
        }
        docRepository.save(newDoc);
        return new SubmissionError(false);
    }
}


