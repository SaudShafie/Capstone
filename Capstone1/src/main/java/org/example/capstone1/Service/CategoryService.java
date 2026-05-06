package org.example.capstone1.Service;

import org.example.capstone1.Model.Category;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CategoryService {
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
}
