package org.example.capstone1.Model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
    @NotEmpty
    private String id;
    @NotEmpty
    @Size(min = 5,message = "The username should be more than 5 character")
    private String username;
    @NotEmpty
    @Size(min = 6,message = "password should be at least 6 character")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-zA-Z])[a-zA-Z\\d]+$",message = "The password should have both digits and characters")
    private String password;
    @NotEmpty
    @Email
    private String email;
    @NotEmpty
    @Pattern(regexp = "^(Admin|Customer)$",message = "The role should be ether Admin or Customer")
    private String role;
    @Positive(message = "the balance should be a positive number")
    private double balance;

}
