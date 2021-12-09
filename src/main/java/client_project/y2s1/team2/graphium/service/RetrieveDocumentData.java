package client_project.y2s1.team2.graphium.service;

import client_project.y2s1.team2.graphium.data.jpa.entities.Documents;
import client_project.y2s1.team2.graphium.data.jpa.entities.Users;
import client_project.y2s1.team2.graphium.data.jpa.repositories.DocumentsRepositoryJPA;
import client_project.y2s1.team2.graphium.data.jpa.repositories.UsersRepositoryJPA;
import client_project.y2s1.team2.graphium.domain.DocumentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.util.List;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RetrieveDocumentData {
    private DocumentsRepositoryJPA docRepository;

    @Autowired
    UsersRepositoryJPA userRepo;

    @Autowired
    DocumentsRepositoryJPA docsRepo;

    public RetrieveDocumentData(DocumentsRepositoryJPA aDocRepo) {
        docRepository = aDocRepo;
    }

    public Optional<Documents> getDocumentByID(Long docID) {
        return docRepository.findById(docID);
    }

    public List<DocumentDTO> getAllDocsByAdminOrg(String userName){
        Optional<Users> userDetailsOptional =  userRepo.findByUsername(userName);
        Users userDetails = userDetailsOptional.get();
        String orgName = userDetails.getOrganisation().getName();
        List<Documents> allDocsByOrg = docsRepo.findAllByUser_Organisation_Name(orgName);

        ArrayList<DocumentDTO> allDocsByOrgDTOs = new ArrayList<>();
        for( Documents docs : allDocsByOrg){
            allDocsByOrgDTOs.add(docs.toDTO());
        }


        return allDocsByOrgDTOs;
    }

}
