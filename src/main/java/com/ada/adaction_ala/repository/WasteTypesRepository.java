package com.ada.adaction_ala.repository;

import com.ada.adaction_ala.model.WasteTypes;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

// import java.util.List;

@Repository
public interface WasteTypesRepository extends CrudRepository<WasteTypes, Long> {
    // List<Association> findByName(String name);
}