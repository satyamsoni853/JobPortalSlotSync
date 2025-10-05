package com.SlotSync.SlotSync.Entity;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.SlotSync.SlotSync.Dto.ProfileDTO;

import jakarta.annotation.Generated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "profiles")
public class Profile {

  @Id
  private Long id;

  private String email;
  private String about;
  private String jobTitle;
  private String company;
  private String location;
  private List<String> skills;
  private List<String> experiences;
  private List<String> certifications;
  private List<String> educations;

  public ProfileDTO toDto() {
    return new ProfileDTO(id, email, about, jobTitle, company, location, skills, experiences, certifications,
        educations);
  }

}
