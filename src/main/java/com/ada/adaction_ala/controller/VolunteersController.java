package com.ada.adaction_ala.controller;

import com.ada.adaction_ala.model.VolunteerRegisterRequest;
import com.ada.adaction_ala.model.ProfilUpdateRequest;
import com.ada.adaction_ala.model.Volunteers;
import com.ada.adaction_ala.service.VolunteersService;

import org.springframework.http.HttpStatus;
// import org.hibernate.mapping.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;



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

    @PutMapping("/{id}")
    public ResponseEntity<Volunteers> updateVolunteer(@PathVariable Long id, @RequestBody ProfilUpdateRequest updateRequest) {
        Optional<Volunteers> updatedVolunteer = volunteersService.updateVolunteer(id, updateRequest);
        return updatedVolunteer.map(ResponseEntity::ok)
                              .orElseGet(() -> ResponseEntity.notFound().build());
    }

     @PostMapping("/volunteers/login")
    public ResponseEntity<Volunteers> login(@RequestParam String firstname, @RequestParam String password) {
        Optional<Volunteers> volunteer = volunteersService.login(firstname, password);
        return volunteer.map(ResponseEntity::ok)
                       .orElseGet(() -> ResponseEntity.status(401).body(null));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Volunteers>> searchByLocation(@RequestParam String location) {
        List<Volunteers> results = volunteersService.findByLocation(location);
        return ResponseEntity.ok(results);
    }

    @PostMapping("/volunteers/import")
    public ResponseEntity<String> importVolunteers() {
        try {
            volunteersService.importVolunteersFromJson();
            return ResponseEntity.ok("Volunteers imported successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to import volunteers: " + e.getMessage());
        }
    }

    

    @PostMapping("/register")
    public ResponseEntity<Volunteers> registerVolunteer(@RequestBody VolunteerRegisterRequest registerRequest) {
        Optional<Volunteers> registeredVolunteer = volunteersService.registerVolunteer(registerRequest);
        if (registeredVolunteer.isPresent()) {
            return ResponseEntity.ok(registeredVolunteer.get());
        } else if (!registeredVolunteer.isPresent())  {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // 409 Conflict
        } else {
            return registeredVolunteer.map(ResponseEntity::ok)
                              .orElseGet(() -> ResponseEntity.badRequest().build());
            }
}

}