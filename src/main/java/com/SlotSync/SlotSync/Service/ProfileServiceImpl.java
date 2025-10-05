package com.SlotSync.SlotSync.Service;

import com.SlotSync.SlotSync.Dto.ProfileDTO;
import com.SlotSync.SlotSync.Entity.Profile;
import com.SlotSync.SlotSync.Exception.JobPortalException; // FIX: Add missing import
import com.SlotSync.SlotSync.Repository.ProfileRepository;

import com.SlotSync.SlotSync.UtilitiesFile.Utilities;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("profileService")
public class ProfileServiceImpl implements ProfileService {

  private final Utilities utilities;

  @Autowired
  private ProfileRepository profileRepository;

  ProfileServiceImpl(Utilities utilities) {
    this.utilities = utilities;
  }

  @Override
  public Long createProfile(String email) throws JobPortalException {
    // Implementation for creating a profile
    Profile profile = new Profile();
    profile.setId(utilities.getNextSequence("profile"));
    profile.setEmail(email);
    profile.setAbout("");
    profile.setSkills(new ArrayList<>());
    profile.setExperiences(new ArrayList<>());
    profile.setCertifications(new ArrayList<>());
    profile.setEducations(new ArrayList<>());
    profileRepository.save(profile);
    return profile.getId();

  }

  @Override
  public ProfileDTO getProfile(Long id) throws JobPortalException {

    return profileRepository.findById(id).orElseThrow(() -> new JobPortalException("PROFILE_NOT_FOUND")).toDto();
  }

  @Override
  public ProfileDTO updateProfile(ProfileDTO profileDTO) throws JobPortalException {
    profileRepository.findById(profileDTO.getId())
        .orElseThrow(() -> new JobPortalException("PROFILE_NOT_FOUND"));

    return profileRepository.save(profileDTO.toEntity()).toDto();
  }

}
