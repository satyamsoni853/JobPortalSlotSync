package com.SlotSync.SlotSync.Api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SlotSync.SlotSync.Dto.ProfileDTO;
import com.SlotSync.SlotSync.Exception.JobPortalException;
import com.SlotSync.SlotSync.Service.ProfileService;
import jakarta.validation.Valid;

@RestController
@CrossOrigin
@Validated
@RequestMapping("/profiles")
public class ProfileApi {
  @Autowired
  private ProfileService profileService;

  @GetMapping("/get/{id}")
  public ResponseEntity<ProfileDTO> getProfile(@PathVariable Long id) throws JobPortalException {
    ProfileDTO profileDTO = profileService.getProfile(id);
    return new ResponseEntity<>(profileDTO, HttpStatus.OK);
  }

  @PutMapping("/update")
  public ResponseEntity<ProfileDTO> updateProfile(@RequestBody @Valid ProfileDTO profileDTO) throws JobPortalException {
    ProfileDTO updatedProfile = profileService.updateProfile(profileDTO);
    return new ResponseEntity<>(updatedProfile, HttpStatus.OK);
  }

  
}
