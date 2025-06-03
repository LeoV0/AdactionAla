package com.ada.adaction_ala.repository;

import com.ada.adaction_ala.model.Volunteers;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface VolunteersRepository extends CrudRepository<Volunteers, Long> {
    List<Volunteers> findByVlLocationIgnoreCase(String location);
}