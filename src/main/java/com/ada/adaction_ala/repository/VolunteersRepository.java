package com.ada.adaction_ala.repository;

import com.ada.adaction_ala.model.Volunteers;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface VolunteersRepository extends CrudRepository<Volunteers, Long> {
    @Query("SELECT v FROM Volunteers v WHERE v.vl_username = :username")
    Optional<Volunteers> findByVl_username(@Param("username") String vl_username);

     @Query("SELECT v FROM Volunteers v WHERE v.vl_firstname = :firstname")
    Optional<Volunteers> findByVl_firstname(@Param("firstname") String firstname);
}