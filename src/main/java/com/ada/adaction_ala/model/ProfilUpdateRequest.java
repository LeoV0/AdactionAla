package com.ada.adaction_ala.model;

import lombok.Data;

@Data
public class ProfilUpdateRequest {
  private String firstname;
  private String password;
  private String location;
}
