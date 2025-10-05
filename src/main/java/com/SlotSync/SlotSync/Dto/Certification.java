package com.SlotSync.SlotSync.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Certification {

  private String title;
  private String issuer;
  private String issueDate;

  private String credentialId;
  
}
