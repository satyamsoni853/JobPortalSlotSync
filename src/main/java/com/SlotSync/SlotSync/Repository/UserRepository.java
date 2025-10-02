package com.SlotSync.SlotSync.Repository;

import com.SlotSync.SlotSync.Entity.User;        // your Mongo @Document
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    public Optional<User> findByEmail(String email);
}
