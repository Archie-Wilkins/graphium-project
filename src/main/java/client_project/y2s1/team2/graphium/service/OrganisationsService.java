package client_project.y2s1.team2.graphium.service;

import client_project.y2s1.team2.graphium.data.jpa.entities.Organisations;
import client_project.y2s1.team2.graphium.data.jpa.repositories.OrganisationsRepositoryJPA;
import client_project.y2s1.team2.graphium.domain.OrganisationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrganisationsService {
    OrganisationsRepositoryJPA orgsRepo;

    public OrganisationsService(OrganisationsRepositoryJPA anOrgRepo) {
        this.orgsRepo = anOrgRepo;
    }

    public List<OrganisationDTO> getAllOrganisationsAsDTOs(){
        List<Organisations> orgsListEntities = orgsRepo.findAll();
        List<OrganisationDTO> orgsListDTO = new ArrayList<>();

        for (Organisations orgs : orgsListEntities){
            orgsListDTO.add(orgs.toOrganisationDTO());
        }
        return orgsListDTO;
    }

}
