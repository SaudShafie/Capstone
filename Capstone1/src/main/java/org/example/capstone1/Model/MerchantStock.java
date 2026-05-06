package org.example.capstone1.Model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MerchantStock {
    @NotEmpty
    private String id;
    @NotEmpty
    private String productId;
    @NotEmpty
    private String merchantId;
    @NotNull
    @Min(value = 10,message = "Stock should be at least 10 at the start ")
    private int stock;
    @NotNull
    @Min(value = 0,message = "sold Quantity mast be 0 at first!")
    @Max(value = 0,message = "sold Quantity mast be 0 at first!")
    private int soldQuantity;

}
