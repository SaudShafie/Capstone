package org.example.capstone1.Model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Merchant {
    @NotEmpty
    private String id;
    @NotEmpty
    @Size(min = 3,message = "The name of the Merchant should be more than three character")
    private String name;
}
