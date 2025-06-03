package com.ada.adaction_ala.repository;

import com.ada.adaction_ala.model.Volunteers;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface VolunteersRepository extends CrudRepository<Volunteers, Long> {
    @Query("SELECT v FROM Volunteers v WHERE TRIM(LOWER(v.vl_location)) = LOWER(:location)")
    List<Volunteers> findByVlLocationIgnoreCase(@Param("location") String location);
}
