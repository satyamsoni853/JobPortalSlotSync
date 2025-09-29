package com.SlotSync.SlotSync.Entity;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Data
@Document(collection = "otp")
@AllArgsConstructor
@NoArgsConstructor
public class OTP {

@Id
    private String email;
    private String otp;
    private LocalDateTime Creationtimestamp; // To track OTP expiry

}
