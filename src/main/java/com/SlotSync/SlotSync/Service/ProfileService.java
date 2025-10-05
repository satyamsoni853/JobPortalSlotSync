package com.SlotSync.SlotSync.Service;

import org.springframework.boot.autoconfigure.batch.BatchProperties.Job;

import com.SlotSync.SlotSync.Exception.JobPortalException;

public interface ProfileService {

  


  public Long createProfile(String email) throws JobPortalException;

  

  
  
}
