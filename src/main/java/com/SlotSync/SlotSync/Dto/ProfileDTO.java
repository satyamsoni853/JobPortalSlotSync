
package com.SlotSync.SlotSync.Dto;

import java.util.List;

import com.SlotSync.SlotSync.Entity.Profile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDTO {
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
    return new Profile(null, email, about, jobTitle, company, location, skills, experiences, certifications, educations);
  }

}
