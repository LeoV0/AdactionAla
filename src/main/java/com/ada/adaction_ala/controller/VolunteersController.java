package com.ada.adaction_ala.controller;

import com.ada.adaction_ala.model.Volunteers;
import com.ada.adaction_ala.service.VolunteersService;

// import org.hibernate.mapping.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Optional;
import java.util.List;


@RestController
@RequestMapping("/volunteers")
public class VolunteersController {

    private final VolunteersService volunteersService;

    public VolunteersController(VolunteersService volunteersService) {
        this.volunteersService = volunteersService;
    }

    @GetMapping("")
    public Iterable<Volunteers> getVolunteers() {
        return volunteersService.findAllVolunteers();
    }

    @GetMapping("/volunteers/{id}")
    public ResponseEntity<Volunteers> findVolunteersById(@PathVariable Long id) {
        Optional<Volunteers> volunteer = volunteersService.findVolunteersById(id);
        return volunteer.map(ResponseEntity::ok)
                        .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // @GetMapping("/volunteers/{vl__firstname}")
    // public ResponseEntity<Volunteers> getVolunteers() {
    //     Optional<Volunteers> volunteer = volunteersService.findAllVolunteers();
    //     return volunteer.map(ResponseEntity::ok)
    //                     .orElseGet(() -> ResponseEntity.notFound().build());
    // }

 

    @GetMapping("/search")
    public ResponseEntity<List<Volunteers>> searchByLocation(@RequestParam String location) {
        List<Volunteers> results = volunteersService.findByLocation(location);
        return ResponseEntity.ok(results);
    }

    // FirstName LastName etc..

    @PostMapping("/volunteers/import")
    public ResponseEntity<String> importVolunteers() {
        try {
            volunteersService.importVolunteersFromJson();
            return ResponseEntity.ok("Volunteers imported successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to import volunteers: " + e.getMessage());
        }
    }
}