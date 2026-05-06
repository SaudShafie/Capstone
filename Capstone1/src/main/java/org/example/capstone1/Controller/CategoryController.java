package org.example.capstone1.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.capstone1.ApiResponse.ApiResponse;
import org.example.capstone1.Model.Category;
import org.example.capstone1.Model.Product;
import org.example.capstone1.Service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")

public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/get-categories")
    public ResponseEntity<?> getCategories() {
        ArrayList<Category> categories = categoryService.getCategories();
        if (categories.isEmpty()) {
            return ResponseEntity.status(200).body(new ApiResponse("There is no categories"));
        }
        return ResponseEntity.status(200).body(categories);
    }

    @PostMapping("/add-category")
    public ResponseEntity<?> addCategories(@RequestBody @Valid Category category, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        if (categoryService.addCategory(category)) {
            return ResponseEntity.status(200).body(new ApiResponse("New category has been added"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Category id should be unique"));
    }

    @PutMapping("/update-category/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable String id, @RequestBody @Valid Category category, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        if (categoryService.updateCategory(id, category)) {
            return ResponseEntity.status(200).body(new ApiResponse("category has been updated"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Category id not found!"));
    }

    @DeleteMapping("/delete-category/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable String id) {
        if (categoryService.deleteCategory(id)) {
            return ResponseEntity.status(200).body(new ApiResponse("category has been deleted"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Category id not found!"));
    }
@GetMapping("/get-prodcuts-by-categoryName/{name}")
    public ResponseEntity<?> getByCategoryName(@PathVariable String name) {
        if (!categoryService.checkName(name))
            return ResponseEntity.status(400).body(new ApiResponse("Theres no category with this name"));
        ArrayList<Product> products = categoryService.getProductsByCategoryName(name);
        if (products.isEmpty()) {
            return ResponseEntity.status(200).body(new ApiResponse("No products found in this category"));
        }
        return ResponseEntity.status(200).body(products);
    }

}
