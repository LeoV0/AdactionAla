package com.ada.adaction_ala.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.ada.adaction_ala.config.LocalDateTimeFromDateDeserializer;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "volunteers")
public class Volunteers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("firstname")
    @Column(nullable = false)
    private String vl_firstname;

    @JsonProperty("lastname")
    @Column(nullable = false)
    private String vl_lastname;

    @JsonProperty("email")
    @Column(nullable = false, unique = true)
    private String vl_email;

    @JsonProperty("username")
    @Column(nullable = false, unique = true)
    private String vl_username;

    @JsonProperty("password")
    @JsonIgnore
    @Column(nullable = false)
    private String vl_password;

    @JsonProperty("location")
    private String vl_location;

    @JsonProperty("created_at")
    @JsonDeserialize(using = LocalDateTimeFromDateDeserializer.class)
    @Column(name = "vl_created_at")
    private LocalDateTime vl_created_at;

    @JsonProperty("updated_at")
    @JsonDeserialize(using = LocalDateTimeFromDateDeserializer.class)
    @Column(name = "vl_updated_at")
    private LocalDateTime vl_updated_at;

    @JsonProperty("points")
    private Integer vl_points;

    @PrePersist
    protected void onCreate() {
        vl_created_at = LocalDateTime.now();
        vl_updated_at = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        vl_updated_at = LocalDateTime.now();
    }
}