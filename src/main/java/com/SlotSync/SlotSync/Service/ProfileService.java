package com.SlotSync.SlotSync.Service;

import org.springframework.boot.autoconfigure.batch.BatchProperties.Job;

import com.SlotSync.SlotSync.Dto.ProfileDTO;
import com.SlotSync.SlotSync.Exception.JobPortalException;

public interface ProfileService {

  


  public Long createProfile(String email) throws JobPortalException;
  public ProfileDTO getProfile(Long id) throws JobPortalException;
  public ProfileDTO updateProfile( ProfileDTO profileDTO) throws JobPortalException;

  

  

  
  
}
