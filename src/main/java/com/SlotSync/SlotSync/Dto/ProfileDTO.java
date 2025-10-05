
package com.SlotSync.SlotSync.Dto;

import java.util.List;

import com.SlotSync.SlotSync.Entity.Profile;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDTO {
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

  public Profile toEntity(){
    return new Profile(id, email, about, jobTitle, company, location, skills, experiences, certifications, educations);
  }

}
