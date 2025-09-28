package com.SlotSync.SlotSync.Entity;

import com.SlotSync.SlotSync.Dto.AccountType;
import com.SlotSync.SlotSync.Dto.UserDTO;

import org.springframework.data.annotation.Id; // FIX: use Spring Data Id for Mongo
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

// Suggestion: Use more specific annotations instead of @Data for better control
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "users")
public class User {
    @Id
    private String id;   // FIX: align with DTO (String)

    private String name;

    @Indexed(unique = true)
    private String email;

    private String password;

    private AccountType accountType;

    public UserDTO toDTO() {
        return new UserDTO(this.id, this.name, this.email, this.password, this.accountType);
    }
}
