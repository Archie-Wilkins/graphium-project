package client_project.y2s1.team2.graphium.service;

import client_project.y2s1.team2.graphium.data.jpa.entities.Documents;
import client_project.y2s1.team2.graphium.data.jpa.repositories.DocumentsRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RetrieveDocumentData {
    private DocumentsRepositoryJPA docRepository;

    public RetrieveDocumentData(DocumentsRepositoryJPA aDocRepo) {
        docRepository = aDocRepo;
    }

    public Optional<Documents> getDocumentByID(Long docID) {
        return docRepository.findById(docID);
    }
}
