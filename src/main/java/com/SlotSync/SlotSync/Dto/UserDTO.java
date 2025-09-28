package com.SlotSync.SlotSync.Dto;

import com.SlotSync.SlotSync.Entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private String id;

    @NotBlank(message = "{user.name.absent}")
    private String name; // FIX: must be String for @NotBlank

    @NotBlank(message = "{user.email.absent}")
    @Email(message = "{user.email.invalid}")
    private String email;

    @NotBlank(message = "{user.password.absent}")
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d).{8,64}$",
            message = "{user.password.weak}"
    )
    private String password;

    private AccountType accountType;

    public User toEntity() {
        return new User(this.id, this.name, this.email, this.password, this.accountType);
    }
}
