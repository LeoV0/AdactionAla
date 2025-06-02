package com.ada.adaction_ala.repository;

import com.ada.adaction_ala.model.Association;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

// import java.util.List;

@Repository
public interface AssociationRepository extends CrudRepository<Association, Long> {
    // List<Association> findByName(String name);
}