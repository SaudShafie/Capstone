package org.example.capstone1.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.capstone1.ApiResponse.ApiResponse;
import org.example.capstone1.Model.Product;
import org.example.capstone1.Service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/get-products")
    public ResponseEntity<?> getProducts(){
        ArrayList<Product> products= productService.getProducts();
        if (products.isEmpty()){
            return ResponseEntity.status(200).body(new ApiResponse("There is no products"));
        }
        return ResponseEntity.status(200).body(products);
    }
    @PostMapping("/add-product")
    public ResponseEntity<?> addProduct(@RequestBody @Valid Product product, Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        if (productService.addProduct(product)){
            return ResponseEntity.status(200).body(new ApiResponse("New product has been added"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("product id should be unique"));
    }
    @PutMapping("/update-product/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable String id,@RequestBody @Valid Product product, Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        if (productService.updateProduct(id,product)){
            return ResponseEntity.status(200).body(new ApiResponse("product has been updated"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("product id not found!"));
    }
    @DeleteMapping("/delete-product/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable String id){
        if (productService.deleteProduct(id)){
            return ResponseEntity.status(200).body(new ApiResponse("product has been deleted"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("product id not found!"));
    }

}
