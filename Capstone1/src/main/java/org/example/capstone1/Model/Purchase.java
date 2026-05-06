package org.example.capstone1.Model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Purchase {
    private String userId;
    private String productId;
    private String merchantId;
    private int quantity;
}
