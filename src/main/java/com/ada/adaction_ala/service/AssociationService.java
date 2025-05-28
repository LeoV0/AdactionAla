package com.ada.adaction_ala.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ada.adaction_ala.model.Association;
import com.ada.adaction_ala.repository.AssociationRepository;

import lombok.Data;

@Data
@Service
public class AssociationService {

  @Autowired
  private AssociationRepository associationRepository;

  public Optional<Association> getAssociation(final Long id){
    return associationRepository.findById(id);
  }

  public Iterable<Association> getAssociation(){
    return associationRepository.findAll();
  }

  public void deleteAssociation(final Long id){
    associationRepository.deleteById(id);
  }

  public Association saveAssociation(Association association){
    Association savedAssociation = associationRepository.save(association);
    return savedAssociation;
  }
  
}
