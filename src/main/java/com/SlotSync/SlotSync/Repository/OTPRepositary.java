package com.SlotSync.SlotSync.Repository;

import com.SlotSync.SlotSync.Entity.OTP;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface OTPRepositary extends MongoRepository<OTP, String> {

    Optional<OTP> findByEmail(String email);

    List<OTP> findAllByCreationTimeBefore(LocalDateTime creationTime);
} 
