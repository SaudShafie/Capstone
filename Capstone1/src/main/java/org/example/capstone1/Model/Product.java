package org.example.capstone1.Model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {
    @NotEmpty

    private String id;
    @NotEmpty
    @Size(min = 3,message = "The name of the Product should be more than three character")
    private String name;
    @Positive
    private double price;
    @NotEmpty
    private String categoryId;
    @Positive
    private double costPrice;

}
