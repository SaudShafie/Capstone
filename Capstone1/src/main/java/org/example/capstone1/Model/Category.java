package org.example.capstone1.Model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Category {
    @NotEmpty

    private String id;
    @NotEmpty
    @Size(min = 3,message = "The name of the category should be more than three character")
    private String name;

}
