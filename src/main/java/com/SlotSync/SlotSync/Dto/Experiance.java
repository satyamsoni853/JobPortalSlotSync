package com.SlotSync.SlotSync.Dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Experiance {
  private String title;
  private String company;
  private LocalDateTime startDate;
  private LocalDateTime endDate;
  private String description;
  private String location;

  
}
