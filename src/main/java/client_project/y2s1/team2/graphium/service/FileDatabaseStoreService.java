package client_project.y2s1.team2.graphium.service;

import client_project.y2s1.team2.graphium.data.jpa.entities.Documents;
import client_project.y2s1.team2.graphium.data.jpa.repositories.DocumentsRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.Document;
import java.io.IOException;

@Service
public class FileDatabaseStoreService {
    @Autowired
    private DocumentsRepositoryJPA docRepository;

    public Documents store(MultipartFile file) throws IOException {
        Documents receivedDoc = new Documents("Test File", "test", file.getBytes());
        return docRepository.save(receivedDoc);
    }
}


