package com.SlotSync.SlotSync.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.SlotSync.SlotSync.Entity.Profile;

public interface ProfileRepository extends MongoRepository<Profile, Long> {

  

  
}
