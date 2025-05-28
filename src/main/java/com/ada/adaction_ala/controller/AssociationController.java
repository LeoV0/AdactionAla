package com.ada.adaction_ala.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RestController;
import com.ada.adaction_ala.model.Association;
import com.ada.adaction_ala.service.AssociationService;


@RestController
public class AssociationController {
  @Autowired
  private AssociationService associationService;

  @GetMapping("/associations")
  public Iterable<Association> getAssociations() {
    return associationService.getAssociation();
  }
  
}
