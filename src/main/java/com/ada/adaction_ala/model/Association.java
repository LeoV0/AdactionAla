package com.ada.adaction_ala.model;


// import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
// import jakarta.persistence.Table;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@Entity
public class Association {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("name")
    private String ass_name;

    @JsonProperty("description")
    private String ass_description;

    @JsonProperty("points")
    private Integer ass_points;

    @JsonProperty("image")
    private String ass_image;



// Remplacé par @Data :

  // protected Association(){}

  // public Association(String ass_name, String ass_description, Number ass_points, String ass_image){
  //   this.ass_name= ass_name;
  //   this.ass_description = ass_description;
  //   this.ass_points = ass_points;
  //   this.ass_image = ass_image;
  // }

  // @Override
  // public String toString(){
  //   return String.format(
  //     "Association[id=%d, ass_name='%s', ass_description='%s', ass_points='%s', ass_image='%s'], id, ass_name, ass_description, ass_points, ass_image");
  // }

  // public Long getId(){
  //   return id;
  // }

  // public String getAssName(){
  //   return ass_name;
  // }

  // public String getAssDescription(){
  //   return ass_description;
  // }

  // public Number getAssPoints(){
  //   return ass_points;
  // }

  // public String getAssImage(){
  //   return ass_image;
  // }
}