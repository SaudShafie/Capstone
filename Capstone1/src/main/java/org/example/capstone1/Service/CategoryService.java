package org.example.capstone1.Service;

import lombok.RequiredArgsConstructor;
import org.example.capstone1.Model.Category;
import org.example.capstone1.Model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final ProductService productService;
     private ArrayList<Category> categories =new ArrayList<>();
    public ArrayList<Category> getCategories(){
        return categories;
    }
    public boolean addCategory(Category category){
        for (Category x : categories){
            if (x.getId().equals(category.getId())){
                return false;
            }

        }
        categories.add(category);
        return true;
    }
    public boolean updateCategory(String id, Category category){
        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).getId().equals(id)){
                categories.set(i,category);
                return true;
            }
        }
        return false;
    }
    public boolean deleteCategory(String id){

        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).getId().equals(id)){
                categories.remove(i);
                return true;
            }
        }
        return false;
    }
    public boolean checkId(String id){
        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).getId().equals(id)){
                return true;
            }
        }
        return false;
    }
    public Category getById(String id){
        for (Category x: categories){
            if (x.getId().equals(id)){
                return x;
            }
        }
        return null;
    }
    public boolean checkName(String name){
        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).getName().equals(name)){
                return true;
            }
        }
        return false;
    }

    public ArrayList<Product> getProductsByCategoryName(String name) {
        String categoryId = "";
        for (Category category : categories) {
            if (category.getName().equals(name)) {
                categoryId = category.getId();
                break;
            }
        }
        if (categoryId.isEmpty()) {
            return new ArrayList<>();
        }
        ArrayList<Product> productsByCategory = new ArrayList<>();
        for (Product product : productService.getProducts()) {
            if (product.getCategoryId().equals(categoryId)) {
                productsByCategory.add(product);
            }
        }
        return productsByCategory;
    }
}
